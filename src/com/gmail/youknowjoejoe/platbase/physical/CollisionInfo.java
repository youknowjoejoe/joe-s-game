package com.gmail.youknowjoejoe.platbase.physical;

import com.gmail.youknowjoejoe.platbase.Vector2f;

public class CollisionInfo {
	private boolean colliding = false;
	private Vector2f collisionNormal = new Vector2f(0,0);
	private float penetrationDistance = 0;
	
	private Physical p1;
	private Physical p2;
	
	public CollisionInfo(Physical p1, Physical p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean isColliding() {
		return colliding;
	}
	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}
	public Vector2f getCollisionNormal() {
		return collisionNormal;
	}
	public void setCollisionNormal(Vector2f collisionNormal) {
		this.collisionNormal = collisionNormal;
	}
	public float getPenetrationDistance() {
		return penetrationDistance;
	}
	public void setPenetrationDistance(float penetrationDistance) {
		this.penetrationDistance = penetrationDistance;
	}
	public CollisionInfo getInverse(){
		CollisionInfo inverse = new CollisionInfo(this.p2,this.p1);
		inverse.setCollisionNormal(this.collisionNormal.scaledBy(-1));
		inverse.setPenetrationDistance(this.penetrationDistance);
		inverse.setColliding(this.colliding);
		return inverse;
	}
	public Physical getP2(){
		return p2;
	}
	
	public void resolveCollision(){
		float d = 0.5f;
		if(!p1.isStaticFixed() && !p2.isStaticFixed()){
			this.p1.translate(collisionNormal.scaledBy(penetrationDistance*d/2.0f));
			this.p2.translate(collisionNormal.scaledBy(-penetrationDistance*d/2.0f));
		} else if(!p1.isStaticFixed() && p2.isStaticFixed()) {
			this.p1.translate(collisionNormal.scaledBy(penetrationDistance*d));
		} else if(!p2.isStaticFixed() && p1.isStaticFixed()){
			this.p2.translate(collisionNormal.scaledBy(-penetrationDistance*d));
		} else {
			
		}
	}
	
}
