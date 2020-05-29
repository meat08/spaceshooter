package ru.spaceshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.ButtonExit;
import ru.spaceshooter.sprite.ButtonLoad;
import ru.spaceshooter.sprite.ButtonPlay;
import ru.spaceshooter.sprite.LogoMainMenu;
import ru.spaceshooter.sprite.Star;

public class MenuScreen extends BaseScreen {

    private final Game game;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private ButtonLoad buttonLoad;
    private LogoMainMenu logoMainMenu;
    private HitExplodePool hitExplodePool;
    private Star[] stars;
    private Music menuMusic;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        background = new Background(bg);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        hitExplodePool = new HitExplodePool(atlas);
        logoMainMenu = new LogoMainMenu(atlas, hitExplodePool);
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        buttonLoad = new ButtonLoad(atlas, game, fileHandle);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainScreen.mp3"));
        menuMusic.play();
        menuMusic.setVolume(0.5f);
        menuMusic.setLooping(true);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonLoad.resize(worldBounds);
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
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        buttonLoad.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        buttonLoad.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        logoMainMenu.update(delta);
        hitExplodePool.updateActiveSprites(delta);
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
        logoMainMenu.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        buttonLoad.draw(batch);
        hitExplodePool.drawActiveSprites(batch);
        batch.end();
    }

}