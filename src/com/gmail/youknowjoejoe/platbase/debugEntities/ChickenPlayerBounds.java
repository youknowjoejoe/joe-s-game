package com.gmail.youknowjoejoe.platbase.debugEntities;

import java.util.LinkedList;
import java.util.List;

import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.physical.Bounds;
import com.gmail.youknowjoejoe.platbase.physical.CollisionInfo;
import com.gmail.youknowjoejoe.platbase.physical.PhysicalBounds;

public class ChickenPlayerBounds extends PhysicalBounds {
	
	private List<CollisionInfo> previousCollisionInfos = new LinkedList<CollisionInfo>();
	
	public ChickenPlayerBounds(Bounds bounds, boolean staticFixed, Transformation t) {
		super(bounds, staticFixed, t);
	}
	
	@Override
	public CollisionInfo specificModificationOf(CollisionInfo ci) {
		previousCollisionInfos.add(ci);
		return ci;
	}
	
	public void emptyCollisionInfosList(){
		previousCollisionInfos.clear();
	}

	public List<CollisionInfo> getPreviousCollisionInfos() {
		return previousCollisionInfos;
	}
}
