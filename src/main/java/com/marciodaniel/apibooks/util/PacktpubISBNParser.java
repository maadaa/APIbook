package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PacktpubISBNParser extends ISBMParser {

	private static final String BOOK_INFO_ISBN_13 = "book-info-isbn13";
	private static final String ITEMPROP = "itemprop";
	private static final String ISBN_LOWER = "isbn";

	@Override
	public void parse(Document document, Book book) {
		Elements elementsByClass = document.getElementsByClass(BOOK_INFO_ISBN_13);

		elementsByClass.stream().map(elementC -> elementC.getElementsByAttributeValue(ITEMPROP, ISBN_LOWER))
				.forEach(element1 -> book.setIsbn(element1.text().strip()));
	}
}
