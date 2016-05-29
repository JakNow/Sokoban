package pl.sokoban.games.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import pl.sokoban.games.main.Body;
import pl.sokoban.games.main.Body.STATE;

public class KeyInput extends KeyAdapter {

	private boolean[] keyDown = new boolean[4];

	private boolean acceptExit = false;

	private Level level;
	private Body body;

	public KeyInput(Level level, Body body) {
		this.level = level;
		this.body = body;

		keyDown[0] = false; // W
		keyDown[1] = false; // S
		keyDown[2] = false; // D
		keyDown[3] = false; // A

	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (!level.getPause() && Body.gameState == STATE.Game && !level.getExit()) {
			switch (e.getKeyCode()) {
			case 37:
				level.playerMove('l');
				break;
			case 38:
				level.playerMove('g');
				break;
			case 39:
				level.playerMove('p');
				break;
			case 40:
				level.playerMove('d');
				break;
			case 65:
				level.playerMove('l');
				break;
			case 87:
				level.playerMove('g');
				break;
			case 68:
				level.playerMove('p');
				break;
			case 83:
				level.playerMove('d');
				break;
			}
		}

		if (key == KeyEvent.VK_P && !level.getPause() && !body.getCooldown() && !level.getExit()) {
			level.setPause(true);
			body.setCooldown(10);

		}
		if (key == KeyEvent.VK_P && level.getPause() && !body.getCooldown() ) {
			level.setPause(false);
			body.setCooldown(10);
		}

		if (key == KeyEvent.VK_ESCAPE && Body.gameState == STATE.Game && !body.getCooldown() && !level.getExit() && !level.getPause()) {

			level.setExit(true);
			body.setCooldown(10);
			if (acceptExit) {
				System.exit(1);
			}
		}
		if (key == KeyEvent.VK_ESCAPE && Body.gameState == STATE.Game && !body.getCooldown() && level.getExit()) {

			level.setExit(false);
			body.setCooldown(10);

		}

		if (key == KeyEvent.VK_R) {
			level.restartMap();
		}

	}

	public void keyReleased(KeyEvent e) {

	}

	public void acceptExit(boolean exit) {
		this.acceptExit = exit;
	}
}
