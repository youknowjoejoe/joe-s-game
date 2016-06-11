package com.gmail.youknowjoejoe.platbase.tilemap;

import java.awt.Graphics2D;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;
import com.gmail.youknowjoejoe.platbase.drawable.Drawable;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableImage;
import com.gmail.youknowjoejoe.platbase.physical.CollisionInfo;
import com.gmail.youknowjoejoe.platbase.physical.Physical;
import com.gmail.youknowjoejoe.platbase.physical.PhysicalBounds;

public class TileMap {
	private TileType[] tileIDs;
	private int[][] tileGrid;
	private int tileSize;
	
	public TileMap(TileType[] tileIDs, int[][] tileGrid, int tileSize){
		this.tileIDs = tileIDs;
		this.tileGrid = tileGrid;
		this.tileSize = tileSize;
	}
	
	public void draw(Graphics2D g2d){
		//Fix drawing, very inefficient
		for(int i = 0; i < tileGrid.length; i++){
			for(int e = 0; e < tileGrid[0].length; e++){
				if(tileGrid[i][e] != -1){
					Drawable d = new DrawableImage(tileIDs[tileGrid[i][e]].getDrawable().getImage(),new Transformation(tileIDs[tileGrid[i][e]].getTransformation().getMultiplier(),tileIDs[tileGrid[i][e]].getTransformation().getTranslation().plus(new Vector2f((i+0.5f)*this.tileSize,(e+0.5f)*this.tileSize))));
					d.draw(g2d);
				}
			}
		}
	}
	
	public void resolveCollisions(Physical p){
		Vector2f topLeft = p.getNoCameraTransformedBounds().getPos().minus(p.getNoCameraTransformedBounds().getDim().scaledBy(0.5f)).scaledBy(1.0f/this.tileSize).floored();
		Vector2f bottomRight = p.getNoCameraTransformedBounds().getPos().plus(p.getNoCameraTransformedBounds().getDim().scaledBy(0.5f)).scaledBy(1.0f/this.tileSize).floored();
		//Vector2f bottomRight = topLeft.plus(p.getNoCameraTransformedBounds().getDim().scaledBy(1.0f/this.tileSize)).floored();
		
		if(bottomRight.getX() < 0 || bottomRight.getY() < 0 || topLeft.getX() > this.tileGrid.length*this.tileSize || topLeft.getY() > this.tileGrid.length*this.tileSize){
			//outside of the tile grid
		} else {
			CollisionInfo smallestCollision = null;
			do {
				smallestCollision = null;
				float smallestDistanceSquared = 1000000000;
				for(int i = (int) Math.max((topLeft.getX()),0); i <= (int) Math.min((bottomRight.getX()),this.tileGrid.length-1); i++){
					for(int e = (int) Math.max((topLeft.getY()),0); e <= (int) Math.min((bottomRight.getY()),this.tileGrid[0].length-1); e++){
						if(tileGrid[i][e] != -1){
							Vector2f tilePos = new Vector2f((i+0.5f)*this.tileSize,(e+0.5f)*this.tileSize);
							float currentDistanceSquared = p.getNoCameraTransformedBounds().getPos().getDistanceSquared(new Transformation(tileIDs[tileGrid[i][e]].getTransformation().getMultiplier(),tileIDs[tileGrid[i][e]].getTransformation().getTranslation().plus(tilePos)).transformNoCamera(tileIDs[tileGrid[i][e]].getPhysical().getPureBounds().getPos()));
							if(currentDistanceSquared < smallestDistanceSquared){
								PhysicalBounds physical = tileIDs[tileGrid[i][e]].getPhysical();
								CollisionInfo info = p.getCollisionInfo(new PhysicalBounds(physical.getPureBounds(), physical.isStaticFixed(), new Transformation(tileIDs[tileGrid[i][e]].getTransformation().getMultiplier(),tileIDs[tileGrid[i][e]].getTransformation().getTranslation().plus(tilePos))));
								if(info.isColliding()){
									smallestDistanceSquared = currentDistanceSquared;
									smallestCollision = info;
								}
							}
						}
					}
				}
				if(smallestCollision != null){
					smallestCollision.resolveCollision();
				}
			} while(smallestCollision != null);
		}
	}
}
