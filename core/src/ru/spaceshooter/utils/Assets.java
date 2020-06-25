package ru.spaceshooter.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ru.spaceshooter.screen.ScreenManager;

public class Assets {
    private static final Assets ourInstance = new Assets();

    private AssetManager assetManager;

    private Assets() {
        assetManager = new AssetManager();
    }

    public static Assets getInstance() {
        return ourInstance;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case MENU: {
                assetManager.load("textures/bg.png", Texture.class);
                assetManager.load("textures/menuAtlas.tpack", TextureAtlas.class);
                assetManager.load("sounds/mainScreen.mp3", Music.class);
                assetManager.load("skin/uiskin.json", Skin.class);
                break;
            }
            case GAME: {
                assetManager.load("textures/bg.png", Texture.class);
                assetManager.load("textures/mainAtlas.tpack", TextureAtlas.class);
                assetManager.load("skin/uiskin.json", Skin.class);
                assetManager.load("sounds/gameScreen.mp3", Music.class);
                assetManager.load("sounds/bossMusic.mp3", Music.class);
                assetManager.load("sounds/bullet.wav", Sound.class);
                assetManager.load("sounds/laser.wav", Sound.class);
                assetManager.load("sounds/explosion.wav", Sound.class);
                assetManager.load("sounds/big_explosion.wav", Sound.class);
                break;
            }
        }
    }

    public void clear() {
        assetManager.clear();
    }
}
