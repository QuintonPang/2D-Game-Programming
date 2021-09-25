package com.quinton.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.quinton.game.entity.mob.Chaser;
import com.quinton.game.entity.mob.Dummy;
import com.quinton.game.entity.mob.Shooter;
import com.quinton.game.entity.mob.Star;
import com.quinton.game.level.tile.Tile;

public class SpawnLevel extends Level{
		
	//private Tile[] tiles;
	//int[] tiles;

	public SpawnLevel(String path) {
			
		super(path);
	}
		
	protected void loadLevel(String path) {
		
		
			try {
				BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
				int w = width = image.getWidth();
				int h = height = image.getHeight();	
				// same as int w = width, int w = image.getWidth();
				//tiles = new Tile[w*h];
				tiles = new int[w*h];				
				image.getRGB(0, 0, w, h, tiles, 0,w);
			}catch(IOException e){
				
				e.printStackTrace();
				System.out.println("Exception! Could not load level file!");
		
			}
			
			
			for (int i =0;i<5;i++) {
				add(new Dummy(20,65));
			}
			
			
			//add(new Star(17, 50));
			
			add(new Chaser(20,55));
			
			add(new Shooter(20,55));
			
	
			
			
			
		
	}
	
	// rendering tiles depending on color drawn
	// grass = 0xFF00 (green)
	// flower = 0XFFFF00 (yellow)
	// rock = 0x7F7F00 (dark yellow)
	// 0xff00, 0xff00ff00 are different in presence of '00'
	protected void generateLevel() {
		/*for (int i=0;i<levelPixels.length;i++) {
			if(levelPixels[i]==0xff00ff00) tiles[i] = Tile.grass;
			if(levelPixels[i]==0xffffff00) tiles[i] = Tile.flower;
			if(levelPixels[i]==0xff7f7f00) tiles[i] = Tile.rock; 
		}
		*/
	}
}
