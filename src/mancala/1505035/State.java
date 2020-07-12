package mancala;

import java.util.ArrayList;
import java.util.List;

public class State {
//	private Player p1;
//	private Player p2;
	private int board[];
	private State parentState;
	private int start;
	private int exclude;
	private int p,q,r,s;
	private int extraMove;
	private int hole;
	public State() {
//		p1 = new Player(6);
//		p2 = new Player(6);
		board = new int[14];
		exclude = 0;
		start = 0;
		p=3;
		q=5;
		r=7;
		s=11;
		extraMove = 0;
	}
	public State(State state) {
		board = new int[14];
		for(int i = 0; i < board.length; i++) board[i] = state.board[i];
	}
	public void boardInit() {
		for (int i = 0; i < board.length; i++) {
			if(i == 6 || i == 13) board[i] = 0; 
			else board[i] = 4;
		}
	}
	public void  playerInit(int start, int exclude) {
		this.start = start;
		this.exclude = exclude;
	}
	
	public int getBinVal(int binIdx) {
		return board[binIdx];
	}
	public void finishTask() {
		for(int i =0; i< 6; i++) {
			board[6] += board[i]; board[i] = 0;
			board[13] += board[i+7]; board[i+7] = 0;
		}
	}
	public boolean isEnd() {
		if(board[6] == 48 || board[13] == 48) return true;
		boolean isEnd = true;
		for(int i = 0; i < 6; i++) if(board[i] != 0) isEnd = false; 
		if(isEnd == true) return true;
		isEnd = true;
		for(int i = 7; i < 13; i++) if(board[i] != 0) isEnd = false;
		return isEnd;
	}
	public void setParent(State state) {
		this.parentState = state;
	}
	public State getParent() {
		return parentState;
	}
	public int getStart() {return start;} public int getExclude() {return exclude;}
	public int getExtraMoveCount() {
		return extraMove;
	}
	public void setExtraMove(int move) {
		extraMove += move;
	}
	public int getBinId() {return hole;}
	public void makeMove(int binIdx) {
		if(binIdx >= board.length) System.out.println("invalid starting move");
		int stones = board[binIdx]; 
		int parentStorage = parentState.start+6;
		board[binIdx] =0;
		while(stones > 0) {
			binIdx = (binIdx+1)%14;
			if(binIdx == parentState.exclude) continue;
			board[binIdx] += 1;
			stones--;
		}
		if(binIdx == parentStorage) {
			extraMove++;
			start = parentState.start;
			exclude = parentState.exclude;
			return ;
		}
		if(board[binIdx] == 1 && binIdx >= parentState.start && binIdx < parentStorage) {
			int opIdx = (2*parentStorage - binIdx)%14;
			board[parentStorage] = board[parentStorage] + board[opIdx] +1;
			board[binIdx] = board[opIdx] = 0;
		}
		return;
	}
	public List<State> getChildren(){
		List<State> children = new ArrayList<>();
		int n = start;
		for(; n < start + 6; n++) {
			//System.out.println("N = "+n);
			int stones = board[n]; if(stones == 0) continue;
			State state = new State(this);
			state.playerInit((start+7)%14, (exclude+7)%14);
			state.parentState = this;
			state.setExtraMove(this.extraMove);
			state.hole = n;
			state.makeMove(n);
			children.add(state);
		}
		return children;
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			for(int i = 6; i>=0; i--) System.out.print(board[i]+"	");
			System.out.println();
			for(int i = 7; i<14; i++) System.out.print("	"+board[i]);
			return "\n";
		}
	//return heuristic value
	public int heuristicValue(boolean isPlayer1, int choice) {
		if(isPlayer1 == true) return getByChoice(choice, isPlayer1);
		else  return getByChoice(choice, isPlayer1);
	}
	public int getByChoice(int i, boolean isPlayer1) {
		if(i== 1) return H1(isPlayer1);
		else if(i == 2) return H2(isPlayer1);
		else if(i == 3) return H3(isPlayer1);
		else return H4(isPlayer1);
	}
	// Heuristics function	
	public int H1(boolean isPlayer1) {
		if(isPlayer1 == true)
			return board[6] - board[13];
		else return board[13] - board[6];
	}
	
	public int H2(boolean isPlayer1) {
		int stoneOnMySide = 0;
		int stoneOnOpSide = 0;
		int starting = 0;
		if(isPlayer1 == true) starting  = 0;
		else starting = 7;
		for(int i = starting; i < starting+6; i++) {
			stoneOnMySide += board[i];
			stoneOnOpSide += board[(i+7)%14];
		}
		
		return p*H1(isPlayer1)+q*(stoneOnMySide - stoneOnOpSide);
	}
	
	public int H3(boolean isPlayer1) {
		return H2(isPlayer1)+r*extraMove;
	}
	public int H4(boolean isPlayer1) {
		if(isPlayer1 == true)
			return H3(isPlayer1) + s*board[6];
		else return H3(isPlayer1) + s*board[13];
	}
}
