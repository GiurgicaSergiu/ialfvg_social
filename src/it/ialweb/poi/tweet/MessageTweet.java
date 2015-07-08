package it.ialweb.poi.tweet;

import java.util.Calendar;


public class MessageTweet {
	
	private String tweet;
	private String uidUser;
	private long date;
	
	public MessageTweet(String tweet) {
		this.tweet = tweet;
		this.date = Calendar.getInstance().getTimeInMillis();
	}

	public String getTweet() {
		return tweet;
	}

	public String getUidUser() {
		return uidUser;
	}

	public long getDate() {
		return date;
	}

	public void setUidUser(String uidUser) {
		this.uidUser = uidUser;
	}
	

}
