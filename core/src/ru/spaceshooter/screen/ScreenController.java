package ru.spaceshooter.screen;

import ru.spaceshooter.SpaceShooter;

public class ScreenController {

    private final SpaceShooter spaceShooter;
    private final MenuScreen menuScreen;
    private final GameScreen gameScreen;

    public ScreenController(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
        this.menuScreen = new MenuScreen(this);
        this.gameScreen = new GameScreen(this);
        spaceShooter.setScreen(menuScreen);
    }

    public void setMenuScreen() {
        spaceShooter.setScreen(menuScreen);
    }

    public void setGameScreen() {
        spaceShooter.setScreen(gameScreen);
    }

}
