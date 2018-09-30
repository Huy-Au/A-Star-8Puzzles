import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Solver {

	Queue<Board> frontier = new PriorityQueue<Board>(new Comparator<Board>() {
		public int compare(Board board1, Board board2) {
			return board1.getPriority() - board2.getPriority(); // place smaller fvalue at front of queue
		}
	});
	
	ArrayList<Board> explored = new ArrayList<>();

	public Solver(Board initial) { 
		if(initial.goalCheck()){
			System.out.println("Solved");
		}
		frontier.add(initial);
		explored.add(initial);
		while (!frontier.isEmpty()) {
			Board active = frontier.poll();
			Iterable<Board> neighbours = active.getNeighbors();
			for (Board current : neighbours) {
				if (!explored.contains(current)) {
					current.parent = active;
					frontier.add(current);

				} else { // Check if shorter path available
					int tentative = current.fValue;
					int indexOfState = explored.indexOf(current);
					Board exploredState = explored.get(indexOfState);
					int oldFValue = exploredState.fValue;
					if(tentative < oldFValue){ //Better path found to Node currently in explored set
						explored.remove(exploredState);
						explored.add(current);
					}
				}
				if (current.goalCheck()) {
					// Printing actions taken to get to goal
					System.out.println("Found goal\n\n");
					Stack<Board> pathToDest = new Stack<Board>();
					while (!current.equals(initial)) {
						pathToDest.push(current);
						current = current.parent;
					}
					System.out.println("Initial state:");
					current.printState(current.state);
					System.out.println("---------------");
					int count = 1;
					while (!pathToDest.isEmpty()) {
						Board init = pathToDest.pop();
						System.out.println("Step " + count++);
						init.printState(init.state);
						System.out.println();
					}
					return;
				}
			}
		}
	}
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int N = 3;
		int[][] tiles = new int[3][3];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				tiles[i][j] = reader.nextInt();
		Board initial = new Board(tiles);
		Solver solver = new Solver(initial);
		reader.close();
		// Obtain 9 values from (0-8) from user, tiles will be placed from left to right, top to bottom.
	}
}







































/////////////////////////////THIS ONE STILL WORKS///////////////////////////////
//import java.awt.List;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.PriorityQueue;
//import java.util.Queue;
//import java.util.Scanner;
//import java.util.Set;
//import java.util.Stack;
//
//public class Solver {
//
//	Queue<Board> frontier = new PriorityQueue<Board>(new Comparator<Board>() {
//		public int compare(Board board1, Board board2) {
//			return board1.getPriority() - board2.getPriority(); // smaller
//																// fvalue first
//		}
//	});
//	Set<Board> explored = new HashSet<>();
//
//	// problem, adding elements in queue are slightly different, e.g. getting to
//	// "state1" on step 2 is different to "state1" on step 7 because their
//	// f value and g value are different
//	// implemented equals method and hash method, hopefully resolved
//
//	public Solver(Board initial) { // find a solution to the initial board
//		// check if initial is solved already
//		frontier.add(initial);
//		explored.add(initial);
//		while (!frontier.isEmpty()) {
//			Board active = frontier.poll();
//			Iterable<Board> neighbours = active.getNeighbors();
//			for (Board current : neighbours) {
//				if (!explored.contains(current) && !frontier.contains(current)) {
//					current.parent = active;
//					System.out.println("\n-------------\nPrinting current followed by parent");
//					current.printState(current.state);
//					System.out.println();
//					current.parent.printState(current.parent.state);
//					System.out.println("\n-------------");
//					frontier.add(current);
//
//				} else { // found a shorter path to this Board
//					// TODO convert explored and list maybe
//				}
//				if (current.goalCheck()) {
//					// TODO This needs to be placed somewhere appropriate not in
//					// correct location
//					System.out.println("Found goal");
//					current.printState(current.state);
//
//					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//					// current.
//					Stack<Board> pathToDest = new Stack<Board>();
//					while (!current.equals(initial)) {
//						pathToDest.push(current);
//						current = current.parent;
//					}
//					pathToDest.push(current);
//					System.out.println("Size is: " + pathToDest.size());
//					while (!pathToDest.isEmpty()) {
//						Board init = pathToDest.pop();
//						System.out.println("Steps taken");
//						init.printState(init.state);
//					}
//					return;
//				}
//			}
//		}
//
//		// while(!frontier.isEmpty()){
//		// Board active = frontier.poll();
//		// Iterable<Board> neighbours = active.getNeighbors();
//		// for(Board current: neighbours){
//		// frontier.add(current);
//		// }
//		// }
//	}
//
//	public int moves() { // return min number of moves to solve initial board;
//							// -1 if no solution
//		return -1;
//	}
//
//	public Iterable<Board> solution() { // return an Iterable of board positions
//										// in solution
//		return null;
//	}
//
//	public static void main(String[] args) {
//		Scanner reader = new Scanner(System.in);
//
//		int N = 3;
////		int[][] tiles = new int[][]{{1,2,3}, {8,0,4}, {7,6,5}};
////		int[][] tiles1 = new int[][]{{1,2,3}, {8,0,4}, {7,6,5}};
////		int[][] tiles2 = new int[][]{{1,2,3}, {8,4,0}, {7,6,5}};
//		int[][] tiles = new int[3][3];
//		for (int i = 0; i < N; i++)
//			for (int j = 0; j < N; j++)
//				tiles[i][j] = reader.nextInt();
//		
//		
////		Solver solver = new Solver(initial);
//
//	}
//}