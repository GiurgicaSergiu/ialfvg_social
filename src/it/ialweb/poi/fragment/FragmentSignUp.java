package it.ialweb.poi.fragment;


import java.util.Map;

import com.firebase.client.Firebase;
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

public class FragmentSignUp extends Fragment implements Firebase.ValueResultHandler<Map<String, Object>>{

	private EditText userName,userPassword; 
	private Button btnSignUp;
	
	public static FragmentSignUp getInstance(){
		return new FragmentSignUp();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_signup, null);
		
		userName = (EditText) view.findViewById(R.id.etUserName);
		userPassword = (EditText) view.findViewById(R.id.etUserPassword);
		btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
		
		btnSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatabaseInstance.getInstance().createUser(userName.getText().toString(), userPassword.getText().toString(), FragmentSignUp.this);
			}
		});
		
		return view;
	}

	@Override
	public void onError(FirebaseError arg0) {
		Snackbar.make(getActivity().findViewById(R.id.coordinator), "Error", Snackbar.LENGTH_LONG).show();	
		
	}

	@Override
	public void onSuccess(Map<String, Object> arg0) {
		Snackbar.make(getActivity().findViewById(R.id.coordinator), "Registrazione effettuata", Snackbar.LENGTH_LONG).show();	
		getActivity().onBackPressed();
	}
}
