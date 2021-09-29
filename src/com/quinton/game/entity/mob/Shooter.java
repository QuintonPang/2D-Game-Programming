package com.quinton.game.entity.mob;

import java.util.Collections;
import java.util.List;

import com.quinton.game.entity.Entity;
import com.quinton.game.entity.mob.Mob.Direction;
import com.quinton.game.entity.particle.Particle;
import com.quinton.game.entity.spawner.ParticleSpawner;
import com.quinton.game.graphics.AnimatedSprite;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;
import com.quinton.game.util.Debug;
import com.quinton.game.util.Vector2i;

public class Shooter extends Mob {


	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);
	
	private AnimatedSprite animSprite;
	
	private Entity rand = null;
	
	int xa = 0, ya = 0;
	
	
	private int time = 0;
	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
		
	}
	
	public void update() {
		
		//shootClosest();
		shootRandom();
		
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
			//move(xa,ya);
		} else {
			walking = false;
		}
		
	}
	
	private void shootRandom() {

	
		List<Entity> entities = level.getEntities(this, 500);
		entities.add(level.getClientPlayer());
		
			
		if(time%(30 + random.nextInt(91))==0) {
			int index = random.nextInt(entities.size());
			
			rand = entities.get(index);
			
		}
		
		//Collections.shuffle(entities);
		
		
		
		if(entities.size()>0) {
			//System.out.println(rand);
			//rand = entities.get(0);
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double direction = Math.atan2(dy, dx);
			shoot(x, y , direction);
		}

		//double px = p.getX();
		//double py = p.getY();
		//double dx = px - x;
		//double dy = py - y;
		
		if (rand!=null) {
			//System.out.println(rand);
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double direction = Math.atan2(dy, dx);
			shoot(x, y , direction);
		}
			
		
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x-16) ,(int)(y-16), sprite, 0);
		Debug.drawRect(screen, 50, 50, 16, 16, true);
		}
	
	private void shootClosest() {
		//Player p = level.getClientPlayer();
				List<Entity> entities = level.getEntities(this, 50);
				entities.add(level.getClientPlayer());
				double min = 0;
				Entity closest = null;
				
				for(int i=0;i<entities.size();i++) {
					Entity e = entities.get(i);
					double distance = Vector2i.getDistance(new Vector2i((int)x,(int)y),new Vector2i((int)e.getX(),(int)e.getY()));
					// find shortest distance
					if (i==0||distance<min) {
						min = distance;
						closest = e;
					}
				}
				
				
				//double px = p.getX();
				//double py = p.getY();
				//double dx = px - x;
				//double dy = py - y;
				
				if (closest!=null) {
					double dx = closest.getX() - x;
					double dy = closest.getY() - y;
					double direction = Math.atan2(dy, dx);
					shoot(x, y , direction);
				}
				
	}
}
