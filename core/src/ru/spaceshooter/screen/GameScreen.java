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
import ru.spaceshooter.base.State;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.AsteroidPool;
import ru.spaceshooter.pool.BonusPool;
import ru.spaceshooter.pool.BossPool;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.EnemyPool;
import ru.spaceshooter.pool.ExplosionAsteroidPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.pool.NebulaPool;
import ru.spaceshooter.sprite.Asteroid;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.Boss;
import ru.spaceshooter.sprite.Bullet;
import ru.spaceshooter.sprite.Enemy;
import ru.spaceshooter.sprite.ExplosionNuke;
import ru.spaceshooter.sprite.ForceShield;
import ru.spaceshooter.sprite.HpBar;
import ru.spaceshooter.sprite.Bonus;
import ru.spaceshooter.sprite.MainShip;
import ru.spaceshooter.sprite.Star;
import ru.spaceshooter.base.MainMenu;
import ru.spaceshooter.utils.AsteroidEmitter;
import ru.spaceshooter.utils.BonusEmitter;
import ru.spaceshooter.utils.BossEmitter;
import ru.spaceshooter.utils.EnemyEmitter;
import ru.spaceshooter.utils.GameData;
import ru.spaceshooter.utils.NebulaEmitter;

public class GameScreen extends BaseScreen {

    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.02f;
    private static final int FRAGS_TO_LEVEL_UP = 20;
    private static final int LEVEL_TO_INCREASE_HP = 3;
    private static final float STAR_SPEED_INCREASE = 0.007f;
    private static final String FRAGS = "Убито: ";
    private static final String LIVES = "Жизни: ";
    private static final String LEVEL = "Уровень: ";

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
    private AsteroidPool asteroidPool;
    private BossPool bossPool;
    private ExplosionAsteroidPool explosionAsteroidPool;
    private EnemyEmitter enemyEmitter;
    private BonusEmitter bonusEmitter;
    private NebulaEmitter nebulaEmitter;
    private AsteroidEmitter asteroidEmitter;
    private BossEmitter bossEmitter;
    private ExplosionNuke explosionNuke;
    private Music gameMusic;
    private State previousState;
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
    private MainMenu mainMenu;
    private boolean isNuked;
    private boolean isBoss;

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
        explosionAsteroidPool = new ExplosionAsteroidPool(atlas);
        hitExplodePool = new HitExplodePool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, hitExplodePool, this);
        bossPool = new BossPool(bulletPool, explosionPool, worldBounds, hitExplodePool, this);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, hitExplodePool, this);
        bonusPool = new BonusPool(worldBounds);
        nebulaPool = new NebulaPool(worldBounds);
        asteroidPool = new AsteroidPool(worldBounds, explosionAsteroidPool);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);
        bossEmitter = new BossEmitter(atlas, bossPool);
        enemyEmitter.setDiffFactor(difficultyFactor);
        bonusEmitter = new BonusEmitter(atlas, bonusPool, mainShip);
        nebulaEmitter = new NebulaEmitter(atlas, nebulaPool);
        asteroidEmitter = new AsteroidEmitter(atlas, asteroidPool);
        forceShield = new ForceShield(atlas);
        explosionNuke = new ExplosionNuke(atlas, this, worldBounds);
        hpBar = new HpBar(atlas);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        musicOnOff();
        state = State.PLAYING;
        previousState = state;
        level = 1;
        isNuked = false;
        isBoss = false;
        mainMenu = new MainMenu(multiplexer, this, fileHandle);
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
        bossEmitter.resize(worldBounds);
        hpBar.resize(worldBounds, mainShip);
        font.setSize(FONT_SIZE);
        mainMenu.resize();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        bossPool.dispose();
        explosionPool.dispose();
        bonusPool.dispose();
        nebulaPool.dispose();
        asteroidPool.dispose();
        mainShip.dispose();
        gameMusic.dispose();
        font.dispose();
        mainMenu.dispose();
        explosionNuke.dispose();
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
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void exitConfig() {
        state = State.PAUSE;
        mainMenu.hideConf();
    }

    public void musicOnOff() {
        if (isMusicOn) {
            gameMusic.play();
            gameMusic.setLooping(true);
        } else {
            gameMusic.stop();
        }
    }

    @Override
    public void setVolumeMusic(float volumeMusic) {
        super.setVolumeMusic(volumeMusic);
        gameMusic.setVolume(volumeMusic);
    }

    @Override
    public void setSenseAccel(float senseAccel) {
        super.setSenseAccel(senseAccel);
        mainShip.setSense(senseAccel);
    }

    @Override
    public void setDifficultyFactor(float diff) {
        super.setDifficultyFactor(diff);
        enemyEmitter.setDiffFactor(diff);
    }

    public void saveGame() {
        gameData.saveGameData(
                frags,
                level,
                mainShip.getMaxHp(),
                mainShip.getHp(),
                mainShip.pos.x,
                mainShip.getLives(),
                mainShip.getUpgradeCount()
        );
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
            enemyEmitter.setDiffFactor(difficultyFactor);
            mainShip.loadGame(
                    gameData.getMaxHp(),
                    gameData.getHp(),
                    gameData.getMainShipX(),
                    gameData.getLives(),
                    gameData.getUpgradeCount(),
                    atlas
            );
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
        isNuked = false;
        enemyEmitter.setLevel(level);
        nebulaEmitter.setLevel(level);
        enemyEmitter.setDiffFactor(difficultyFactor);
        state = State.PLAYING;
        freeActivePools();
        for (Star star : stars) {
            star.setVStart();
        }
        mainShip.startNewGame();
    }

    public void nuke() {
        explosionNuke.generate();
        final List<Enemy> enemies = enemyPool.getActiveObjects();
        for (Enemy enemy : enemies) {
            if (!enemy.isDestroyed()) {
                enemy.destroy();
            }
        }
        final List<Asteroid> asteroids = asteroidPool.getActiveObjects();
        for (Asteroid asteroid : asteroids) {
            if (!asteroid.isDestroyed()) {
                asteroid.destroy();
            }
        }
    }

    public void setNuked(boolean nuked) {
        isNuked = nuked;
    }

    public boolean isNotNuked() {
        return !isNuked;
    }

    private void freeActivePools() {
        explosionPool.freeAllActive();
        enemyPool.freeAllActive();
        bulletPool.freeAllActive();
        bonusPool.freeAllActive();
        nebulaPool.freeAllActive();
        hitExplodePool.freeAllActive();
        asteroidPool.freeAllActive();
        explosionAsteroidPool.freeAllActive();
        bossPool.freeAllActive();
    }

    private void update(float delta) {
        nebulaPool.updateActiveSprites(delta);
        nebulaEmitter.generate(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        explosionNuke.update(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            asteroidPool.updateActiveSprites(delta);
            bonusPool.updateActiveSprites(delta);
            bossPool.updateActiveSprites(delta);
            hitExplodePool.updateActiveSprites(delta);
            explosionAsteroidPool.updateActiveSprites(delta);
            mainShip.update(delta);
            if (!isNuked & !isBoss) {
                enemyEmitter.generate(delta);
                asteroidEmitter.generate(delta);
            }
            bonusEmitter.generate(delta);
            hpBar.update(delta);
            forceShield.update(delta);
            changeLevel();
            mainMenu.setMenuVisible(false);
        } else {
            mainMenu.setMenuVisible(true);
            mainMenu.setSaveButtonVisible(!isBoss);
        }
        mainMenu.update();
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
            bossPool.drawActiveSprites(batch);
            bonusPool.drawActiveSprites(batch);
            if (!isNuked) {
                mainShip.draw(batch);
                if (mainShip.isShield()) {
                    forceShield.draw(batch);
                }
            }
            hitExplodePool.drawActiveSprites(batch);
            asteroidPool.drawActiveSprites(batch);
            explosionAsteroidPool.drawActiveSprites(batch);
            hpBar.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (!explosionNuke.isDestroyed()) {
            explosionNuke.draw(batch);
        }
        printInfo();
        batch.end();
        if (state != State.PLAYING) {
            mainMenu.draw();
        }
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
        bonusPool.freeAllDestroyed();
        hitExplodePool.freeAllDestroyed();
        nebulaPool.freeAllDestroyed();
        asteroidPool.freeAllDestroyed();
        explosionAsteroidPool.freeAllDestroyed();
        bossPool.freeAllDestroyed();
    }

    private void checkCollision () {
        if (state != State.PLAYING) {
            return;
        }
        final List<Enemy> enemies = enemyPool.getActiveObjects();
        final List<Bullet> bullets = bulletPool.getActiveObjects();
        final List<Bonus> bonuses = bonusPool.getActiveObjects();
        final List<Asteroid> asteroids = asteroidPool.getActiveObjects();
        final List<Boss> bosses = bossPool.getActiveObjects();
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
        for (Boss boss : bosses) {
            for (Bullet bullet : bullets) {
                if (bullet.getOwner() != mainShip ||  bullet.isDestroyed()) {
                    continue;
                }
                if (boss.isBulletCollision(bullet)) {
                    boss.damage(bullet.getDamage());
                    bullet.destroy();
                    if (boss.isDestroyed()) {
                        nuke();
                        levelUp();
                        mainShip.upgradeShip(atlas, boss.getBossType());
                        isBoss = false;
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
                        mainShip.addHp(Math.max((int)(10 / difficultyFactor) * level, 50));
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
                    case 4: {
                        bonus.destroy();
                        nuke();
                        break;
                    }
                }
            }
        }
        for (Asteroid asteroid : asteroids) {
            for (Bullet bullet : bullets) {
                if (bullet.isDestroyed()) {
                    continue;
                }
                if (asteroid.isBulletCollision(bullet)) {
                    if (bullet.getOwner() == mainShip) {
                        asteroid.damage(bullet.getDamage());
                    }
                    bullet.destroy();
                }
            }
            float minDistAsteroid = asteroid.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(asteroid.pos) < minDistAsteroid) {
                mainShip.damage(asteroid.getDamage(mainShip.getHp()));
                asteroid.destroy();
            }

        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
            mainMenu.gameOver();
        }
    }

    private void changeLevel() {
        if (frags > 0) {
            if (frags % FRAGS_TO_LEVEL_UP == 0 & tempFrags != frags) {
                levelUp();
                tempFrags = frags;
                generateBoss();
            }
            if (level % LEVEL_TO_INCREASE_HP == 0 & prevLevel != level ) {
                mainShip.addMaxHp((int)(10 / difficultyFactor));
                mainShip.addHp(mainShip.getMaxHp() - mainShip.getHp());
                prevLevel = level;
            }
        }
    }

    private void levelUp() {
        level += 1;
        enemyEmitter.setLevel(level);
        nebulaEmitter.setLevel(level);
        for (Star star : stars) {
            star.addVY(level * STAR_SPEED_INCREASE);
        }
    }

    private void generateBoss() {
        switch (level) {
            case 5: {
                bossEmitter.generate(1);
                isBoss = true;
            }
        }
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN);
        font.draw(batch, sbHp.append(LIVES).append(mainShip.getLives()), worldBounds.pos.x, worldBounds.getTop() - TEXT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(level), worldBounds.getRight() - TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN, Align.right);
    }
}
