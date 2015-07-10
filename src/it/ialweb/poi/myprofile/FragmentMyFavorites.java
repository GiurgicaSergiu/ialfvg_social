package it.ialweb.poi.myprofile;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.fragment.FragmentTimeLine;
import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.poi.tweet.MyRecyclerAdapterTweet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public class FragmentMyFavorites extends Fragment implements ChildEventListener {
	
	private static final String USER_UID = "USER_UID";

	private List<MessageTweet> tweetItemList;

    private RecyclerView recyclerView;

    private MyRecyclerAdapterTweet adapter;


	public static FragmentMyFavorites getInstance(String uid) {
		FragmentMyFavorites fragment = new FragmentMyFavorites();
		Bundle bundle = new Bundle();
		bundle.putString(USER_UID, uid);
		fragment.setArguments(bundle);
		return fragment;
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
		
		DatabaseInstance.getInstance().getMyFavorites(this, getArguments().getString(USER_UID));

		return recyclerView;
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
			messageTweet.setEmail(email);
			
		    tweetItemList.add(messageTweet);
		    
		    recyclerView.setAdapter(new MyRecyclerAdapterFavorites(getActivity(), tweetItemList));
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