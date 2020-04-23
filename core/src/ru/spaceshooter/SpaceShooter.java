package ru.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceShooter extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backgroundImage;
	Texture actionImage;
	Sprite backgroundSprite;

	float x = 0;
	float y = 0;
	float screenWidth;
	float screenHeight;

	boolean up = false;
	boolean down = false;
	boolean right = false;
	boolean left = false;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		backgroundImage = new Texture("background.jpg");
		actionImage = new Texture("sphere.png");
		backgroundSprite = new Sprite(backgroundImage);
		screenWidth  = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		backgroundSprite.setSize(screenWidth, screenHeight);
		backgroundSprite.setPosition(0,0);
	}

	@Override
	public void render () {
		float speed = 2.5f;

		float width = actionImage.getWidth();
		float height = actionImage.getHeight();
		up = !down || y < 0;
		down = !up || y + height > screenHeight;
		right = !left || x < 0;
		left = !right || x + width > screenWidth;

		if(down) y -= speed;
		if(up) y += speed;
		if(left) x -= speed;
		if(right) x += speed;

		batch.begin();
		backgroundSprite.draw(batch);
		batch.draw(actionImage, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		actionImage.dispose();
		backgroundImage.dispose();
	}
}
