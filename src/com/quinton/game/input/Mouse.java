package com.quinton.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// in java, can implement multiple but only can extend one
public class Mouse implements MouseListener, MouseMotionListener{

	// location of mouse
	private static int mouseX = -1;
	private static int mouseY = -1;
	
	// mouse button
	private static int mouseB = -1;
	
	public static int getX() {
		return mouseX;
	}
	
	public static int getY() {
		return mouseY;
	}
	
	public static int getButton() {
		return mouseB;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseB = MouseEvent.NOBUTTON;
		
	}

}
