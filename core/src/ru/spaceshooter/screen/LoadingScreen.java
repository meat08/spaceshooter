package ru.spaceshooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.base.Font;
import ru.spaceshooter.utils.Assets;

public class LoadingScreen extends BaseScreen {
    private Texture texture;
    private Font font;
    private String textLoad;

    public LoadingScreen() {
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), 20, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        this.texture = new Texture(pixmap);
        pixmap.dispose();
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        if (Locale.getDefault().toString().equals("ru_RU")) {
            textLoad = "Загрузка...";
        } else {
            textLoad = "Loading...";
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Assets.getInstance().getAssetManager().update()) {
            ScreenManager.getInstance().goToTarget();
        }
        batch.begin();
        font.draw(batch, textLoad, worldBounds.pos.x, 0.05f, Align.center);
        batch.draw(texture, worldBounds.getLeft(), 0f, worldBounds.getWidth() * Assets.getInstance().getAssetManager().getProgress(), 0.01f);
        batch.end();
    }

    @Override
    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}
