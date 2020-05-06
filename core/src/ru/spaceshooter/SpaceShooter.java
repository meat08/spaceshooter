package ru.spaceshooter;

import com.badlogic.gdx.Game;

import ru.spaceshooter.screen.ScreenController;

public class SpaceShooter extends Game {

	@Override
	public void create () {
		new ScreenController(this);
	}

}
