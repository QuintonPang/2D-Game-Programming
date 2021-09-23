package com.quinton.game.entity;

import java.util.Random;

import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.level.Level;

public abstract class Entity {
	
	protected double x,y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void render(Screen screen) {
		if (sprite!=null) screen.renderSprite((int) x, (int) y, sprite, true);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	//remove entity from level
	
	public void remove() {
		
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		
		this.level = level;
	}
	
	

}
