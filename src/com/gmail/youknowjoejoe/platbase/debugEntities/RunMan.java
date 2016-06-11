package com.gmail.youknowjoejoe.platbase.debugEntities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.gmail.youknowjoejoe.platbase.GamePanel;
import com.gmail.youknowjoejoe.platbase.Input;
import com.gmail.youknowjoejoe.platbase.Transformation;
import com.gmail.youknowjoejoe.platbase.Vector2f;
import com.gmail.youknowjoejoe.platbase.drawable.Drawable;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableAnimation;
import com.gmail.youknowjoejoe.platbase.drawable.DrawableImage;
import com.gmail.youknowjoejoe.platbase.physical.Bounds;
import com.gmail.youknowjoejoe.platbase.physical.CollisionInfo;
import com.gmail.youknowjoejoe.platbase.physical.Physical;
import com.gmail.youknowjoejoe.platbase.tilemap.Entity;
import com.gmail.youknowjoejoe.platbase.tilemap.EntityReturn;

public class RunMan implements Entity {
	private Transformation t;
	private Drawable d;
	private ChickenPlayerBounds p;
	
	private DrawableImage idleRight;
	private DrawableImage idleLeft;
	private DrawableImage flyRight;
	private DrawableImage flyLeft;
	private boolean idling = false;
	private DrawableAnimation runningRight;
	private DrawableAnimation runningLeft;
	
	//private PlayerState state = PlayerState.InAir;
	private Map<PlayerState,Boolean> state = new HashMap<PlayerState,Boolean>();
	
	private Vector2f vel = new Vector2f(0,0);
	private Vector2f gravityAccel = new Vector2f(0,70.0f);
	private float movementSpeed = 150.0f;
	private float groundPoundSpeed = 300.0f;
	private float jumpVel = 4000.0f;
	private float wallJumpVel = 4800.0f;
	private Vector2f wallJumpNormal = new Vector2f(0,0);
	private Vector2f onGroundFriction = new Vector2f(90,0);
	private Vector2f inAirFriction = new Vector2f(75,0);
	private Vector2f onWallFriction = new Vector2f(0,60);
	private KeyLock leftKeyLock = new KeyLock(0);
	private KeyLock rightKeyLock = new KeyLock(0);
	private KeyLock zKeyLock = new KeyLock(0);
	
