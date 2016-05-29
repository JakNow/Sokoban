package pl.sokoban.games.toolbox;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Loader {

	private List<BufferedImage> menu_tiles = new ArrayList<BufferedImage>();
	private List<BufferedImage> options_tiles = new ArrayList<BufferedImage>();
	private List<BufferedImage> level_tiles = new ArrayList<BufferedImage>();
	private List<BufferedImage> map_tiles = new ArrayList<BufferedImage>();
	private List<BufferedImage> gui_tiles = new ArrayList<BufferedImage>();
	private List<BufferedImage> level_number_tiles = new ArrayList<BufferedImage>();
	private BufferedImage image;
	
	public Loader(){
		
	}
	
	
	
	public BufferedImage loadImage(String path) {

		try {
			image = ImageIO.read(getClass().getResource("/"+path+".png"));
		} catch (IOException e) {
			System.out.println("no image found");
			e.printStackTrace();
		}
		return image;
	}
	public List<BufferedImage> getImage(String name){
		switch (name){
		case "menu_tiles":
			return menu_tiles = getMenuTiles();
		case "options_tiles":
			return options_tiles = getOptionsTiles();
		case "level_tiles":
			return level_tiles = getLevelTiles();
		case "gui_tiles":
			return gui_tiles = getGuiTiles();
		case "map_tiles":
			return map_tiles = getMapTiles();
		case "level_number_tiles":
			return level_number_tiles = getLevelNumberTiles();
		default:
			return null;
		}
	}
	
	private List<BufferedImage> getLevelNumberTiles(){
		level_number_tiles.add(loadImage("levels/one_button_0"));
		level_number_tiles.add(loadImage("levels/one_button_1"));
		level_number_tiles.add(loadImage("levels/one_button_2"));

		level_number_tiles.add(loadImage("levels/two_button_0"));
		level_number_tiles.add(loadImage("levels/two_button_1"));
		level_number_tiles.add(loadImage("levels/two_button_2"));

		level_number_tiles.add(loadImage("levels/three_button_0"));
		level_number_tiles.add(loadImage("levels/three_button_1"));
		level_number_tiles.add(loadImage("levels/three_button_2"));

		level_number_tiles.add(loadImage("levels/four_button_0"));
		level_number_tiles.add(loadImage("levels/four_button_1"));
		level_number_tiles.add(loadImage("levels/four_button_2"));

		level_number_tiles.add(loadImage("levels/five_button_0"));
		level_number_tiles.add(loadImage("levels/five_button_1"));
		level_number_tiles.add(loadImage("levels/five_button_2"));

		level_number_tiles.add(loadImage("levels/six_button_0"));
		level_number_tiles.add(loadImage("levels/six_button_1"));
		level_number_tiles.add(loadImage("levels/six_button_2"));
		
		return level_number_tiles;
	}
	private List<BufferedImage> getOptionsTiles(){
		options_tiles.add(loadImage("return_button"));
		options_tiles.add(loadImage("return_button_over"));
		options_tiles.add(loadImage("no_button"));
		options_tiles.add(loadImage("no_button_over"));
		
		return options_tiles;
	}
	
	private List<BufferedImage> getMapTiles(){
		map_tiles.add(loadImage("level_1"));
		map_tiles.add(loadImage("level_2"));
		map_tiles.add(loadImage("level_3"));
		map_tiles.add(loadImage("level_4"));
		map_tiles.add(loadImage("level_5"));
		map_tiles.add(loadImage("level_6"));
		
		return map_tiles;
	}
	
	private List<BufferedImage> getLevelTiles(){
		level_tiles.add(loadImage("tile_0")); 			//0
		level_tiles.add(loadImage("tile_1"));			//1
		level_tiles.add(loadImage("tile_2"));			//2
		level_tiles.add(loadImage("tile_3"));			//3
		level_tiles.add(loadImage("player_one"));		//4
		level_tiles.add(loadImage("player_two"));		//5
		level_tiles.add(loadImage("player_three"));		//6
		level_tiles.add(loadImage("player_four"));		//7
		
		level_tiles.add(loadImage("crate_tile_one"));	//8
		level_tiles.add(loadImage("crate_tile_two"));	//9
		level_tiles.add(loadImage("crate_tile_three"));	//10
		level_tiles.add(loadImage("crate_tile_four"));	//11
		level_tiles.add(loadImage("crate_tile_five"));	//12
		level_tiles.add(loadImage("crate_tile_six"));	//13
		level_tiles.add(loadImage("crate_tile_seven"));	//14
		level_tiles.add(loadImage("crate_tile_eight"));	//15

		level_tiles.add(loadImage("exit_tile_one"));	//16
		level_tiles.add(loadImage("exit_tile_two"));	//17
		level_tiles.add(loadImage("exit_tile_three"));	//18
		level_tiles.add(loadImage("exit_tile_four"));	//19
		level_tiles.add(loadImage("exit_tile_five"));	//20
		level_tiles.add(loadImage("exit_tile_six"));	//21
		level_tiles.add(loadImage("exit_tile_seven"));	//22
		level_tiles.add(loadImage("exit_tile_eight"));	//23
		level_tiles.add(loadImage("crate_tile_nine"));	//24
		return level_tiles;
	}
	private List<BufferedImage> getMenuTiles(){
		
		menu_tiles.add(loadImage("game_title"));			//0
		menu_tiles.add(loadImage("play_button"));			//1
		menu_tiles.add(loadImage("play_button_over"));		//2
		menu_tiles.add(loadImage("options_button"));		//3
		menu_tiles.add(loadImage("options_button_over"));	//4
		
		menu_tiles.add(loadImage("return_button"));			//5
		menu_tiles.add(loadImage("return_button_over"));	//6
		menu_tiles.add(loadImage("continue_button"));		//7
		menu_tiles.add(loadImage("continue_button_over"));	//8
		menu_tiles.add(loadImage("exit_button"));			//9
		
		menu_tiles.add(loadImage("exit_button_over"));		//10
		menu_tiles.add(loadImage("highscore_button"));		//11
		menu_tiles.add(loadImage("highscore_button_over"));	//12
		menu_tiles.add(loadImage("yes_button"));			//13
		menu_tiles.add(loadImage("yes_button_over"));		//14
		
		menu_tiles.add(loadImage("no_button"));				//15
		menu_tiles.add(loadImage("no_button_over"));		//16
		menu_tiles.add(loadImage("clear_button"));			//17
		menu_tiles.add(loadImage("clear_button_over"));		//18
		menu_tiles.add(loadImage("choose_title"));			//19
		
		menu_tiles.add(loadImage("brawo"));					//20
		return menu_tiles;
	}
	
	private List<BufferedImage> getGuiTiles(){
		gui_tiles.add(loadImage("yes_button"));					//0
		gui_tiles.add(loadImage("yes_button_over"));			//1
		gui_tiles.add(loadImage("no_button"));					//2
		gui_tiles.add(loadImage("no_button_over"));				//3

		gui_tiles.add(loadImage("continue_button_pause"));		//4
		gui_tiles.add(loadImage("continue_button_over_pause"));	//5
		gui_tiles.add(loadImage("return_button_pause"));		//6
		gui_tiles.add(loadImage("return_button_over_pause"));	//7
		
		return gui_tiles;
	}
}
