package com.sgdc.cms.repositories;

import com.sgdc.cms.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
