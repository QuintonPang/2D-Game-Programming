package com.quinton.game.mob.entity.mob;

//import java.util.ArrayList;
//import java.util.List;

import com.quinton.game.entity.Entity;
//import com.quinton.game.entity.particle.Particle;
import com.quinton.game.entity.projectile.Projectile;
import com.quinton.game.entity.projectile.WizardProjectile;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	//direction, 0 north, 1 east, 2 south, 3 west
	protected int dir = 0;
	protected boolean moving = false;
	
	//protected List<Projectile> projectiles = new ArrayList<Projectile>();
	
	public void move(int xa, int ya) {
		
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
		if(xa>0) dir = 1;
		if(xa<0) dir = 3;
		if(ya>0) dir = 2;
		if(ya<0) dir = 0;
		
		if(!collision(xa,ya)) {
			x+=xa;
			y+=ya;
		}/*else {
			Particle p = new Particle(x,y,50,500);
			level.add(p);
		}*/
	}
	
	public abstract void update();
	
	protected void shoot (int x, int y, double dir) {
		//dir = Math.toDegrees(dir);
		//System.out.println("Angle: " + dir);
		Projectile p = new WizardProjectile(x,y,dir);
		//projectiles.add(p);
		//level.addProjectile(p);
		level.add(p);
		
	}
	
	public abstract void render(Screen screen);
	
	private boolean collision(int xa, int ya) {
		boolean solid = false;
		// edit space of collision
		for(int c=0;c<4;c++) {
			//check corners
			//width of collision area
			int xt = ((x + xa) + c % 2 * 14 - 8) / 16;
			//height of collision area
			int yt = ((y + ya) + c / 2 * 12 + 3) / 16;
			if(level.getTile(xt, yt).solid()) solid = true;
		}

		return solid;
	}
}
