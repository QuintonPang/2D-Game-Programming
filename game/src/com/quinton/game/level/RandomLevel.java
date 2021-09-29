package com.quinton.game.level;

import java.util.Random;

public class RandomLevel extends Level{
	
	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		
		// pass to parent class
		super(width,height);
	}
	
	protected void generateLevel() {
		
		for (int y=0; y<height;y++) {
			
			for (int x=0; x<width;x++) {
				
				// random tile id from 0 to 3
				tilesInt[x+y*width] = random.nextInt(4);
	
			}
		}
	}
}
