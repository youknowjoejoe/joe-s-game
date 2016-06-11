package com.gmail.youknowjoejoe.platbase;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		
		JFrame window = new JFrame("joe\"s game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(new GamePanel(1600,900));
		window.pack();
		window.setVisible(true);
	}
}
