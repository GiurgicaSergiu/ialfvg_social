package it.ialweb.user;

import it.ialweb.poi.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserHolder extends RecyclerView.ViewHolder {
	
    public ImageView imgUser;
    public TextView userName;
    public Button btnFollow;
    public LinearLayout llUser;

    public UserHolder(View view) {
        super(view);
        this.imgUser = (ImageView) view.findViewById(R.id.imgUser);
        this.userName = (TextView) view.findViewById(R.id.txtEmailUser);
        this.btnFollow = (Button) view.findViewById(R.id.btnFollow);
        this.llUser = (LinearLayout) view.findViewById(R.id.llUser);
    }
    
   

}