package pl.sokoban.games.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import pl.sokoban.games.main.Body;
import pl.sokoban.games.main.Body.STATE;
import pl.sokoban.games.toolbox.Button;
import pl.sokoban.games.toolbox.HighScore;
import pl.sokoban.games.toolbox.Loader;
import pl.sokoban.games.toolbox.Maths;

public class Menu extends MouseAdapter implements MouseMotionListener {

	private Level level;
	private HighScore highScore;
	private Options options;
	private Loader loader = new Loader();

	private Button playButton, optionsButton, exitButton, returnButton, continueButton, highscoreButton,
			returnHighScoreButton;

	private Button level1, level2, level3, level4, level5, level6;

	private Button clearScore;
	
	private List<BufferedImage> menu_tiles_list = new ArrayList<BufferedImage>();
	private List<BufferedImage> level_number_list = new ArrayList<BufferedImage>();


	public Menu(Level level, HighScore highScore, Options options) {

		this.level = level;
		this.highScore = highScore;
		this.options = options;

		playButton = new Button(Body.WIDTH / 2 - 100, 150, 200, 100);
		optionsButton = new Button(Body.WIDTH / 2 - 100, 250, 200, 100);
		highscoreButton = new Button(Body.WIDTH / 2 - 150, 350, 300, 100);
		exitButton = new Button(Body.WIDTH / 2 - 100, Body.HEIGHT - 150, 200, 100);

		level1 = new Button(300, 300, 32, 32);
		level2 = new Button(350, 300, 32, 32);
		level3 = new Button(400, 300, 32, 32);
		level4 = new Button(450, 300, 32, 32);
		level5 = new Button(500, 300, 32, 32);
		level6 = new Button(550, 300, 32, 32);

		clearScore = new Button(Body.WIDTH / 2 - 50, Body.HEIGHT - 175, 100, 50);
		returnButton = new Button(Body.WIDTH / 2 - 100, Body.HEIGHT - 150, 200, 100);
		continueButton = new Button(Body.WIDTH / 2 - 150, Body.HEIGHT - 150, 300, 100);

		
		returnHighScoreButton = new Button(Body.WIDTH / 2 - 100, Body.HEIGHT - 125, 200, 100);
		menu_tiles_list = loader.getImage("menu_tiles");
		level_number_list = loader.getImage("level_number_tiles");
	}

	public void render(Graphics g) {
		if (Body.gameState == STATE.Menu) {

			Font font = new Font("Arial", 1, 50);
			g.setFont(font);
			g.setColor(Color.GREEN);

			g.drawImage(menu_tiles_list.get(0), Body.WIDTH / 2 - 150, 0, null);

			playButton.createButton(g,menu_tiles_list.get(1));
			if (Maths.mouseOverImage(playButton)) {
				playButton.createButton(g,menu_tiles_list.get(2));
			}

			optionsButton.createButton(g,menu_tiles_list.get(3));
			if (Maths.mouseOverImage(optionsButton)) {
				optionsButton.createButton(g,menu_tiles_list.get(4));

			}

			exitButton.createButton(g,menu_tiles_list.get(9));
			if (Maths.mouseOverImage(exitButton)) {
				exitButton.createButton(g,menu_tiles_list.get(10));
			}
			highscoreButton.createButton(g,menu_tiles_list.get(11));
			if (Maths.mouseOverImage(highscoreButton)) {
				highscoreButton.createButton(g,menu_tiles_list.get(12));
			}
		}
		if (Body.gameState == STATE.Level) {
			
			g.drawImage(menu_tiles_list.get(19), Body.WIDTH / 2 - 200, 0, null);

level1.createLevelButton(g, 1, level_number_list,level.getLevelCheck(1));
level2.createLevelButton(g, 2, level_number_list,level.getLevelCheck(2));
level3.createLevelButton(g, 3, level_number_list,level.getLevelCheck(3));
level4.createLevelButton(g, 4, level_number_list, level.getLevelCheck(4));
level5.createLevelButton(g, 5, level_number_list,level.getLevelCheck(5));
level6.createLevelButton(g, 6, level_number_list,level.getLevelCheck(6));

			
			returnButton.createButton(g,menu_tiles_list.get(5));
			if (Maths.mouseOverImage(returnButton)) {
				returnButton.createButton(g,menu_tiles_list.get(6));
			}
		}
		if (Body.gameState == STATE.HighScore) {
			g.drawImage(menu_tiles_list.get(11), Body.WIDTH / 2 - 150, 0, null);

			highScore.render(g);

			clearScore.createButton(g,menu_tiles_list.get(17));
			if (Maths.mouseOverImage(clearScore)) {
				clearScore.createButton(g,menu_tiles_list.get(18));
			}

			returnHighScoreButton.createButton(g,menu_tiles_list.get(5));
			if (Maths.mouseOverImage(returnHighScoreButton)) {
				returnHighScoreButton.createButton(g,menu_tiles_list.get(6));
			}
		}
		if (Body.gameState == STATE.End) {
			Font font = new Font("Arial", 1, 50);
			Font highScore = new Font ("Arial",1,25);
			g.setFont(font);
			g.setColor(Color.GREEN);
			
			if (HighScore.PROGRESS_SCORE || HighScore.PROGRESS_TIME){
			g.drawImage(menu_tiles_list.get(20), Body.WIDTH/2-150, Body.HEIGHT/2-150, null);
			g.drawString("New high score!", 260, 100);
			g.setFont(highScore);
			if (!HighScore.PROGRESS_SCORE){
				g.setColor(Color.red);
			}
			g.drawString("Moves: " + Integer.toString(level.getCount()), 260, 150);
			if (!HighScore.PROGRESS_TIME){
				g.setColor(Color.red);
			}
			g.drawString("Time: " + GUI.getTimeEnd(), 500, 150);
			}
			
			
			if (!HighScore.PROGRESS_SCORE && !HighScore.PROGRESS_TIME){
				g.drawString("Congratulations!", Body.WIDTH/2-215, Body.HEIGHT/2-100);
			}
			continueButton.createButton(g,menu_tiles_list.get(7));

			if (Maths.mouseOverImage(continueButton)) {
				continueButton.createButton(g,menu_tiles_list.get(8));
			}

		}
		if (Body.gameState == STATE.Options) {
			options.render(g);
		}
	}

