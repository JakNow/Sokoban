package pl.sokoban.games.game;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import pl.sokoban.games.main.Body;
import pl.sokoban.games.main.Body.STATE;
import pl.sokoban.games.toolbox.Button;
import pl.sokoban.games.toolbox.Loader;
import pl.sokoban.games.toolbox.Maths;

public class Options extends MouseAdapter{
	
	
	private Button returnButton;
	private Loader loader = new Loader();
	
	private List<BufferedImage> options_tiles_list = new ArrayList<BufferedImage>();
	
	
	public Options(){
		
		
		options_tiles_list = loader.getImage("options_tiles");
		
		returnButton = new Button(Body.WIDTH / 2 - 100, Body.HEIGHT - 150, 200, 100);
	}

	public void render(Graphics g){
		returnButton.createButton(g, options_tiles_list.get(0));
		if (Maths.mouseOverImage(returnButton)) {
			returnButton.createButton(g, options_tiles_list.get(1));
		}
	}
	public void tick(){
		
	}
	public void mousePressed(MouseEvent e) {
	
		if (Body.gameState == STATE.Options){
			if (Maths.mouseOver(returnButton)) {
				Body.gameState = STATE.Menu;
			}
		}
	}
	
}
