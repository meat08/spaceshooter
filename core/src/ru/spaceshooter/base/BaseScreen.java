/*    Copyright (C) 2020  Ilya Mafov <i.mafov@gmail.com>
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package ru.spaceshooter.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;
    protected FileHandle fileHandle;
    protected Preferences preferences;
    protected Rect worldBounds;
    protected boolean isMusicOn;
    protected boolean isSoundOn;
    protected boolean isAccelerometerOn;
    protected float volumeMusic;
    protected float volumeSound;
    protected float senseAccel;
    protected float difficultyFactor;
    protected InputMultiplexer multiplexer;
    protected State state;
    private Rect screenBounds;
    private Rect glBounds;
    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;
    private Vector2 touch;

    @Override
    public void show() {
        multiplexer = new InputMultiplexer(this);
        Gdx.input.setInputProcessor(multiplexer);
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
        volumeMusic = preferences.getFloat("volumeMusic", 0.5f);
        volumeSound = preferences.getFloat("volumeSound", 0.5f);
        senseAccel = preferences.getFloat("senseAccel", 0.85f);
        difficultyFactor = preferences.getFloat("difficultyFactor", 1f);
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
        return true;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return true;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return true;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    public float getVolumeMusic() {
        return volumeMusic;
    }

    public void setVolumeMusic(float volumeMusic) {
        this.volumeMusic = volumeMusic;
        preferences.putFloat("volumeMusic", volumeMusic);
    }

    public float getVolumeSound() {
        return volumeSound;
    }

    public void setVolumeSound(float volumeSound) {
        this.volumeSound = volumeSound;
        preferences.putFloat("volumeSound", volumeSound);
    }

    public float getSenseAccel() {
        return senseAccel;
    }

    public void setSenseAccel(float senseAccel) {
        this.senseAccel = senseAccel;
        preferences.putFloat("senseAccel", senseAccel);
    }

    public void setDifficultyFactor(float diff) {
        this.difficultyFactor = diff;
        preferences.putFloat("difficultyFactor", difficultyFactor);
    }

    public float getDifficultyFactor() {
        return difficultyFactor;
    }
}