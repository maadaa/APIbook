package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.nodes.Document;

public abstract class ISBMParser {

	protected static final String UL = "ul";
	protected static final String ISBN = "ISBN";
	protected static final String BLANK_REPLACEMENT = "";

	public abstract void parse(Document document, Book book);
}
