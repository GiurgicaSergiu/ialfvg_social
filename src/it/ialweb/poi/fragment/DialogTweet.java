package it.ialweb.poi.fragment;

import java.util.Calendar;

import it.ialweb.poi.R;
import it.ialweb.poi.database.DatabaseInstance;
import it.ialweb.poi.tweet.MessageTweet;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class DialogTweet extends DialogFragment {


	public static DialogTweet getInstance() {
		DialogTweet dialog = new DialogTweet();

		return dialog;
	}
	
	private Button btnTweet;
	private EditText etTweet;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.dialog_tweet, null);

		btnTweet = (Button) view.findViewById(R.id.btnTweet);
		etTweet = (EditText) view.findViewById(R.id.etTweet);
		
		btnTweet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageTweet messageTweet = new MessageTweet(etTweet.getText().toString());
				DatabaseInstance.getInstance().tweet(messageTweet);
				dismiss();
			}
		});

		vBuilder.setView(view);

		return vBuilder.create();
	}

}