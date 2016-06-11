package com.gmail.youknowjoejoe.platbase;

public class Vector2f {
	private float x;
	private float y;
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2f plus(Vector2f v){
		return new Vector2f(this.x+v.getX(),this.y+v.getY());
	}
	
	public Vector2f minus(Vector2f v){
		return new Vector2f(this.x-v.getX(),this.y-v.getY());
	}
	
	public Vector2f times(Vector2f v){
		return new Vector2f(this.x*v.getX(),this.y*v.getY());
	}
	
	public Vector2f dividedBy(Vector2f v){
		return new Vector2f(this.x/v.getX(),this.y/v.getY());
	}
	
	public Vector2f scaledBy(float f){
		return new Vector2f(this.x*f,this.y*f);
	}
	
	public Vector2f rotatedBy(float theta){
		return new Vector2f((float) (Math.cos(theta)*this.x-Math.sin(theta)*this.y), (float) (Math.sin(theta)*this.x+Math.cos(theta)*y));
	}
	
	public Vector2f normalized(){
		float magnitude = (float) Math.sqrt((this.x*this.x)+(this.y*this.y));
		return new Vector2f(this.x/magnitude,this.y/magnitude);
	}
	
	public Vector2f absoluteValue(){
		return new Vector2f(Math.abs(this.x),Math.abs(this.y));
	}
	
	public Vector2f floored(){
		return new Vector2f((float) Math.floor(this.x),(float) Math.floor(this.y));
	}
	
	public Vector2f roundTowardsZero(){
		float newX = 0;
		float newY = 0;
		if(this.x > 0){
			newX = (float) Math.floor(this.x);
		} else if(this.x < 0){
			newX = (float) Math.ceil(this.x);
		}
		
		if(this.y > 0){
			newY = (float) Math.floor(this.y);
		} else if(this.y < 0){
			newY = (float) Math.ceil(this.y);
		}
		
		return new Vector2f(newX,newY);
	}
	
	public float dotProduct(Vector2f v){
		return (this.x*v.getX()) + (this.y*v.getY());
	}
	
	public float getDistanceSquared(Vector2f v){
		Vector2f thisToV = v.minus(this);
		return thisToV.getX()*thisToV.getX() + thisToV.getY()*thisToV.getY();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Vector2f ceiled() {
		return new Vector2f((float) Math.ceil(this.x),(float) Math.ceil(this.y));
	}

	public Vector2f rounded() {
		return new Vector2f((float) Math.round(this.x),(float) Math.round(this.y));
	}
}
