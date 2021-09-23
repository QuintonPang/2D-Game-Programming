package com.quinton.game.level;

import java.util.ArrayList;
import java.util.List;

import com.quinton.game.entity.Entity;
import com.quinton.game.entity.mob.Player;
import com.quinton.game.entity.particle.Particle;
import com.quinton.game.entity.projectile.Projectile;
import com.quinton.game.entity.spawner.Spawner;
import com.quinton.game.graphics.Screen;
import com.quinton.game.level.tile.Tile;

public class Level {
	
	//protected Tile[] tiles;
	protected int width, height;
	protected int[] tilesInt;
	// store pixels of level
	protected int[] tiles;
	
	protected List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();
	
	//public static Level level = new Level("/textures/levels/level.png");
	public static Level spawn = new SpawnLevel("/textures/levels/spawn.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width*height];
		generateLevel();
	}
	
	// directory of level
	public Level(String path) {
		
		loadLevel(path);
		generateLevel();
		
		//add(new Spawner(16*16,62*16,Spawner.Type.PARTICLE, 500, this));
	}

	protected void loadLevel(String path) {
		
		//load level from path
		
		
	}

	protected void generateLevel() {
		
		
	}
	
	public List<Projectile> getProjectiles(){
		return projectiles;
	}
	
	//x and y is position of entity, xa and xy is where it is heading, size is size of object
	public boolean tileCollision(int x, int y,  int size, int xOffset, int yOffset) {
		boolean solid = false;
		// edit space of collision
		for(int c=0;c<4;c++) {
			//check corners
			//width of collision area
			//int xt = (((int)x + (int)xa) + c % 2 * size / 10 - 4) / 16;
			
			// more accurate collision area
			int xt = ( x - c % 2 * size + xOffset) >> 4;
			
			//height of collision area
			///int yt = (((int)y + (int)ya) + c / 2 * size / 10 + 5) >> 4;
		
			int yt = ( y - c / 2 * size + yOffset) >> 4;
			if(getTile(xt, yt).solid()) solid = true;
		}

		return solid;
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}

		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for(int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}

		remove();
	}

	private void remove() {
		for(int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}

		for(int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for(int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}

	
	
	private void time() {
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		
		
		screen.setOffset(xScroll, yScroll);
		//find coordinates of tiles of corners
		int x0 = xScroll>>4;		//left up
		int x1 = (xScroll+screen.width+16)>>4; //right up // +16 to cover black borders on the right and bottom
		int y0 = yScroll>>4;		//left bottom
		int y1 = (yScroll+screen.height+16)>>4; //right bottom
		
		//render from corners to corners
		for(int y=y0;y<y1;y++) {
			for(int x=x0;x<x1;x++) {
				
				//find type of tile and render it
				getTile(x,y).render(x, y, screen);
				
				//prevent out of bounds exception when moving out of the map
				/*if(x+y*16<0 || x+y*16>=256) {
					Tile.voidTile.render(x, y, screen);
					continue;
					
				}
				tiles[x+y*16].render(x, y, screen);
				*/
			}
		}
		
		for(int i = 0; i< entities.size(); i++) {
			entities.get(i).render(screen);
		}
		
		for(int i = 0; i< projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for(int i = 0; i< particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for(int i = 0; i< players.size(); i++) {
			players.get(i).render(screen);
		}
	}
	
	
	public void add(Entity e) {
		//check type
		e.init(this);
		if (e instanceof Particle) {
			
			particles.add((Particle) e);
			
		}else if(e instanceof Projectile) {
			
			projectiles.add((Projectile) e);
		}else if(e instanceof Player) {
			
			players.add((Player) e);
		}else {
			entities.add(e);
		}
		
	}
	
	/*
	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}
	*/
	
	// return tile to be generated
	public Tile getTile(int x, int y) {
		//prevent out of bounds exception when moving out of the map
		/*if(x<0||x>=width||y<0||y>=height) return Tile.voidTile;
		if(tilesInt[x+y*width]==0) return Tile.grass;
		if(tilesInt[x+y*width]==1) return Tile.flower;
		if(tilesInt[x+y*width]==2) return Tile.rock;
		// if blank
		return Tile.voidTile;
		*/
		
		//prevent out of bounds exception when moving out of the map
			/*	if(x<0||x>=width||y<0||y>=height) return Tile.voidTile;
				if(tiles[x+y*width]==0xff00ff00) return Tile.grass;
				if(tiles[x+y*width]==0xffffff00) return Tile.flower;
				if(tiles[x+y*width]==0xff7f7f00) return Tile.rock;
				// if blank
				return Tile.voidTile;
				
				*/
		
		if(x<0||x>=width||y<0||y>=height) return Tile.voidTile;
		if(tiles[x+y*width]==Tile.col_spawn_floor) return Tile.spawn_floor;
		if(tiles[x+y*width]==Tile.col_spawn_grass) return Tile.spawn_grass;
		if(tiles[x+y*width]==Tile.col_spawn_hedge) return Tile.spawn_hedge;
		if(tiles[x+y*width]==Tile.col_spawn_wall1) return Tile.spawn_wall1;
		if(tiles[x+y*width]==Tile.col_spawn_wall2) return Tile.spawn_wall2;
		if(tiles[x+y*width]==Tile.col_spawn_water) return Tile.spawn_water;
		// if blank
		return Tile.voidTile;
		
	}
	
	public List<Player>getPlayer() {
		return players;
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	// get first player
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	// find nearby entities
	public List<Entity>getEntities(Entity e, int radius){
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for (int i=0; i<entities.size();i++) {
			Entity entity = entities.get(i);
			int x = (int)entity.getX();
			int y = (int)entity.getY();
			
			// get distance from mob to player
			int dx = Math.abs(x-ex);
			int dy = Math.abs(y-ey);
			double dist = Math.sqrt(dx*dx + dy *dy);
			if (dist<=radius) result.add(entity);
		}
		
		return result;
	}
	// find nearby players
	public List<Player> getPlayers(Entity e, int radius){
		List<Player> result = new ArrayList<Player>();
		
		for (int i =0;i<players.size();i++) {
			Player player = players.get(i);
			int x = (int)player.getX();
			int y =(int) player.getY();
			
			int ex = (int)e.getX();
			int ey = (int)e.getY();
			// get distance from mob to player
			int dx = Math.abs(x-ex);
			int dy = Math.abs(y-ey);
			double dist = Math.sqrt(dx*dx + dy *dy);
			if (dist<=radius) result.add(player);

		}
		return result;
	}
}
