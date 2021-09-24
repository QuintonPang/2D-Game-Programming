package com.quinton.game.entity.mob;

import java.util.List;

import com.quinton.game.entity.mob.Mob.Direction;
import com.quinton.game.graphics.AnimatedSprite;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;
import com.quinton.game.level.Node;
import com.quinton.game.util.Vector2i;

public class Star extends Mob {


	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);
	
	private AnimatedSprite animSprite = null;
	
	private boolean walking = false;
	private double xa, ya = 0;
	private List <Node> path;
	private int time = 0;
	private double speed = 0.5;
	
	public Star(int x, int y) {
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
		
	
		if (xa!=0||ya!=0) {
			
			walking = true;
			move(xa,ya);
		} else {
			walking = false;
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
		
		// player location 
		int px = (int)level.getPlayerAt(0).getX();
		int py = (int)level.getPlayerAt(0).getY();
		
		// current location of star
		Vector2i start = new Vector2i ((int)getX() >> 4,(int) getY() >> 4);
		Vector2i destination = new Vector2i(px>>4,py>>4);
		
		// recalculates path every second
		if (time%60==0) path = level.findPath(start, destination);
		if (path!=null) {
		
			if(path.size()>0) {
				
				// starts from the last of array which is nearest to star
				Vector2i vec = path.get(path.size()-1).tile;
				if (x< vec.getX()<<4) xa+=speed;
				if (x> vec.getX()<<4) xa-=speed;
				if (y< vec.getY()<<4) ya+=speed;
				if (y> vec.getY()<<4) ya-=speed;
			}
		}
		
		//Player player = level.getClientPlayer();
		List<Player> players = level.getPlayers(this, 50);
		if (players.size()>0){
			Player player = players.get(0);
			
			

			if (xa!=0||ya!=0) {
				
				walking = true;
				move(xa,ya);
			} else {
				walking = false;
			}
		
		}
	}
}

