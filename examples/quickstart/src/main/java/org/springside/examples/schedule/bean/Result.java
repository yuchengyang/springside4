package org.springside.examples.schedule.bean;

public class Result {
	boolean success;
	String msg;
	
	public Result() {
		super();
	}
	
	public Result(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
