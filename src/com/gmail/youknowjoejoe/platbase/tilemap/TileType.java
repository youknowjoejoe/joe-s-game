package com.gmail.youknowjoejoe.platbase.tilemap;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableImage;
import com.gmail.youknowjoejoe.platbase.physical.PhysicalBounds;

public class TileType implements Entity {
	private DrawableImage d;
	private PhysicalBounds p;
	private Transformation t;
	
	public TileType(DrawableImage d, PhysicalBounds p, Transformation t){
		this.d = d;
		this.p = p;
		this.t = t;
	}
	
	public DrawableImage getDrawable() {
		return d;
	}

	public PhysicalBounds getPhysical() {
		return p;
	}

	public void setTransformation(Transformation t){
		this.d.setT(t);
		this.p.setT(t);
		this.t = t;
	}
	
	public Transformation getTransformation(){
		return t;
	}

	@Override
	public EntityReturn update() {
		
		return null;
	}
}
