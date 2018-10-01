package com.marciodaniel.apibooks.service.imp;

import com.marciodaniel.apibooks.domain.Book;
import com.marciodaniel.apibooks.domain.BookList;
import com.marciodaniel.apibooks.repository.BookRepository;
import com.marciodaniel.apibooks.service.BookService;
import com.marciodaniel.apibooks.util.AmazonISBMParser;
import com.marciodaniel.apibooks.util.FundamentalISBNParser;
import com.marciodaniel.apibooks.util.HtmlBooksParser;
import com.marciodaniel.apibooks.util.KuramkitapISBNParser;
import com.marciodaniel.apibooks.util.ManningISBMParser;
import com.marciodaniel.apibooks.util.PacktpubISBNParser;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {

	private static final String KOTLIN_BOOKS_HTML = "https://kotlinlang.org/docs/books.html";
	private static final String ARTICLE = "article";
	private static final String H_2 = "h2";
	private static final String P = "p";
	private static final String DIV = "div";
	private static final String A = "a";
	private static final String MANNING = "manning";
	private static final String AMAZON = "amazon";
	private static final String PACKTPUB = "packtpub";
	private static final String FUNDAMENTAL = "fundamental";
	private static final String KURAMKITAP = "kuramkitap";
	private static final String HTTPS = "https";

	private final BookRepository bookRepository;

	@Autowired
	public BookServiceImp(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> findById(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public BookList listAllBooks() {
		Optional<String> responseBody = bookRepository.getResponseBody(KOTLIN_BOOKS_HTML);

		List<Book> books = new ArrayList<>();

		responseBody.ifPresent(s -> {
			HtmlBooksParser booksParser = new HtmlBooksParser(s, ARTICLE, H_2, P, A, DIV);

			books.addAll(booksParser.generateBooks());
		});

		return generateBookList(books);
	}

	private BookList generateBookList(List<Book> books) {

		completeBooksFieldISBM(books);

		return new BookList(books);
	}

	private void completeBooksFieldISBM(List<Book> books) {
		books.parallelStream().forEach(book -> {
			Optional<String> responseBody = bookRepository.getResponseBody(book.getIsbn());

			responseBody.ifPresent(s -> {
				Document document = new HtmlBooksParser(s).parse();

				bookISBNParser(book, document);
			});
		});
	}

	private void bookISBNParser(Book book, Document document) {
		if (isContains(book, MANNING)) {
			new ManningISBMParser().parse(document, book);
		}

		if (isContains(book, AMAZON))
			new AmazonISBMParser().parse(document, book);

		if (isContains(book, PACKTPUB))
			new PacktpubISBNParser().parse(document, book);

		if (isContains(book, FUNDAMENTAL))
			new FundamentalISBNParser().parse(document, book);

		if (isContains(book, KURAMKITAP))
			new KuramkitapISBNParser().parse(document, book);

		if (isContains(book, HTTPS))
			book.setIsbn(null);
	}

	private boolean isContains(Book book, String manning) {
		return book.getIsbn().contains(manning);
	}
}
