package com.quinton.game.entity.mob;

import java.awt.Color;
//import java.util.List;
import java.awt.Font;

import com.quinton.game.Game;
//import com.quinton.game.entity.Entity;
import com.quinton.game.entity.projectile.Projectile;
import com.quinton.game.entity.projectile.WizardProjectile;
import com.quinton.game.graphics.AnimatedSprite;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;
import com.quinton.game.graphics.UIManager;
import com.quinton.game.graphics.ui.UILabel;
import com.quinton.game.graphics.ui.UIPanel;
import com.quinton.game.graphics.ui.UIProgressBar;
import com.quinton.game.input.Keyboard;
import com.quinton.game.input.Mouse;
import com.quinton.game.level.Level;
import com.quinton.game.util.Vector2i;

public class Player extends Mob {
	
	private Keyboard input;
	//player sprite based on direction
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	//private AnimatedSprite test = new AnimatedSprite(SpriteSheet.player_down,32,32,3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up,32,32,3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right,32,32,3);
	
	private AnimatedSprite animSprite = null;
	
	Projectile p;
	private int fireRate = 0;
	
	private UIManager ui; 
	private UIProgressBar uiHealthBar;
	
	private String name;
	
	//player spawned at specific location
	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		//initial sprite before moving
		sprite = Sprite.player_forward;
		animSprite = down;
		fireRate = WizardProjectile.FIRE_RATE;
		ui = Game.getUIManager();
		//UIPanel panel = new UIPanel(new Vector2i(300-80,  0));
		UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300-80)*3,  0), new Vector2i(80*3, 168*3)).setColor(0x4f4f4f);
		ui.addPanel(panel);
		//panel.addComponent(new UILabel(new Vector2i(10,38) , "Hello"));
		
		UILabel nameLabel = new UILabel(new Vector2i(40,200) , name);
		nameLabel.setColor(0xbbbbbb);
		nameLabel.setFont(new Font("Verdana", Font.BOLD,24));
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);
		uiHealthBar = new UIProgressBar(new Vector2i(40,215), new Vector2i(80*2 - 10 , 20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(new Color(0xdd3030));
		panel.addComponent(uiHealthBar);
		
		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2,16)) , "HP");
		hpLabel.setColor(0xffffff);
		hpLabel.setFont(new Font("Verdana", Font.PLAIN,16));
		panel.addComponent(hpLabel);
		
		health = 100;
	}
	
	//player spawned at default location
	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
	}
	
	public String getName() {
		return name;
	}
	
	public void update() {
		
		// get entities near the player
		//List<Entity> es = level.getEntities(this,20);
		//System.out.println(es.size());
		//for (Entity e:es) System.out.println(e);
		
		//test.update();
		if(walking) animSprite.update();
		//reset frame after player stops
		else animSprite.setFrame(0);
		if(fireRate>0) fireRate--;
		double xa = 0, ya = 0;
		double speed = 3;
		//reset anim if too long
		if(anim<7500) anim++;
		else anim = 0;
		if (input.up) { 
			ya-=speed;
			animSprite = up;	
		}
		if (input.down) {
			ya+=speed;
			animSprite = down;
		}
		if (input.left) {
			xa-=speed;
			animSprite = left;
		}
		if(input.right) {
			xa+=speed;
			animSprite = right;
		}
		
	
		if (xa!=0||ya!=0) {
			
			walking = true;
			move(xa,ya);
		} else {
			walking = false;
		}
		
		clear();
		updateShooting();

		
	}
	
	private void clear() {
		for(int i=0; i<level.getProjectiles().size();i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}

	int time = 0;
	private void updateShooting() {
		// shooting by pressing mouse button
		if (Mouse.getButton()==1 && fireRate<=0) {
			
			// get location of center of window
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			// atan means tan^-1 dy dx, atan2 prevents error of dividing by 0 
			// returns angle in radians
			double dir = Math.atan2(dy, dx);
			shoot(x,y,dir);
			fireRate = WizardProjectile.FIRE_RATE;
		}
		
		//uiHealthBar.setProgress((time++%100)/100.0);
		uiHealthBar.setProgress(health);

	}
	
	public void render(Screen screen) {
		//screen.renderPlayer(x - 16, y -16, Sprite.player0);
		int flip = 0;
	/*	if (dir==2) {
			sprite = Sprite.player_forward;
			if (walking) {
				if (anim%20>10) {
					sprite = Sprite.player_forward1;
				}else {
					sprite = Sprite.player_forward2;
				}
			}
		}
		if (dir==0) {
			sprite = Sprite.player_back;
			if (walking) {
				if (anim%20>10) {
					sprite = Sprite.player_back1;
				}else {
					sprite = Sprite.player_back2;
				}
			}
		}
		//if (dir==3) sprite = Sprite.player_left;
		if (dir==1||dir==3) if (walking) {
			sprite = Sprite.player_side;
			if (anim%20>10) {
				sprite = Sprite.player_side1;
			}else {
				sprite = Sprite.player_side2;
			}
		}
		if (dir==3) flip = 1;
		*/
		//sprite = test.getSprite();
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16),(int)( y -16), sprite, flip);
		
	}
	
	public void init(Level level) {
		this.level = level;
	}
}

