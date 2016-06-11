package com.gmail.youknowjoejoe.platbase.drawable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gmail.youknowjoejoe.platbase.GamePanel;
import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;

public class DrawableAnimation implements Drawable{
	private BufferedImage[] images;
	private int currentImage = 0;
	private double frameTime;
	private double timePassed = 0;
	private Transformation t;
	
	public DrawableAnimation(BufferedImage[] images, double frameTime, Transformation t){
		this.images = images;
		this.frameTime = frameTime;
		this.t = t;
	}
	
	public void update(){
		timePassed += GamePanel.deltaTime;
		while(timePassed > frameTime){
			currentImage++;
			if(currentImage == images.length-1){
				currentImage = 0;
			}
			timePassed -= frameTime;
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		BufferedImage image = images[currentImage];
		
		if(t.hasMultiplier()){
			Vector2f topLeft = t.transform(new Vector2f(-image.getWidth()/2,-image.getHeight()/2));
			Vector2f dimensions = t.getTotalMultiplier().times(new Vector2f(image.getWidth(),image.getHeight()));
			g2d.drawImage(image,(int) Math.floor(topLeft.getX()),(int) Math.floor(topLeft.getY()),(int) Math.floor(dimensions.getX()),(int) Math.floor(dimensions.getY()),null);
		} else {
			Vector2f topLeft = t.transform(new Vector2f(-image.getWidth()/2,-image.getHeight()/2));
			g2d.drawImage(image,(int) Math.floor(topLeft.getX()),(int) Math.floor(topLeft.getY()),null);
		}
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	public Transformation getT() {
		return t;
	}

	public void setT(Transformation t) {
		this.t = t;
	}
}
