package com.pietro.blog_back_spring.exceptions;

import org.jspecify.annotations.Nullable;

public class BadRequestException extends RuntimeException {

	public BadRequestException(@Nullable String msg, Throwable cause) {
		super(msg, cause);
	}

	public BadRequestException(@Nullable String msg) {
		super(msg);
	}

}

