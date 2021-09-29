package com.quinton.game.entity.spawner;

import java.util.ArrayList;
import java.util.List;

import com.quinton.game.entity.Entity;
import com.quinton.game.entity.particle.Particle;
import com.quinton.game.level.Level;

public class Spawner extends Entity {
	
	//private List<Entity> entities = new ArrayList<Entity>();
	
	// decide entity to spawn
	public enum Type{
		MOB, PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		this.init(level);
		this.x = x;
		this.y = y;
		this.type= type;
		/*
		for (int i = 0;i<amount;i++) {
			if (type == Type.PARTICLE) {
				level.add(new Particle(x,y,50));
			}
		}
		*/
	}
}
