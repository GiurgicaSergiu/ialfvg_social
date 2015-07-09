package it.ialweb.poi.tweet;

import it.ialweb.poi.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageTweetHolder extends RecyclerView.ViewHolder {
	
    protected ImageView imgUser;
    protected TextView userName;
    protected TextView tweet;
    protected TextView date;
    protected LinearLayout llLayout;
    protected ImageView imgFavorites;

    public MessageTweetHolder(View view) {
        super(view);
        this.imgUser = (ImageView) view.findViewById(R.id.imgUser);
        this.userName = (TextView) view.findViewById(R.id.txtUserName);
        this.tweet = (TextView) view.findViewById(R.id.txtTweet);
        this.date = (TextView) view.findViewById(R.id.txtDate);
        this.llLayout = (LinearLayout) view.findViewById(R.id.llTweets);
        this.imgFavorites = (ImageView) view.findViewById(R.id.imgFavorites);
    }

}