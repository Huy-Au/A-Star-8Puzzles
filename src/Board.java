import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	public int[][] state;
	final public static int[] goalPosition = {4, 0, 1, 2, 5, 8, 7, 6, 3}; //position of {0, 1, 2, 3, 4, 5, 6, 7, 8}
	final public static int[][] goalState = {{1,2,3},{8,0,4},{7,6,5}};
	public int fValue;
	public int runningCost;		// gvalue
	public int manhattan;		// hvalue
	Board parent;
	
	@Override
    public boolean equals(Object obj) {
		Board temp = (Board)obj;
        return (Arrays.deepHashCode(this.state) == Arrays.deepHashCode(temp.state))?true:false;
    }   

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.state);
    }
	
	public int[][] getState(){
		return state;
	}
	
	public int getPriority(){
		return fValue;
	}

	public Board(int[][] tiles) { // construct a board from an N-by-N array of tiles
		parent = null;
		int size = tiles.length;
		state = new int[size][size];
		state = passArray(tiles);
		manhattan = manhattan();
	}
	
	public Board(Board copy){
		this.fValue = copy.fValue;
		this.runningCost = copy.runningCost;
		this.manhattan = copy.manhattan;
		this.parent = copy.parent;
		this.state = copy.state;
	}

	public int manhattan() { // return sum of Manhattan distances between blocks and goals
		int total = 0;
		for(int i = 0; i < state.length; i++){
			for (int j = 0; j < state.length; j++){
				int tilePosition = (i * (state.length)) + j; 				//	ranges from 0 - 8
				int tileValue = state[i][j];
				if(goalPosition[tileValue] == tilePosition){
					continue;												//	current examined tile is in correct location
				} else {
					int tileRow, destRow, tileCol, destCol, manhattan;		//	obtain row, col of tile current placement and row, col of ideal placement
					tileCol = j;
					tileRow = i;
					destCol = goalPosition[tileValue] % state.length;
					destRow = (goalPosition[tileValue] - destCol) / state.length;
					manhattan = Math.abs(destCol - tileCol) + Math.abs(destRow - tileRow);	//	sum the absolute difference between current tile placement and ideal location
					total += manhattan;
				}
			}
		}
		return total;
	}


	public boolean goalCheck() {
		return Arrays.deepEquals(state, goalState);
	}

	public Iterable<Board> getNeighbors() { // return an Iterable of all neighboring board positions
		ArrayList<int[][]> neighbors = new ArrayList<>();
		ArrayList<Board> nextMove = new ArrayList<>();
		int zeroRow = 0, zeroCol = 0;
		boolean validMove[] = new boolean[4];
		for(int i = 0; i < state.length; i++ ){
			for(int j = 0; j < state.length; j++){
				if(state[i][j] == 0){
					zeroRow = i;
					zeroCol = j;
				}
			}
		}
		if(zeroRow != 0){
			int[][] nextState = new int[3][3];
			nextState = passArray(state);
			int tileAbove = nextState[zeroRow-1][zeroCol];
			nextState[zeroRow][zeroCol] = tileAbove;
			nextState[zeroRow-1][zeroCol] = 0;
			Board newBoard = new Board(nextState);
			newBoard.runningCost = this.runningCost + 1;
			newBoard.fValue = newBoard.manhattan + newBoard.runningCost;
			nextMove.add(newBoard);
		}
		if(zeroRow != state.length - 1){
			int[][] nextState = new int[3][3];
			nextState = passArray(state);
			int tileBelow = nextState[zeroRow+1][zeroCol];
			nextState[zeroRow][zeroCol] = tileBelow;
			nextState[zeroRow+1][zeroCol] = 0;
			Board newBoard = new Board(nextState);
			newBoard.runningCost = this.runningCost + 1;
			newBoard.fValue = newBoard.manhattan + newBoard.runningCost;
			nextMove.add(newBoard);
		}
		if(zeroCol != 0){
			int[][] nextState = new int[3][3];
			nextState = passArray(state);
			int tileLeft = nextState[zeroRow][zeroCol-1];
			nextState[zeroRow][zeroCol] = tileLeft;
			nextState[zeroRow][zeroCol-1] = 0;
			Board newBoard = new Board(nextState);
			newBoard.runningCost = this.runningCost + 1;
			newBoard.fValue = newBoard.manhattan + newBoard.runningCost;
			nextMove.add(newBoard);
		}
		if(zeroCol != state.length - 1){
			int[][] nextState = new int[3][3];
			nextState = passArray(state);
			int tileRight = nextState[zeroRow][zeroCol+1];
			nextState[zeroRow][zeroCol] = tileRight;
			nextState[zeroRow][zeroCol+1] = 0;
			Board newBoard = new Board(nextState);
			newBoard.runningCost = this.runningCost + 1;
			newBoard.fValue = newBoard.manhattan + newBoard.runningCost;
			nextMove.add(newBoard);
		}
		return nextMove;									
	}
	
	public void printState(int[][] test){
		for(int i = 0; i < test.length; i++){
			for(int j = 0; j < test.length; j++){
				System.out.print("" + test[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public int[][] passArray(int[][] input){
		int retArr[][] = new int[input.length][input.length];
		for(int i = 0; i < input.length; i++){
			for(int j = 0; j < input.length; j++){
				retArr[i][j] = input[i][j];
			}
		}
		return retArr;
	}
}