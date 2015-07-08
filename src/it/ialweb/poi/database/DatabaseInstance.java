package it.ialweb.poi.database;


import it.ialweb.poi.tweet.MessageTweet;

import java.util.Map;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.Firebase.ValueResultHandler;
import com.firebase.client.Query;

public class DatabaseInstance {
	
	private Firebase myFirebaseRef = new Firebase("https://baassocial.firebaseio.com/");
	
	private static DatabaseInstance databaseInstance;
	
	private DatabaseInstance() {
	
	}
	
	public static DatabaseInstance getInstance(){
		if(databaseInstance==null)
			databaseInstance = new DatabaseInstance();
		return databaseInstance;
	}

	public Firebase getMyFirebaseRef() {
		return myFirebaseRef;
	}
	
	public void createUser(String userEmail,String userPassword,ValueResultHandler<Map<String, Object>> handler){
		myFirebaseRef.createUser(userEmail, userPassword, handler);
	}
	
	public void login(String userEmail,String userPassword,AuthResultHandler handler){
		myFirebaseRef.authWithPassword(userEmail, userPassword, handler);
	}
	
	public boolean isLogged(){
		return myFirebaseRef.getAuth()==null?false:true;
	}
	
	public void logout(){
		myFirebaseRef.unauth();
	}
	
	public String getUserEmail(){
		AuthData authData = myFirebaseRef.getAuth();
		return (String) authData.getProviderData().get("email");
	}

	public void tweet(MessageTweet tweet){
		tweet.setUidUser(myFirebaseRef.getAuth().getUid());
		Firebase firebaseTweet = myFirebaseRef.child("tweets");
		firebaseTweet.push().setValue(tweet);
	}
	
	public void getTweets(ChildEventListener listener){
		Firebase firebaseTweet = myFirebaseRef.child("tweets");
		
		Query queryRef = firebaseTweet.orderByChild("Date");
		
		queryRef.addChildEventListener(listener);
	}
}
