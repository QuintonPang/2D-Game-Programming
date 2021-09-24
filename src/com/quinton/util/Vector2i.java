package com.quinton.util;

// A* STAR SEARCH ALGORITHM
// 2 means two coordinates, i means return integer
public class Vector2i {
	private int x,y;
	
	public Vector2i() {
		set(0,0); 
	}
	
	// set mob's position according to player's position
	public Vector2i(Vector2i vector) {
		set(vector.x,vector.y);
	}
	
	public Vector2i(int x, int y) {
		set(x,y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == this.getX() && vec.getY() ==  this.getY()) return true;
		return false;
	}
	
	public void test() {
		Vector2i player_position = new Vector2i(80,40).setX(15);
		Vector2i mob_position = new Vector2i(player_position).setX(50);
	}
	
	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2i subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public Vector2i setX(int x) {
		
		this.x = x;
		return this;
	}
	
	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
