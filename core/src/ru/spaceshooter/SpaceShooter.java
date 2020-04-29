package ru.spaceshooter;

import com.badlogic.gdx.Game;

import ru.spaceshooter.screen.MenuScreen;

public class SpaceShooter extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen());
	}

}
