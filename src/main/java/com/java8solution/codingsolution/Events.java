package com.java8solution.codingsolution;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Events {

	private String eventid;
    
    private int duration;
     
	private String type;
    
    private String host;
    
    private boolean alert;

    public Events(String eventid, int duration, String type, String host, boolean alert) {
		super();
		this.eventid = eventid;
		this.duration = duration;
		this.type = type;
		this.host = host;
		this.alert = alert;
	}

    
    public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

}