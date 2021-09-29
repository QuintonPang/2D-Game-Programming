package com.quinton.game.graphics;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

import com.quinton.game.graphics.ui.UIComponent;
import com.quinton.game.graphics.ui.UIPanel;

public class UIManager {
	
	private List<UIPanel> panels = new ArrayList<UIPanel>();

	public UIManager() {
		
	}
	
	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}
	/*
	public void render(Screen screen) {
		//screen.drawRect(50,50, 20, 20, 0xff00ff, false);
		for(UIPanel panel:panels) {
			panel.render(screen);
		}
	}
	*/
	public void render(Graphics g) {
		for(UIPanel panel:panels) {
			panel.render(g);
		}
	}
	
	public void update() {
		for(UIPanel panel:panels) {
			panel.update();
		}
	}
	
	
}
