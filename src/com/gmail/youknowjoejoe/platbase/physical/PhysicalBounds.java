package com.gmail.youknowjoejoe.platbase.physical;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;

public class PhysicalBounds implements Physical{
	private Bounds bounds;
	private Transformation t;
	private int reactID = 0;
	private boolean staticFixed;
	
	public PhysicalBounds(Bounds bounds, boolean staticFixed, Transformation t){
		this.bounds = bounds;
		this.staticFixed = staticFixed;
		this.t = t;
	}

	@Override
	public CollisionInfo getCollisionInfo(Physical p) {
		CollisionInfo ci = new CollisionInfo(this,p);
		
		Vector2f distance = p.getNoCameraTransformedBounds().getPos().minus(this.getNoCameraTransformedBounds().getPos()).absoluteValue();
		Vector2f dimSum = p.getNoCameraTransformedBounds().getDim().plus(this.getNoCameraTransformedBounds().getDim());
		
		Vector2f overlap = dimSum.scaledBy(0.5f).minus(distance);
		
		if(overlap.getX() > 0 && overlap.getY() > 0){
			ci.setColliding(true);
			this.reactTo(p.getReactId());
			p.reactTo(reactID);
		} else {
			return ci;
		}
		
		if(overlap.getX() < overlap.getY()){
			ci.setPenetrationDistance(overlap.getX());
			float xdistance = p.getNoCameraTransformedBounds().getPos().getX()-this.getNoCameraTransformedBounds().getPos().getX();
			ci.setCollisionNormal(new Vector2f(-xdistance/Math.abs(xdistance),0));
		} else {
			ci.setPenetrationDistance(overlap.getY());
			float ydistance = p.getNoCameraTransformedBounds().getPos().getY()-this.getNoCameraTransformedBounds().getPos().getY();
			ci.setCollisionNormal(new Vector2f(0,-ydistance/Math.abs(ydistance)));
		}
		
		return ci.getP2().specificModificationOf((this.specificModificationOf(ci).getInverse()));
	}

	public void setT(Transformation t) {
		this.t = t;
	}
	
	@Override
	public CollisionInfo specificModificationOf(CollisionInfo ci) {
		return ci;
	}

	@Override
	public Bounds getTransformedBounds() {
		return new Bounds(t.transform(bounds.getPos()),t.getTotalMultiplier().times(bounds.getDim()));
	}
	
	@Override
	public Bounds getNoCameraTransformedBounds() {
		return new Bounds(t.transformNoCamera(bounds.getPos()),t.getMultiplier().times(bounds.getDim()));
	}
	
	public Bounds getPureBounds(){
		return bounds;
	}
	
	public void setDimensions(Vector2f dim){
		bounds.setDim(dim);
	}

	@Override
	public void translate(Vector2f translation) {
		t.setTranslation(t.getTranslation().plus(translation));
	}

	@Override
	public void reactTo(int reactID) {
		
	}

	@Override
	public int getReactId() {
		return reactID;
	}

	@Override
	public boolean isStaticFixed() {
		return staticFixed;
	}
	
}
