package com.gmail.youknowjoejoe.platbase.debugEntities;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;
import com.gmail.youknowjoejoe.platbase.drawable.Drawable;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableImage;
import com.gmail.youknowjoejoe.platbase.physical.Bounds;
import com.gmail.youknowjoejoe.platbase.physical.Physical;
import com.gmail.youknowjoejoe.platbase.physical.PhysicalBounds;
import com.gmail.youknowjoejoe.platbase.tilemap.Entity;
import com.gmail.youknowjoejoe.platbase.tilemap.EntityReturn;

public class PushableChicken implements Entity {

	private Transformation t;
	private DrawableImage d;
	private PhysicalBounds p;
	
	public PushableChicken(Transformation t){
		this.t = t;
		try {
			this.d = new DrawableImage(ImageIO.read(getClass().getResource("/Res/chickenMoveable.png")), t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.p = new PhysicalBounds(new Bounds(new Vector2f(0,0), new Vector2f(100,100)), false, t);
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
		
		return null;
	}

}
