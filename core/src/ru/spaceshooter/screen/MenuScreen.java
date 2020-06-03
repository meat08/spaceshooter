package ru.spaceshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.TitleConfig;
import ru.spaceshooter.sprite.buttons.ButtonAccelerometerOnOff;
import ru.spaceshooter.sprite.buttons.ButtonBack;
import ru.spaceshooter.sprite.buttons.ButtonConfig;
import ru.spaceshooter.sprite.buttons.ButtonExit;
import ru.spaceshooter.sprite.buttons.ButtonLoad;
import ru.spaceshooter.sprite.buttons.ButtonMusicOnOff;
import ru.spaceshooter.sprite.buttons.ButtonPlay;
import ru.spaceshooter.sprite.LogoMainMenu;
import ru.spaceshooter.sprite.Star;
import ru.spaceshooter.sprite.buttons.ButtonSoundOnOff;

public class MenuScreen extends BaseScreen {

    private final Game game;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private ButtonPlay buttonPlay;
    private LogoMainMenu logoMainMenu;
    private HitExplodePool hitExplodePool;
    private Star[] stars;
    private Music menuMusic;
    private State state;

    private enum State {ACTIVE, CONFIG}

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
        titleConfig = new TitleConfig(atlas);
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        buttonLoad = new ButtonLoad(atlas, game, fileHandle);
        buttonConfig = new ButtonConfig(atlas, this);
        buttonBack = new ButtonBack(atlas, this);
        buttonMusicOnOff = new ButtonMusicOnOff(atlas, this);
        buttonSoundOnOff = new ButtonSoundOnOff(atlas, this);
        buttonAccelerometerOnOff = new ButtonAccelerometerOnOff(atlas, this);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainScreen.mp3"));
        musicChange();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        titleConfig.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonLoad.resize(worldBounds);
        buttonConfig.resize(worldBounds);
        buttonBack.resize(worldBounds);
        buttonMusicOnOff.resize(worldBounds);
        buttonSoundOnOff.resize(worldBounds);
        buttonAccelerometerOnOff.resize(worldBounds);
        logoMainMenu.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
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
        buttonDispose();
        super.dispose();
    }

    private void buttonDispose() {
        buttonBack.dispose();
        buttonConfig.dispose();
        buttonPlay.dispose();
        buttonExit.dispose();
        buttonLoad.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.ACTIVE) {
            buttonExit.touchDown(touch, pointer, button);
            buttonPlay.touchDown(touch, pointer, button);
            buttonLoad.touchDown(touch, pointer, button);
            buttonConfig.touchDown(touch, pointer, button);
        } else if (state == State.CONFIG) {
            buttonBack.touchDown(touch, pointer, button);
            buttonMusicOnOff.touchDown(touch, pointer, button);
            buttonSoundOnOff.touchDown(touch, pointer, button);
            buttonAccelerometerOnOff.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.ACTIVE) {
            buttonExit.touchUp(touch, pointer, button);
            buttonPlay.touchUp(touch, pointer, button);
            buttonLoad.touchUp(touch, pointer, button);
            buttonConfig.touchUp(touch, pointer, button);
        } else if (state == State.CONFIG) {
            buttonBack.touchUp(touch, pointer, button);
            buttonMusicOnOff.touchUp(touch, pointer, button);
            buttonSoundOnOff.touchUp(touch, pointer, button);
            buttonAccelerometerOnOff.touchUp(touch, pointer, button);
        }
        return false;
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

    public void enterConfig() {
        state = State.CONFIG;
    }

    public void exitConfig() {
        state = State.ACTIVE;
    }

    public void musicChange() {
        if (isMusicOn) {
            menuMusic.play();
            menuMusic.setVolume(0.5f);
            menuMusic.setLooping(true);
        } else {
            menuMusic.stop();
        }
    }

    private void update(float delta) {
        logoMainMenu.update(delta);
        titleConfig.update(delta);
        hitExplodePool.updateActiveSprites(delta);
        buttonMusicOnOff.update(delta);
        buttonSoundOnOff.update(delta);
        buttonAccelerometerOnOff.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.ACTIVE) {
            logoMainMenu.draw(batch);
            buttonExit.draw(batch);
            buttonPlay.draw(batch);
            buttonLoad.draw(batch);
            buttonConfig.draw(batch);
            hitExplodePool.drawActiveSprites(batch);
        } else if (state == State.CONFIG) {
            titleConfig.draw(batch);
            buttonBack.draw(batch);
            buttonMusicOnOff.draw(batch);
            buttonSoundOnOff.draw(batch);
            buttonAccelerometerOnOff.draw(batch);
        }
        batch.end();
    }

}