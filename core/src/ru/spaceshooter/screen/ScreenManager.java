package ru.spaceshooter.screen;

import com.badlogic.gdx.Screen;

import ru.spaceshooter.SpaceShooter;
import ru.spaceshooter.utils.Assets;

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME
        }

    private static ScreenManager ourInstance = new ScreenManager();

    private SpaceShooter game;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private Screen targetScreen;


    public static ScreenManager getInstance() {
        return ourInstance;
    }

    public void init(SpaceShooter game) {
        this.game = game;
        this.gameScreen = new GameScreen();
        this.menuScreen = new MenuScreen(game);
    }

    public void changeScreen(ScreenType type) {
        Assets.getInstance().clear();
        game.setScreen(new LoadingScreen());
        switch (type) {
            case MENU: {
                targetScreen = menuScreen;
                Assets.getInstance().loadAssets(ScreenType.MENU);
                break;
            }
            case GAME: {
                targetScreen = gameScreen;
                Assets.getInstance().loadAssets(ScreenType.GAME);
                break;
            }
        }
    }

    public void goToTarget() {
        game.setScreen(targetScreen);
    }
}
