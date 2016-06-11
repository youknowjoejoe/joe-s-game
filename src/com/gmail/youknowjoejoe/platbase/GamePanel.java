package com.gmail.youknowjoejoe.platbase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.gmail.youknowjoejoe.platbase.debugEntities.ChickenPlayer;
import com.gmail.youknowjoejoe.platbase.debugEntities.PushableChicken;
import com.gmail.youknowjoejoe.platbase.debugEntities.RunMan;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableImage;
import com.gmail.youknowjoejoe.platbase.physical.Bounds;
import com.gmail.youknowjoejoe.platbase.physical.PhysicalBounds;
import com.gmail.youknowjoejoe.platbase.tilemap.Level;
import com.gmail.youknowjoejoe.platbase.tilemap.TileMap;
import com.gmail.youknowjoejoe.platbase.tilemap.TileType;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	private boolean running;
	
	private int width;
	private int height;
	
	private double oldTime;
	private double currentTime;
	private double frameTime;
	public static double deltaTime = 1.0/128.0;
	private double extraTime = 0;
	private double timeScale = 1;
	
	private RunMan player;
	
	private Level lvl;
	
	private long count = 0;
	
	public GamePanel(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width,height));
		this.addKeyListener(this);
		this.requestFocus();
		this.setFocusable(true);
		
		Transformation t = new Transformation(new Vector2f(1,1), new Vector2f(0,0));
		DrawableImage d = null;
		try {
			d = new DrawableImage(ImageIO.read(getClass().getResource("/Res/chickenTile.png")), t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PhysicalBounds p = new PhysicalBounds(new Bounds(new Vector2f(0,0), new Vector2f(100,100)),true,t);
		
		player = new RunMan(new Transformation(new Vector2f(100/32,100/32), new Vector2f(150,-50)));
		
		
		TileMap map1 = new TileMap(
		new TileType[]{new TileType(d, p, t)},
		new int[][]{{-1,-1,-1,-1,0,0},{-1,0,0,-1,0,0},{-1,0,-1,-1,0,0},{-1,0,-1,-1,0,0},{-1,-1,-1,-1,0,0},{-1,-1,-1,-1,0,0},{-1,-1,-1,-1,0,0},{0,0,0,0,0,0}},
		100);
		
		lvl = new Level(map1, null);
		lvl.addEntity(player);
		lvl.addEntity(new PushableChicken(new Transformation(new Vector2f(1,1), new Vector2f(200,-50))));
		
		running = true;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		
		oldTime = System.nanoTime()/1000000000.0;
		
		while(running){
			currentTime = System.nanoTime()/1000000000.0;
			frameTime = currentTime-oldTime;
			oldTime = currentTime;
			
			extraTime += frameTime*timeScale;
			
			while(extraTime >= deltaTime){
				logic();
				extraTime -= deltaTime;
			}
			
			repaint();
			
			//DELAY FOR PURPOSEFUL LAG BELOW
			/*try {
				Thread.sleep((long) (100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/**/
		}
		
	}
	
	public void logic(){
		lvl.update();
		lvl.resolveCollisions();
		
		Transformation.setCameraTranslation(player.getTransformation().getTranslation().scaledBy(-1).ceiled());
		
		count++;
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.translate(width/2, height/2);
		g2d.setColor(Color.white);
		g2d.fillRect(-width/2, -height/2, width, height);
		lvl.draw(g2d);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Input.keyDown(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Input.keyUp(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
}
