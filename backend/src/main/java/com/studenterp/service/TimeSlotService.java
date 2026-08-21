package com.studenterp.service;

import com.studenterp.entity.TimeSlot;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public List<TimeSlot> findAll() { return timeSlotRepository.findAll(); }
    public TimeSlot findById(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", id));
    }
    public TimeSlot create(TimeSlot timeSlot) { return timeSlotRepository.save(timeSlot); }
    public void delete(Long id) { timeSlotRepository.deleteById(id); }
}
