package com.development.orangemuffin.twoworlds.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.development.orangemuffin.twoworlds.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = MyGame.TITLE;
        config.width = MyGame.WIDTH;
        config.height = MyGame.HEIGHT;
		new LwjglApplication(new MyGame(), config);
	}
}
