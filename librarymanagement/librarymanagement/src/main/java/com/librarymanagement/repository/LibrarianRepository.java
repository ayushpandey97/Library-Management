package com.librarymanagement.repository;

import com.librarymanagement.model.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    List<Librarian> findByNameContainingIgnoreCase(String name);
}
