package com.marciodaniel.apibooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marciodaniel.apibooks.domain.Book;
import com.marciodaniel.apibooks.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class BookControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		Book book = new Book();
		book.setTitle("Book title example");
		book.setDescription("Book description example");
		book.setIsbn("9781617293290");
		book.setLanguage("BR");

		this.bookRepository.save(book);
	}

	@Test
	public void testFindIdNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/books/3"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void testErrorBadRequest() throws Exception {
		Book book = new Book();
		book.setLanguage("BR");
		book.setTitle("Teste Para Erro");
		mockMvc.perform(MockMvcRequestBuilders.post("/books", book))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void testPostBook() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();

		objectNode.put("title", "Book title example");
		objectNode.put("description", "Book description example");
		objectNode.put("ISBN", "9781617293290");
		objectNode.put("language", "BR");

		mockMvc.perform(MockMvcRequestBuilders.post("/books")
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(objectNode)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.header().exists("Location"));
	}

	@Test
	public void testFindBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("title").value("Book title example"))
				.andExpect(MockMvcResultMatchers.jsonPath("description").value("Book description example"))
				.andExpect(MockMvcResultMatchers.jsonPath("ISBN").value("9781617293290"))
				.andExpect(MockMvcResultMatchers.jsonPath("language").value("BR"));
	}
}
