package it.ialweb.user;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MyRecyclerAdapterUser extends RecyclerView.Adapter<UserHolder> {


    private List<User> usersItemList;

    private Context mContext;

    public MyRecyclerAdapterUser(Context context, List<User> tweetItemList) {
        this.usersItemList = tweetItemList;
        this.mContext = context;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cella_user, null);
        UserHolder mh = new UserHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final UserHolder tweetHolder, final int i) {
 
    	tweetHolder.userName.setText(usersItemList.get(i).getEmail().split("@")[0]);
    	tweetHolder.btnFollow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatabaseInstance.getInstance().saveFollow(usersItemList.get(i));
				DatabaseInstance.getInstance().saveFollowers(usersItemList.get(i));
			}
		});
    	
    }

    @Override
    public int getItemCount() {
        return (null != usersItemList ? usersItemList.size() : 0);
    }


}