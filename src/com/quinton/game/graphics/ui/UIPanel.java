package com.quinton.game.graphics.ui;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;

//import com.quinton.game.graphics.Screen;
//import com.quinton.game.graphics.Sprite;
import com.quinton.game.util.Vector2i;

public class UIPanel extends UIComponent {
	private List<UIComponent> components = new ArrayList<UIComponent>();
	private Vector2i /*position,*/ size;
	//private Sprite sprite;
	//private Color color;
	
	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		//this.position = position;
		//sprite = new Sprite(80,168,0xaaaaaa);
		
		// 50% opacity
		//color = new Color(0x7fcacaca,true);
		
		color = new Color(0xcacaca);
		
		this.size = size;
	}
	
	public void addComponent(UIComponent component) {
		// to tell which panel does each component belongs to
		component.init(this);
		components.add(component);
	}
	
	public void update() {
		for(UIComponent component:components) {
			component.setOffset(position);
			component.update();
		}
	}
	/*
	public void render(Screen screen) {
		screen.renderSprite(position.getX(), position.getY(), sprite, false);
		for(UIComponent component:components) {
			component.render(screen);
		}
	}
	*/
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
		for(UIComponent component:components) {
			component.render(g);
		}
	}
}
