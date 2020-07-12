package mancala;

import java.util.List;

public class MiniMax {
	State nexState = null;
	int cutLimit;
	boolean isPlayer1;
	int choice;
	public State MiniMaxDecision(State state,int depthLimit, boolean isPlayer1,int choice) {
		cutLimit = depthLimit;
		this.isPlayer1 = isPlayer1;
		this.choice = choice;
		int v = MaxValue(state, depthLimit);
		//System.out.println("Max val: "+v);
		return nexState;
	}
	
	public int MaxValue(State state , int depthLimit) {
		if(depthLimit == 0) return state.heuristicValue(isPlayer1,choice);
		if(state.isEnd() == true) return state.heuristicValue(isPlayer1,choice);
		int v = Integer.MIN_VALUE;
		State s = null;
		List<State> children = state.getChildren();
		for (State state2 : children) {
			int minval = MinValue(state2, depthLimit-1);
			if(v < minval) {
				v = minval;
				if(depthLimit == cutLimit) nexState = state2;
			}
		}
		return v;
	}
	
	public int MinValue(State state, int depthLimit) {
		if(depthLimit == 0) return state.heuristicValue(isPlayer1,choice);
		if(state.isEnd() == true) return state.heuristicValue(isPlayer1,choice);
		int v = Integer.MAX_VALUE;
		List<State> children = state.getChildren();
		for (State s : children) {
			int maxval = MaxValue(s, depthLimit -1);
			v = Math.min(v, maxval);
		}
		
		return v;
	}
}
