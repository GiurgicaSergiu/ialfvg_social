package it.ialweb.user;

import it.ialweb.poi.R;
import it.ialweb.poi.TransformEmail;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.fragment.FragmentTimeLine;
import it.ialweb.poi.myprofile.ActivityMyProfile;
import it.ialweb.poi.myprofile.MyRecyclerAdapterUserProfile;
import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.poi.tweet.MyRecyclerAdapterTweet;
import it.ialweb.user.MyRecyclerAdapterUser.LaunchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
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

public class FragmentAllUsers extends Fragment implements ChildEventListener,LaunchActivity {
	
	private static final String TO_DO = "to_to";

	private static final String USER = "user_all";

	private List<User> userItemList;

    private RecyclerView recyclerView;
    
    private LoadUser loadUser;

	public static FragmentAllUsers getInstance(LoadUser toDo,String user) {
		FragmentAllUsers fragmentAllUsers = new FragmentAllUsers();
		
		Bundle bundle = new Bundle();
		bundle.putSerializable(TO_DO, toDo);
		bundle.putString(USER, user);
		fragmentAllUsers.setArguments(bundle);
		return fragmentAllUsers;
	}
	
	public static FragmentAllUsers getInstance(LoadUser toDo) {
		FragmentAllUsers fragmentAllUsers = new FragmentAllUsers();
		
		Bundle bundle = new Bundle();
		bundle.putSerializable(TO_DO, toDo);
		fragmentAllUsers.setArguments(bundle);
		return fragmentAllUsers;
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
		
		loadUser = (LoadUser) getArguments().getSerializable(TO_DO);
		
		switch (loadUser) {
		case ALLUSER:
			DatabaseInstance.getInstance().getAllUsers(this);
			break;
		case MYFOLLOWERS:
			DatabaseInstance.getInstance().getMyFollowers(this,getArguments().getString(USER));
			break;
		case MYFOLLOWING:
			DatabaseInstance.getInstance().getMyFollowing(this,getArguments().getString(USER));
			break;

		default:
			break;
		}
		

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
		    switch (loadUser) {
			case ALLUSER:
				 recyclerView.setAdapter(new MyRecyclerAdapterUser(getActivity(), userItemList,this));

				break;

			default:
				 recyclerView.setAdapter(new MyRecyclerAdapterUserProfile(getActivity(), userItemList,FragmentAllUsers.this));

				break;
			}
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

	@Override
	public void launchIt(String uid,String email) {
		Intent intent = new Intent(getActivity(),ActivityMyProfile.class);
		Bundle bundle = new Bundle();
		bundle.putString(ActivityMyProfile.UID, uid);
		bundle.putString(ActivityMyProfile.EMAIL, TransformEmail.getNameByEmail(email));
		intent.putExtras(bundle); 
		startActivity(intent);
		
	}
}
