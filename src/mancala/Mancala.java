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
		int choice1 =1, choice2=2, next =3,choice;
		int depthLimit = 10;
		int win[] = new int[5];
		System.out.print("**********************  Mancala  *********************\n");//Enter Heuristic choice for Player 1 and Player 2: ");
		//choice1 = scanner.nextInt(); choice2 = scanner.nextInt();
		//System.out.println();
		
		State state = new State();
		//AlphaBetaPruning alphaBetaPruning = new AlphaBetaPruning();
		state.boardInit();
		System.out.println(state);
		state.playerInit(0, 13);
		boolean isPlayer1 = true;
		choice = choice1;
		for(int k =1; k<5;k++){
			choice1=k; 
			for(int j = k+1; j <5; j++) {
				choice2 = j;
				for(int i =0 ; i< 100; i++) {
					state.boardInit();
						
					do {
						AlphaBetaPruning alphaBetaPruning = new AlphaBetaPruning();
						State newState = alphaBetaPruning.alphaBetaDecision(state, depthLimit,isPlayer1,choice);
						//System.out.println("\nBin: "+newState.getBinId());
						//System.out.println(newState);
						if(newState.getStart() != state.getStart()) {
							isPlayer1 = !isPlayer1;
							choice = (choice == choice1) ? choice2: choice1;
							//System.out.println(isPlayer1+" "+choice);
						}
//						if(choice == 1) depthLimit = 1;
//						else depthLimit = 12;
						state = newState;
					}while(!state.isEnd());
					state.finishTask();
					//System.out.println("\n"+state);
					int p = state.getBinVal(6) > state.getBinVal(13) ? choice1:choice2;
					//System.out.println("heuristic: "+p);
					win[p]++;
				}
				for (int i = 1; i < win.length; i++) {
					System.out.print(win[i] + "	");
				}
				System.out.println();
			}
	    }
		
	}
}
