package com.quinton.game.level;

import com.quinton.game.util.Vector2i;

public class Node {

	public Vector2i tile;
	public Node parent; //from which point the mob came from to trace the path
	public double fCost,gCost,hCost;
	// fCost calculates fastest path
	// gCost calculates how long the path takes
	// hCost is the straight line to destination
	
	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost= hCost;
		this.fCost = this.gCost + this.hCost;
	}
}
