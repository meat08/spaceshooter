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
import ru.spaceshooter.pool.NebulaPool;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.Bullet;
import ru.spaceshooter.sprite.TitleConfig;
import ru.spaceshooter.sprite.buttons.ButtonAccelerometerOnOff;
import ru.spaceshooter.sprite.buttons.ButtonBack;
import ru.spaceshooter.sprite.buttons.ButtonConfig;
import ru.spaceshooter.sprite.buttons.ButtonExit;
import ru.spaceshooter.sprite.buttons.ButtonLoad;
import ru.spaceshooter.sprite.buttons.ButtonMusicOnOff;
import ru.spaceshooter.sprite.buttons.ButtonNewGame;
import ru.spaceshooter.sprite.buttons.ButtonResume;
import ru.spaceshooter.sprite.buttons.ButtonSave;
import ru.spaceshooter.sprite.Enemy;
import ru.spaceshooter.sprite.ForceShield;
import ru.spaceshooter.sprite.TitleGameOver;
import ru.spaceshooter.sprite.HpBar;
import ru.spaceshooter.sprite.Bonus;
import ru.spaceshooter.sprite.MainShip;
import ru.spaceshooter.sprite.TitlePause;
import ru.spaceshooter.sprite.Star;
import ru.spaceshooter.sprite.buttons.ButtonSoundOnOff;
import ru.spaceshooter.utils.BonusEmitter;
import ru.spaceshooter.utils.EnemyEmitter;
import ru.spaceshooter.utils.GameData;
import ru.spaceshooter.utils.NebulaEmitter;

public class GameScreen extends BaseScreen {

    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.02f;
    private static final int FRAGS_TO_LEVEL_UP = 20;
    private static final int LEVEL_TO_INCREASE_HP = 4;
    private static final float STAR_SPEED_INCREASE = 0.007f;
    private static final String FRAGS = "Убито: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Уровень: ";

    private enum State {PLAYING, GAME_OVER, PAUSE, CONFIG}

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private ForceShield forceShield;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private HitExplodePool hitExplodePool;
    private BonusPool bonusPool;
    private NebulaPool nebulaPool;
    private EnemyEmitter enemyEmitter;
    private BonusEmitter bonusEmitter;
    private NebulaEmitter nebulaEmitter;
    private Music gameMusic;
    private State state;
    private State previousState;
    private TitleGameOver titleGameOver;
    private TitlePause titlePause;
    private ButtonResume buttonResume;
    private ButtonSave buttonSave;
    private ButtonNewGame buttonNewGame;
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

