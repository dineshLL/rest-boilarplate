package org.pensions.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sms {

	private String to;
	private String message;
	
	public Sms() {
		// TODO Auto-generated constructor stub
	}

	public String getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
