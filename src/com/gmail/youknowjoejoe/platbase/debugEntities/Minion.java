package com.gmail.youknowjoejoe.platbase.debugEntities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;
import com.gmail.youknowjoejoe.platbase.drawable.Drawable;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableImage;
import com.gmail.youknowjoejoe.platbase.physical.Bounds;
import com.gmail.youknowjoejoe.platbase.physical.CollisionInfo;
import com.gmail.youknowjoejoe.platbase.physical.Physical;
import com.gmail.youknowjoejoe.platbase.tilemap.Entity;
import com.gmail.youknowjoejoe.platbase.tilemap.EntityReturn;

public class Minion implements Entity{
	
	private DrawableImage d;
	private Transformation t;
	private ChickenPlayerBounds p;
	
	public Minion(Transformation t){
		this.t = t;
		this.p = new ChickenPlayerBounds(new Bounds(new Vector2f(0,0), new Vector2f(100,100)), false, t);
		try {
			this.d = new DrawableImage(ImageIO.read(getClass().getResource("/Res/chickenMoveable.png")),t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Drawable getDrawable() {
		return d;
	}

	@Override
	public Physical getPhysical() {
		return p;
	}

	@Override
	public void setTransformation(Transformation t) {
		this.t = t;
	}

	@Override
	public Transformation getTransformation() {
		return t;
	}

	@Override
	public EntityReturn update() {
		boolean removeSelf = false;
		
		List<CollisionInfo> previousCollisionInfos = p.getPreviousCollisionInfos();
		for(CollisionInfo ci: previousCollisionInfos){
			if(ci.isColliding() && ci.getCollisionNormal().getX() != 0){
				removeSelf = true;
			}
		}
		p.emptyCollisionInfosList();
		
		if(removeSelf){
			return new EntityReturn(null,removeSelf);
		}
		return null;
	}

}
