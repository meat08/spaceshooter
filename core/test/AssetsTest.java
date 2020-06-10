import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;


import java.util.Arrays;
import java.util.Collection;


@RunWith(value = Parameterized.class)
public class AssetsTest {

    private static Application application;

    private String atlas;
    private String spriteName;
    private Class<? extends Exception> expectedException;

    public AssetsTest(String atlas, String spriteName, Class<? extends Exception> expectedException) {
        this.atlas = atlas;
        this.spriteName = spriteName;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters
    public static Collection<?> inputParameters() {
        return Arrays.asList(new Object[][] {
                {"textures/mainAtlas.tpack", "bonusGuns", null},
                {"textures/mainAtlas.tpack", "bonusHeal", null},
                {"textures/mainAtlas.tpack", "bonusShield", null},
                {"textures/mainAtlas.tpack", "bulletEnemy", null},
                {"textures/mainAtlas.tpack", "bulletEnemy_type1", null},
                {"textures/mainAtlas.tpack", "bulletEnemy_type2", null},
                {"textures/mainAtlas.tpack", "bulletMainShip", null},
                {"textures/mainAtlas.tpack", "enemy0", null},
                {"textures/mainAtlas.tpack", "enemy0_type1", null},
                {"textures/mainAtlas.tpack", "enemy1", null},
                {"textures/mainAtlas.tpack", "enemy1_type1", null},
                {"textures/mainAtlas.tpack", "enemy2", null},
                {"textures/mainAtlas.tpack", "enemy2_type1", null},
                {"textures/mainAtlas.tpack", "explosion", null},
                {"textures/mainAtlas.tpack", "hit", null},
                {"textures/mainAtlas.tpack", "hp_bar", null},
                {"textures/mainAtlas.tpack", "main_ship", null},
                {"textures/mainAtlas.tpack", "nebula0", null},
                {"textures/mainAtlas.tpack", "nebula1", null},
                {"textures/mainAtlas.tpack", "nebula2", null},
                {"textures/mainAtlas.tpack", "shield", null},
                {"textures/mainAtlas.tpack", "star", null},
                {"textures/menuAtlas.tpack", "hit", null},
                {"textures/menuAtlas.tpack", "menu_logo_ship", null},
                {"textures/menuAtlas.tpack", "star", null},
                {"textures/menuAtlas.tpack", "null", NullPointerException.class}
        });
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() {
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @AfterClass
    public static void cleanUp() {
        application.exit();
    }

    @Test
    public void assetsAtlasExist() {
        new TextureAtlas(atlas);
    }

    @Test
    public void textureInAtlasExist() {
        if (expectedException != null) {
            thrown.expect(expectedException);
        }
        new TextureRegion(new TextureAtlas(atlas).findRegion(spriteName));
    }

    @Test
    public void drawTexture() {
        if (expectedException != null) {
            thrown.expect(expectedException);
        }
        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        batch.begin();
        batch.draw(new TextureRegion(new TextureAtlas(atlas).findRegion(spriteName)), 0, 0);
        batch.end();
    }
}
