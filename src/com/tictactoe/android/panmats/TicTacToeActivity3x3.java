package com.tictactoe.android.panmats;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.*;

public class TicTacToeActivity3x3 extends Activity {

	// Da Game Handler
	private TicTacToe3x3 game;

	// array that holds the buttons of the board
	private Button boardButtons[];

	// display info during the game
	private TextView infoTextView;

	// who goes first
	private boolean playerOneFirst = true;

	// decide if game is vs android or 1vs1
	private boolean singlePlayer = false;
	private boolean playerOneTurn = true;
	private boolean gameOver = false;

	private String _PLAYER_1_NAME = "Player 1";
	private String _PLAYER_2_NAME = "Player 2";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_game_3x3);

		boolean gameType = getIntent().getExtras().getBoolean("gameType");

		// get the players names passed from previous activity in extras
		if (!gameType) 
		{
			if (!getIntent().getExtras().getString("_PLAYER_1_NAME").isEmpty())
				_PLAYER_1_NAME = getIntent().getExtras().getString("_PLAYER_1_NAME");
			if (!getIntent().getExtras().getString("_PLAYER_2_NAME").isEmpty())
				_PLAYER_2_NAME = getIntent().getExtras().getString("_PLAYER_2_NAME");
		}
		// initialize the board buttons
		boardButtons = new Button[game.getBOARD_SIZE()];
		boardButtons[0] = (Button) findViewById(R.id.one);
		boardButtons[1] = (Button) findViewById(R.id.two);
		boardButtons[2] = (Button) findViewById(R.id.three);
		boardButtons[3] = (Button) findViewById(R.id.four);
		boardButtons[4] = (Button) findViewById(R.id.five);
		boardButtons[5] = (Button) findViewById(R.id.six);
		boardButtons[6] = (Button) findViewById(R.id.seven);
		boardButtons[7] = (Button) findViewById(R.id.eight);
		boardButtons[8] = (Button) findViewById(R.id.nine);

		infoTextView = (TextView) findViewById(R.id.information);

		// create a new 3x3 TicTacToe game object
		game = new TicTacToe3x3();

		// start a new game
		startNewGame(gameType);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.newGame:
			startNewGame(singlePlayer);
			break;
		case R.id.exitGame:
			TicTacToeActivity3x3.this.finish();
			break;
		}
		return true;
	}

	// starts a new game
	// clears the board and resets all buttons / text
	// sets game over to be false
	private void startNewGame(boolean isSinglePlayer) {

		this.singlePlayer = isSinglePlayer;
		game.clearBoard();
		for (int i = 0; i < boardButtons.length; i++) {
			boardButtons[i].setText("");
			boardButtons[i].setEnabled(true);
			boardButtons[i].setOnClickListener(new ButtonClickListener(i));
		}

		// setting up first textviews
		if (singlePlayer) {

			if (playerOneFirst) {
				infoTextView.setText(R.string.first_human);
				playerOneFirst = false;
			} else {
				infoTextView.setText(R.string.turn_computer);
				int move = game.nextMoveAI();
				setMove(game.PLAYER_TWO, move);
				playerOneFirst = true;
			}
		} else {

			if (playerOneFirst) {
				infoTextView.setText(_PLAYER_1_NAME + " goes first!");
				playerOneFirst = false;
			} else {
				infoTextView.setText(_PLAYER_2_NAME + " goes first!");
				playerOneFirst = true;
			}
		}

		gameOver = false;
	}

	// implements an onClickListener for every button 
	private class ButtonClickListener implements View.OnClickListener {
		int location;

		public ButtonClickListener(int location) {
			this.location = location;
		}

		public void onClick(View view) {
			if (!gameOver) {
				if (boardButtons[location].isEnabled()) {
					if (singlePlayer) {
						setMove(game.PLAYER_ONE, location);

						int winner = game.checkForWinner();

						if (winner == 0) {
							infoTextView.setText(R.string.turn_computer);
							int move = game.nextMoveAI();
							setMove(game.PLAYER_TWO, move);
							winner = game.checkForWinner();
						}
						if (winner == 0)
							infoTextView.setText(R.string.turn_human);
						else if (winner == 1) {
							infoTextView.setText(R.string.result_tie);
							gameOver = true;
							promptNewGame(getApplicationContext().getString(
									R.string.result_tie));
						} else if (winner == 2) {
							infoTextView.setText(R.string.result_human_wins);
							gameOver = true;
							promptNewGame(getApplicationContext().getString(
									R.string.result_human_wins));
						} else {
							infoTextView.setText(R.string.result_android_wins);
							gameOver = true;
							promptNewGame(getApplicationContext().getString(
									R.string.result_android_wins));
						}
					} else {
						if (playerOneTurn)
							setMove(game.PLAYER_ONE, location);
						else
							setMove(game.PLAYER_TWO, location);

						int winner = game.checkForWinner();

						if (winner == 0) {
							if (playerOneTurn) {
								infoTextView.setText(_PLAYER_2_NAME
										+ "'s turn!");
								playerOneTurn = false;
							} else {
								infoTextView.setText(_PLAYER_1_NAME
										+ "'s turn!");
								playerOneTurn = true;
							}
						} else if (winner == 1) {
							infoTextView.setText(R.string.result_tie);
							gameOver = true;
							promptNewGame(getApplicationContext().getString(
									R.string.result_tie));
						} else if (winner == 2) {
							infoTextView.setText(_PLAYER_1_NAME + " wins!");
							gameOver = true;
							playerOneTurn = false;
							promptNewGame(_PLAYER_1_NAME + " wins!");
						} else {
							infoTextView.setText(_PLAYER_2_NAME + " wins!");
							gameOver = true;
							playerOneTurn = true;
							promptNewGame(_PLAYER_2_NAME + " wins!");
						}
					}
				}
			}
		}

	}

	// prompt for a new game when a game ends
	private void promptNewGame(String result) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				TicTacToeActivity3x3.this);
		alertDialog.setTitle("Tic-Tac-Toe");
		alertDialog.setMessage(result + " Start new game?");
		alertDialog.setCancelable(false);
		alertDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						startNewGame(singlePlayer);
					}
				});
		alertDialog.setNegativeButton("Quit",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						TicTacToeActivity3x3.this.finish();
					}
				});

		alertDialog.create();
		alertDialog.show();
	}

	// set move for the current player
	private void setMove(char player, int location) {
		game.setMove(player, location);
		boardButtons[location].setEnabled(false);
		boardButtons[location].setText(String.valueOf(player));
		if (player == game.PLAYER_ONE)
			boardButtons[location].setTextColor(Color.GREEN);
		else
			boardButtons[location].setTextColor(Color.RED);
	}
}
