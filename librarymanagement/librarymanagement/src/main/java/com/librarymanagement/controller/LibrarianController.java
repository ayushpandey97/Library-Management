package com.librarymanagement.controller;

import com.librarymanagement.model.Book;
import com.librarymanagement.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/librarians")
public class LibrarianController {

    @Autowired
    private LibrarianService librarianService;

    // Endpoint to accept a borrow request
    @PostMapping("/borrow")
    public ResponseEntity<String> acceptBorrowRequest(@RequestParam Long bookId, @RequestParam Long userId) {
        String response = librarianService.acceptBorrowRequest(bookId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Endpoint to return a borrowed book
    @PostMapping("/return/{borrowedBookId}")
    public ResponseEntity<String> returnBorrowedBook(@PathVariable Long borrowedBookId) {
        String response = librarianService.returnBorrowedBook(borrowedBookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Endpoint to add a new book
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = librarianService.addBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // Endpoint to update book details
    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book bookDetails) {
        Book updatedBook = librarianService.updateBook(bookId, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // Endpoint to delete a book
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        librarianService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint to get all books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = librarianService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Endpoint to search books by title
    @GetMapping("/books/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = librarianService.searchBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
