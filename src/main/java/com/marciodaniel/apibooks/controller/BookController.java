package com.marciodaniel.apibooks.controller;

import com.marciodaniel.apibooks.domain.Book;
import com.marciodaniel.apibooks.domain.BookList;
import com.marciodaniel.apibooks.exceptions.SearchNotFoundException;
import com.marciodaniel.apibooks.service.BookService;
import com.marciodaniel.apibooks.service.imp.BookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/books", consumes = "application/json")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookServiceImp bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity saveBook(@Valid @RequestBody Book book, UriComponentsBuilder builder) {
		bookService.save(book);

		return ResponseEntity.created(builder.path("/books/{id}").buildAndExpand(book.getId()).toUri())
				.contentType(MediaType.APPLICATION_JSON).build();
	}

	@GetMapping("/{id:[\\d]+}")
	public ResponseEntity<Book> findBookById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.FOUND).contentType(MediaType.APPLICATION_JSON)
				.body(bookService.findById(id).orElseThrow(() -> new SearchNotFoundException("Not found any Book")));
	}

	@GetMapping
	public ResponseEntity<BookList> listBooks() {
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bookService.listAllBooks());
	}
}
