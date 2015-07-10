package it.ialweb.poi;

import java.util.Random;

import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.fragment.DialogTweet;
import it.ialweb.poi.fragment.FragmentLogin;
import it.ialweb.poi.fragment.FragmentTimeLine;
import it.ialweb.poi.fragment.FragmentUser;


import it.ialweb.poi.myprofile.ActivityMyProfile;
import it.ialweb.poi.tweet.MessageTweet;
import it.ialweb.user.FragmentAllUsers;
import it.ialweb.user.LoadUser;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

	protected static final String DIALOG_TWEET = "tweet";
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private Menu myMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Firebase.setAndroidContext(this);
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		viewPager = (ViewPager) findViewById(R.id.pager);

		FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			private int[] titles = new int[] { R.string.Timeline, R.string.Users, R.string.MyProfile };

			@Override
			public int getCount() {
				return titles.length;
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {
				case 0:
					return FragmentTimeLine.getInstance();
				case 1:
					return FragmentAllUsers.getInstance(LoadUser.ALLUSER);
				case 2:
					return FragmentUser.getInstance();

				default:
					return new PlaceHolder();
				
				}
				
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return getResources().getString(titles[position]);
			}
		};
		viewPager.setAdapter(adapter);

		tabLayout.setupWithViewPager(viewPager);

		findViewById(R.id.fabBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogTweet.getInstance().show(getFragmentManager(), DIALOG_TWEET);
			}
		});

		
	}

	public static class PlaceHolder extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			RecyclerView recyclerView = new RecyclerView(getActivity());
			recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
			recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
				@Override
				public int getItemCount() {
					return 30;
				}

				@Override
				public void onBindViewHolder(ViewHolder holder, int position) {
					((TextView) holder.itemView).setText("Item " + position);
				}

				@Override
				public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
					LayoutInflater layoutInflater = getActivity().getLayoutInflater();
					View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
					return new ViewHolder(view) {
					};
				}
			});
			return recyclerView;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_logout:
				DatabaseInstance.getInstance().logout();
				getSupportFragmentManager().beginTransaction().replace(R.id.fl_user, FragmentLogin.getInstance()).commit();
				invalidateOptionsMenu();
			return true;

		case R.id.action_profile:
			Intent intent = new Intent(MainActivity.this,ActivityMyProfile.class);
			Bundle bundle = new Bundle();
			bundle.putString(ActivityMyProfile.UID, DatabaseInstance.getInstance().getUserUid());
			bundle.putString(ActivityMyProfile.EMAIL, TransformEmail.getNameByEmail(DatabaseInstance.getInstance().getUserEmail()));
			intent.putExtras(bundle); 
			startActivity(intent);
		default:
			break;
		}

	
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_logout).setVisible(DatabaseInstance.getInstance().isLogged());
		menu.findItem(R.id.action_profile).setVisible(DatabaseInstance.getInstance().isLogged());
		return true;
	}
}