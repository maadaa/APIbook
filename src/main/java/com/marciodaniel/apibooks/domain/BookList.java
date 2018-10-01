package com.marciodaniel.apibooks.domain;

import java.util.List;

public class BookList {

	private int numberBooks;

	private List<Book> books;

	public BookList(List<Book> books) {
		this.books = books;
	}

	public int getNumberBooks() {
		return books.size();
	}

	public List<Book> getBooks() {
		return books;
	}

	public void add(Book book) {
		books.add(book);
	}
}
