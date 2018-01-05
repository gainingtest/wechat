package com.js.support.message;


public class Message {
	
	private boolean result;//结果
	private String info;//消息
	private Object object; 
	
	public Message(){
		
	}
	
	public Message(boolean reslut, String info){
		setInfo(info);
		setResult(reslut);
	}
	
	public Message(boolean reslut, String info, Object object){
		setInfo(info);
		setResult(reslut);
		setObject(object);
	}
	
	
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	

}
