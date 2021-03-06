package com.quinton.game;

import java.awt.Canvas;
import java.awt.Color;
//import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.quinton.game.entity.mob.Player;
import com.quinton.game.events.Event;
import com.quinton.game.events.EventListener;
import com.quinton.game.graphics.Screen;
import com.quinton.game.graphics.Font;
import com.quinton.game.graphics.Sprite;
import com.quinton.game.graphics.SpriteSheet;
import com.quinton.game.graphics.UIManager;
import com.quinton.game.graphics.layers.Layer;
import com.quinton.game.graphics.ui.UIPanel;
import com.quinton.game.input.Keyboard;
import com.quinton.game.input.Mouse;
import com.quinton.game.level.Level;
import com.quinton.game.level.RandomLevel;
import com.quinton.game.level.SpawnLevel;
import com.quinton.game.level.TileCoordinate;
import com.quinton.game.net.player.NetPlayer;
import com.thecherno.raincloud.serialization.RCDatabase;
import com.thecherno.raincloud.serialization.RCField;
import com.thecherno.raincloud.serialization.RCObject;



public class Game extends Canvas implements Runnable, EventListener{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private static int width =300;
	private static int width = 300 - 80;
	private static int height = 168;
	//private static int height = width/16 * 9; // ratio of 9:16
	private static int scale =3;
	
	int x, y =0;
	
	private Thread thread;
	private JFrame frame; //creates local variable frame
	private boolean running = false;
	private Keyboard key;
	private Level level;
	private Player player;
	
	private Screen screen;
	private Font font;
	
	private static UIManager uiManager;
	 
	private List<Layer> layerStack = new ArrayList<Layer>();

