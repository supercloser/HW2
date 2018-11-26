package HW;

import java.util.Random;
import java.util.Scanner;
/*
 * @Author Lidor Amitay
 * 
 * in this program i created The Game Of Life ! 
 * 
 */

public class HW2_LidorAmitay {
	public static boolean[][] Board;// static boolean array to work in all methods

	public static void main(String[] args) {
		// At first we take 2 parameters from the user.
		System.out.println("********** GAME OF LIFE X*X **********");
		Scanner scn = new Scanner(System.in);
		int x, max;
		System.out.println("Please enter X parameter :");
		x = scn.nextInt();// the board size (X*X).
		System.out.println("Enter max number of boards :");
		max = scn.nextInt();// Maximum times the board will run to finish the program(if it didnt end
							// before).
		boolean[][] startBoard = new boolean[x][x];
		Board = getStartBoard(x);
		copyBoard(Board, startBoard);// Copying the start board from the static board
        System.out.println("Start Board ");
		toString(startBoard);
		System.out.println();
		boolean[][] beforeChange = new boolean[x][x];// a new boolean array we use to check if the board canged from
														// step i to the next step.
		int i;
		int timesChanged = 0;
		for (i = 1; i <= max; i++) { // "for" loop to send the board game for check and update in the game methods.
			timesChanged++; // counting how many times the board changed.
			copyBoard(startBoard, beforeChange);
			startBoard = checkBoard(startBoard);
			if (isIdentical(startBoard, beforeChange))// the games stops if the board is not changing any more.
				break;
			System.out.println("Step " + i);
			toString(startBoard);
			System.out.println("Board Changed \n");
		}
		System.out.println("Finished! ");
		toString(startBoard);
		System.out.printf("The Board Changed %d times ", timesChanged);

		scn.close();

	}

//  this method generate a new random boolean array X*X (X user parameter).
	public static boolean[][] getStartBoard(int x) {
		Random random = new Random();
		boolean[][] randBoard = new boolean[x][x];
		for (int i = 0; i < randBoard.length; i++)
			for (int j = 0; j < randBoard[i].length; j++) {
				randBoard[i][j] = random.nextBoolean();

			}
		return randBoard;
	}

//  a method that print that game board(true prints "1" false "0").
	public static void toString(boolean arr[][]) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j]) {
					System.out.print(1 + "  ");
				} else
					System.out.print(0 + "  ");
			}
			System.out.println();
		}
		

	}

	// This method copying the array values from the source to copyTo. we use the
	// duplicate to change the game board.
	public static void copyBoard(boolean[][] source, boolean[][] copyTo) {
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[i].length; j++) {
				copyTo[i][j] = source[i][j];
			}
		}

	}

// checking the board and send it to setCell for the next update.
	public static boolean[][] checkBoard(boolean arr[][]) {
		boolean[][] arrCopy = new boolean[arr.length][arr.length];
		copyBoard(arr, arrCopy);
		// making a copy to the game board array , to use for the update
		//(checking neighbors in the game board and update in the copy).
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arrCopy[i][j] = setCell(i, j, arrCopy, arr);
			}
		}
		return arrCopy;

	}

// this method updates the values of the new game board.
	public static boolean setCell(int x, int y, boolean gameBoardCopy[][], boolean[][] gameBoardOriginal) {
		int liveNeighbors = getNeighbors(x, y, gameBoardOriginal);
		// sending each values from the board and update the values in the copy(to keep
		// the previous board, so we can check all his values).
		if (liveNeighbors > 4 || liveNeighbors < 2) {// game rules : if the cell have more then 4 neighbors or less the
														// 2 he's dead.
			gameBoardCopy[x][y] = false;
		} else if (liveNeighbors == 3) {// if the cell have 3 living neighbors he's reborn.(2-4 neighbors and the cell
										// stay alive).
			gameBoardCopy[x][y] = true;
		}

		return gameBoardCopy[x][y];

	}

	public static boolean isIdentical(boolean[][] arr1, boolean[][] arr2) {// A method that check if the board is
																			// identical to the previous board.
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				if (!(arr1[i][j] == arr2[i][j]))
					return false;
			}
		}
		return true;
	}

	public static int getNeighbors(int x, int y, boolean gameBoard[][]) {// counting cell's neighbors.
		int neighbors = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int ix = i + x;
				int jy = j + y;
				if (!(ix == x && jy == y)) {

					// fix board exceptions
					if (ix < 0)
						ix = gameBoard.length - 1;
					if (jy < 0)
						jy = gameBoard[x].length - 1;

					if (ix >= gameBoard.length)
						ix -= gameBoard.length;
					if (jy >= gameBoard[x].length)
						jy -= gameBoard[x].length;

					if (gameBoard[ix][jy])
						neighbors++;

				}
			}
		}

		return neighbors;
	}

}
