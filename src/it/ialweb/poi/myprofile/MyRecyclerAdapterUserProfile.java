package it.ialweb.poi.myprofile;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.user.User;
import it.ialweb.user.UserHolder;
import it.ialweb.user.MyRecyclerAdapterUser.LaunchActivity;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class MyRecyclerAdapterUserProfile extends RecyclerView.Adapter<UserHolder> {


    private List<User> usersItemList;

    private Context mContext;
    
    private LaunchActivity launchActivity;

    public MyRecyclerAdapterUserProfile(Context context, List<User> tweetItemList,LaunchActivity launchActivity) {
        this.usersItemList = tweetItemList;
        this.mContext = context;
        this.launchActivity = launchActivity;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cella_user_profile, null);
        UserHolder mh = new UserHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final UserHolder tweetHolder, final int i) {
 
    	tweetHolder.userName.setText(usersItemList.get(i).getEmail().split("@")[0]);
    	tweetHolder.llUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchActivity.launchIt(usersItemList.get(i).getUid(),usersItemList.get(i).getEmail());
			}
		});
    	
    }

    @Override
    public int getItemCount() {
        return (null != usersItemList ? usersItemList.size() : 0);
    }


}