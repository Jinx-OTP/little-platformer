package com.jinx.otp.input_processors;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.jinx.otp.services.InputProcessorService;

public class PlayerMoveInputProcessor implements InputProcessor {

    private InputProcessorService inputProcessorService = InputProcessorService.getInputProcessorService();

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                inputProcessorService.keyMoveLeftPressed();
                return true;
            case Keys.RIGHT:
                inputProcessorService.keyMoveRightPressed();
                return true;
            case Keys.UP:
            case Keys.BACKSPACE:
            case Keys.SPACE:
                inputProcessorService.keyJumpPressed();
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                inputProcessorService.keyMoveLeftReleased();
                return true;
            case Keys.RIGHT:
                inputProcessorService.keyMoveRightReleased();
                return true;
            case Keys.UP:
            case Keys.BACKSPACE:
            case Keys.SPACE:
                inputProcessorService.keyJumpReleased();
            default:
                return false;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