	public RunMan(Transformation t){
		this.t = t;
		try {
			BufferedImage[] images1 = new BufferedImage[8];
			for(int i = 0; i < 8; i++){
				images1[i] = ImageIO.read(getClass().getResource("/Res/RunAnimationRight/sprite_"+i+".png"));
			}
			this.runningRight = new DrawableAnimation(images1, 0.1, t);
			
			BufferedImage[] images2 = new BufferedImage[8];
			for(int i = 0; i < 8; i++){
				images2[i] = ImageIO.read(getClass().getResource("/Res/RunAnimationLeft/sprite_"+i+".png"));
			}
			this.runningLeft = new DrawableAnimation(images2, 0.1, t);
			
			this.idleRight = new DrawableImage(ImageIO.read(getClass().getResource("/Res/RunAnimationIdles/sprite_right.png")),t);
			this.idleLeft = new DrawableImage(ImageIO.read(getClass().getResource("/Res/RunAnimationIdles/sprite_left.png")),t);
			this.flyRight = new DrawableImage(ImageIO.read(getClass().getResource("/Res/RunAnimationRight/sprite_5.png")),t);
			this.flyLeft = new DrawableImage(ImageIO.read(getClass().getResource("/Res/RunAnimationLeft/sprite_5.png")),t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.d = idleRight;
		this.p = new ChickenPlayerBounds(new Bounds(new Vector2f(0,0), new Vector2f(32,32)), false, t);
		
		state.put(PlayerState.InAir, true);
		state.put(PlayerState.OnGround, false);
		state.put(PlayerState.OnWall, false);
		state.put(PlayerState.FacingLeft, false);
		state.put(PlayerState.FacingRight, true);
	}
	
	@Override
	public Drawable getDrawable() {
		return d;
	}

	@Override
	public Physical getPhysical() {
		return p;
	}

	@Override
	public void setTransformation(Transformation t) {
		this.t = t;
		
	}

	@Override
	public Transformation getTransformation() {
		return t;
	}

	@Override
	public EntityReturn update() {
		List<Entity> entitiesToAdd = new LinkedList<Entity>();
		boolean removeSelf = false;
		
		state.put(PlayerState.InAir, true);
		state.put(PlayerState.OnGround, false);
		state.put(PlayerState.OnWall, false);
		state.put(PlayerState.OnCeil, false);
		state.put(PlayerState.MovingRight, false);
		state.put(PlayerState.MovingLeft, false);
		
		List<CollisionInfo> previousCollisionInfos = p.getPreviousCollisionInfos();
		for(CollisionInfo ci: previousCollisionInfos){
			if(ci.isColliding()){
				if(ci.getCollisionNormal().getY() == -1){
					state.put(PlayerState.InAir, false);
					state.put(PlayerState.OnGround, true);
					vel.setY(0);
				}
				if(ci.getCollisionNormal().getY() == 1){
					state.put(PlayerState.OnCeil, true);
				}
				if((ci.getCollisionNormal().getX() == 1 && vel.getX() < 0) || (ci.getCollisionNormal().getX() == -1 && vel.getX() > 0)){
					vel.setX(0);
				}
				if(ci.getCollisionNormal().getX() == 1 || ci.getCollisionNormal().getX() == -1){
					state.put(PlayerState.OnWall, true);
					float theta = (float) (Math.PI*1/3.0f);
					if(ci.getCollisionNormal().getX() > 0){
						wallJumpNormal = ci.getCollisionNormal().rotatedBy(-theta);
					} else {
						wallJumpNormal = ci.getCollisionNormal().rotatedBy(theta);
					}
				}
			}
		}
		p.emptyCollisionInfosList();
		
		if(Input.isUpDown() && state.get(PlayerState.OnGround) && !state.get(PlayerState.OnWall)){
			vel.setY(0);
			vel = (vel.plus(new Vector2f(0,-1).scaledBy(jumpVel)));
		}
		if(Input.isUpDown() && state.get(PlayerState.OnWall) && !state.get(PlayerState.OnCeil)){
			vel.setY(0);
			if(wallJumpNormal.getX() > 0){
				leftKeyLock.lock(0.4);
			} else {
				rightKeyLock.lock(0.4);
			}
			vel = (vel.plus(wallJumpNormal.scaledBy(wallJumpVel)));
		}
		if(Input.isRightDown() && !rightKeyLock.isLocked()){
			vel = (vel.plus(new Vector2f(1,0).scaledBy(movementSpeed)));
			state.put(PlayerState.FacingRight, true);
			state.put(PlayerState.MovingRight, true);
			
			state.put(PlayerState.FacingLeft, false);
			state.put(PlayerState.MovingLeft, false);
		}
		if(Input.isLeftDown() && !leftKeyLock.isLocked()){
			vel = (vel.plus(new Vector2f(-1,0).scaledBy(movementSpeed)));
			state.put(PlayerState.FacingLeft, true);
			state.put(PlayerState.MovingLeft, true);
			
			state.put(PlayerState.FacingRight, false);
			state.put(PlayerState.MovingRight, false);
		}
		if(Input.isDownDown()){
			vel = (vel.plus(new Vector2f(0,1).scaledBy(groundPoundSpeed)));
		}
		
		vel = vel.plus(gravityAccel);
		if(state.get(PlayerState.OnGround)){
			applyFriction(onGroundFriction);
		}
		if(state.get(PlayerState.OnWall)){
			applyFriction(onWallFriction);
		}
		else if(state.get(PlayerState.InAir)){
			applyFriction(inAirFriction);
		}
		
		t.setTranslation(t.getTranslation().plus(vel.scaledBy(0.001f)));
		
		if(Input.isZDown() && !zKeyLock.isLocked()){
			entitiesToAdd.add(new Minion(new Transformation(new Vector2f(1,1), this.t.getTranslation().plus(new Vector2f(100,0)))));
			zKeyLock.lock(0.2);
		}
		
		rightKeyLock.update(GamePanel.deltaTime);
		leftKeyLock.update(GamePanel.deltaTime);
		zKeyLock.update(GamePanel.deltaTime);
		
		if(!state.get(PlayerState.OnWall) && state.get(PlayerState.OnGround)){
			if(state.get(PlayerState.MovingRight)){
				runningRight.update();
				d = runningRight;
				idling = false;
			} else if(state.get(PlayerState.MovingLeft)){
				runningLeft.update();
				d = runningLeft;
				idling = false;
			} else if(state.get(PlayerState.FacingRight)){
				d = idleRight;
				idling = true;
			} else if(state.get(PlayerState.FacingLeft)){
				d = idleLeft;
				idling = true;
			}
		}
		if(state.get(PlayerState.OnWall) && state.get(PlayerState.OnGround)){
			if(state.get(PlayerState.FacingRight)){
				d = idleRight;
				idling = true;
			} else if(state.get(PlayerState.FacingLeft)){
				d = idleLeft;
				idling = true;
			}
		}
		if(state.get(PlayerState.InAir)){
			if(state.get(PlayerState.FacingRight)){
				d = flyRight;
				idling = false;
			} else if(state.get(PlayerState.FacingLeft)){
				d = flyLeft;
				idling = false;
			}
			//this.p.getPureBounds().getDim().setX(32);
		}
		
		if(idling){
			//this.p.getPureBounds().getDim().setX(10);
		} else {
			
		}
		if(!entitiesToAdd.isEmpty()){
			return new EntityReturn(entitiesToAdd,removeSelf);
		}
		return null;
	}
	
	private void applyFriction(Vector2f friction){
		Vector2f positiveVel = this.vel.absoluteValue().minus(friction);
		positiveVel.setX(Math.max(positiveVel.getX(),0.0f));
		positiveVel.setY(Math.max(positiveVel.getY(),0.0f));
		if(this.vel.getX() >= 0 && this.vel.getY() >= 0){
			this.vel = positiveVel;
		} else if(this.vel.getX() < 0 && this.vel.getY() >= 0){
			this.vel = positiveVel.times(new Vector2f(-1,1));
		} else if(this.vel.getX() >= 0 && this.vel.getY() < 0){
			this.vel = positiveVel.times(new Vector2f(1,-1));
		} else if(this.vel.getX() < 0 && this.vel.getY() < 0){
			this.vel = positiveVel.times(new Vector2f(-1,-1));
		}
	}
	
	private enum PlayerState{
		OnGround, InAir, OnWall, OnCeil, MovingRight, MovingLeft, FacingRight, FacingLeft
	}
}
