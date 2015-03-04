package com.tictactoe.android.panmats;

import com.tictactoe.android.panmats.R;
import com.tictactoe.android.panmats.TicTacToeActivity3x3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainScreenActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);
		
		//radiogroup containing 3x3/4x4 game types
		final RadioGroup radioDimensions = (RadioGroup) findViewById(R.id.rDimensions);

		//set the vs.Android button depending on radio button selected
		((Button) findViewById(R.id.bHumanVsAndroid)).setOnClickListener(new OnClickListener() 
		{
			public void onClick(View V) {
				Log.d("DEBUG", "Selected Human Vs Android Button!");
				Intent intent;
				final int selected = radioDimensions.getCheckedRadioButtonId();
				//selected intent
				if (selected == (int) findViewById(R.id.rb4x4).getId())
					intent = new Intent(MainScreenActivity.this,TicTacToeActivity4x4.class);
				else
					intent = new Intent(MainScreenActivity.this,TicTacToeActivity3x3.class);

				intent.putExtra("gameType", true);
				startActivityForResult(intent, 0);
			}
		});
		
		//set the vs.Human button 
		((Button) findViewById(R.id.bHumanVsHuman)).setOnClickListener(new OnClickListener() {
			public void onClick(View V) {
				Log.d("DEBUG", "Selected Human Vs Human Button!");

				//in case of human vs human open up a Dialog for player names 
				final Dialog dialog = new Dialog(MainScreenActivity.this);
				dialog.setContentView(R.layout.activity_name_prompt);
				dialog.setTitle("Enter your names!");
				final EditText editP1 = (EditText) dialog.findViewById(R.id.etPlayer1Name);
				editP1.setInputType(InputType.TYPE_CLASS_TEXT);
				final EditText editP2 = (EditText) dialog.findViewById(R.id.etPlayer2Name);
				editP2.setInputType(InputType.TYPE_CLASS_TEXT);
				Button bDone = (Button) dialog.findViewById(R.id.bDoneNames);
				bDone.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent;
						final int selected = radioDimensions.getCheckedRadioButtonId();
						if (selected == (int) findViewById(R.id.rb4x4).getId())
							intent = new Intent(MainScreenActivity.this,TicTacToeActivity4x4.class);
						else
							intent = new Intent(MainScreenActivity.this,TicTacToeActivity3x3.class);
						intent.putExtra("gameType", false);
						//using Intent extras for the players names as well to pass on next screen
						intent.putExtra("_PLAYER_1_NAME", editP1.getText().toString());
						intent.putExtra("_PLAYER_2_NAME", editP2.getText().toString());
						startActivityForResult(intent, 0);
					}
				});
				dialog.show();
			}
		});

		//exit Button
		((Button) findViewById(R.id.bExit)).setOnClickListener(new OnClickListener() {
			public void onClick(View V) {
				Log.d("DEBUG", "Selected Exit Game Button!");
				MainScreenActivity.this.finish();
			}
		});
	}
}