package com.jinx.otp.services;

import com.jinx.otp.GameScreen;
import com.jinx.otp.constants.Direction;
import com.jinx.otp.player.PlayerModel;
import com.jinx.otp.rooms.RoomModel;

public class InputProcessorService {

    private static InputProcessorService service;

    public static InputProcessorService getInputProcessorService() {
        if (null == service) {
            service = new InputProcessorService();
        }
        return service;
    }

    private final PlayerMovementService playerMovementService;

    private boolean isRightKeyStillPressed = false;
    private boolean isLeftKeyStillPressed = false;
    private boolean isJumpKeyStillPressed = false;
    private boolean isCrouchKeyStillPressed = false;
    private boolean isCrouchKeyJustReleased = false;
    private boolean isDoorAlreadyChecked = false;
    private boolean isDoorInteractionKeyPressed = false;

    private InputProcessorService() {
        playerMovementService = PlayerMovementService.getPlayerMovementService();
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

    public void keyCrouchPressed() {
        isCrouchKeyStillPressed = true;
    }

    public void keyCrouchReleased() {
        isCrouchKeyStillPressed = false;
        isCrouchKeyJustReleased = true;
    }

    public void keyDoorInteractionPressed() {
        isDoorInteractionKeyPressed = true;
    }

    public void keyDoorInteractionReleased() {
        isDoorAlreadyChecked = false;
        isDoorInteractionKeyPressed = false;
    }

    public void processPlayerMovement(float delta, PlayerModel player, RoomModel room, GameScreen gameScreen) {
        move(delta, player);
        jump(delta, player);
        croutch(player);
        doorInteraction(delta, player, room, gameScreen);
    }

    private void move(float delta, PlayerModel player) {
        if (isRightKeyStillPressed) {
            playerMovementService.move(player, delta, Direction.RIGHT);
        }
        if (isLeftKeyStillPressed) {
            playerMovementService.move(player, delta, Direction.LEFT);
        }
    }

    private void jump(float delta, PlayerModel player) {
        if (isJumpKeyStillPressed) {
            playerMovementService.move(player, delta, Direction.UP);
        }
    }

    private void croutch(PlayerModel player) {
        if (isCrouchKeyStillPressed) {
            playerMovementService.croutch(player);
        } else if (isCrouchKeyJustReleased) {
            playerMovementService.standUp(player);
            isCrouchKeyJustReleased = false;
        }

    }

    private void doorInteraction(float delta, PlayerModel player, RoomModel room, GameScreen gameScreen) {
        if (isDoorInteractionKeyPressed && !isDoorAlreadyChecked) {
            playerMovementService.doorInteraction(player, room, gameScreen);
            isDoorAlreadyChecked = true;
        }
    }

}
