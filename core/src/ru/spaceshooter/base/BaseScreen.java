package ru.spaceshooter.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.math.MatrixUtils;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.TitleConfig;
import ru.spaceshooter.sprite.buttons.ButtonAccelerometerOnOff;
import ru.spaceshooter.sprite.buttons.ButtonBack;
import ru.spaceshooter.sprite.buttons.ButtonConfig;
import ru.spaceshooter.sprite.buttons.ButtonExit;
import ru.spaceshooter.sprite.buttons.ButtonLoad;
import ru.spaceshooter.sprite.buttons.ButtonMusicOnOff;
import ru.spaceshooter.sprite.buttons.ButtonSoundOnOff;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;
    protected FileHandle fileHandle;
    protected Preferences preferences;
    protected Rect worldBounds;
    protected boolean isMusicOn;
    protected boolean isSoundOn;
    protected boolean isAccelerometerOn;
    protected ButtonExit buttonExit;
    protected ButtonLoad buttonLoad;
    protected ButtonConfig buttonConfig;
    protected ButtonBack buttonBack;
    protected ButtonMusicOnOff buttonMusicOnOff;
    protected ButtonSoundOnOff buttonSoundOnOff;
    protected ButtonAccelerometerOnOff buttonAccelerometerOnOff;
    protected TitleConfig titleConfig;
    private Rect screenBounds;
    private Rect glBounds;
    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;
    private Vector2 touch;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
        fileHandle = Gdx.files.local("bin/GameData.json");
        preferences = Gdx.app.getPreferences("GamePreference");
        isMusicOn = preferences.getBoolean("isMusicOn", true);
        isAccelerometerOn = preferences.getBoolean("isAccelOn", false);
        isSoundOn = preferences.getBoolean("isSoundOn", true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.2f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void flushPreference() {
        preferences.flush();
    }

    public void resize(Rect worldBounds) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    public void setMusicOn() {
        isMusicOn = !isMusicOn;
        preferences.putBoolean("isMusicOn", isMusicOn);
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public void setSoundOn() {
        isSoundOn = !isSoundOn;
        preferences.putBoolean("isSoundOn", isSoundOn);
    }

    public boolean isAccelerometerOn() {
        return isAccelerometerOn;
    }

    public void setAccelerometerOn() {
        isAccelerometerOn = !isAccelerometerOn;
        preferences.putBoolean("isAccelOn", isAccelerometerOn);
    }
}