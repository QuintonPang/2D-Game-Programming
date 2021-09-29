package com.quinton.game.graphics.ui;


public class UIButtonListener {

	public void entered(UIButton button) {
		button.setColor(0xcdcdcd);
		//System.out.println("Entered");
	};
	
	public void exited(UIButton button) {
		button.setColor(0xaaaaaa);
		//System.out.println("Exited");
	};
	
	public void released(UIButton button) {
		button.setColor(0xcdcdcd);
		//System.out.println("Released");
	};
	
	public void pressed(UIButton button) {
		button.setColor(0xcc2222);
		//System.out.println("Pressed");
	};
}