	//  image
	private BufferedImage image;// = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	// array of pixels (raster)
	private int[] pixels;//= ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		//game.frame.setTitle("Rain");
		game.frame.add(game);
		//size the window according to component
		game.frame.pack();
		//close program
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set on center of screen
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();

	}
	
	//constructor of game
	public Game() {
		
		
		setSize();
		//Dimension size = new Dimension(width*scale, height*scale);
		//Dimension size = new Dimension(width*scale + 80 * 3, height*scale);
		//setPreferredSize(size);
				
		//initialize local variable frame4
		frame = new JFrame();		
		screen = new Screen(width, height);
		key = new Keyboard();
		// 64 by 64
		//level = new RandomLevel(64,64);
		level = Level.spawn;
		addLayer(level);
		font = new Font();
		uiManager = new UIManager();
		// set spawn location
		TileCoordinate playerSpawn = new TileCoordinate(19,42);
		player = new Player("Quinton",playerSpawn.x(),playerSpawn.y(),key);
		//player.init(level);
		level.add(player);
		level.add(new NetPlayer());
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		
		// mouse click
		addMouseListener(mouse);
		
		// location of mouse
		addMouseMotionListener(mouse);
		
		save();
	}
	
	private void save() {
		RCDatabase db = new RCDatabase("Screen");
		
		RCObject obj = new RCObject("Resolution");
		obj.addField(RCField.Integer("width", width));
		obj.addField(RCField.Integer("height", height));
		obj.addField(RCField.Integer("scale", scale));
		db.addObject(obj);
		
		db.serializeToFile("res/data/screen.bin");
	}
	
	private void load() {
		
	}
	
	private void setSize() {
		RCDatabase db = RCDatabase.DeserializeFromFile("res/data/screen.bin");
		if (db != null) {
			RCObject obj = db.findObject("Resolution");
			width = obj.findField("width").getInt();
			height = obj.findField("height").getInt();
			scale = obj.findField("scale").getInt();
		}
	
		Dimension size = new Dimension(width*scale + 80 * 3, height*scale);
		setPreferredSize(size);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	public static UIManager getUIManager() {
		return uiManager;
	}
	

	public void addLayer(Layer layer) {
		this.layerStack.add(layer);
	}
	
	//synchronized means it runs individually to prevent interruption
	
	public synchronized void start() {
		running = true;
		//this refers to game class
		thread = new Thread(this,"Display");
		thread.start();
		
	}
	
	public synchronized void stop() {
		
		try {
			//stops thread
			thread.join();
		}catch(InterruptedException e){
			
			e.printStackTrace();
			
		}
	}
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/ 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		while (running) {
			
			long now = System.nanoTime();
		
			// finds duration between lastTime and now
			delta += (now-lastTime)/ns;
			// resets lastTime to now for next loop
			lastTime = now;
			
			while (delta>=1) {
				
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				
				// add one second
				timer += 1000;
				//System.out.println(updates+"ups, "+frames +"fps");
				
				frame.setTitle("Rain | " + updates+"ups, "+frames +"fps");
				
				//reset updates and fps for next loop
				updates = 0;
				frames = 0;
				
			}
		
			
		}
		
		stop();
		
		
	}
	
	public void render() {
		
		//Buffer strategy is to load screen to prepare it to be rendered while another screen is being rendered
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			
			createBufferStrategy(3);
			return;
		}	
			
		screen.clear();
		
		//set view of player in middle of the screen by moving the map to the left and downwards without affecting postion of player on the map
		//int xScroll = player.x - screen.width/2;
		//int yScroll = player.y - screen.height/2;
		
		double xScroll = player.getX() - screen.width/2;
		double yScroll = player.getY() - screen.height/2;

		level.setScroll((int)xScroll, (int)yScroll);
		
		// render layers
		for(int i = 0;i<layerStack.size();i++) {
			layerStack.get(i).render(screen);
		}
		
		//level.render(/*(int)xScroll,(int)yScroll,*/ screen);
		//uiManager.render(screen);
		//player.render(screen);
		//screen.renderSheet(40, 40, SpriteSheet.player_down, false);
		/*
		Sprite sprite = new Sprite(2,2,0xffffff);
		Random random = new Random();
		for(int i = 0; i<100; i++) {
			int x = random.nextInt(20);
			int y = random.nextInt(20);
			screen.renderSprite(width - 60 +x , 50 + y ,sprite,true);
		}
		*/
		
		//screen.render(x,y);
		//font.render(50,50,-3,0xffffff,"?Hello\n Quinton ?",screen);
	
		for (int i=0;i<pixels.length;i++) {
			//setting the pixels array to the pixels array in screen class
			pixels[i]= screen.pixels[i];
			//if(pixels[i]==0)continue;
			//System.out.println(pixels[i]);
		}
		
		
		
		//Draw the graphics
		Graphics g = bs.getDrawGraphics();

		// to check holes on screen
		g.setColor(new Color(0xff00ff));
		g.drawRect(0,0,getWidth(),getHeight());
		
		/*g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight()); */
		
		//draw buffered image
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.drawImage(image, 0, 0, width*scale , height*scale, null);
		
		//coordinates on screen
		g.setColor(Color.WHITE);
		//g.setFont(new Font("Verdana",0,30));
		//g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
		g.drawString("X:" + (int)player.getX() + " Y: " + (int)player.getY(), 675, 450);
		//g.drawString("Mouse Button: " + Mouse.getButton(), 100, 100);
		
		uiManager.render(g);
		
		//removes the graphics every loop
		g.dispose();
		bs.show();	
		
	}
	
	public void update() {
		
		key.update();
		
		/*if (key.up) y--;
		if (key.down) y++;
		if (key.left) x--;
		if(key.right) x++;
		*/
		
		//player.update();
		level.update();
		uiManager.update();
		
		// update layers
		for(int i = 0;i<layerStack.size();i++) {
			layerStack.get(i).update();
		}
	}

	@Override
	public void onEvent(Event event) {
		for (int i = layerStack.size()-1;i>=0;i--)
			layerStack.get(i).onEvent(event);
	}

}
