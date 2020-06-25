package ru.spaceshooter;

import com.badlogic.gdx.Game;

import ru.spaceshooter.screen.MenuScreen;
import ru.spaceshooter.screen.ScreenManager;

public class SpaceShooter extends Game {

	@Override
	public void create () {
		ScreenManager.getInstance().init(this);
		ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
	}

}
