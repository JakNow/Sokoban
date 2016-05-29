package pl.sokoban.games.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import pl.sokoban.games.main.Body;
import pl.sokoban.games.main.Body.STATE;
import pl.sokoban.games.toolbox.Button;
import pl.sokoban.games.toolbox.HighScore;
import pl.sokoban.games.toolbox.Loader;
import pl.sokoban.games.toolbox.Maths;

public class GUI extends MouseAdapter {

	private Level level;
	private Loader loader = new Loader();

	private Button exitYes, exitNo;
	private Button continueButton, returnMenu;
	private List<BufferedImage> gui_tiles_list = new ArrayList<BufferedImage>();

	private int mx, my, xx, yy;
	private int[] mousePoint = new int[2];

	static int min = 0;
	static int sec = 0;

	public GUI(Level level) {
		this.level = level;

		exitYes = new Button(Body.WIDTH / 2 - 75, Body.HEIGHT / 2 + 30, 50, 50);
		exitNo = new Button(Body.WIDTH / 2 +25, Body.HEIGHT / 2 + 30, 50, 50);
		continueButton = new Button(Body.WIDTH / 2 - 75, Body.HEIGHT / 2 +15, 150, 50);
		returnMenu = new Button(Body.WIDTH / 2 - 50, Body.HEIGHT / 2 + 75, 100, 50);

		gui_tiles_list = loader.getImage("gui_tiles");
	}

	public static int[] startTime() {
		int[] time = new int[2];
		min = 0;
		sec = 0;
		time[0] = min;
		time[1] = sec;
		return time;
	}

	public static int[] getTime() {
		int[] time = new int[2];
		time[0] = min;
		time[1] = sec;

		return time;
	}

	public void render(Graphics g) {
		Font guiFont = new Font("Ariel", 1, 30);
		Font pauseFont = new Font("Ariel", 1, 30);

		g.setFont(guiFont);
		g.setColor(Color.green);

		// moves
		g.drawString("Moves: ", 215, 50);
		g.drawString(Integer.toString(level.getCount()), 325, 50);
		g.drawString("Time: ", 515, 50);

		guiClock(g);
		// exit menu
		if (level.getExit()) {

			g.setColor(Color.black);
			g.fillRect(Body.WIDTH / 2 - 100, Body.HEIGHT / 2 - 105, 210, 200);

			g.setColor(Color.green);
			g.drawRect(Body.WIDTH / 2 - 100, Body.HEIGHT / 2 - 105, 210, 200);
			g.drawString("Do you really", Body.WIDTH / 2 - 90, Body.HEIGHT / 2 - 70);
			g.drawString("want to quit?", Body.WIDTH / 2 - 89, Body.HEIGHT / 2 - 30);

			exitYes.createButton(g, gui_tiles_list.get(0));
			if (Maths.mouseOverImage(exitYes)) {
				exitYes.createButton(g, gui_tiles_list.get(1));
			}

			exitNo.createButton(g, gui_tiles_list.get(2));
			if (Maths.mouseOverImage(exitNo)) {
				exitNo.createButton(g, gui_tiles_list.get(3));
			}

			// pause menu;
		}
		if (level.getPause()) {
			g.setColor(Color.black);
			g.fillRect(Body.WIDTH / 2 - 100, Body.HEIGHT / 2 - 150, 200, 300);

			g.setColor(Color.green);
			g.drawRect(Body.WIDTH / 2 - 100, Body.HEIGHT / 2 - 150, 200, 300);
			Font note = new Font("Ariel",1,20);
			g.setFont(note);
			g.drawString("Press R to restart", Body.WIDTH/2-80, Body.HEIGHT/2-50);
			g.setFont(pauseFont);
			g.drawString("PAUSE", Body.WIDTH / 2 - 50, Body.HEIGHT / 2 - 110);

			continueButton.createButton(g, gui_tiles_list.get(4));
			if (Maths.mouseOverImage(continueButton)) {
				continueButton.createButton(g, gui_tiles_list.get(5));
			}

			returnMenu.createButton(g, gui_tiles_list.get(6));
			if (Maths.mouseOverImage(returnMenu)) {
				returnMenu.createButton(g, gui_tiles_list.get(7));
			}

		}
	}

	public void tick() {

	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

		if (Body.gameState == STATE.Game) {
			if (level.getExit()) {
				if (Maths.mouseOver(exitYes)) {
					HighScore.progress();
					HighScore.gameProgress();
					System.exit(1);
				}
				if (Maths.mouseOver(exitNo)) {
					level.setExit(false);
					level.setPause(false);
				}
			}
			if (level.getPause()) {
				if (Maths.mouseOver(continueButton)) {
					level.setPause(false);
				}
				if (Maths.mouseOver(returnMenu)) {
					level.setPause(false);
					Body.gameState = STATE.Menu;
				}
			}
		}
	}

	public int[] mousePoint() {
		mx = (int) MouseInfo.getPointerInfo().getLocation().getX();
		my = (int) MouseInfo.getPointerInfo().getLocation().getY();

		xx = Body.getFrameX();
		yy = Body.getFrameY();

		mousePoint[0] = mx - xx;
		mousePoint[1] = my - yy;

		return mousePoint;

	}

	private void guiClock(Graphics g) {
		if (min < 10 && sec < 10) {
			g.drawString("0" + Integer.toString(min) + ":0" + Integer.toString(sec), 600, 50);
		} else if (min < 10 && sec >= 10) {
			g.drawString("0" + Integer.toString(min) + ":" + Integer.toString(sec), 600, 50);
		} else if (min >= 10 && sec < 10) {
			g.drawString(Integer.toString(min) + ":0" + Integer.toString(sec), 600, 50);
		} else if (min >= 0 && sec >= 10) {
			g.drawString(Integer.toString(min) + ":" + Integer.toString(sec), 600, 50);
		}
	}

	public static String getTimeEnd(){
		String time = "00:00";
		if (min < 10 && sec < 10) {
			return time = "0" + Integer.toString(min) + ":0" + Integer.toString(sec);
		} else if (min < 10 && sec >= 10) {
			return time = "0" + Integer.toString(min) + ":" + Integer.toString(sec);
		} else if (min >= 10 && sec < 10) {
			return time = Integer.toString(min) + ":0" + Integer.toString(sec);
		} else if (min >= 0 && sec >= 10) {
			 return time = Integer.toString(min) + ":" + Integer.toString(sec);
		}
		return time;
	}
		
	

	public void setTimer() {

		if (!level.getPause() && !level.getExit()) {
			sec++;
			if (sec >= 60) {
				min += 1;
				sec = 0;

			}
		}
	}
}
