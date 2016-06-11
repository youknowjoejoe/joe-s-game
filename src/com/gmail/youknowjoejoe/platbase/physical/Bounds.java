package com.gmail.youknowjoejoe.platbase.physical;

import com.gmail.youknowjoejoe.platbase.Vector2f;

public class Bounds {
	private Vector2f pos;
	private Vector2f dim;
	
	public Bounds(Vector2f pos, Vector2f dim){
		this.pos = pos;
		this.dim = dim;
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	public Vector2f getDim() {
		return dim;
	}

	public void setDim(Vector2f dim) {
		this.dim = dim;
	}
}
