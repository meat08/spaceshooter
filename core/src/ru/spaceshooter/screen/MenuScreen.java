package ru.spaceshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.base.State;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.LogoMainMenu;
import ru.spaceshooter.sprite.Star;
import ru.spaceshooter.base.MainMenu;

public class MenuScreen extends BaseScreen {

    private final Game game;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private LogoMainMenu logoMainMenu;
    private HitExplodePool hitExplodePool;
    private Star[] stars;
    private Music menuMusic;
    private MainMenu mainMenu;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        state = State.ACTIVE;
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        background = new Background(bg);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        hitExplodePool = new HitExplodePool(atlas);
        logoMainMenu = new LogoMainMenu(atlas, hitExplodePool);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainScreen.mp3"));
        musicOnOff();
        mainMenu = new MainMenu(multiplexer, game, this, fileHandle);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logoMainMenu.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainMenu.resize();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        menuMusic.dispose();
        mainMenu.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.CONFIG) {
            if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                exitConfig();
                flushPreference();
            }
        }
        return false;
    }

    public void exitConfig() {
        state = State.ACTIVE;
        mainMenu.hideConf();
    }

    public void musicOnOff() {
        if (isMusicOn) {
            menuMusic.play();
            menuMusic.setLooping(true);
        } else {
            menuMusic.stop();
        }
    }

    @Override
    public void setVolumeMusic(float volumeMusic) {
        super.setVolumeMusic(volumeMusic);
        menuMusic.setVolume(volumeMusic);
    }

    private void update(float delta) {
        logoMainMenu.update(delta);
        hitExplodePool.updateActiveSprites(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        mainMenu.update();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.ACTIVE) {
            logoMainMenu.draw(batch);
            hitExplodePool.drawActiveSprites(batch);
        }
        batch.end();
        mainMenu.draw();
    }

}