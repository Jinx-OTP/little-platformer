package com.jinx.otp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LittlePlatformerGame extends Game {

    SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(this));
    }

    public void changeScreen(Screens destination, Screen oldScreen) {
        boolean screenChanged = false;
        switch (destination) {
            case MAIN_MENU:
                setScreen(new MainMenuScreen(this));
                screenChanged = true;
                break;
            case GAME:
                setScreen(new GameScreen(this));
                screenChanged = true;
            default:
                break;
        }
        if (screenChanged && null != oldScreen) {
            oldScreen.dispose();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}