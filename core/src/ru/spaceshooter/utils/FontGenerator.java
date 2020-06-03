package ru.spaceshooter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontGenerator {

    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    public static BitmapFont generateFont(int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + RUSSIAN_CHARACTERS;
        parameter.size = size;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2f;
        parameter.color = Color.LIGHT_GRAY;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal("font/gabriola.ttf") );
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();

        return font;
    }
}
