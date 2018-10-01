package com.marciodaniel.apibooks.repository;

import com.marciodaniel.apibooks.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	default Optional<String> getResponseBody(String url) {
		ResponseEntity<String> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
		return responseEntity.getStatusCode().is2xxSuccessful() ? Optional.ofNullable(responseEntity.getBody()) : Optional.empty();
	}
}
