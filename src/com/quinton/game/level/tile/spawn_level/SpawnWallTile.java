package com.quinton.game.level.tile.spawn_level;

import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.level.tile.Tile;

public class SpawnWallTile extends Tile {

	public SpawnWallTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x,int y, Screen screen) {
		
		screen.renderTile(x<<4, y<<4, this);
		
	}
	
	public boolean solid() {
		return true;
	}

}
