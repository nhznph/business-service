package com.dxc.business.exception;

import com.dxc.business.common.BusinessError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom exception for Proposal Gen service
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1491607362490161046L;
	private final BusinessError response;
	private final transient List<Object> parameters = new ArrayList<>();

	/**
	 * Constructs a new {@code ProposalGenException} with the specified detail message. The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param response NumberGeneratorRestError
	 */
	public BusinessException(BusinessError response) {
		this(response, null, new Object[0]);
	}

	/**
	 * Constructs a new {@code ProposalGenException} with the specified detail message including parameters. The cause is not initialized, and may subsequently
	 * be initialized by a call to {@link #initCause}.
	 *
	 * @param response Sensor flow response * @param params list of parameters.
	 */
	public BusinessException(BusinessError response, Object... params) {
		this(response, null, params);
	}

	/**
	 * Construct the {@code ProposalGenException} with specified {@link BusinessError}, the cause {@link Throwable} and the list of parameters.
	 *
	 * @param response Rest error response.
	 * @param cause    root cause of this exception.
	 * @param params   list of parameters.
	 */
	public BusinessException(BusinessError response, Throwable cause, Object... params) {
		super(response.name() + Arrays.stream(params).map(Object::toString).collect(Collectors.joining(",", "[", "]")), cause);
		this.response = response;
		Collections.addAll(this.parameters, params);
	}

	/**
	 * @return
	 */
	public List<Object> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	/**
	 * @param param
	 */
	public void addParameters(Object param) {
		this.parameters.add(param);
	}

	/**
	 * @return
	 */
	public BusinessError getResponse() {
		return response;
	}
}
