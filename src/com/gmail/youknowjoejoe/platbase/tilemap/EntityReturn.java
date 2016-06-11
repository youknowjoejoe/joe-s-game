package com.gmail.youknowjoejoe.platbase.tilemap;

import java.util.LinkedList;
import java.util.List;

public class EntityReturn {
	private List<Entity> entitiesToAdd;
	private boolean removeSelf;
	
	public EntityReturn(){
		this.entitiesToAdd = new LinkedList<Entity>();
		removeSelf = false;
	}
	
	public EntityReturn(List<Entity> entitiesToAdd, boolean removeSelf){
		this.entitiesToAdd = entitiesToAdd;
		this.removeSelf = removeSelf;
	}

	public List<Entity> getEntitiesToAdd() {
		return entitiesToAdd;
	}

	public void setEntitiesToAdd(List<Entity> entitiesToAdd) {
		this.entitiesToAdd = entitiesToAdd;
	}
	
	public void addEntity(Entity e){
		entitiesToAdd.add(e);
	}

	public boolean isRemoveSelf() {
		return removeSelf;
	}

	public void setRemoveSelf(boolean removeSelf) {
		this.removeSelf = removeSelf;
	}
}
