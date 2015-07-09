package it.ialweb.user;

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

public class FragmentAllUsers extends Fragment implements ChildEventListener {
	
	private List<User> userItemList;

    private RecyclerView recyclerView;

	public static FragmentAllUsers getInstance() {
		return new FragmentAllUsers();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_timeline, null);
		
		userItemList = new ArrayList<User>();
		
	    recyclerView = new RecyclerView(getActivity());

	    recyclerView.setHasFixedSize(true);
	    
	    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
	    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	    layoutManager.setReverseLayout(true);
	    layoutManager.setStackFromEnd(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.list_padding_top));
		
		DatabaseInstance.getInstance().getAllUsers(this);

		return recyclerView;
	}

	@Override
	public void onCancelled(FirebaseError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
		
		 Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
		    String uid = (String)value.get("uid");
		    String user = (String)value.get("email");
		    
		    User u = new User(user, uid);
		    userItemList.add(u);
		    
		    recyclerView.setAdapter(new MyRecyclerAdapterUser(getActivity(), userItemList));
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
