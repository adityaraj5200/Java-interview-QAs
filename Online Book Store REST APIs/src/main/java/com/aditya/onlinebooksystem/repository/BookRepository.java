package com.aditya.onlinebooksystem.repository;

import com.aditya.onlinebooksystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom queries can be added here in future if needed
}
