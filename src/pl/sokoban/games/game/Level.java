package pl.sokoban.games.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import pl.sokoban.games.main.Body;
import pl.sokoban.games.main.Body.STATE;
import pl.sokoban.games.toolbox.HighScore;
import pl.sokoban.games.toolbox.Loader;

public class Level {

	private Loader loader = new Loader();
	
	
	private List<BufferedImage> tiles_list = new ArrayList<BufferedImage>();
	private List<BufferedImage> map_tiles = new ArrayList<BufferedImage>();

	private int levelNumb;
	private int[][] mapLoader = null;
	private int[][] map;
	private int[][] mapExit;

	private boolean checkExit = false;
	private boolean checkPause = false;

	private int[] levelCheck = new int[6];

	int playerX;
	int playerY;

	int count;

	public Level() {

		levelCheck = HighScore.getProgress();
		
		tiles_list = loader.getImage("level_tiles");
		map_tiles = loader.getImage("map_tiles");
		count = 0;
	}

	public void tick() {
		if (Body.gameState == STATE.Game) {

			if (checkWin(map, mapExit)) {
				if (levelNumb == 6) {
					levelCheck(levelNumb, 2);
					HighScore.saveHighScore(levelNumb - 1, count);
					HighScore.saveBestTime(levelNumb-1, GUI.getTime());
					
					Body.gameState = STATE.End;

				} else {
					levelCheck(levelNumb, 2);
					if (levelCheck[levelNumb] != 2) {
						levelCheck(levelNumb + 1, 1);
					}
					HighScore.saveHighScore(levelNumb - 1, count);
					HighScore.saveBestTime(levelNumb-1, GUI.getTime());
				
					Body.gameState = STATE.End;
				}
			}
		}

	}

	public void render(Graphics g) {

		drawMap(g, map, mapExit);
		
	}

	public BufferedImage getMap(BufferedImage image) {
		image = map_tiles.get(getLevel()-1);
		return image;
	}

	public int[][] loadMap(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		mapLoader = new int[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {

				int pixel = image.getRGB(x, y);

				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				// floor
				if (red == 255 && green == 255 && blue == 255) {
					mapLoader[y][x] = 0;
				}
				// wall
				if (red == 0 && green == 0 && blue == 0) {
					mapLoader[y][x] = 1;
				}
				// crate
				if (red == 0 && green == 255 && blue == 0) {
					mapLoader[y][x] = 3;
				}

				// player
				if (red == 0 && green == 0 && blue == 255) {
					mapLoader[y][x] = 4;
					setY(y);
					setX(x);
				}
			}
		}
		return mapLoader;
	}

	public int[][] loadMapExit(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		mapLoader = new int[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {

				int pixel = image.getRGB(x, y);

				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				// exit
				if (red == 255 && green == 0 && blue == 0) {
					mapLoader[y][x] = 2;
				}

			}
		}
		return mapLoader;
	}