    @Override
    public void show() {
        super.show();
        gameData = new GameData();
        json = new Json();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        font = new Font("font/font.fnt", "font/font.png");
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameScreen.mp3"));
        background = new Background(bg);
        stars = new Star[128];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, this);
        hitExplodePool = new HitExplodePool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, hitExplodePool, this);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, hitExplodePool, this);
        bonusPool = new BonusPool(worldBounds);
        nebulaPool = new NebulaPool(worldBounds);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);
        bonusEmitter = new BonusEmitter(atlas, bonusPool, mainShip);
        nebulaEmitter = new NebulaEmitter(atlas, nebulaPool);
        forceShield = new ForceShield(atlas);
        titleGameOver = new TitleGameOver(atlas);
        titlePause = new TitlePause(atlas);
        titleConfig = new TitleConfig(atlas);
        buttonExit = new ButtonExit(atlas);
        buttonResume = new ButtonResume(atlas, this);
        buttonSave = new ButtonSave(atlas, this);
        buttonNewGame = new ButtonNewGame(atlas, this);
        buttonLoad = new ButtonLoad(atlas, this, fileHandle);
        buttonConfig = new ButtonConfig(atlas, this);
        buttonBack = new ButtonBack(atlas, this);
        buttonMusicOnOff = new ButtonMusicOnOff(atlas, this);
        buttonSoundOnOff = new ButtonSoundOnOff(atlas, this);
        buttonAccelerometerOnOff = new ButtonAccelerometerOnOff(atlas, this);
        hpBar = new HpBar(atlas);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        musicChange();
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
        forceShield.resize(worldBounds, mainShip);
        enemyEmitter.resize(worldBounds);
        buttonResume.resize(worldBounds);
        buttonSave.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonLoad.resize(worldBounds);
        hpBar.resize(worldBounds, mainShip);
        buttonExit.resize(worldBounds);
        buttonConfig.resize(worldBounds);
        buttonBack.resize(worldBounds);
        buttonMusicOnOff.resize(worldBounds);
        buttonSoundOnOff.resize(worldBounds);
        buttonAccelerometerOnOff.resize(worldBounds);
        titleGameOver.resize(worldBounds);
        titlePause.resize(worldBounds);
        titleConfig.resize(worldBounds);
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
        nebulaPool.dispose();
        mainShip.dispose();
        gameMusic.dispose();
        font.dispose();
        buttonDispose();
        super.dispose();
    }

    private void buttonDispose() {
        buttonBack.dispose();
        buttonConfig.dispose();
        buttonExit.dispose();
        buttonLoad.dispose();
        buttonNewGame.dispose();
        buttonResume.dispose();
        buttonSave.dispose();
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
        } else if (state == State.CONFIG) {
            if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                exitConfig();
                flushPreference();
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
            buttonLoad.touchDown(touch, pointer, button);
            buttonExit.touchDown(touch, pointer, button);
        } else if (state == State.PAUSE) {
            buttonExit.touchDown(touch, pointer, button);
            buttonResume.touchDown(touch, pointer, button);
            buttonSave.touchDown(touch, pointer, button);
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
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
            buttonLoad.touchUp(touch, pointer, button);
            buttonExit.touchUp(touch, pointer, button);
        } else if (state == State.PAUSE) {
            buttonExit.touchUp(touch, pointer, button);
            buttonResume.touchUp(touch, pointer, button);
            buttonSave.touchUp(touch, pointer, button);
            buttonConfig.touchUp(touch, pointer, button);
        } else if (state == State.CONFIG) {
            buttonBack.touchUp(touch, pointer, button);
            buttonMusicOnOff.touchUp(touch, pointer, button);
            buttonSoundOnOff.touchUp(touch, pointer, button);
            buttonAccelerometerOnOff.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void enterConfig() {
        state = State.CONFIG;
    }

    public void exitConfig() {
        state = State.PAUSE;
    }

    public void musicChange() {
        if (isMusicOn) {
            gameMusic.play();
            gameMusic.setVolume(0.3f);
            gameMusic.setLooping(true);
        } else {
            gameMusic.stop();
        }
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
            nebulaEmitter.setLevel(level);
            mainShip.loadGame(gameData.getMaxHp(), gameData.getMaxHp(), gameData.getMainShipX());
            freeActivePools();
            for (Star star : stars) {
                for (float vy : gameData.getStarV()) {
                    star.setVY(vy);
                }
            }
            state = State.PAUSE;
        }
    }

    public void startNewGame() {
        frags = 0;
        level = 1;
        enemyEmitter.setLevel(level);
        nebulaEmitter.setLevel(level);
        state = State.PLAYING;
        freeActivePools();
        for (Star star : stars) {
            star.setVStart();
        }
        mainShip.startNewGame();
    }

    private void freeActivePools() {
        explosionPool.freeAllActive();
        enemyPool.freeAllActive();
        bulletPool.freeAllActive();
        bonusPool.freeAllActive();
        nebulaPool.freeAllActive();
        hitExplodePool.freeAllActive();
    }

    private void update(float delta) {
        nebulaPool.updateActiveSprites(delta);
        nebulaEmitter.generate(delta);
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
            enemyEmitter.generate(delta);
            bonusEmitter.generate(delta);
            hpBar.update(delta);
            forceShield.update(delta);
            changeLevel();
        } else if (state == State.GAME_OVER) {
            titleGameOver.update(delta);
        } else if (state == State.PAUSE) {
            titlePause.update(delta);
            buttonExit.update(delta);
            buttonResume.update(delta);
        } else if (state == State.CONFIG) {
            titleConfig.update(delta);
            buttonMusicOnOff.update(delta);
            buttonSoundOnOff.update(delta);
            buttonAccelerometerOnOff.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        nebulaPool.drawActiveSprites(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            bonusPool.drawActiveSprites(batch);
            mainShip.draw(batch);
            hitExplodePool.drawActiveSprites(batch);
            if (mainShip.isShield()) {
                forceShield.draw(batch);
            }
            hpBar.draw(batch);
        } else if (state == State.GAME_OVER) {
            titleGameOver.draw(batch);
            buttonNewGame.draw(batch);
            buttonLoad.draw(batch);
            buttonExit.draw(batch);
        } else if (state == State.PAUSE) {
            titlePause.draw(batch);
            buttonExit.draw(batch);
            buttonResume.draw(batch);
            buttonSave.draw(batch);
            buttonConfig.draw(batch);
        } else if (state == State.CONFIG) {
            titleConfig.draw(batch);
            buttonBack.draw(batch);
            buttonMusicOnOff.draw(batch);
            buttonSoundOnOff.draw(batch);
            buttonAccelerometerOnOff.draw(batch);
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
        nebulaPool.freeAllDestroyed();
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
            if (enemy.getBottom() <= worldBounds.getBottom()) {
                enemy.setDestroyBottom(true);
                enemy.destroy();
                if (mainShip.pos.dst(enemy.pos) < worldBounds.getHalfWidth()) {
                    mainShip.damage(enemy.getDamage());
                }
                enemy.setDestroyBottom(false);
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
                nebulaEmitter.setLevel(level);
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
