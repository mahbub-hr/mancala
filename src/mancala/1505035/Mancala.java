package mancala;

import java.util.List;
import java.util.Scanner;

public class Mancala {
	static void print(List<State> node) {
		System.out.println("Printing children");
		int i =1;
		for (State state2 : node) {
			System.out.print("child "+i+": \n");i++;
			System.out.print(state2+"\n");
		}
	}
	public static void main(String [] arg) {
		Scanner scanner = new Scanner(System.in);
		int choice1, choice2, choice;
		System.out.print("**********************  Mancala  *********************\nEnter Heuristic choice for Player 1 and Player 2: ");
		choice1 = scanner.nextInt(); choice2 = scanner.nextInt();
		System.out.println();
		
		State state = new State();
		AlphaBetaPruning alphaBetaPruning = new AlphaBetaPruning();
		state.boardInit();
		System.out.println(state);
		state.playerInit(0, 13);
		boolean isPlayer1 = true;
		choice = choice1;
		do {
			State newState = alphaBetaPruning.alphaBetaDecision(state, 10,isPlayer1,choice);
			System.out.println("\nBin: "+newState.getBinId());
			System.out.println(newState);
			if(newState.getStart() != state.getStart()) {
				isPlayer1 = !isPlayer1;
				choice = (choice == choice1 ? choice2: choice1);
			}
			state = newState;
		}while(!state.isEnd());
		state.finishTask();
		System.out.println("\n"+state);
		//System.out.print("Player1: "+ state.getBinVal(6)+ "\nPlayer2: "+state.getBinVal(13));
	}
}
