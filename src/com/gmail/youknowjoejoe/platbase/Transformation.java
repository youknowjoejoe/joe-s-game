package com.gmail.youknowjoejoe.platbase;

public class Transformation {
	private Vector2f multiplier;
	private Vector2f translation;
	
	private static Vector2f cameraMultiplier = new Vector2f(1,1);
	private static Vector2f cameraTranslation = new Vector2f(0,0);
	
	public static Vector2f getCameraMultiplier() {
		return cameraMultiplier;
	}

	public static void setCameraMultiplier(Vector2f cameraMultiplier) {
		Transformation.cameraMultiplier = cameraMultiplier;
	}

	public static Vector2f getCameraTranslation() {
		return cameraTranslation;
	}

	public static void setCameraTranslation(Vector2f cameraTranslation) {
		Transformation.cameraTranslation = cameraTranslation;
	}

	public Transformation(Vector2f multiplier, Vector2f translation){
		this.multiplier = multiplier;
		this.translation = translation;
	}
	
	public Vector2f transform(Vector2f pt){
		return pt.times(multiplier).plus(translation).plus(cameraTranslation).times(cameraMultiplier);
	}
	
	public Vector2f transformNoCamera(Vector2f pt){
		return pt.times(multiplier).plus(translation);
	}
	
	public static Vector2f cameraTransform(Vector2f pt){
		return pt.plus(cameraTranslation).times(cameraMultiplier);
	}

	public Vector2f getMultiplier() {
		return multiplier;
	}
	
	public boolean hasMultiplier() {
		Vector2f totalMultiplication = cameraMultiplier.times(this.multiplier);
		if(totalMultiplication.getX() == 1 && totalMultiplication.getY() == 1){
			return false;
		}
		return true;
	}
	
	public Vector2f getTotalMultiplier(){
		return this.multiplier.times(cameraMultiplier);
	}

	public void setMultiplier(Vector2f multiplier) {
		this.multiplier = multiplier;
	}

	public Vector2f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector2f translation) {
		this.translation = translation;
	}
}
