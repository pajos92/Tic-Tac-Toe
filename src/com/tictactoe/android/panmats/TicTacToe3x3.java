package com.tictactoe.android.panmats;

import java.util.Random;

public class TicTacToe3x3 {

	private char mBoard[];
	private final static int BOARD_SIZE = 9;

	//player characters 
	public static final char PLAYER_ONE = 'X';
	public static final char PLAYER_TWO = 'O';
	public static final char EMPTY_SPACE = ' ';

	private Random mRand;

	// Return the size of the board
	public static int getBOARD_SIZE() {
		return BOARD_SIZE;
	}
	
	//Constructor initializing game
	public TicTacToe3x3() {
		mBoard = new char[BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++)
			mBoard[i] = EMPTY_SPACE;
		
		mRand = new Random();
	}

	// Clear the board 
	public void clearBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			mBoard[i] = EMPTY_SPACE;
		}
	}

	// set the given player at the given location on the game board.
	public void setMove(char player, int location) {
		mBoard[location] = player;
	}

	// Return the next move for the computer to make.
	public int nextMoveAI() {
		int move;
		
		//first check if there is a move that wins the game
		for (int i = 0; i < getBOARD_SIZE(); i++) 
		{
			if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO) 
			{
				char curr = mBoard[i];
				mBoard[i] = PLAYER_TWO;
				if (checkForWinner() == 3) 
				{
					setMove(PLAYER_TWO, i);
					return i;
				} else
					mBoard[i] = curr;
			}
		}

		// then check if the enemy wins to block it
		for (int i = 0; i < getBOARD_SIZE(); i++) 
		{
			if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO) 
			{
				char curr = mBoard[i];
				mBoard[i] = PLAYER_ONE;
				if (checkForWinner() == 2) {
					setMove(PLAYER_TWO, i);
					return i;
				} else
					mBoard[i] = curr;
			}
		}

		// if there is no winning or blocking, generate a move
		do {
			move = mRand.nextInt(getBOARD_SIZE());
		} while (mBoard[move] == PLAYER_ONE || mBoard[move] == PLAYER_TWO);

		setMove(PLAYER_TWO, move);
		return move;
	}

	/*
	 *  Check for a winner and return a status value indicating who has won.
	 *  No winner yet returns 0, Ended in tie reterns 1 ,'X' won returns 2, 'O' won returns 3
	 */
	
	public int checkForWinner() {
		
		// Check horizontal wins
		for (int i = 0; i <= 6; i += 3) {
			if (mBoard[i] == PLAYER_ONE && mBoard[i + 1] == PLAYER_ONE
					&& mBoard[i + 2] == PLAYER_ONE)
				return 2;
			if (mBoard[i] == PLAYER_TWO && mBoard[i + 1] == PLAYER_TWO
					&& mBoard[i + 2] == PLAYER_TWO)
				return 3;
		}

		// Check vertical wins
		for (int i = 0; i <= 2; i++) {
			if (mBoard[i] == PLAYER_ONE && mBoard[i + 3] == PLAYER_ONE
					&& mBoard[i + 6] == PLAYER_ONE)
				return 2;
			if (mBoard[i] == PLAYER_TWO && mBoard[i + 3] == PLAYER_TWO
					&& mBoard[i + 6] == PLAYER_TWO)
				return 3;
		}

		// Check for diagonal wins
		if ((mBoard[0] == PLAYER_ONE && mBoard[4] == PLAYER_ONE && mBoard[8] == PLAYER_ONE)
				|| mBoard[2] == PLAYER_ONE
				&& mBoard[4] == PLAYER_ONE
				&& mBoard[6] == PLAYER_ONE)
			return 2;
		if ((mBoard[0] == PLAYER_TWO && mBoard[4] == PLAYER_TWO && mBoard[8] == PLAYER_TWO)
				|| mBoard[2] == PLAYER_TWO
				&& mBoard[4] == PLAYER_TWO
				&& mBoard[6] == PLAYER_TWO)
			return 3;

		// Check for a tie
		for (int i = 0; i < getBOARD_SIZE(); i++) {
			// if we find non-taken space, then no one has won yet
			if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO)
				return 0;
		}

		// if we reach here, its definitely a tie
		return 1;
	}
}