package com.librarymanagement.service;

import com.librarymanagement.model.Book;
import com.librarymanagement.model.BorrowedBook;
import com.librarymanagement.model.Librarian;
import com.librarymanagement.model.User;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedBookRepository;
import com.librarymanagement.repository.LibrarianRepository;
import com.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibrarianService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    // Method to accept a borrow request
    public String acceptBorrowRequest(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (book.getAvailableCopies() > 0) {
            BorrowedBook borrowedBook = new BorrowedBook(book, user, LocalDate.now());
            borrowedBookRepository.save(borrowedBook);

            // Decrement available copies
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);

            return "Borrow request accepted for book: " + book.getTitle();
        } else {
            return "No available copies for book: " + book.getTitle();
        }
    }

    // Method to return a borrowed book
    public String returnBorrowedBook(Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new RuntimeException("Borrowed book record not found"));

        // Logic to update the book and user records
        Book book = borrowedBook.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        borrowedBookRepository.delete(borrowedBook);
        return "Book returned: " + book.getTitle();
    }

    // Method to add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Method to update book details
    public Book updateBook(Long bookId, Book bookDetails) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        // Update available copies if needed
        book.setAvailableCopies(bookDetails.getAvailableCopies());
        return bookRepository.save(book);
    }

    // Method to delete a book
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    // Method to get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Method to search books by title
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
