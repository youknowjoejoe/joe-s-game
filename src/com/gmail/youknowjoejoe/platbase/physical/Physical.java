package com.gmail.youknowjoejoe.platbase.physical;

import com.gmail.youknowjoejoe.platbase.Vector2f;

public interface Physical {
	public CollisionInfo getCollisionInfo(Physical p);
	public Bounds getTransformedBounds();
	public Bounds getNoCameraTransformedBounds();
	public void translate(Vector2f translation);
	public void reactTo(int reactID);
	public int getReactId();
	public boolean isStaticFixed();
	public CollisionInfo specificModificationOf(CollisionInfo ci);
}
