package com.gmail.youknowjoejoe.platbase.drawable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;

public class DrawableImage implements Drawable{
	private BufferedImage image;
	private Transformation t;
	
	public DrawableImage(BufferedImage image, Transformation t){
		this.image = image;
		this.t = t;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if(t.hasMultiplier()){
			Vector2f topLeft = t.transform(new Vector2f(-image.getWidth()/2,-image.getHeight()/2));
			Vector2f dimensions = t.getTotalMultiplier().times(new Vector2f(image.getWidth(),image.getHeight()));
			g2d.drawImage(image,(int) Math.floor(topLeft.getX()),(int) Math.floor(topLeft.getY()),(int) Math.floor(dimensions.getX()),(int) Math.floor(dimensions.getY()),null);
		} else {
			Vector2f topLeft = t.transform(new Vector2f(-image.getWidth()/2,-image.getHeight()/2));
			g2d.drawImage(image,(int) Math.floor(topLeft.getX()),(int) Math.floor(topLeft.getY()),null);
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Transformation getT() {
		return t;
	}

	public void setT(Transformation t) {
		this.t = t;
	}
}