	private void drawMap(Graphics g, int[][] map, int[][] mapExit) {

		try {
			int diffX = Body.WIDTH / 2 - map[0].length / 2 * 32;
			int diffY = Body.HEIGHT / 2 - map.length / 2 * 32;

			for (int j = 0; j < map.length; j++) {
				for (int i = 0; i < map[0].length; i++) {
					switch (map[j][i]) {
					case 0:
						g.drawImage(tiles_list.get(0), 32 * i + diffX, 32 * j + diffY, null);
						break;
					case 1:
						g.drawImage(tiles_list.get(1), 32 * i + diffX, 32 * j + diffY, null);
						break;

					case 3:
						crateAnimation(g, i, j, diffX, diffY);
						break;
					case 4:
						//draw player
						playerAnimation(g,i,j,diffX,diffY);
					
						break;
					}

				}
			}

			for (int j = 0; j < mapExit.length; j++) {
				for (int i = 0; i < mapExit[0].length; i++) {
					switch (mapExit[j][i]) {

					case 2:
						exitAnimation(g, i, j, diffX, diffY);
						break;

					}
					if (map[j][i] == 3 && mapExit[j][i] == 2) {
						//crateAnimation(g, i, j, diffX, diffY);
						g.drawImage(tiles_list.get(24), 32 * i + diffX, 32 * j + diffY, null);
						
					}
					if (map[j][i] == 4 && mapExit[j][i] == 2) {
						playerAnimation(g,i,j,diffX,diffY);
					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println("problem");
			Body.gameState = STATE.Level;
		}

	}


	public void playerMove(char gdzie) {
		count++;
		switch (gdzie) {
		case 'l':
			if (map[playerY][playerX - 1] == 0) {
				map[playerY][playerX - 1] = 4;
				map[playerY][playerX] = 0;
				playerX--;
			} else if ((map[playerY][playerX - 1] == 3) && (map[playerY][playerX - 2] == 0)) {
				map[playerY][playerX - 2] = 3;
				map[playerY][playerX - 1] = 4;
				map[playerY][playerX] = 0;
				playerX--;
			}
			break;
		case 'p':
			if (map[playerY][playerX + 1] == 0) {
				map[playerY][playerX + 1] = 4;
				map[playerY][playerX] = 0;
				playerX++;
			} else if ((map[playerY][playerX + 1] == 3) && (map[playerY][playerX + 2] == 0)) {
				map[playerY][playerX + 2] = 3;
				map[playerY][playerX + 1] = 4;
				map[playerY][playerX] = 0;
				playerX++;
			}
			break;
		case 'g':
			if (map[playerY - 1][playerX] == 0) {
				map[playerY - 1][playerX] = 4;
				map[playerY][playerX] = 0;
				playerY--;
			} else if ((map[playerY - 1][playerX] == 3) && (map[playerY - 2][playerX] == 0)) {
				map[playerY - 2][playerX] = 3;
				map[playerY - 1][playerX] = 4;
				map[playerY][playerX] = 0;
				playerY--;
			}
			break;
		case 'd':
			if (map[playerY + 1][playerX] == 0) {
				map[playerY + 1][playerX] = 4;
				map[playerY][playerX] = 0;
				playerY++;
			} else if ((map[playerY + 1][playerX] == 3) && (map[playerY + 2][playerX] == 0)) {
				map[playerY + 2][playerX] = 3;
				map[playerY + 1][playerX] = 4;
				map[playerY][playerX] = 0;
				playerY++;
			}

			break;
		}
		
	}

	private void playerAnimation(Graphics g,int i,int j,int diffX, int diffY){
		
		switch(Body.getAnimation()){
		case 0:
			g.drawImage(tiles_list.get(4),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 2: 
			g.drawImage(tiles_list.get(5),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 4: 
			g.drawImage(tiles_list.get(6),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 6: 
			g.drawImage(tiles_list.get(7),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 1:
			g.drawImage(tiles_list.get(4),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 3: 
			g.drawImage(tiles_list.get(5),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 5: 
			g.drawImage(tiles_list.get(6),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 7: 
			g.drawImage(tiles_list.get(7),32 * i + diffX, 32 * j + diffY,null);
			break;
		default: 
			g.drawImage(tiles_list.get(4),32 * i + diffX, 32 * j + diffY,null);break;
		}
	}
	
	private void crateAnimation(Graphics g,int i,int j,int diffX, int diffY){
		switch(Body.getAnimation()){
		case 0:
			g.drawImage(tiles_list.get(8),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 1: 
			g.drawImage(tiles_list.get(9),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 2: 
			g.drawImage(tiles_list.get(10),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 3: 
			g.drawImage(tiles_list.get(11),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 4:
			g.drawImage(tiles_list.get(12),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 5: 
			g.drawImage(tiles_list.get(13),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 6: 
			g.drawImage(tiles_list.get(14),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 7: 
			g.drawImage(tiles_list.get(15),32 * i + diffX, 32 * j + diffY,null);
			break;
		default: 
			g.drawImage(tiles_list.get(8),32 * i + diffX, 32 * j + diffY,null);break;
		}
	}
	
	private void exitAnimation(Graphics g,int i,int j,int diffX, int diffY){
		switch(Body.getAnimation()){
		case 0:
			g.drawImage(tiles_list.get(16),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 1: 
			g.drawImage(tiles_list.get(17),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 2: 
			g.drawImage(tiles_list.get(18),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 3: 
			g.drawImage(tiles_list.get(19),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 4:
			g.drawImage(tiles_list.get(20),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 5: 
			g.drawImage(tiles_list.get(21),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 6: 
			g.drawImage(tiles_list.get(22),32 * i + diffX, 32 * j + diffY,null);
			break;
		case 7: 
			g.drawImage(tiles_list.get(23),32 * i + diffX, 32 * j + diffY,null);
			break;
		default: 
			g.drawImage(tiles_list.get(12),32 * i + diffX, 32 * j + diffY,null);break;
		}
	}

	public void restartMap(){
		map = getMapLoader();
		mapExit = getMapExitLoader();
	}
	
	public boolean checkWin(int[][] map, int[][] mapExit) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (mapExit[i][j] == 2 && map[i][j] != 3) {
					return false;
				}
			}
		}

		return true;
	}
	
	public int[] clearProgress(){
		for (int i = 1; i < levelCheck.length;i++){
			levelCheck[i] =0;
		}
		levelCheck[0]=1;
		
		return levelCheck;
	}

	public boolean getPause() {
		return checkPause;
	}

	public void setPause(boolean checkPause) {
		this.checkPause = checkPause;
	}

	public boolean getExit() {
		return checkExit;
	}

	public void setExit(boolean checkExit) {
		this.checkExit = checkExit;
	}

	public int getCount() {
		return count;
	}

	public int[][] getMap() {
		return map;
	}
	
	public void setMap(int[][] map, int[][] mapExit) {
		this.map = map;
		this.mapExit = mapExit;
	}

	public int[][] getMapLoader() {
		mapLoader = loadMap(map_tiles.get(getLevel() - 1));
		return mapLoader;
	}

	public int[][] getMapExitLoader() {
		mapLoader = loadMapExit(map_tiles.get(getLevel() - 1));
		return mapLoader;
	}

	public void setLevel(int levelNumb) {
		this.levelNumb = levelNumb;
	}

	public void levelCheck(int levelNumb, int levelStatus) {
		levelCheck[levelNumb - 1] = levelStatus;
	}
	public int getLevelStatus(int levelNumb){
		return levelCheck[levelNumb];
	}

	public int getLevelCheck(int levelNumb) {
		return levelCheck[levelNumb - 1];
	}

	public void setX(int playerX) {
		this.playerX = playerX;
	}

	public void setY(int playerY) {
		this.playerY = playerY;
	}

	public int getLevel() {
		return levelNumb;
	}

	public int getMoveCounts() {
		return count;
	}

	public void setMoveCounts() {
		count = 0;
	}
}
