package com.quinton.game.level.tile;

import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;
import com.quinton.game.level.tile.spawn_level.SpawnFloorTile;
import com.quinton.game.level.tile.spawn_level.SpawnGrassTile;
import com.quinton.game.level.tile.spawn_level.SpawnHedgeTile;
import com.quinton.game.level.tile.spawn_level.SpawnWallTile;
import com.quinton.game.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
	
	// Coordinates of a tile
	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	// Spawn Level Tiles
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	// color representing each tiles
	public final static int col_spawn_grass = 0xff00ff1D;
	public final static int col_spawn_hedge = 0; //unused
	public final static int col_spawn_water = 0;  //unused
	public final static int col_spawn_wall1 = 0xff727272;
	public final static int col_spawn_wall2 = 0xff000000;
	public final static int col_spawn_floor = 0xff7F3300;
	
	public Tile(Sprite sprite) {
		this.sprite=sprite;
	}
	
	// default, to prevent error when object is tile obj = new GrassTile():
	public void render(int x, int y, Screen screen) {
		
		
		
	}
	
	// if player can walk through the sprite
	public boolean solid() {
		// by default false
		return false;
	}

}
