package com.quinton.game.net.player;

import com.quinton.game.entity.mob.Mob;
import com.quinton.game.graphics.Screen;

public class NetPlayer extends Mob {

	public NetPlayer() {
		x = 22 * 16;
		y = 42 * 16;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Screen screen) {
		screen.fillRect((int)x,(int) y, 32, 32, 0x2030cc, true);
		
	}
	
}
