package com.quinton.game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.quinton.game.entity.Entity;
import com.quinton.game.entity.mob.Mob;
import com.quinton.game.entity.mob.Player;
import com.quinton.game.entity.particle.Particle;
import com.quinton.game.entity.projectile.Projectile;
import com.quinton.game.entity.spawner.ParticleSpawner;
import com.quinton.game.entity.spawner.Spawner;
import com.quinton.game.events.Event;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.layers.Layer;
import com.quinton.game.level.tile.Tile;
import com.quinton.game.util.Vector2i;

public class Level extends Layer{
	
	//protected Tile[] tiles;
	protected int width, height;
	protected int[] tilesInt;
	// store pixels of level
	protected int[] tiles;
	
	protected List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	//private List<Player> players = new ArrayList<Player>();
	
	private List<Mob> players = new ArrayList<Mob>();
	
	private int xScroll, yScroll;
	
	// sorter to sort paths
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1; // moves forward
			if (n1.fCost > n0.fCost) return -1; // moves backwards
			return 0;
		}
	};
	
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

	
	/*
	private void time() {
		
	}
	*/
	
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}
	
	public void render(/*int xScroll, int yScroll,*/ Screen screen) {
		
		
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
	
	/*
	public List<Player>getPlayers() {
		return players;
	}
	*/
	
	public List<Mob>getPlayers() {
		return players;
	}
	
	/*
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	*/
	
	public Mob getPlayerAt(int index) {
		return players.get(index);
	}
	/*
	// get first player
	public Player getClientPlayer() {
		return players.get(0);
	}
	*/
	
	public Player getClientPlayer() {
		return (Player)players.get(0);
	}
	
	// find nearby entities
	public List<Entity>getEntities(Entity e, int radius){
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for (int i=0; i<entities.size();i++) {
			Entity entity = entities.get(i);
			// preventing from returning itself
			if(entity.equals(e)) continue;
			int x = (int)entity.getX();
			int y = (int)entity.getY();
			
			// prevent from getting particles
			if(entity instanceof ParticleSpawner) continue;
			
			// get distance from mob to player
			int dx = Math.abs(x-ex);
			int dy = Math.abs(y-ey);
			double dist = Math.sqrt(dx*dx + dy *dy);
			if (dist<=radius) result.add(entity);
		}
		
		return result;
	}
	/*
	// find nearby players
	public List<Player> getPlayers(Entity e, int radius){
		List<Player> result = new ArrayList<Player>();
		
		for (int i =0;i<players.size();i++) {
			//Player player = players.get(i);
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
	*/
	public List<Mob> getPlayers(Entity e, int radius){
		//List<Player> result = new ArrayList<Player>();
		List<Mob> result = new ArrayList<Mob>();
		
		for (int i =0;i<players.size();i++) {
			//Player player = players.get(i);
			Mob player = players.get(i);
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
	
	public List<Node> findPath(Vector2i start, Vector2i goal){
		// considered tiles
		 List<Node> openList = new ArrayList<Node>();
		 // not considered tiles
		 List<Node> closedList = new ArrayList<Node>();
		 // place to start
		 Node current = new Node(start,null,0, getDistance(start,goal));
		 openList.add(current);
		
		 while(openList.size()>0) {
			 Collections.sort(openList,nodeSorter);
			 current = openList.get(0);
			 
			 if(current.tile.equals(goal)) {
				 
				 List <Node> path = new ArrayList<Node>();
				 while (current.parent!=null) {
					 
					 path.add(current);
					 // backtraces the path
					 current = current.parent;

				 }
				 openList.clear();
				 closedList.clear();
				
				 return path;
			 }
			 
			
			 openList.remove(current);
			 closedList.add(current);

			 
			 // check adjacency
			 for (int i=0;i<9;i++) {
				 // removes middle tile which is the location being checked at instance
				 if ( i ==4) continue;
				 int x = current.tile.getX();
				 int y = current.tile.getY();
				 
				 // gets coordinates of adjacent tiles
				 int xi = (i%3) - 1;
				 int yi = (i/3) -1;
				 
				 Tile at = getTile(x+xi, y+yi);
				 if (at == null) continue;
				 if (at.solid()) continue;
				 Vector2i a = new Vector2i(x+xi,y+yi);
				 double gCost = current.gCost + (getDistance(current.tile,a)==1?1:0.95); // prefers diagonal over straight lines
				 double hCost = getDistance(a, goal);
				 Node node = new Node(a, current, gCost, hCost);
				 if (vecInList(closedList,a) && gCost>=node.gCost) continue;
				 if (!vecInList(openList,a) || gCost<node.gCost) openList.add(node);
			
			 }
		 }
		 
		 closedList.clear();
		 return null;
	}
	
	// check vectors if in list
	private boolean vecInList(List<Node> list,Vector2i vector) {
		for (Node n: list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	public double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void onEvent(Event event) {
		getClientPlayer().onEvent(event);
	}
	
	public void addPlayer(Mob player) {
		players.add(player);
		player.init(this);
	}
}
