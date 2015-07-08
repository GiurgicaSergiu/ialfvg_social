package it.ialweb.poi.tweet;

import it.ialweb.poi.R;

import java.util.Date;
import java.util.List;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyRecyclerAdapterTweet extends RecyclerView.Adapter<MessageTweetHolder> {


    private List<MessageTweet> tweetItemList;

    private Context mContext;

	private int lastPosition = -1;

    public MyRecyclerAdapterTweet(Context context, List<MessageTweet> tweetItemList) {
        this.tweetItemList = tweetItemList;
        this.mContext = context;
    }

    @Override
    public MessageTweetHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cella_tweet, null);
        MessageTweetHolder mh = new MessageTweetHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MessageTweetHolder tweetHolder, int i) {
 
    	tweetHolder.userName.setText(tweetItemList.get(i).getUidUser());
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