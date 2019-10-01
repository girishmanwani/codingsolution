package com.java8solution.codingsolution;

public class ResultPojo {

	private String id;
	private boolean alert;
	private String type;
	private String host;
	private long startTS;
	private long endTS;
	private long lapseTS;
	
	public long getStartTS() {
		return startTS;
	}
	public void setStartTS(long startTS) {
		this.startTS = startTS;
	}
	public long getEndTS() {
		return endTS;
	}
	public void setEndTS(long endTS) {
		this.endTS = endTS;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public long getLapseTS() {
		return lapseTS;
	}
	public void setLapseTS(long lapseTS) {
		this.lapseTS = lapseTS;
	}

}
