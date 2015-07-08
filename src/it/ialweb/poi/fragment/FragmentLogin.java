package it.ialweb.poi.fragment;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentLogin extends Fragment implements AuthResultHandler {

	protected static final String FRAGMENT_SIGN_UP = "fragmentsignup";
	private EditText userName,userPassword; 
	private Button btnLogin,btnSignUp;
	
	public static FragmentLogin getInstance(){
		return new FragmentLogin();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, null);
		
		userName = (EditText) view.findViewById(R.id.etUserName);
		userPassword = (EditText) view.findViewById(R.id.etUserPassword);
		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatabaseInstance.getInstance().login(userName.getText().toString(), userPassword.getText().toString(), FragmentLogin.this);
			}
		});
		
		btnSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			getFragmentManager().beginTransaction().add(R.id.fl_user, FragmentSignUp.getInstance()).addToBackStack(FRAGMENT_SIGN_UP).commit();
			}
		});
		
		return view;
	}

	@Override
	public void onAuthenticated(AuthData arg0) {
		Snackbar.make(getActivity().findViewById(R.id.coordinator), "Success", Snackbar.LENGTH_LONG).show();		
	}

	@Override
	public void onAuthenticationError(FirebaseError arg0) {
		Snackbar.make(getActivity().findViewById(R.id.coordinator), "Error", Snackbar.LENGTH_LONG).show();		
	}

}
