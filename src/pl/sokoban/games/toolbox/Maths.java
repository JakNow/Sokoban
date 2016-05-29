package pl.sokoban.games.toolbox;

import java.awt.MouseInfo;

import pl.sokoban.games.main.Body;

public class Maths {

	public static float clamp(float var, float min, float max){
		if (var >= max){
			return var = max;
		}
		else if (var <= min){
			return var = min;
		}
		else 
			return var;
	}
	public static boolean mouseOverLevel(Button button) {
		int[] mousePoint = mousePoint();
		int mx = mousePoint[0];
		int my = mousePoint[1];

		if (mx > button.getX() && mx < button.getX() + button.getWidth()) {
			if (my > button.getY() && my < button.getY() - button.getHeight()) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public static  boolean mouseOverImage(Button button) {
		int[] mousePoint = mousePoint();
		int mx = mousePoint[0];
		int my = mousePoint[1];

		if (mx > button.getX() && mx < button.getX()+button.getWidth()) {
			if (my > button.getY() && my < button.getY() + button.getHeight()) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public static boolean mouseOver(Button button) {
		int[] mousePoint = mousePoint();
		int mx = mousePoint[0];
		int my = mousePoint[1];
		
		if (mx >= button.getX() && mx <= button.getX() + button.getWidth()) {
			if (my >= button.getY()  && my <= button.getY()+ button.getHeight()) {
				return true;
			} else
				return false;
		} else
			return false;
	}
	
	public static int[] mousePoint() {
		int[] mousePoint = new int[2];
		int mx = (int) MouseInfo.getPointerInfo().getLocation().getX();
		int my = (int) MouseInfo.getPointerInfo().getLocation().getY();

		int xx = Body.getFrameX();
		int yy = Body.getFrameY();

		mousePoint[0] = mx - xx;
		mousePoint[1] = my - yy-26;

		return mousePoint;

	}
}
