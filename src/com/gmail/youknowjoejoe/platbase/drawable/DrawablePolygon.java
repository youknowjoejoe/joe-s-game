package com.gmail.youknowjoejoe.platbase.drawable;

import java.awt.Color;
import java.awt.Graphics2D;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;

public class DrawablePolygon implements Drawable{
	private Vector2f[] vertices;
	private Color c;
	private Transformation t;
	
	public DrawablePolygon(Vector2f[] vertices, Color c, Transformation t){
		this.vertices = vertices;
		this.c = c;
		this.t = t;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		int[] xpoints = new int[vertices.length];
		int[] ypoints = new int[vertices.length];
		
		for(int i = 0; i < vertices.length; i++){
			Vector2f transformedPoint = t.transform(vertices[i]);
			
			xpoints[i] = (int) transformedPoint.getX();
			ypoints[i] = (int) transformedPoint.getY();
		}
		
		g2d.setColor(c);
		g2d.fillPolygon(xpoints, ypoints, vertices.length);
	}

}
