package com.quinton.game.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.event.MouseEvent;
import org.w3c.dom.ranges.RangeException;

import com.quinton.game.input.Mouse;
import com.quinton.game.util.Vector2i;

public class UIButton extends UIComponent {
	

	private UIButtonListener buttonListener;
	private UIActionListener actionListener;
	private UILabel label;
	
	private boolean inside = false;
	private boolean pressed = false;
	private boolean ignorePressed = false;
	
	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position,size);
		this.actionListener = actionListener;
		label = new UILabel(new Vector2i(position.getX()+4,position.getY()+size.getY()-10),"");
		label.setColor(0x666666);
		label.active = false;
	
		// color for button
		setColor(0xaaaaaa);
	
		buttonListener =  new UIButtonListener();
		
	
	}

	
	void init(UIPanel panel) {
		super.init(panel);
		panel.addComponent(label);
	}
	
	public void setText(String text) {
		if(text.equals("")) {
			label.active = false;
			return;
		}
		else label.text = text;
	}
	
	
	
	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().getX(),getAbsolutePosition().getY(),size.getX(),size.getY());
		boolean leftMouseButtonDown = Mouse.getButton()==MouseEvent.BUTTON1;
		if (rect.contains(new Point(Mouse.getX(),Mouse.getY()))) {
			
			// inside variable is to prevent the buttonListener from being called every second
			 
			if(!inside) {
				// this prevents the button from being triggered when the press originates from outside the button
				if(leftMouseButtonDown) {
					ignorePressed = true;
				}else {
					ignorePressed = false;
				}
				buttonListener.entered(this);		
			}
			inside = true;
			
			if(!ignorePressed&&!pressed&&leftMouseButtonDown) {
				buttonListener.pressed(this);
				actionListener.perform();
				pressed = true;
			}else if (pressed&&Mouse.getButton()==MouseEvent.NOBUTTON) {
				if(pressed) {
					buttonListener.released(this);
					pressed = false;
				}
				ignorePressed = false;
			}
		}else {
			if(inside) {
				buttonListener.exited(this);
				pressed = false;
			}
			inside = false;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.getX()+offset.getX(),position.getY()+offset.getY(),size.getX(),size.getY());
		if (label!=null) label.render(g);
		
		
	}
	
	public void setButtonListner(UIButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}
	
}
