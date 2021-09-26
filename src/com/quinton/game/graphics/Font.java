package com.quinton.game.graphics;

public class Font {

	private static SpriteSheet font = new SpriteSheet("/fonts/arial.png",16);
	// Extract sheet cells into individual sprites
	private static Sprite[] characters = Sprite.split(font);
	
	// commenting is form different lines for readability
	private static String charIndex = "ABCDEFGHIJKLM" + //
										"NOPQRSTUVWXYZ" + //
										"abcdefghijklm" + //
										"nopqrstuvwxyz" + //
										"0123456789.,‘" + //
										"’“”;:!@$%()-+";
	
	public Font() {
		
	}
	
	//default spacing of 16 pixels
	public void render(int x, int y, String text, Screen screen) {
		render(x,y,16,0,text,screen);
	}
	
	
	public void render(int x, int y, int color,String text, Screen screen) {
		render(x,y,0,color,text,screen);
	}
	
	
	public void render(int x, int y, int spacing, int color,String text, Screen screen) {
		int line = 0;
		int xOffset = 0;
		int yOffset = 0;
		for(int i = 0; i<text.length();i++) {
			xOffset += 16+spacing;
			char currentChar = text.charAt(i);
			if (currentChar=='g'||currentChar=='y'||currentChar=='q'||currentChar=='p'||currentChar=='j'||currentChar==',') yOffset=4;
			if ( currentChar=='\n') {
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			// index is -1 when the currentChar doesn't exist
			if (index == -1) continue;
			screen.renderTextCharacter(x + xOffset, y + yOffset + line*20, characters[index], color, false);
		}
	}
}
