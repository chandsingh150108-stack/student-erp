package com.studenterp.service;

import com.studenterp.entity.Notification;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findByUser(Long userId) { return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId); }
    public Long countUnread(Long userId) { return notificationRepository.countByUserIdAndIsReadFalse(userId); }

    public Notification create(Notification notification) { return notificationRepository.save(notification); }

    public Notification markAsRead(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        n.setIsRead(true);
        return notificationRepository.save(n);
    }
}
