package com.gmail.youknowjoejoe.platbase;

import java.awt.event.KeyEvent;

public class Input {
	private static boolean upDown;
	private static boolean rightDown;
	private static boolean leftDown;
	private static boolean downDown;
	private static boolean zDown;
	
	public static boolean isUpDown() {
		return upDown;
	}
	public static boolean isRightDown() {
		return rightDown;
	}
	public static boolean isLeftDown() {
		return leftDown;
	}
	public static boolean isDownDown() {
		return downDown;
	}
	public static boolean isZDown() {
		return zDown;
	}
	
	public static void keyDown(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			upDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			rightDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			leftDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			downDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z){
			zDown = true;
		}
	}
	
	public static void keyUp(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			upDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			rightDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			leftDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			downDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z){
			zDown = false;
		}
	}
	
}
