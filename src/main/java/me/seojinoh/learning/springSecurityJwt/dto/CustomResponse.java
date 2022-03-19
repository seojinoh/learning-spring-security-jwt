package me.seojinoh.learning.springSecurityJwt.dto;

public class CustomResponse {

	private int		status;
	private String	message;
	private Object	data;

	public CustomResponse() {
	}

	public CustomResponse(int status) {
		this.status = status;
	}

	public CustomResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public CustomResponse(int status, Object data) {
		this.status = status;
		this.data = data;
	}

	public CustomResponse(int status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
