package com.quinton.game.entity.mob;

import java.util.List;

import com.quinton.game.entity.mob.Mob.Direction;
import com.quinton.game.graphics.AnimatedSprite;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;

public class Chaser extends Mob {


	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);
	
	private AnimatedSprite animSprite = null;
	
	private boolean walking = false;
	private int xa, ya = 0;
	
	public Chaser(int x, int y) {
			this.x = x<<4;
			this.y = y<<4;
			this.sprite = Sprite.dummy;
			animSprite = down;
	}

	@Override
	public void update() {
		
		move();
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya<0) { 
			dir = Direction.UP;	
			animSprite = up;
		}
		if (ya>0) {
			dir = Direction.DOWN;
			animSprite = down;
		}
		if (xa<0) {
			dir = Direction.LEFT;
			animSprite = left;
		}
		if(xa>0) {
			dir = Direction.RIGHT;
			animSprite = right;
			
		}
		
	
	
		
	}

	@Override
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderChaser((int)(x-16) ,(int)(y-16), this);
		
	}
	
	public void move() {
		
		xa = 0;
		ya = 0;
		
		//Player player = level.getClientPlayer();
		//List<Player> players = level.getPlayers(this, 50);
		
		List<Mob> players = level.getPlayers(this, 50);
		if (players.size()>0){
			//Player player = players.get(0);
			Mob player = players.get(0);
			//moves towards player
			if(x<player.getX()) xa++;
			if(x>player.getX()) xa--;
			if(y<player.getY()) ya++;
			if(y>player.getY()) ya--;
			
			
			if (xa!=0||ya!=0) {
				
				walking = true;
				move(xa,ya);
			} else {
				walking = false;
			}
		
		}else walking=false; // stops when there is no player around
	}
}
