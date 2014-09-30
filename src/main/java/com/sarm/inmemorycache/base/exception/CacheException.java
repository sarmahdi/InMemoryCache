/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sarm.inmemorycache.base.exception;

/**
 *
 * @author sarm
 */
public class CacheException extends BaseException {
  

   // Class constructors
	/**
	 * Default constructor.  Constructs a new CacheException with null
	 * as its error message string.
	 */
	public CacheException() {
		// Call base class constructor
		super();
	}

	/**
	 * Constructs the object. Constructs a new CacheException with
	 * the specified error message.
	 *
	 * @param message the error message
	 */
	public CacheException(String message) {
		// Call base class constructor
		super(message);
	}

	/**
	 * Constructs the object. Constructs a new CacheException with
	 * the specified error message and nested exception.
	 *
	 *
	 * @param message the error message
	 * @param cause the nested exception
	 */
	public CacheException(String message, Throwable cause) {
		// Call base class constructor
		super(message);
		// Set the cause variable
		this.setCause(cause);
	}

	/**
	 * Constructs the object. Constructs a new CacheException with
	 * the specified error message.
	 *
	 * @param cause the nested exception
	 */
	public CacheException(Throwable cause) {
		// Call base class constructor
		this.setCause(cause);
	}

}
