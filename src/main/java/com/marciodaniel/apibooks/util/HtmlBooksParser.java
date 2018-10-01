package com.marciodaniel.apibooks.util;

import com.marciodaniel.apibooks.domain.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlBooksParser {

	private String body;

	private String targetElement;

	private String targetTagTitle;

	private String targetTagDescription;

	private String targetTagLinkForISBN;

	private String targetTagLanguage;

	private static final String HREF = "href";

	public HtmlBooksParser(String body) {
		this.body = body;
	}

	public HtmlBooksParser(String htmlBody, String targetElement, String targetTagTitle, String targetTagDescription, String targetTagLinkForISBN, String targetTagLanguage) {
		this.body = htmlBody;
		this.targetElement = targetElement;
		this.targetTagTitle = targetTagTitle;
		this.targetTagDescription = targetTagDescription;
		this.targetTagLinkForISBN = targetTagLinkForISBN;
		this.targetTagLanguage = targetTagLanguage;
	}

	public Document parse() {
		return Jsoup.parse(body);
	}

	public List<Node> getNodes() {
		Document document = parse();

		return document.getElementsByTag(targetElement).first().childNodes();
	}

	public ArrayList<Book> generateBooks() {

		var ref = new Object() {
			Book book = null;
		};

		var books = new ArrayList<Book>();

		getNodes().stream().filter(node -> node instanceof Element).forEach(node -> {
			Elements title = ((Element) node).getElementsByTag(targetTagTitle);
			Elements language = ((Element) node).getElementsByTag(targetTagLanguage);
			Elements linkForISBM = ((Element) node).getElementsByTag(targetTagLinkForISBN);
			Elements descriptionTags = ((Element) node).getElementsByTag(targetTagDescription);

			if (title.size() != 0) {
				if (ref.book != null) {
					books.add(ref.book);
					ref.book = null;
				}
				ref.book = new Book();
				ref.book.setTitle(title.text());
			}

			if (descriptionTags.size() != 0) {
				assert ref.book != null;
				var description = ref.book.getDescription();
				if (description != null) {
					description = description.concat("\r\n").concat(descriptionTags.text());
					ref.book.setDescription(description);
				} else {
					ref.book.setDescription(descriptionTags.text());
				}
			}

			if (linkForISBM.size() != 0 && descriptionTags.size() == 0) {
				assert ref.book != null;
				ref.book.setIsbn(linkForISBM.attr(HREF));
			}

			if (language.size() != 0) {
				assert ref.book != null;
				ref.book.setLanguage(language.text());
			}
		});

		if (ref.book != null)
			books.add(ref.book);

		return books;
	}
}
