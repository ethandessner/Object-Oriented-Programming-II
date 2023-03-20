package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {
	Random random = new Random();
	int score;
	int totalRows = getRows();
	int totalCols = getCols();
	/* Include whatever instance variables you think are useful. */

	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * 
	 * @param rows number of rows in board
	 * @param cols number of columns in board
	 * @param random random number generator to be used during game when
	 * rows are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows, cols);
		this.random = random;
		score = 0;
	}

	/**
	 * The game is over when the last row (the one with index equal
	 * to board.length-1) contains at least one cell that is not empty.
	 */
	/*
	 * Checks to see if there are empty spaces in the last row. If all spaces are empty, then count will
	 * be equal to the length of the last row. If count is not equal the length of the last row, that means
	 * there is at least one space that has a color in it, and true is returned, meaning the game is over.
	 * Otherwise, false is returned meaning that there were no colors found in the last row of the board.
	 */
	public boolean isGameOver() {
		for(int x = 0; x < totalCols; x++) {
			if(getBoardCell(board.length - 1, x) != BoardCell.EMPTY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the player's score.  The player should be awarded one point
	 * for each cell that is cleared.
	 * 
	 * @return player's score
	 */
	public int getScore() {
		return score;
	}
	/*
	 * This method is called in the processCell method for the purpose of collapsing rows. It takes a row as a
	 * parameter and checks each cell. If a cell is filled, the method returns false, meaning the row is not empty.
	 * The method returns true by default meaning there were no contents in the row.
	 */
	public boolean emptyRowCheck(int row) {
		for(int colCheck = 0; colCheck < totalCols; colCheck++) {
			if(board[row][colCheck] != BoardCell.EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method must do nothing in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do 
	 * the following:
	 * 
	 * 1. Shift the existing rows down by one position.
	 * 2. Insert a row of random BoardCell objects at the top
	 * of the board. The row will be filled from left to right with cells 
	 * obtained by calling BoardCell.getNonEmptyRandomBoardCell().  (The Random
	 * number generator passed to the constructor of this class should be
	 * passed as the argument to this method call.)
	 */
	/*
	 * Starts at the row above the last because if there are contents in the last row, the game would already be over.
	 * The loop moves up the board form the bottom and if there are any contents in the row, the entire row is shifted 
	 * down one row. Once the loop gets to the very top of the board, a random set of cells will be filled into the 
	 * first row. The first row will always be empty once the loop reaches the top since all of the board's contents 
	 * will have shifted down one row total.
	 */
	public void nextAnimationStep() {
		if(isGameOver() == false) {
			for(int rowCheck = totalRows - 2; rowCheck >= 0; rowCheck--) {
				for(int colFill = 0; colFill < totalCols; colFill++) {
					//row is moved down cell by cell
					setBoardCell(rowCheck + 1, colFill, getBoardCell(rowCheck, colFill)); 
					if(rowCheck == 0) {
						//first row is filled with random cells
						setBoardCell(rowCheck, colFill, BoardCell.getNonEmptyRandomBoardCell(random)); 
					}
				}

			}
		}
	}

	/**
	 * This method is called when the user clicks a cell on the board.
	 * If the selected cell is not empty, it will be set to BoardCell.EMPTY, 
	 * along with any adjacent cells that are the same color as this one.  
	 * (This includes the cells above, below, to the left, to the right, and 
	 * all in all four diagonal directions.)
	 * 
	 * If any rows on the board become empty as a result of the removal of 
	 * cells then those rows will "collapse", meaning that all non-empty 
	 * rows beneath the collapsing row will shift upward. 
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for 
	 * invalid row or "Invalid column index" for invalid column.  We check 
	 * for row validity first.
	 */
	/*
	 * Checks the surrounding cells of the target cell to see if they are the same. There is no need to reference the
	 * color of the cell specifically since the cells are being compared as a whole. If a surrounding cell is the same
	 * as the target cell, the surrounding cell is set to empty. The target cell is set to empty once all surrounding
	 * cells have been compared because if the target cell was cleared first, there would be no point of reference.
	 * The player's score is incremented by one every time a cell is cleared.
	 */
	public void processCell(int rowIndex, int colIndex) {
		int up = rowIndex - 1;
		int down = rowIndex + 1;
		int left = colIndex - 1;
		int right = colIndex + 1;

		if(board[rowIndex][colIndex] != BoardCell.EMPTY) {

			if(up >= 0) {
				if(board[up][colIndex] == board[rowIndex][colIndex]) { 
					//if the cell above the target cell is the same, set it to empty
					board[up][colIndex] = BoardCell.EMPTY;
					score++;
				}
				if(right < totalCols) {
					//if the cell above and to the right of the target cell is the same, set it to empty
					if(board[up][right] == board[rowIndex][colIndex]) { 
						board[up][right] = BoardCell.EMPTY;
						score++;
					}
				}
				if(left >= 0) {
					//if the cell above and to the left of the target cell is the same, set it to empty
					if(board[up][left] == board[rowIndex][colIndex]) {
						board[up][left] = BoardCell.EMPTY;
						score++;
					}
				}
			}
			if(left > 0) {
				//if the cell to the left of the target cell is the same, set it to empty
				if(board[rowIndex][left] == board[rowIndex][colIndex]) { 
					board[rowIndex][left] = BoardCell.EMPTY;
					score++;
				}
			}
			if(right < totalCols) {
				//if the cell to the right of the target cell is the same, set it to empty
				if(board[rowIndex][right] == board[rowIndex][colIndex]) {
					board[rowIndex][right] = BoardCell.EMPTY;
					score++;
				}
			}
			if(down < totalRows) {
				//if the cell below the target cell is the same, set it to empty
				if(board[down][colIndex] == board[rowIndex][colIndex]) {
					board[down][colIndex] = BoardCell.EMPTY;
					score++;
				}
				if(left >= 0) {
					//if the cell below and to the left of the target cell is the same, set it to empty
					if(board[down][left] == board[rowIndex][colIndex]) {
						board[down][left] = BoardCell.EMPTY;
						score++;
					}
				}
				if(right < totalCols) {
					//if the cell below and to the right of the target cell is the same, set it to empty
					if(board[down][right] == board[rowIndex][colIndex]) {
						board[down][right] = BoardCell.EMPTY;
						score++;
					}
				}
			}
			//target cell is set to empty
			board[rowIndex][colIndex] = BoardCell.EMPTY;
			score++;
		}
		/*
		 * The board is scanned starting from the row above the last row. There is no need to scan the last row because
		 * if there are contents in it, the game is over. The board is scanned until an empty row is found using calls
		 * to the emptyRowCheck() method. Once an empty row is found, the contents below the empty row are filled into
		 * the empty row cell by cell. Once a cell is moved it is set to empty to make sure that the entire board moves
		 * up.
		 */
		int emptyRow;
		for(int findEmptyRow = totalRows - 2; findEmptyRow > 0; findEmptyRow--) {
			if(emptyRowCheck(findEmptyRow) == true) {
				emptyRow = findEmptyRow;
				for(int x = emptyRow; x < totalRows - 1; x++) { //totalRows - 1 b/c no need to scan last row
					for(int y = 0; y < totalCols; y++) {
						board[x][y] = board[x + 1][y]; //sets the empty row to the row below it cell by cell
						board[x + 1][y] = BoardCell.EMPTY; //row that was moved up is now wiped cell by cell
					}
				}
			}
		}
	}
}
