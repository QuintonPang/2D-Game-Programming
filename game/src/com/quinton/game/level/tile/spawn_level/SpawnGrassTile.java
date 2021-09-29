package com.quinton.game.level.tile.spawn_level;

import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.level.tile.Tile;

public class SpawnGrassTile extends Tile {

	
	public SpawnGrassTile(Sprite sprite) {
		super(sprite);
	}
		
	public void render(int x,int y, Screen screen) {
			
		screen.renderTile(x<<4, y<<4, this);
		
	}
	

}
