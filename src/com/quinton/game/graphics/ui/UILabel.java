package com.quinton.game.graphics.ui;

import java.awt.Color;
//import com.quinton.game.graphics.Font;
import java.awt.Font;
import java.awt.Graphics;
//import com.quinton.game.graphics.Screen;
import com.quinton.game.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	public boolean dropShadow = false;
	public int dropShadowOffset = 2;
	
	public UILabel(Vector2i position, String text) {
		super(position);
		//font = new Font();
		font = new Font("Helvetica",Font.PLAIN,32);
		this.text = text;
		color = new Color(0xff00ff);
	}
	
	public UILabel setFont(Font font) {
		this.font = font;
		return this;
	}

	public void render(Graphics g) {
		if (dropShadow) {
			g.setColor(Color.black);
			// text shadow
			g.setFont(new Font(font.getFontName(),font.getStyle(),font.getSize()));
			g.drawString(text,position.getX()+offset.getX()+dropShadowOffset,position.getY()+offset.getY()+dropShadowOffset);
		}
		g.setColor(color);
		g.setFont(font);
		g.drawString(text,position.getX()+offset.getX(),position.getY()+offset.getY());
	}
	
	
	/*
	public void render(Screen screen) {
		font.render(position.getX() + offset.getX(), position.getY() + offset.getY(), -5, 0, text, screen);
	}
	*/
}
