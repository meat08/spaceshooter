package ru.spaceshooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

import java.util.List;
import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.base.Font;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BonusPool;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.EnemyPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.Bullet;
import ru.spaceshooter.sprite.ButtonExit;
import ru.spaceshooter.sprite.ButtonMusicMute;
import ru.spaceshooter.sprite.ButtonNewGame;
import ru.spaceshooter.sprite.ButtonResume;
import ru.spaceshooter.sprite.ButtonSave;
import ru.spaceshooter.sprite.Enemy;
import ru.spaceshooter.sprite.EngineMainShip;
import ru.spaceshooter.sprite.ForceShield;
import ru.spaceshooter.sprite.GameOver;
import ru.spaceshooter.sprite.HpBar;
import ru.spaceshooter.sprite.Bonus;
import ru.spaceshooter.sprite.MainShip;
import ru.spaceshooter.sprite.PauseLogo;
import ru.spaceshooter.sprite.Star;
import ru.spaceshooter.utils.BonusEmitter;
import ru.spaceshooter.utils.EnemyEmitter;
import ru.spaceshooter.utils.GameData;

public class GameScreen extends BaseScreen {

    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.02f;
    private static final int FRAGS_TO_LEVEL_UP = 20;
    private static final int LEVEL_TO_INCREASE_HP = 4;
    private static final float STAR_SPEED_INCREASE = 0.007f;
    private static final String FRAGS = "Убито: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Уровень: ";

    private enum State {PLAYING, GAME_OVER, PAUSE}

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private EngineMainShip engineMainShip;
    private ForceShield forceShield;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private HitExplodePool hitExplodePool;
    private BonusPool bonusPool;
    private EnemyEmitter enemyEmitter;
    private BonusEmitter bonusEmitter;
    private Music gameMusic;
    private State state;
    private State previousState;
    private GameOver gameOver;
    private PauseLogo pauseLogo;
    private ButtonExit buttonExit;
    private ButtonResume buttonResume;
    private ButtonSave buttonSave;
    private ButtonNewGame buttonNewGame;
    private ButtonMusicMute buttonMusicMute;
    private HpBar hpBar;
    private int frags;
    private int tempFrags;
    private int level;
    private int prevLevel;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private Json json;
    private GameData gameData;
    private boolean isMusicOn;

