package com.marciodaniel.apibooks.service;

import com.marciodaniel.apibooks.domain.Book;
import com.marciodaniel.apibooks.domain.BookList;

import java.util.Optional;

public interface BookService {

	Book save(Book book);

	Optional<Book> findById(Long id);

	BookList listAllBooks();
}
