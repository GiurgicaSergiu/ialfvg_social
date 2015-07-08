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
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentUserDetails extends Fragment implements Firebase.ValueResultHandler<Map<String, Object>>{

	
	public static FragmentUserDetails getInstance(){
		return new FragmentUserDetails();
	}
	
	private TextView txtUserEmail;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_details, null);
		
		txtUserEmail = (TextView) view.findViewById(R.id.txtEmail);
		
		txtUserEmail.setText(DatabaseInstance.getInstance().getUserEmail());
		
		return view;
	}

	@Override
	public void onError(FirebaseError arg0) {
		Snackbar.make(getActivity().findViewById(R.id.coordinator), "Error", Snackbar.LENGTH_LONG).show();	
		
	}

	@Override
	public void onSuccess(Map<String, Object> arg0) {
		Snackbar.make(getActivity().findViewById(R.id.coordinator), "Registrazione effettuata", Snackbar.LENGTH_LONG).show();	
	}
}
