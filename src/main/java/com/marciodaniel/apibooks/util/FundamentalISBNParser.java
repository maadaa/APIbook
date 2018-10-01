package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FundamentalISBNParser extends ISBMParser {

	private static final String DARK_BLUE_TEXT = "dark-blue-text";
	private static final String ISBN_DOT = "ISBN:";

	@Override
	public void parse(Document document, Book book) {
		Elements elementsByClass = document.getElementsByClass(DARK_BLUE_TEXT);

		elementsByClass.stream().filter(elementC -> elementC.text().contains(ISBN)).map(Element::text)
				.forEach(isbn -> book.setIsbn(isbn.replace(ISBN_DOT, BLANK_REPLACEMENT).strip()));
	}
}
