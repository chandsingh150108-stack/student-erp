package com.studenterp.controller;

import com.studenterp.entity.Room;
import com.studenterp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> findAll() { return ResponseEntity.ok(roomService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long id) { return ResponseEntity.ok(roomService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> create(@RequestBody Room room) { return ResponseEntity.ok(roomService.create(room)); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room room) { return ResponseEntity.ok(roomService.update(id, room)); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) { roomService.delete(id); return ResponseEntity.ok().build(); }
}
