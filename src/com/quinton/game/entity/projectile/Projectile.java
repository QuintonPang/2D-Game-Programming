package com.quinton.game.entity.projectile;

import java.util.Random;

import com.quinton.game.entity.Entity;
import com.quinton.game.graphics.Sprite;

public abstract class Projectile extends Entity {

	// where projectile spawns
	final protected double xOrigin, yOrigin;
	
	//overwrites x,y of entity for higher precision
	protected double x, y;
	
	protected double angle;
	protected Sprite sprite;
	
	// new x new y
	protected double nx, ny;
	
	protected double speed, /*rateOfFire, */range, damage;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	protected void move() {
		
	}
}
