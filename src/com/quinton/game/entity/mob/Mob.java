package com.quinton.game.entity.mob;

//import java.util.ArrayList;
//import java.util.List;

import com.quinton.game.entity.Entity;
//import com.quinton.game.entity.particle.Particle;
import com.quinton.game.entity.projectile.Projectile;
import com.quinton.game.entity.projectile.WizardProjectile;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;

public abstract class Mob extends Entity {

	//protected Sprite sprite;
	//direction, 0 north, 1 east, 2 south, 3 west
	//protected int dir = 0;
	protected boolean moving = false;
	
	protected int health;

	protected enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction dir;
	
	//protected List<Projectile> projectiles = new ArrayList<Projectile>();
	
	public void move(double xa, double ya) {
		
		//System.out.println("Size: "+projectiles.size());
		// moving vertically and horizontally at the same time
		// allow sliding
		if(xa!=0 && ya!=0) {
			move(xa,0);
			move(0,ya);
			//stops execution of function
			return;
		}
		
		//determine directions
		if(xa>0) dir = Direction.RIGHT;
		if(xa<0) dir = Direction.LEFT;
		if(ya>0) dir = Direction.DOWN;
		if(ya<0) dir = Direction.UP;
		
		// check collision for every one pixel of movement
		//for (int y = 0; y<Math.abs(ya); y++) {
		//}
		
		while (xa!=0) {
			
			 // check if can subtract by 1	
			 if(Math.abs(xa)>1 ) {
				 if  (!collision(abs(xa),ya)) {
					 this.x+=abs(xa);					 
				 }
				 xa-=abs(xa);
			 } else {
				 if  (!collision(abs(xa),ya) ) {
					 this.x+=xa;					 
				 }
				 
				 xa=0;
			 }
		}
		while (ya!=0) {
			
			 // check if can subtract by 1	
			 if(Math.abs(ya)>1 ) {
				 if  (!collision(xa,abs(ya))) {
					 this.y+=abs(ya);					 
				 }
				 ya-=abs(ya);
			 } else {
				 if  (!collision(xa, abs(ya))) {
					 this.y+=ya;					 
				 }
				 
				 ya=0;
			 }
		}
		//for (int x = 0; x<Math.abs(xa); x++) {
		//}
		if(!collision(xa,ya)) {
			//x+=xa;
			//y+=ya;
		}/*else {
			Particle p = new Particle(x,y,50,500);
			level.add(p);
		}*/
	
	}
	
	public int abs(double i) {
		if (i<0) return -1;
		return 1;
	}
	public abstract void update();
	
	protected void shoot (double x, double y, double dir) {
		
		//dir = Math.toDegrees(dir);
		//System.out.println("Angle: " + dir);
		
		Projectile p = new WizardProjectile(x,y,dir);
		level.add(p);
		//projectiles.add(p); 
		
	}
	
	public abstract void render(Screen screen);
	
	private boolean collision(double xa, double ya) {
		boolean solid = false;
		// edit space of collision
		for(int c=0;c<4;c++) {
			//check corners
			//width of collision area
			double xt = ((x + xa ) - c % 2* 15) / 16;
			//height of collision area
			double yt = ((y + ya ) - c / 2 * 15) / 16;
			
			// convert to integer
			int ix = (int) Math.ceil(xt); //rounds off the number
			int iy = (int) Math.ceil(yt);
			if (c%2==0) ix = (int) Math.floor(xt);   // returns integer nearest and lesser
			if (c/2==0) iy = (int) Math.floor(yt);
			if(level.getTile(ix, iy).solid()) solid = true;
		}

		return solid;
	}
}
