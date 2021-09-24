package com.quinton.game.graphics;

import java.util.Random;

import com.quinton.game.entity.mob.Chaser;
import com.quinton.game.entity.mob.Mob;
import com.quinton.game.entity.mob.Star;
import com.quinton.game.entity.projectile.Projectile;
import com.quinton.game.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 8;
	// repeats every 7 tiles
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int xOffset, yOffset;
	
	public int[] tiles = new int[64*64];
	
	private Random random = new Random ();
	
	int time=0;
	int counter=0;
	
	
	public Screen(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		//array of pixels
		
		pixels = new int[width*height];
		
		for (int i=0; i<64*64;i++) {
			
			//generates tiles with random color starting from black to white
			
			tiles[i]=random.nextInt(0xffffff);
		}
		
	}
	
	public void clear() {
		
		for (int i=0;i<pixels.length;i++) {
			pixels[i]=0;
		}
	}
	
	/*
	public void render(int xOffset, int yOffset) {
		
		counter++;
		
		if (counter%100==0) time++;
		
		for (int y=0; y<height;y++) {
			
			int yp = y + yOffset;
			
			
			//prevents out of bounds exception
			
			if(yp<0||yp>=height) continue;
				
			
			
			
			for (int x=0;x<width;x++) {	
				
				int xp = x + xOffset;
				
				if(xp<0||xp>=width) continue;
				// looking for tiles
				// >> 4 means divides by 2^4=16, reduces fps
				// check if is greater than map_size_mask, if it is, it returns to 0
				// int tileIndex = (xx>>4 & MAP_SIZE_MASK) + (yy>>4 & MAP_SIZE_MASK) * MAP_SIZE;
 				// pixels[x + y*width] = tiles[tileIndex];
 				pixels[xp + yp*width] = Sprite.grass.pixels[(x&15) + (y&15) * Sprite.grass.SIZE];
			}
		}
	}
	*/
	
	// boolean fixed = true means it is fixed on the map, if not, fixed on the screen
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp-=xOffset;
			yp-=yOffset;
		}
		
		for(int y=0;y<sprite.getHeight();y++) {
			int ya = y + yp;
			for(int x=0;x<sprite.getWidth();x++) {
				int xa = x + xp;
				if (xa<0||xa>=width||ya<0||ya>=height) continue;
				pixels[xa+ya*width] = sprite.pixels[x+y*sprite.getWidth()];
			}
		}
	}
	
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp-=xOffset;
			yp-=yOffset;
		}
		
		for(int y=0;y<sheet.HEIGHT;y++) {
			int ya = y + yp;
			for(int x=0;x<sheet.WIDTH;x++) {
				int xa = x + xp;
				if (xa<0||xa>=width||ya<0||ya>=height) continue;
				pixels[xa+ya*width] = sheet.pixels[x+y*sheet.WIDTH];
			}
		}
	}

	
public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		
		//replacing location of tiles following movement of player
		xp-=xOffset;
		yp-=yOffset;
		
		for(int y=0;y<32;y++) {
			//absolute position of sprite
			int ya = y+yp;
			int ys = y;
			if (flip==2||flip==3) {
				ys = 31 - y;
				
			}
			
			for(int x=0;x<32;x++) {
				int xa = x+xp;
				int xs = x;
				// flipping image by rendering from end of array
				if (flip==1||flip==3) {
					xs = 31 - x;				
				}
				if(xa<-32||xa>=width||ya<0||ya>=height) break;
				if(xa<0) xa=0;
				int color = sprite.pixels[xs+ys*32];
				// removes pink background of player
				if (color!=0xffff00ff) pixels[xa+ya*width] = color;
				

		
			}
		}
	}

	// for chaser
	public void renderChaser(int d, int e, Mob mob) {
		
		//replacing location of tiles following movement of player
		d-=xOffset;
		e-=yOffset;
		
		for(int y=0;y<32;y++) {
			//absolute position of sprite
			int ya = y+e;
			int ys = y;
			
			for(int x=0;x<32;x++) {
				int xa = x+d;
				int xs = x;

				if(xa<-32||xa>=width||ya<0||ya>=height) break;
				if(xa<0) xa=0;
				int color = mob.getSprite().pixels[xs+ys*32];
				//changes blue color to red color
				if (mob instanceof Chaser && color == 0xff472BBF ) color = 0xffBA0015;
				if (mob instanceof Star && color == 0xff472BBF) color = 0xffE8E83A; // darkish yellow color
				// removes pink background of player
				if (color!=0xffff00ff) pixels[xa+ya*width] = color;
				
	
		
			}
		}
	}
	// for projectile
	public void renderProjectile(int xp, int yp, Projectile p) {
		
	
		xp-=xOffset;
		yp-=yOffset;
		
		for(int y=0;y<p.getSpriteSize();y++) {
		
			int ya = y+yp;
			
			for(int x=0;x<p.getSpriteSize();x++) {
				int xa = x+xp;
			
				if(xa<-p.getSpriteSize()||xa>=width||ya<0||ya>=height) break;				
				if(xa<0) xa=0;				
				int col = p.getSprite().pixels[x+y*p.getSpriteSize()];
				if (col!=0xffff00ff) pixels[xa+ya*width] = col;
				
				
	
		
			}
		}
	}
	
	// for tiles of map
	public void renderTile(int xp, int yp, Tile tile ) {
		
		//replacing location of tiles following movement of player
		xp-=xOffset;
		yp-=yOffset;
		
		for(int y=0;y<tile.sprite.SIZE;y++) {
			//absolute position of sprite
			int ya = y+yp;
			
			for(int x=0;x<tile.sprite.SIZE;x++) {
				int xa = x+xp;
				// only render the tile we see
				if(xa<-tile.sprite.SIZE||xa>=width||ya<0||ya>=height) break;
				// remove black border by making sure incomplete tiles are rendered also
				if(xa<0) xa=0;
				// determine what sprite is it, etc grass, rock, ...
				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];
				

		
			}
		}
	}
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
