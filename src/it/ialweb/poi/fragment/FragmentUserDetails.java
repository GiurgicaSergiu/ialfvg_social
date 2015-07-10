package it.ialweb.poi.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import it.ialweb.poi.R;
import it.ialweb.poi.TransformEmail;
import it.ialweb.poi.MainActivity.PlaceHolder;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.poi.tweet.MyRecyclerAdapterTweet;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentUserDetails extends Fragment implements ChildEventListener {

	private static final String User = "user";

	public static FragmentUserDetails getInstance(String user) {
		FragmentUserDetails fragmentUserDetails = new FragmentUserDetails();
		Bundle bundle = new Bundle();
		bundle.putString(User, user);
		fragmentUserDetails.setArguments(bundle);
		return fragmentUserDetails;
	}

	private List<MessageTweet> tweetItemList;

	private RecyclerView recyclerView;

	private MyRecyclerAdapterTweet adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_details, null);
		

		tweetItemList = new ArrayList<MessageTweet>();

		recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

		recyclerView.setHasFixedSize(true);

		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		layoutManager.setReverseLayout(true);
		layoutManager.setStackFromEnd(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setPadding(0, 0, 0,
				(int) getResources().getDimension(R.dimen.list_padding_top));
		
		
		
		DatabaseInstance.getInstance().getMyTweets(this, getArguments().getString(User));

		return view;
	}

	@Override
	public void onCancelled(FirebaseError arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
		Map<String, Object> value = (Map<String, Object>) dataSnapshot
				.getValue();
		Long date = (Long) value.get("date");
		String tweet = (String) value.get("tweet");
		String user = (String) value.get("uidUser");
		String email = (String) value.get("email");

		MessageTweet messageTweet = new MessageTweet(tweet);
		messageTweet.setUidUser(user);
		messageTweet.setEmail(TransformEmail.getNameByEmail(email));
		tweetItemList.add(messageTweet);

		recyclerView.setAdapter(new MyRecyclerAdapterTweet(getActivity(),
				tweetItemList));
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
