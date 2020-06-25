package ru.spaceshooter.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.spaceshooter.SpaceShooter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 700;
		config.height = 900;
		config.resizable = false;
		config.addIcon("textures/ic_launcher.png", Files.FileType.Internal);
		new LwjglApplication(new SpaceShooter(), config);
	}
}
