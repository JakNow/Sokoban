package pl.sokoban.games.main;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5128407453474142933L;
	static JFrame frame;
	public Window(int width, int height, String title, Body body){
		frame = new JFrame(title);
		frame.setSize(width,height);
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(body);
		
		body.start();
		
	}
	
	public static JFrame getFrame(){
		return frame;
	}
}
