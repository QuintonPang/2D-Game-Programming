package com.quinton.game.entity.mob;

import com.quinton.game.entity.mob.Mob.Direction;
import com.quinton.game.graphics.AnimatedSprite;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;

public class Shooter extends Mob {


	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);
	
	private AnimatedSprite animSprite;
	
	int xa = 0, ya = 0;
	
	
	private int time = 0;
	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
		
	}
	
	public void update() {
		
		Player p = level.getClientPlayer();
		double px = p.getX();
		double py = p.getY();
		double dx = px - x;
		double dy = py - y;
		double direction = Math.atan2(dy, dx);
		shoot(x, y , direction);
		
		
		time++;

		boolean walking = false;
		if(time%(random.nextInt(50)+30) ==0) {
			//ya = -ya;
			// returns number -1, 0 ,1 or 2
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			
			//stops randomly
			if (random.nextInt(4)==0) {
				xa = 0;
				ya = 0;
			}
		}
		//int xa = 0;
		//int ya = 0;
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya<0) { 
			animSprite = up;
			dir = Direction.UP;	
		}
		if (ya>0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa<0) {
			animSprite = left;
			dir = Direction.LEFT;
		}
		if(xa>0) {
			animSprite = right;
			dir = Direction.RIGHT;
			
		}
		
	
		if (xa!=0||ya!=0) {
			
			walking = true;
			move(xa,ya);
		} else {
			walking = false;
		}
		
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x-16) ,(int)(y-16), sprite, 0);
		
	}
}
