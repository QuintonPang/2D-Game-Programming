package com.quinton.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	// state of all keys
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right;

	
	public void update() {
		
		//get the state of the key from array
		
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		//get the id of pressed key and put the state of the key into the array
		keys[e.getKeyCode()]=true;

	}	

	@Override
	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()]=false;
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
