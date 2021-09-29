package com.quinton.game.entity.particle;
/*
import java.util.ArrayList;
import java.util.List;
*/
import com.quinton.game.entity.Entity;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;

public class Particle extends Entity {
	//private static List<Particle> particles = new ArrayList<Particle>();
	private Sprite sprite;
	
	private int life;
	
	//calculate how long particle exists
	private int time = 0;
	
	// xx and yy are doubles
	protected double xa, ya, za; 
	protected double xx, yy, zz;
	
	// single particle
	public Particle(int x, int y, int life) {
		
		sprite = Sprite.particle_normal;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		
		// disappears randomly
		this.life = life + (random.nextInt(20)-10);
		
		// random double between -1 and 1
		// adding 2.5 is to let particles bounce off the wall
		this.xa = random.nextGaussian(); // + 2.5;
		// prevents particles from falling through the wall
		//if(xa<0) xa = 0.1;
		this.ya = random.nextGaussian();
		
		// z is depth
		this.zz = random.nextFloat() + 2.0;
	}

	/*
	// multiple particles
	public Particle(int x, int y, int life, int amount) {
		
		//this counts as one particle, so we need to minus one from amount
		this(x, y, life);
		for (int i = 0; i<amount-1;i++) {
			particles.add(new Particle(x,y,life))
;		}
		particles.add(this);
	}
	*/
	
	public void update() {
		time++;
		
		// if time is huge
		if(time>=7400) time = 0;
		
		if(time> life)	remove();
		// rate of y falling downwards increases
		za-= 0.1;
		
		// when it hits the floor, the direction changes oppositely (bounces off the floor)
		if (zz <0) {
			zz = 0;
			za *= -0.55;
			
			// halves movement across x and y axis
			xa *= 0.4;
			ya *= 0.4;
		}
		
		move(xx+xa, (yy +ya) + (zz+za));
		/*this.xx += xa;
		this.yy += ya;
		this.zz += za;*/
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		// edit space of collision
		for(int c=0;c<4;c++) {
			
			//determines corners from left up to right up to left down to right down
			double xt = ( x - c % 2 * 16) / 16;
			double yt = ( y - c / 2 * 16) / 16;
			
			// convert to integer
			int ix = (int) Math.ceil(xt); //rounds off the number
			int iy = (int) Math.ceil(yt);
			if (c%2==0) ix = (int) Math.floor(xt);   // returns integer nearest and lesser
			if (c/2==0) iy = (int) Math.floor(yt);
			if(level.getTile(ix, iy).solid()) solid = true;
		}

		return solid;
	}
	private void move(double x, double y) {
		
		// changes direction of particles
		if (collision(x,y)) {
			this.xa*=-0.5;
			this.ya*=-0.5;
			this.za*=-0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;

		
	}

	public void render(Screen screen) {
		screen.renderSprite((int)xx -1 /*- 14 */,(int)yy - (int)zz - 1 ,sprite,true);
	}
}
