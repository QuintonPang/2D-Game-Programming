package com.quinton.game.graphics;

public class Sprite {

	public final int SIZE;
	private int x,y;
	public int[] pixels;
	protected SpriteSheet sheet;
	private int width, height;
	
	public static Sprite grass = new Sprite(16,0,0,SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16,1,0,SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16,2,0,SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);
	
	// Spawn Level Sprites
	public static Sprite spawn_grass = new Sprite(16,0,0,SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16,1,0,SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16,2,0,SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16,0,1,SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16,0,2,SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16,1,1,SpriteSheet.spawn_level);
	
	//player sprite
	/*public static Sprite player0 = new Sprite(16,0,10,SpriteSheet.tiles);
	public static Sprite player1 = new Sprite(16,1,10,SpriteSheet.tiles);
	public static Sprite player2 = new Sprite(16,0,11,SpriteSheet.tiles);
	public static Sprite player3 = new Sprite(16,1,11,SpriteSheet.tiles);
	*/
	
	//player sprite with size 32*32
	// position in spritesheet counts by 32 pixels
	public static Sprite player_forward = new Sprite(32,2,5,SpriteSheet.tiles);
	public static Sprite player_back = new Sprite(32,0,5,SpriteSheet.tiles);
	//public static Sprite player_left = new Sprite(32,3,5,SpriteSheet.tiles);
	//public static Sprite player_right = new Sprite(32,1,5,SpriteSheet.tiles);
	public static Sprite player_side = new Sprite(32,1,5,SpriteSheet.tiles);
	
	public static Sprite player_forward1 = new Sprite(32,2,6,SpriteSheet.tiles);
	public static Sprite player_back1 = new Sprite(32,0,6,SpriteSheet.tiles);
	public static Sprite player_side1 = new Sprite(32,1,6,SpriteSheet.tiles);
	
	public static Sprite player_forward2 = new Sprite(32,2,7,SpriteSheet.tiles);
	public static Sprite player_back2 = new Sprite(32,0,7,SpriteSheet.tiles);
	public static Sprite player_side2 = new Sprite(32,1,7,SpriteSheet.tiles);
	
	
	
	//projectile sprite
	public static Sprite projectile_wizard = new Sprite(16,0,0,SpriteSheet.projectile_wizard);
	
	//particles
	public static Sprite particle_normal = new Sprite(3,0xAAAAAA);
	
	//dummy
	public static Sprite dummy= new Sprite(32,0,0,SpriteSheet.dummy);
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
	
		SIZE = width==height?width:-1;
		this.width = width;
		this.height = height;
		this.sheet = sheet; 

	}
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE*SIZE];
		this.x = x*size;
		this.y = y*size;
		this.sheet = sheet; 
		load();
	}
	
	//constructor for void sprite
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE*SIZE];
		setColor(color);
	}
	
	//constructor for rectangular sprite
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
			
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = width==height?width:-1;
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		// forces new pixels to be created instead of being referenced to the same memory address
		//for(int i = 0;i<pixels.length;i++) this.pixels[i] = pixels[i];
		
		// same effect
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
		//this.pixels = pixels;

	}
	
	
	private void setColor(int color) {
		for (int i=0;i<pixels.length;i++) {
			pixels[i] = color;
		}
		
	}

	private void load() {
		for (int y=0; y<height;y++) {
			
			for (int x=0; x<width; x++) {
				pixels[x+y*width] = sheet.pixels[(x+this.x)+(y + this.y ) *sheet.SPRITE_WIDTH];
			
			}
		}
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public static Sprite[] split(SpriteSheet sheet) {
		 int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		 Sprite[] sprites = new Sprite[amount];
		 
		 int[] pixels = new int[sheet.SPRITE_WIDTH*sheet.SPRITE_HEIGHT];
		 int current = 0;
		 
		 for (int yp = 0; yp<sheet.getHeight()/sheet.SPRITE_HEIGHT;yp++) {
			 for(int xp = 0; xp<sheet.getWidth()/sheet.SPRITE_WIDTH;xp++) {
				 //gets a sprite
				 
				 for(int y=0;y<sheet.SPRITE_HEIGHT;y++) {
					 for(int x=0; x<sheet.SPRITE_WIDTH;x++) {
						 // gets pixels of a sprite
						 int xo = x + xp * sheet.SPRITE_WIDTH;
						 int yo = y + yp * sheet.SPRITE_HEIGHT;
		
						 pixels[x+y*sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					 }
				 }
			 
				 sprites[current++]=new Sprite(pixels,sheet.SPRITE_WIDTH,sheet.SPRITE_HEIGHT);
			 
			 }
		 }
		 
		 return sprites;
	}
}
