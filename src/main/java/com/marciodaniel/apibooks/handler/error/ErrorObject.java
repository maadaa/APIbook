package com.marciodaniel.apibooks.handler.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorObject {

	private final String message;
	private final String field;
	private final Object parameter;
}
