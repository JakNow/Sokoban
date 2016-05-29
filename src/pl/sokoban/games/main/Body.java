package pl.sokoban.games.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import pl.sokoban.games.game.GUI;
import pl.sokoban.games.game.KeyInput;
import pl.sokoban.games.game.Level;
import pl.sokoban.games.game.Menu;
import pl.sokoban.games.game.Options;
import pl.sokoban.games.toolbox.HighScore;

public class Body extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1771131127210009997L;

	public static int WIDTH = 920, HEIGHT = WIDTH * 9 / 12;

	private Thread thread;
	private boolean running = false;

	private Menu menu;
	private Level level;
	private GUI gui;
	private Options options;
	private HighScore highScore;

	private Frame frame;

	private static int xx, yy;

	private double cooldown;
	private double cdTimer;
	private boolean outCooldown = false;
	private static int animations;

	public enum STATE {
		Menu, Level, Options, Game, End, HighScore,
	};

	public static STATE gameState = STATE.Menu;

	public Body() {

		options = new Options();
		level = new Level();
		gui = new GUI(level);
		highScore = new HighScore(level);
		menu = new Menu(level, highScore, options);

		this.addMouseListener(menu);
		this.addMouseListener(gui);
		this.addMouseListener(options);
		this.addKeyListener(new KeyInput(level, this));

		new Window(WIDTH, HEIGHT, "Sokoban", this);
		frame = Window.getFrame();

	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		long animTimer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
				if (outCooldown) {
					cooldown++;
					if (cooldown > cdTimer) {
						outCooldown = false;
						cooldown = 0;
					}
				}
			}
			if (running)
				render();
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				if (gameState == STATE.Game)
					gui.setTimer();

			}
			if (System.currentTimeMillis() - animTimer > 125) {
				animTimer += 125;
				if (gameState == STATE.Game && !level.getPause() && !level.getExit()) {
					animations++;
					getAnimation();
				}
			}
		}
		stop();

	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;

	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (gameState != STATE.Game) {
			menu.render(g);

		}

		if (gameState == STATE.Game) {
			level.render(g);
			gui.render(g);
		}
		g.dispose();
		bs.show();
	}

	public void tick() {

		highScore.tick();

		if (gameState == STATE.Game) {
			level.tick();
			gui.tick();
		}

		xx = frame.getX();
		yy = frame.getY();

	}

	public static int getAnimation() {
		if (animations >= 8) {
			animations = 0;
		}
		return animations;
	}

	public void setCooldown(double cdTimer) {
		this.cdTimer = cdTimer;
		this.outCooldown = true;
	}

	public boolean getCooldown() {
		return outCooldown;
	}

	public static int getFrameX() {
		return xx;
	}

	public static int getFrameY() {
		return yy;
	}

	public static void main(String[] args) {
		new Body();
	}
}