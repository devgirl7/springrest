package com.app.models;

public class ResponseVO<T> {
	private T results;
	
	public ResponseVO() {
		
	}
	
	public ResponseVO(T results) {
		this.results = results;
	}
	
	public T getResults() {
		return this.results;
	}
}