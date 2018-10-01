package com.marciodaniel.apibooks.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "Book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "title")
	private String title;

	@NotBlank
	@Column(name = "description")
	private String description;

	@NotBlank
	@JsonAlias("ISBN")
	@JsonProperty("ISBN")
	@Column(name = "isbn")
	private String isbn;

	@NotBlank
	@Column(name = "language")
	private String language;

	public Long getId() {
		if (id == null)
			id = ((Double) (Math.random() * (Math.pow(10, 4)))).longValue();

		return id;
	}

	public String getIsbn() {
		if (isbn == null)
			isbn = "Unavailable";

		return isbn;
	}
}
