package com.quinton.game.graphics.ui;

import com.quinton.game.graphics.Screen;
import java.awt.Color;
import com.quinton.game.util.Vector2i;
import java.awt.Graphics;

public class UIComponent {
	
	public Vector2i size;
	public Vector2i position;
	protected Vector2i offset;
	public int backgroundColor;
	public Color color;
	protected UIPanel panel;
	
	public boolean active = true;
	
	public UIComponent setColor(int color) {
		Color col = new Color(color);
		this.color = col;
		return this;
	}
	
	public void update() {
		
	}
	
	/*
	public void render(Screen screen) {
		
	}*/
	
	public void render(Graphics g) {
		
	}
	
	public Vector2i getAbsolutePosition() {
		return new Vector2i(position).add(offset);
	}
	
	public UIComponent(Vector2i position) {
		this.position = position;
		offset = new Vector2i();
	}
	
	public UIComponent(Vector2i position, Vector2i size) {
		this.position = position;
		this.size = size;
		offset = new Vector2i();
	}
	
	void setOffset(Vector2i offset) {
		this.offset = offset;
	}

	void init(UIPanel panel) {
		this.panel = panel;
	}
}
