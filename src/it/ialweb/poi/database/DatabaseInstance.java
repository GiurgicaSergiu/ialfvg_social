package it.ialweb.poi.database;


import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.user.User;

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
	
	public void saveUser(){
		User user = new User(getUserEmail(),myFirebaseRef.getAuth().getUid());
		myFirebaseRef.child("users/"+user.getUid()).setValue(user);
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
	
	public String getUserUid(){
		return myFirebaseRef.getAuth().getUid();
	}
	
	public String getUserEmail(){
		AuthData authData = myFirebaseRef.getAuth();
		return (String) authData.getProviderData().get("email");
	}

	public void tweet(MessageTweet tweet){
		tweet.setUidUser(myFirebaseRef.getAuth().getUid());
		tweet.setEmail(getUserEmail());
		Firebase firebaseTweet = myFirebaseRef.child("tweets");
		firebaseTweet.push().setValue(tweet);
	}
	
	public void getTweets(ChildEventListener listener,String uid){
		Firebase firebaseTweet = myFirebaseRef.child("tweets");
		
		Query queryRef = firebaseTweet.orderByChild("uidUser").equalTo(uid);
		
		queryRef.addChildEventListener(listener);
	}
	public void getTweets(ChildEventListener listener){
		Firebase firebaseTweet = myFirebaseRef.child("tweets");
		
		Query queryRef = firebaseTweet.orderByChild("date");
		
		queryRef.addChildEventListener(listener);
	}
	
	public void getMyTweets(ChildEventListener listener,String uid){
		Firebase firebaseTweet = myFirebaseRef.child("tweets");
		String s = uid;
		Query queryRef = firebaseTweet.orderByChild("uidUser").equalTo(s);
		
		queryRef.addChildEventListener(listener);
	}
	
	public void setFavorites(MessageTweet tweet){
		Firebase firebaseTweet = myFirebaseRef.child("Favorites/" + myFirebaseRef.getAuth().getUid());
		firebaseTweet.push().setValue(tweet);
	}
	
	public void getAllUsers(ChildEventListener listener){
		Firebase firebaseTweet = myFirebaseRef.child("users");
		
		Query queryRef = firebaseTweet.orderByChild("email");
		
		queryRef.addChildEventListener(listener);
	}
	
	public void saveFollow(User user){	
		myFirebaseRef.child("Follow/" + myFirebaseRef.getAuth().getUid() + "/" + user.getUid()).setValue(user);
	}
	public void saveFollowers(User user){
		User u = new User(getUserEmail(),myFirebaseRef.getAuth().getUid());
		myFirebaseRef.child("Followers/" + user.getUid()+ "/" + myFirebaseRef.getAuth().getUid()).setValue(u);
	}
	
	public void getMyFollowers(ChildEventListener listener,String uid){
		Firebase firebaseTweet = myFirebaseRef.child("Follow/"+uid);
		
		Query queryRef = firebaseTweet.orderByChild("email");
		
		queryRef.addChildEventListener(listener);
	}
	
	public void getMyFollowing(ChildEventListener listener,String uid){
		Firebase firebaseTweet = myFirebaseRef.child("Followers/"+uid);
		
		Query queryRef = firebaseTweet.orderByChild("email");
		
		queryRef.addChildEventListener(listener);
	}
	
	public void getMyFavorites(ChildEventListener listener, String uid){
		Firebase firebaseTweet = myFirebaseRef.child("Favorites/" + uid);
		
		Query queryRef = firebaseTweet.orderByChild("Date");
		
		queryRef.addChildEventListener(listener);
	}
}
