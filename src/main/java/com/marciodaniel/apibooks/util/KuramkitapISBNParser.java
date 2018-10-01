package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KuramkitapISBNParser extends ISBMParser {

	private static final String PRD_CUSTOM_FIELDS_0 = "prd_custom_fields_0";
	private static final String STOK_KODU = "Stok Kodu";
	private static final String TARGET = ":";

	@Override
	public void parse(Document document, Book book) {
		Elements elementsByClass = document.getElementsByClass(PRD_CUSTOM_FIELDS_0);

		elementsByClass.stream()
				.filter(elementC -> elementC.text().contains(STOK_KODU)).map(Element::text)
				.forEach(isbn -> book.setIsbn(isbn.replace(STOK_KODU, BLANK_REPLACEMENT).replace(TARGET, BLANK_REPLACEMENT).strip()));
	}
}
