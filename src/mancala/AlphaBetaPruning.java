package mancala;

import java.util.List;

public class AlphaBetaPruning {
	State nexState = null;
	int cutLimit;
	boolean isPlayer1;
	int choice;
	public State alphaBetaDecision(State state,int depthLimit, boolean isPlayer1,int choice) {
		cutLimit = depthLimit;
		this.isPlayer1 = isPlayer1;
		this.choice = choice;
		int v = MaxValue(state, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE);
		//System.out.println("Max val: "+v);
		return nexState;
	}
	
	public int MaxValue(State state , int depthLimit, int alpha, int beta) {
		if(depthLimit == 0) return state.heuristicValue(isPlayer1,choice);
		if(state.isEnd() == true) return state.heuristicValue(isPlayer1,choice);
		int v = Integer.MIN_VALUE;
		List<State> children = state.getChildren();
		for (State state2 : children) {
			int minval = 0;
			if(state.getStart() == state2.getStart()) minval = MaxValue(state2, depthLimit, alpha, beta);
			else 	minval = MinValue(state2, depthLimit-1,alpha,beta);
			
			if(v < minval) {
				v = minval;
				if(depthLimit == cutLimit) nexState = state2;
			}
			if(v >= beta) return v;
			alpha = Math.max(alpha, v);
		}
		return v;
	}
	
	public int MinValue(State state, int depthLimit, int alpha, int beta) {
		if(depthLimit == 0) return state.heuristicValue(isPlayer1, choice);
		if(state.isEnd() == true) return state.heuristicValue(isPlayer1,choice);
		int v = Integer.MAX_VALUE;
		List<State> children = state.getChildren();
		for (State s : children) {
			int maxval = 0;
			if(state.getStart() == s.getStart()) maxval = MinValue(s, depthLimit, alpha, beta);
			else maxval = MaxValue(s, depthLimit -1,alpha,beta);
			v = Math.min(v, maxval);
			if(v < alpha) return v;
			beta = Math.min(beta, v);
		}
		
		return v;
	}
}