    @Override
    public void show() {
        super.show();
        gameData = new GameData();
        json = new Json();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[128];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        hitExplodePool = new HitExplodePool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, hitExplodePool);
        bonusPool = new BonusPool(worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, hitExplodePool);
        engineMainShip = new EngineMainShip(atlas);
        forceShield = new ForceShield(atlas);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);
        bonusEmitter = new BonusEmitter(atlas, bonusPool, mainShip);
        gameOver = new GameOver(atlas);
        pauseLogo = new PauseLogo(atlas);
        buttonExit = new ButtonExit(atlas);
        buttonResume = new ButtonResume(atlas, this);
        buttonSave = new ButtonSave(atlas, this);
        buttonNewGame = new ButtonNewGame(atlas, this);
        buttonMusicMute = new ButtonMusicMute(atlas, this);
        hpBar = new HpBar(atlas);
        font = new Font("font/font.fnt", "font/font.png");
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameScreen.mp3"));
        isMusicOn = true;
        gameMusic.play();
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.3f);
        state = State.PLAYING;
        previousState = state;
        level = 1;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        free();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        engineMainShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        pauseLogo.resize(worldBounds);
        buttonResume.resize(worldBounds);
        buttonSave.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonMusicMute.resize(worldBounds);
        hpBar.resize(worldBounds, mainShip);
        forceShield.resize(worldBounds, mainShip);
        buttonExit.resize(worldBounds);
        font.setSize(FONT_SIZE);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        bonusPool.dispose();
        mainShip.dispose();
        gameMusic.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public void pause() {
        if (state != State.PAUSE) {
            previousState = state;
            state = State.PAUSE;
            gameMusic.pause();
        }
    }

    public void resumeGame() {
        resume();
        state = previousState;
        if (isMusicOn) {
            gameMusic.play();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
            if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                pause();
            }
        } else if (state == State.PAUSE) {
            if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                resumeGame();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
            buttonExit.touchDown(touch, pointer, button);
        } else if (state == State.PAUSE) {
            buttonExit.touchDown(touch, pointer, button);
            buttonResume.touchDown(touch, pointer, button);
            buttonSave.touchDown(touch, pointer, button);
            buttonMusicMute.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
            buttonExit.touchUp(touch, pointer, button);
        } else if (state == State.PAUSE) {
            buttonExit.touchUp(touch, pointer, button);
            buttonResume.touchUp(touch, pointer, button);
            buttonSave.touchUp(touch, pointer, button);
            buttonMusicMute.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void saveGame() {
        gameData.saveGameData(frags, level, mainShip.getMaxHp(), mainShip.getHp(), mainShip.pos.x);
        for (Star star : stars) {
            gameData.saveStarV(star.getVY());
        }
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),false);
        }
    }

    public void loadGame() {
        if (fileHandle.exists()) {
            gameData = json.fromJson(GameData.class,
                    Base64Coder.decodeString(fileHandle.readString()));
            frags = gameData.getFrags();
            level = gameData.getLevel();
            enemyEmitter.setLevel(level);
            mainShip.setMaxHp(gameData.getMaxHp());
            mainShip.setHp(gameData.getHp());
            mainShip.pos.x = gameData.getMainShipX();
            for (Star star : stars) {
                for (float vy : gameData.getStarV()) {
                    star.setVY(vy);
                }
            }
        }
    }

    public void startNewGame() {
        frags = 0;
        level = 1;
        enemyEmitter.setLevel(level);
        state = State.PLAYING;
        explosionPool.freeAllActive();
        enemyPool.freeAllActive();
        bulletPool.freeAllActive();
        bonusPool.freeAllActive();
        hitExplodePool.freeAllActive();
        for (Star star : stars) {
            star.setVStart();
        }
        mainShip.startNewGame();
    }

    public void musicOnOff() {
        isMusicOn = !isMusicOn;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            bonusPool.updateActiveSprites(delta);
            hitExplodePool.updateActiveSprites(delta);
            mainShip.update(delta);
            engineMainShip.update(delta);
            enemyEmitter.generate(delta);
            bonusEmitter.generate(delta);
            hpBar.update(delta);
            forceShield.update(delta);
            changeLevel();
        } else if (state == State.GAME_OVER) {
            gameOver.update(delta);
        } else if (state == State.PAUSE) {
            pauseLogo.update(delta);
            buttonExit.update(delta);
            buttonResume.update(delta);
            buttonMusicMute.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            bonusPool.drawActiveSprites(batch);
            engineMainShip.draw(batch, mainShip);
            mainShip.draw(batch);
            hitExplodePool.drawActiveSprites(batch);
            if (mainShip.isShield()) {
                forceShield.draw(batch);
            }
            hpBar.draw(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
            buttonExit.draw(batch);
        } else if (state == State.PAUSE) {
            pauseLogo.draw(batch);
            buttonExit.draw(batch);
            buttonResume.draw(batch);
            buttonSave.draw(batch);
            buttonMusicMute.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
        bonusPool.freeAllDestroyed();
        hitExplodePool.freeAllDestroyed();
    }

    private void checkCollision () {
        if (state != State.PLAYING) {
            return;
        }
        final List<Enemy> enemies = enemyPool.getActiveObjects();
        final List<Bullet> bullets = bulletPool.getActiveObjects();
        final List<Bonus> bonuses = bonusPool.getActiveObjects();
        for (Enemy enemy : enemies) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                continue;
            }
            for (Bullet bullet : bullets) {
                if (bullet.getOwner() != mainShip ||  bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags += 1;
                    }
                }
            }
        }
        for (Bullet bullet : bullets) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (forceShield.isBulletCollision(bullet) & mainShip.isShield()) {
                bullet.destroy();
            }
            if (mainShip.isBulletCollision(bullet) & !mainShip.isShield()) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        for (Bonus bonus : bonuses) {
            float minDist = bonus.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(bonus.pos) < minDist) {
                switch (bonus.getBonusType()) {
                    case 1: {
                        mainShip.addHp(10 * level);
                        bonus.destroy();
                        break;
                    }
                    case 2: {
                        mainShip.shootSpeedBoost();
                        bonus.destroy();
                        break;
                    }
                    case 3: {
                        bonus.destroy();
                        mainShip.setShield();
                        break;
                    }
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void changeLevel() {
        if (frags > 0) {
            if (frags % FRAGS_TO_LEVEL_UP == 0 & tempFrags != frags) {
                level += 1;
                enemyEmitter.setLevel(level);
                for (Star star : stars) {
                    star.addVY(level * STAR_SPEED_INCREASE);
                }
                tempFrags = frags;
            }
            if (level % LEVEL_TO_INCREASE_HP == 0 & prevLevel != level ) {
                mainShip.addMaxHp(5 * level);
                mainShip.addHp(mainShip.getMaxHp() - mainShip.getHp());
                prevLevel = level;
            }
        }
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - TEXT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(level), worldBounds.getRight() - TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN, Align.right);
    }
}
