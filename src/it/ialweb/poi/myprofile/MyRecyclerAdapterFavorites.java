package it.ialweb.poi.myprofile;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.poi.tweet.MessageTweetHolder;

import java.util.Date;
import java.util.List;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class MyRecyclerAdapterFavorites extends RecyclerView.Adapter<MessageTweetHolder> {


    private List<MessageTweet> tweetItemList;

    private Context mContext;

	private int lastPosition = -1;

    public MyRecyclerAdapterFavorites(Context context, List<MessageTweet> tweetItemList) {
        this.tweetItemList = tweetItemList;
        this.mContext = context;
    }

    @Override
    public MessageTweetHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cella_favorites, null);
        MessageTweetHolder mh = new MessageTweetHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MessageTweetHolder tweetHolder,int i) {
 
    	tweetHolder.userName.setText(tweetItemList.get(i).getEmail());
    	tweetHolder.tweet.setText(tweetItemList.get(i).getTweet());
    	tweetHolder.date.setText((new Date(tweetItemList.get(i).getDate())).toString());

    	if (i > lastPosition)
        {
    		ObjectAnimator.ofFloat(tweetHolder.llLayout, "scaleY", 0.8f, 1.0f)
			.setDuration(300).start();
    		ObjectAnimator.ofFloat(tweetHolder.llLayout, "scaleX", 0.4f, 1.0f)
			.setDuration(300).start();
            lastPosition = i;
        }
    
    }

    @Override
    public int getItemCount() {
        return (null != tweetItemList ? tweetItemList.size() : 0);
    }
}

