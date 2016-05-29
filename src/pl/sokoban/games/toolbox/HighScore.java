package pl.sokoban.games.toolbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.sokoban.games.game.Level;
import pl.sokoban.games.main.Body;

public class HighScore {
	int[] scores = new int[6];
	int[][] time = new int[6][2];

	public static boolean PROGRESS_SCORE = false;
	public static boolean PROGRESS_TIME = false;
	private static Level level;

	public HighScore(Level level) {
	this.level = level;
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		Font font = new Font("Ariel", 1, 21);
		g.setFont(font);

		highScoreBar(g, 1);
		highScoreBar(g, 2);
		highScoreBar(g, 3);
		highScoreBar(g, 4);
		highScoreBar(g, 5);
		highScoreBar(g, 6);

	}

	private void highScoreBar(Graphics g, int level) {
		try {
			g.drawRect(Body.WIDTH / 2 - 125, 175 + (level - 1) * 25, 300, 25);
			g.drawString(Integer.toString(level), Body.WIDTH / 2 - 115, 196 + (level - 1) * 25);

			if (scores[level - 1] != 999999999) {
				g.drawString(Integer.toString(scores[level - 1]), Body.WIDTH / 2 - 35, 196 + (level - 1) * 25);
				g.drawString(timeString(level - 1), Body.WIDTH / 2 + 40, 196 + (level - 1) * 25);
			} else {
				g.drawString("-------------------------", Body.WIDTH / 2 - 50, 196 + (level - 1) * 25);

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private String timeString(int level) {
		String timer = null;
		if (time[level][0] > 9 && time[level][1] > 9) {
			timer = Integer.toString(time[level][0]) + ":" + Integer.toString(time[level][1]);
		}
		if (time[level][0] < 9 && time[level][1] > 9) {
			timer = "0" + Integer.toString(time[level][0]) + ":" + Integer.toString(time[level][1]);
		}
		if (time[level][0] > 9 && time[level][1] < 9) {
			timer = Integer.toString(time[level][0]) + ":0" + Integer.toString(time[level][1]);
		}
		if (time[level][0] < 9 && time[level][1] < 9) {
			timer = "0" + Integer.toString(time[level][0]) + ":0" + Integer.toString(time[level][1]);
		}
		return timer;
	}

	public void tick() {
		scores = highScore();
		time = bestTime();
	}

	public static void gameProgress() {
		int[] compare = progress();
		for (int i = 0; i < 6; i++) {
			if (compare[i] <= level.getLevelStatus(i)) {
				compare[i] = level.getLevelStatus(i);

			}
			try {
				FileOutputStream out = new FileOutputStream(new File("res/progress.txt"));
				ObjectOutputStream writer = new ObjectOutputStream(out);

				writer.writeObject(compare);

				writer.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void saveHighScore(int level, int score) {
		int[] compare = highScore();
		if (compare[level] > score) {
			compare[level] = score;
			PROGRESS_SCORE = true;
			try {
				FileOutputStream out = new FileOutputStream(new File("res/highscore.txt"));
				ObjectOutputStream writer = new ObjectOutputStream(out);

				writer.writeObject(compare);

				writer.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void saveBestTime(int level, int[] time) {
		int[][] compare = bestTime();

		if (compare[level][0] >= time[0] && compare[level][1] >= time[1]) {
			compare[level][0] = time[0];
			compare[level][1] = time[1];
			PROGRESS_TIME = true;
			try {
				FileOutputStream out = new FileOutputStream(new File("res/time.txt"));
				ObjectOutputStream writer = new ObjectOutputStream(out);

				writer.writeObject(compare);

				writer.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void clearTime() {
		int[][] clearTime = new int[6][2];
		for (int i = 0; i < 6; i++) {
			clearTime[i][0] = 999999999;
			clearTime[i][1] = 999999999;
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("res/time.txt"));
			ObjectOutputStream writer = new ObjectOutputStream(out);

			writer.writeObject(clearTime);

			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void clearScore() {
		int[] clearScore = new int[6];
		for (int i = 0; i < 6; i++) {
			clearScore[i] = 999999999;
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("res/highscore.txt"));
			ObjectOutputStream writer = new ObjectOutputStream(out);

			writer.writeObject(clearScore);

			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void clearProgress() {
		int[] clearProgress = new int[6];
		for (int i = 1; i < 6; i++) {
			clearProgress[i] = 0;
		}
		clearProgress[0] = 1;
		try {
			FileOutputStream out = new FileOutputStream(new File("res/progress.txt"));
			ObjectOutputStream writer = new ObjectOutputStream(out);

			writer.writeObject(clearProgress);

			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int[] getProgress() {

		int[] test = new int[6];
		try {
			FileInputStream in = new FileInputStream(new File("res/progress.txt"));
			@SuppressWarnings("resource")
			ObjectInputStream reader = new ObjectInputStream(in);

			test = (int[]) reader.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;

	}

	public static int[] highScore() {
		int[] test = new int[6];
		try {
			FileInputStream in = new FileInputStream(new File("res/highscore.txt"));
			@SuppressWarnings("resource")
			ObjectInputStream reader = new ObjectInputStream(in);

			test = (int[]) reader.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;
	}

	public static int[][] bestTime() {
		int[][] test = new int[6][2];
		try {
			FileInputStream in = new FileInputStream(new File("res/time.txt"));
			@SuppressWarnings("resource")
			ObjectInputStream reader = new ObjectInputStream(in);

			test = (int[][]) reader.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;
	}

	public static int[] progress() {
		int[] test = new int[6];
		try {
			FileInputStream in = new FileInputStream(new File("res/progress.txt"));
			@SuppressWarnings("resource")
			ObjectInputStream reader = new ObjectInputStream(in);

			test = (int[]) reader.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;
	}

}
