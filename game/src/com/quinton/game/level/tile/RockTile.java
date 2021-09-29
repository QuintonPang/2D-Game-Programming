package com.quinton.game.level.tile;

import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x,int y, Screen screen) {
		
		// <<4 means multiply by 2^4
		screen.renderTile(x<<4, y<<4, this);

	}
	
	public boolean solid() {
		return true;
	}
}
