package com.gmail.youknowjoejoe.platbase.debugEntities;

public class KeyLock {
	private double until;
	
	public KeyLock(double until){
		this.until = until;
	}
	
	public void update(double dt){
		if(until > 0){
			until -= dt;
		}
	}
	
	public void lock(double until){
		this.until = until;
	}
	
	public boolean isLocked(){
		if(until > 0){
			return true;
		}
		return false;
	}
}
