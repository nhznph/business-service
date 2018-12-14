package com.dxc.business.common;

import org.springframework.http.HttpStatus;

/**
 * Business Error for Proposal Data service
 */
public enum BusinessError {
	// @formatter:off
	/**
	 * Generic Error.
	 */
	UNEXPECTED(0, HttpStatus.INTERNAL_SERVER_ERROR),
	USER_DOES_NOT_ACTIVATE(1000, HttpStatus.BAD_REQUEST),
	USER_HAVE_NO_INVOICE(1001, HttpStatus.NOT_FOUND),
	TOTAL_MONEY_IS_LESS_THAN_LINMIT(1002, HttpStatus.BAD_REQUEST),
	COUNTRY_CODE_INVALID(1003, HttpStatus.BAD_REQUEST),
	NOT_FOUND(2000, HttpStatus.NOT_FOUND),
	MARKED_DELETE(2001, HttpStatus.BAD_REQUEST),
	ID_FIELD_EMPTY(2002, HttpStatus.BAD_REQUEST);
	// @formatter:on
	private final int code;
	private final HttpStatus httpStatus;

	/**
	 * Init the error with code and http status code.
	 *
	 * @param code       business error code.
	 * @param httpStatus http status code.
	 */
	BusinessError(int code, HttpStatus httpStatus) {
		this.code = code;
		this.httpStatus = httpStatus;
	}

	/**
	 * Get business error code.
	 *
	 * @return business error code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Get http status code.
	 *
	 * @return http status code.
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
