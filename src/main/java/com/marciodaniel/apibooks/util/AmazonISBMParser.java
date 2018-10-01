package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonISBMParser extends ISBMParser {

	private static final String DETAIL_BULLETS = "detail-bullets";
	private static final String TD = "td";
	private static final String ISBN_13 = "ISBN-13:";
	private static final String TARGET = "-";

	@Override
	public void parse(Document document, Book book) {
		Element elementDetail = document.getElementById(DETAIL_BULLETS);

		if (elementDetail != null) {
			Elements td = elementDetail.getElementsByTag(TD);
			td.stream().map(elementTd -> elementTd.getElementsByTag(UL)).forEach(ul -> ul.forEach(elementUl -> {
				elementUl.childNodes().stream().filter(node -> node instanceof Element)
						.map(node -> ((Element) node).text()).filter(isbn -> isbn != null && isbn.contains(ISBN_13))
						.forEach(isbn -> book.setIsbn(isbn.replace(ISBN_13, BLANK_REPLACEMENT).replace(TARGET, BLANK_REPLACEMENT).strip()));
			}));
		}
	}
}
