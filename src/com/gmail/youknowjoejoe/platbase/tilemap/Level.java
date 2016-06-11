package com.gmail.youknowjoejoe.platbase.tilemap;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {
	private TileMap map;
	private List<Entity> entities;
	
	public Level(TileMap map,ArrayList<Entity> entities){
		this.map = map;
		if(entities != null){
			this.entities = entities;
		} else {
			this.entities = new ArrayList<Entity>();
		}
	}
	
	public void addEntity(Entity e){
		this.entities.add(e);
	}
	
	public void update(){
		for(int i = 0; i < this.entities.size(); i++){
			Entity e = this.entities.get(i);
			EntityReturn r = e.update();
			if(r != null){
				if(r.isRemoveSelf()){
					entities.remove(i);
				}
				if(r.getEntitiesToAdd() != null){
					this.entities.addAll(r.getEntitiesToAdd());
				}
			}
		}
	}
	
	public void resolveCollisions(){
		for(int i = 0; i < entities.size(); i++){
			for(int e=i+1; e < entities.size(); e++){
				entities.get(i).getPhysical().getCollisionInfo(entities.get(e).getPhysical()).resolveCollision();
			}
			map.resolveCollisions(entities.get(i).getPhysical());
		}
		
	}
	
	public void draw(Graphics2D g2d){
		for(Entity e : entities){
			e.getDrawable().draw(g2d);
		}
		map.draw(g2d);
	}
}
