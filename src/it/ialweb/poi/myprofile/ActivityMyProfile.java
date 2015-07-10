package it.ialweb.poi.myprofile;

import it.ialweb.poi.R;
import it.ialweb.poi.MainActivity.PlaceHolder;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.fragment.FragmentLogin;
import it.ialweb.poi.fragment.FragmentUserDetails;
import it.ialweb.user.FragmentAllUsers;
import it.ialweb.user.LoadUser;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
public class ActivityMyProfile extends AppCompatActivity {

	protected static final String DIALOG_TWEET = "tweet";
	public static final String UID = "uid";
	public static final String EMAIL = "email_user";
	private TabLayout tabLayout;
	private ViewPager viewPager;
	
	private String userDomain;
	private String userEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		viewPager = (ViewPager) findViewById(R.id.pager);
		
		userDomain = getIntent().getExtras().getString(UID);
		userEmail = getIntent().getExtras().getString(EMAIL);
		
		getSupportActionBar().setTitle(userEmail);

		FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			private String[] titles = new String[] {"TWEET","FOLLOWING","FOLLOWERS","FAVORITES"};

			@Override
			public int getCount() {
				return titles.length;
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {

				case 0:
					return FragmentUserDetails.getInstance(userDomain);
				case 1:
					return FragmentAllUsers.getInstance(LoadUser.MYFOLLOWERS,userDomain);
				case 2:
					return FragmentAllUsers.getInstance(LoadUser.MYFOLLOWING,userDomain);
				case 3:
					return FragmentMyFavorites.getInstance(userDomain);
				default:
					return new PlaceHolder();			
				}
				
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titles[position];
			}
		};
		viewPager.setAdapter(adapter);

		tabLayout.setupWithViewPager(viewPager);

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
				
			return true;

		default:
			break;
		}

	
		return super.onOptionsItemSelected(item);
	}
}