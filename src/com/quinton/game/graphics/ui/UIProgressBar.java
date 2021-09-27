package com.quinton.game.graphics.ui;

import org.w3c.dom.ranges.RangeException;
import java.awt.Graphics;
import java.awt.Color;

import com.quinton.game.util.Vector2i;

public class UIProgressBar extends UIComponent {

	private Vector2i size;
	private double progress; // 0.0 to 1.0
	
	private Color foregroundColor;
	
	public UIProgressBar(Vector2i position, Vector2i size) {
		super(position);
		this.size = size;
	}
	
	public void setProgress(double progress) {
		if ( progress<0|| progress>100) {
			throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR,"Progress must be between 0 and 100");
		}
		
		this.progress = progress/100;
		
	}
	
	public double getProgress() {
		return progress;
	}
	
	public void setForegroundColor(Color color) {
		this.foregroundColor = color;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.getX()+offset.getX(),position.getY()+offset.getY(),size.getX(),size.getY());
		g.setColor(foregroundColor);
		g.fillRect(position.getX()+offset.getX(),position.getY()+offset.getY(),(int)(progress*size.getX()),size.getY());
		
	}
	

}
