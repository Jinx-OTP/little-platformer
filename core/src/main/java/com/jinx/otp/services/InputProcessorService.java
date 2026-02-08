package com.jinx.otp.services;

import com.jinx.otp.constants.Direction;
import com.jinx.otp.player.Player;

public class InputProcessorService {

    private static InputProcessorService service;

    public static InputProcessorService getInputProcessorService() {
        if (null == service) {
            service = new InputProcessorService();
        }
        return service;
    }

    private boolean isRightKeyStillPressed = false;
    private boolean isLeftKeyStillPressed = false;
    private boolean isJumpKeyStillPressed = false;

    private InputProcessorService() {

    }

    public void keyMoveRightPressed() {
        isRightKeyStillPressed = true;
    }

    public void keyMoveRightReleased() {
        isRightKeyStillPressed = false;
    }

    public void keyMoveLeftPressed() {
        isLeftKeyStillPressed = true;
    }

    public void keyMoveLeftReleased() {
        isLeftKeyStillPressed = false;
    }

    public void keyJumpPressed() {
        isJumpKeyStillPressed = true;
    }

    public void keyJumpReleased() {
        isJumpKeyStillPressed = false;
    }

    public void processPlayerMovement(float delta, Player player) {
        if (isRightKeyStillPressed) {
            player.move(delta, Direction.RIGHT);
        }
        if (isLeftKeyStillPressed) {
            player.move(delta, Direction.LEFT);
        }
        if (isJumpKeyStillPressed) {
            player.move(delta, Direction.UP);
        }
    }

}
