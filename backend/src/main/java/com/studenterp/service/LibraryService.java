package com.studenterp.service;

import com.studenterp.entity.*;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookIssueRepository bookIssueRepository;
    private final StudentRepository studentRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    public Book createBook(Book book) { return bookRepository.save(book); }
    public Book updateBook(Long id, Book updated) {
        Book book = getBookById(id);
        book.setIsbn(updated.getIsbn());
        book.setTitle(updated.getTitle());
        book.setAuthor(updated.getAuthor());
        book.setPublisher(updated.getPublisher());
        book.setCategory(updated.getCategory());
        return bookRepository.save(book);
    }
    public void deleteBook(Long id) { bookRepository.deleteById(id); }

    public BookCopy addCopy(BookCopy copy) {
        Book book = getBookById(copy.getBook().getId());
        copy.setBook(book);
        return bookCopyRepository.save(copy);
    }

    public List<BookCopy> getCopies(Long bookId) { return bookCopyRepository.findByBookId(bookId); }

    public BookIssue issueBook(Long copyId, Long studentId) {
        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new ResourceNotFoundException("BookCopy", "id", copyId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        if (!"AVAILABLE".equals(copy.getAvailabilityStatus())) {
            throw new IllegalArgumentException("Book copy is not available");
        }
        copy.setAvailabilityStatus("ISSUED");
        bookCopyRepository.save(copy);

        BookIssue issue = BookIssue.builder()
                .student(student)
                .bookCopy(copy)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .status("ISSUED")
                .build();
        return bookIssueRepository.save(issue);
    }

    public BookIssue returnBook(Long issueId) {
        BookIssue issue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("BookIssue", "id", issueId));
        issue.setReturnDate(LocalDate.now());
        issue.setStatus("RETURNED");
        bookIssueRepository.save(issue);

        BookCopy copy = issue.getBookCopy();
        copy.setAvailabilityStatus("AVAILABLE");
        bookCopyRepository.save(copy);
        return issue;
    }

    public List<BookIssue> getIssuedBooks() { return bookIssueRepository.findByStatus("ISSUED"); }
    public List<BookIssue> getStudentIssues(Long studentId) { return bookIssueRepository.findByStudentId(studentId); }
    public List<BookIssue> getOverdueBooks() {
        return bookIssueRepository.findByStatus("ISSUED").stream()
                .filter(i -> i.getDueDate().isBefore(LocalDate.now()))
                .toList();
    }
}