	public void tick() {
	}


	public void mousePressed(MouseEvent e) {
		
		if (Body.gameState == STATE.Menu) {
			if (Maths.mouseOver(playButton)) {
				Body.gameState = STATE.Level;
			}
			if (Maths.mouseOver(optionsButton)) {
				Body.gameState = STATE.Options;
			}
			if (Maths.mouseOver(highscoreButton)) {
				Body.gameState = STATE.HighScore;
			}
			if (Maths.mouseOver(exitButton)) {
				HighScore.gameProgress();
				System.exit(1);
			}
		}
		if (Body.gameState == STATE.Level) {
			if (Maths.mouseOver(level1) && (level.getLevelCheck(1) == 1 || level.getLevelCheck(1) == 2)) {

				level.setLevel(1);
				level.setMoveCounts();
				level.setMap(level.getMapLoader(), level.getMapExitLoader());
				HighScore.PROGRESS_SCORE = false;
				HighScore.PROGRESS_TIME = false;
				GUI.startTime();
				
				Body.gameState = STATE.Game;

			}
			if (Maths.mouseOver(level2) && (level.getLevelCheck(2) == 1 || level.getLevelCheck(2) == 2)) {

				level.setLevel(2);
				level.setMoveCounts();
				level.setMap(level.getMapLoader(), level.getMapExitLoader());

				HighScore.PROGRESS_SCORE = false;
				HighScore.PROGRESS_TIME = false;
				GUI.startTime();
				
				Body.gameState = STATE.Game;
			}

			if (Maths.mouseOver(level3) && (level.getLevelCheck(3) == 1 || level.getLevelCheck(3) == 2)) {

				level.setLevel(3);
				level.setMoveCounts();
				level.setMap(level.getMapLoader(), level.getMapExitLoader());

				HighScore.PROGRESS_SCORE = false;
				HighScore.PROGRESS_TIME = false;
				GUI.startTime();
				
				Body.gameState = STATE.Game;

			}
			if (Maths.mouseOver(level4) && (level.getLevelCheck(4) == 1 || level.getLevelCheck(4) == 2)) {

				level.setLevel(4);
				level.setMoveCounts();
				level.setMap(level.getMapLoader(), level.getMapExitLoader());

				HighScore.PROGRESS_SCORE = false;
				HighScore.PROGRESS_TIME = false;
				GUI.startTime();
				
				Body.gameState = STATE.Game;

			}
			if (Maths.mouseOver(level5) && (level.getLevelCheck(5) == 1 || level.getLevelCheck(5) == 2)) {

				level.setLevel(5);
				level.setMoveCounts();
				level.setMap(level.getMapLoader(), level.getMapExitLoader());

				HighScore.PROGRESS_SCORE = false;
				HighScore.PROGRESS_TIME = false;
				GUI.startTime();
				
				Body.gameState = STATE.Game;

			}
			if (Maths.mouseOver(level6) && (level.getLevelCheck(6) == 1 || level.getLevelCheck(6) == 2)) {

				level.setLevel(6);
				level.setMoveCounts();
				level.setMap(level.getMapLoader(), level.getMapExitLoader());

				HighScore.PROGRESS_SCORE = false;
				HighScore.PROGRESS_TIME = false;
				GUI.startTime();
				
				Body.gameState = STATE.Game;

			}
			if (Maths.mouseOver(returnButton)) {
				Body.gameState = STATE.Menu;
			}
		}
		if (Body.gameState == STATE.HighScore) {
			if (Maths.mouseOver(clearScore)) {
				level.clearProgress();
				HighScore.clearScore();
				HighScore.clearProgress();
				HighScore.clearTime();
			}
			if (Maths.mouseOver(returnHighScoreButton)) {
				Body.gameState = STATE.Menu;
			}
		}
		if (Body.gameState == STATE.Game) {

		}
		if (Body.gameState == STATE.End) {
			if (Maths.mouseOver(continueButton)) {
				Body.gameState = STATE.Level;
			}
		}
	}
}