package com.dxc.business.advise;

import com.dxc.business.common.BusinessError;
import com.dxc.business.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Advice if there is any exception occurs in the rest API. It will doTransform exception into the http status code and API's error message.
 *
 * @author hpham21
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * Handle the java generic {@link Exception}.
	 *
	 * @param ex java generic {@link Exception}
	 * @return ResponseEntity with message "Un-expected error. Please contact your administrator." and INTERNAL_SERVER_ERROR
	 */
	@ExceptionHandler(Exception.class)
    ResponseEntity<String> exceptionHandler(Exception ex) {
		LOGGER.error("Un-expected error. Please contact your administrator.", ex);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		return new ResponseEntity<>(messageSourceAccessor.getMessage(BusinessError.UNEXPECTED.name()),
				httpHeaders, BusinessError.UNEXPECTED.getHttpStatus());
	}

	/**
	 * Handle the {@link BusinessException}
	 *
	 * @param ex {@link BusinessException}
	 * @return ResponseEntity with {@link BusinessError} specified by the exception.
	 */
	@ExceptionHandler(BusinessException.class)
    ResponseEntity<String> posStorageRestException(BusinessException ex) {
		BusinessError response = ex.getResponse();
		String msgCode = response.name();
		String message = messageSourceAccessor.getMessage(msgCode, ex.getParameters().toArray(), msgCode);
		LOGGER.info(message, ex.getCause() == null ? ex : ex.getCause());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		return new ResponseEntity<>(message, httpHeaders, response.getHttpStatus());
	}


}
