package com.quinton.game.mob.entity.mob;

import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;

public class Dummy extends Mob{
	
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.player_forward;
	}
	
	public void update() {
		
		
	}
	
	public void render(Screen screen) {
		screen.renderMob(x, y, sprite, 0);
	}
}


