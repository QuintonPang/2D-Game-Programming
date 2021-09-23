package com.quinton.game.entity.projectile;

import com.quinton.game.entity.particle.Particle;
import com.quinton.game.entity.spawner.ParticleSpawner;
import com.quinton.game.entity.spawner.Spawner;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;

public class WizardProjectile extends Projectile{

	// external 
	public static final int FIRE_RATE = 15; // Higher is slower!
	
	public WizardProjectile(double x, double y, double dir) {
		super(x, y, dir);
		//range = random.nextInt(100);
		range = 200;
		speed = 4;
		damage = 20;
		//rateOfFire = 15; //time between projectiles
		sprite = Sprite.projectile_wizard;
		
		// vector
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {
		if (level.tileCollision((int)(x + nx) , (int)(y + ny), 6, 4 , 4)) {
			remove();
			level.add(new ParticleSpawner((int)x,(int)y,44, 50, level));
			//level.add(new Spawner((int)x,(int)y,Spawner.Type.PARTICLE, 50, level));
			//Particle p = new Particle((int)x,(int)y,50,500);
			//level.add(p);
		}
		move();
	}
	
	public void move() {
		/*if (!level.tileCollision(x, y, nx, ny, 6)) {
			x += nx;
			y += ny;
		}*/
		
		x += nx;
		y += ny;
		
		//System.out.println(distance());
		if(distance()>range) remove();
	}
	
	private double distance() {
	
		double dist = 0;
		dist = Math.sqrt((xOrigin-x)*(xOrigin-x)+(yOrigin-y)*(yOrigin-y));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x-12,(int)y-2,this);
	}

}
