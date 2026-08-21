package com.studenterp.controller;

import com.studenterp.entity.*;
import com.studenterp.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() { return ResponseEntity.ok(libraryService.getAllBooks()); }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) { return ResponseEntity.ok(libraryService.getBookById(id)); }

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> createBook(@RequestBody Book book) { return ResponseEntity.ok(libraryService.createBook(book)); }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) { return ResponseEntity.ok(libraryService.updateBook(id, book)); }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) { libraryService.deleteBook(id); return ResponseEntity.ok().build(); }

    @PostMapping("/books/{bookId}/copies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookCopy> addCopy(@PathVariable Long bookId, @RequestBody BookCopy copy) {
        copy.setBook(Book.builder().id(bookId).build());
        return ResponseEntity.ok(libraryService.addCopy(copy));
    }

    @GetMapping("/books/{bookId}/copies")
    public ResponseEntity<List<BookCopy>> getCopies(@PathVariable Long bookId) { return ResponseEntity.ok(libraryService.getCopies(bookId)); }

    @PostMapping("/issue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookIssue> issueBook(@RequestParam Long copyId, @RequestParam Long studentId) {
        return ResponseEntity.ok(libraryService.issueBook(copyId, studentId));
    }

    @PostMapping("/return/{issueId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookIssue> returnBook(@PathVariable Long issueId) { return ResponseEntity.ok(libraryService.returnBook(issueId)); }

    @GetMapping("/issued")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookIssue>> getIssuedBooks() { return ResponseEntity.ok(libraryService.getIssuedBooks()); }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<BookIssue>> getStudentIssues(@PathVariable Long studentId) { return ResponseEntity.ok(libraryService.getStudentIssues(studentId)); }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookIssue>> getOverdueBooks() { return ResponseEntity.ok(libraryService.getOverdueBooks()); }
}
