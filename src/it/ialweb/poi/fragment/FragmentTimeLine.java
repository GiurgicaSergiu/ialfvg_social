package it.ialweb.poi.fragment;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.myprofile.MyRecyclerAdapterUserProfile;
import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.poi.tweet.MyRecyclerAdapterTweet;
import it.ialweb.user.FragmentAllUsers;
import it.ialweb.user.MyRecyclerAdapterUser;
import it.ialweb.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTimeLine extends Fragment implements ChildEventListener {
	
	private List<MessageTweet> tweetItemList;

    private RecyclerView recyclerView;

    private MyRecyclerAdapterTweet adapter;

    private ChildEventListener followListener;
    
    private ChildEventListener tweetListener;

	public static FragmentTimeLine getInstance() {
		return new FragmentTimeLine();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_timeline, null);
		
		tweetItemList = new ArrayList<MessageTweet>();
		
	    recyclerView = new RecyclerView(getActivity());

	    recyclerView.setHasFixedSize(true);
	    
	    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
	    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	    layoutManager.setReverseLayout(true);
	    layoutManager.setStackFromEnd(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.list_padding_top));
		
		
		inizializedListener();
		if(DatabaseInstance.getInstance().isLogged()){
			DatabaseInstance.getInstance().getMyFollowing(followListener, DatabaseInstance.getInstance().getUserUid());
		}
		else {
			DatabaseInstance.getInstance().getTweets(this);
		}
		return recyclerView;
	}

	private void inizializedListener() {
		followListener = new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
				 Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
				    String uid = (String)value.get("uid");
				    String user = (String)value.get("email");
					DatabaseInstance.getInstance().getTweets(FragmentTimeLine.this,uid);

			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
	}

	@Override
	public void onCancelled(FirebaseError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
		 Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
		    Long date = (Long)value.get("date");
		    String tweet = (String)value.get("tweet");
		    String user = (String)value.get("uidUser");
		    String email = (String) value.get("email");
		    
		    MessageTweet messageTweet = new MessageTweet(tweet);
		    messageTweet.setUidUser(user);
		    messageTweet.setDate(date);
		    messageTweet.setEmail(email.split("@")[0].substring(0, 1).toUpperCase() + email.split("@")[0].substring(1));
			
		    tweetItemList.add(messageTweet);
		    
		    recyclerView.setAdapter(new MyRecyclerAdapterTweet(getActivity(), tweetItemList));
		/*for (DataSnapshot election : dataSnapshot.getChildren()) {
			 Map<String, Object> value = (Map<String, Object>)election.getValue();
		    Long date = (Long)value.get("date");
		    String tweet = (String)value.get("tweet");
		    String user = (String)value.get("uidUser");
		    
		    MessageTweet messageTweet = new MessageTweet(tweet, date);
		    messageTweet.setUidUser(user);
		    tweetItemList.add(messageTweet);
		}
		recyclerView.setAdapter(new MyRecyclerAdapterTweet(getActivity(), tweetItemList));*/
	}

	@Override
	public void onChildChanged(DataSnapshot arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildMoved(DataSnapshot arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildRemoved(DataSnapshot arg0) {
		// TODO Auto-generated method stub
		
	}
}