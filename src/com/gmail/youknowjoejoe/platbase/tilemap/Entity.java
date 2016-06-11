package com.gmail.youknowjoejoe.platbase.tilemap;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.drawable.Drawable;
import com.gmail.youknowjoejoe.platbase.physical.Physical;

public interface Entity {
	public Drawable getDrawable();
	public Physical getPhysical();
	public void setTransformation(Transformation t);
	public Transformation getTransformation();
	public EntityReturn update();
}
