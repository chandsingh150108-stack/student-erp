package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final StudentRepository studentRepository;

    public List<Event> findAll() { return eventRepository.findAll(); }
    public List<Event> findActive() { return eventRepository.findByActiveTrueOrderByStartDateAsc(); }
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    }
    public Event create(Event event) { return eventRepository.save(event); }
    public Event update(Long id, Event updated) {
        Event event = findById(id);
        event.setName(updated.getName());
        event.setDescription(updated.getDescription());
        event.setStartDate(updated.getStartDate());
        event.setEndDate(updated.getEndDate());
        event.setVenue(updated.getVenue());
        event.setRegistrationDeadline(updated.getRegistrationDeadline());
        event.setActive(updated.getActive());
        return eventRepository.save(event);
    }
    public void delete(Long id) { eventRepository.deleteById(id); }

    public EventRegistration register(Long eventId, Long studentId) {
        Event event = findById(eventId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        if (eventRegistrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            throw new IllegalArgumentException("Already registered for this event");
        }
        EventRegistration reg = EventRegistration.builder().student(student).event(event).build();
        return eventRegistrationRepository.save(reg);
    }

    public List<EventRegistration> getRegistrations(Long eventId) {
        return eventRepository.findById(eventId).isPresent() ?
                eventRegistrationRepository.findByStudentId(eventId) : List.of();
    }

    public List<EventRegistration> getStudentRegistrations(Long studentId) {
        return eventRegistrationRepository.findByStudentId(studentId);
    }
}
