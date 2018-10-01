package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ManningISBMParser extends ISBMParser {

	private static final String PRODUCT_INFO = "product-info";

	@Override
	public void parse(Document document, Book book) {
		Elements elementsByClass = document.getElementsByClass(PRODUCT_INFO);

		elementsByClass.stream().map(elementC -> elementC.getElementsByTag(UL))
				.forEach(ul -> ul.forEach(elementUl -> {
					elementUl.childNodes().stream().filter(node -> node instanceof Element)
							.map(node -> ((Element) node).text()).filter(isbn -> isbn != null && isbn.contains(ISBN))
							.forEach(isbn -> book.setIsbn(isbn.replace(ISBN, BLANK_REPLACEMENT).strip()));
				}));
	}
}
