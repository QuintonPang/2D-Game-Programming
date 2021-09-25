package com.quinton.game.graphics;

public class Font {

	private static SpriteSheet font = new SpriteSheet("/fonts/arial.png",16);
	// Extract sheet cells into individual sprites
	private static Sprite[] characters = Sprite.split(font);
	
	public Font() {
		
	}
	
	public void render(Screen screen) {
		screen.renderSprite(50, 50, characters[50], false);
	}
}
