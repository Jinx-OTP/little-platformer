package com.jinx.otp.services;

import static com.jinx.otp.constants.Constants.GRAVITATION_CONSTANT;
import static com.jinx.otp.constants.Constants.PLAYER_JUMP_VELOCITY;
import static com.jinx.otp.constants.Constants.PLAYER_SPEED;

import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.jinx.otp.constants.Direction;
import com.jinx.otp.map.MapModel;
import com.jinx.otp.map.Obstacle;
import com.jinx.otp.map.ObstacleCollision;
import com.jinx.otp.player.PlayerModel;

public class PlayerMovementService {

    private static PlayerMovementService playerMovementService;
    private static final String LOG_TAG = PlayerMovementService.class.getName();

    public static PlayerMovementService getPlayerMovementService() {
        if (null == playerMovementService) {
            playerMovementService = new PlayerMovementService();
        }
        return playerMovementService;
    }

    private MapService mapService;

    private PlayerMovementService() {
        mapService = MapService.getMapService();
    }

    public void move(PlayerModel player, float delta, Direction direction) {
        final float distance = PLAYER_SPEED * delta;
        switch (direction) {
            case UP:
                if (player.isMidAir()) {
                    break;
                }
                player.increaseVerticalVelocity(PLAYER_JUMP_VELOCITY);
                player.setMidAir(true);
                player.setCurrentPlatform(null);
                break;
            case DOWN:
                player.decreaseVerticalVelocity(10f * delta);
                break;
            case LEFT:
                player.translateX(-distance);
                if (!player.isMidAir() && isPlayerOffPlatform(player)) {
                    player.setMidAir(true);
                    player.setCurrentPlatform(null);
                }
                break;
            case RIGHT:
                player.translateX(distance);
                if (!player.isMidAir() && isPlayerOffPlatform(player)) {
                    player.setMidAir(true);
                    player.setCurrentPlatform(null);
                }
                break;
        }
        if (Application.LOG_DEBUG == Gdx.app.getLogLevel()) {
            final String message = "Vertical velocity: " + player.getVerticalVelocity();
            Gdx.app.debug(LOG_TAG, message);
        }
    }

    private boolean isPlayerOffPlatform(PlayerModel player) {
        final Obstacle currentPlatform = player.getCurrentPlatform();
        final float platformLeftBorder = currentPlatform.getPosX();
        final float platformRightBorder = currentPlatform.getPosX() + currentPlatform.getWidth();
        final float playerLeftBorder = player.getPosX();
        final float playerRightBorder = player.getPosX() + player.getWidth();
        return (playerRightBorder < platformLeftBorder || platformRightBorder < playerLeftBorder);
    }

    public void handleObstacleCollision(PlayerModel player, MapModel map) {
        final Rectangle playerBounds = player.getBoundingRectangle();
        final List<ObstacleCollision> overlapingObstacles = mapService.getOverlapingObstacles(map, playerBounds);
        if (0 < overlapingObstacles.size()) {
            for (ObstacleCollision collision : overlapingObstacles) {
                if (Application.LOG_DEBUG == Gdx.app.getLogLevel()) {
                    Gdx.app.debug(LOG_TAG, collision.toString());
                }
                float verticalOverlap = 0f;
                float horizontalOverlap = 0f;
                boolean isOnLeft = false;
                boolean isOnTop = false;
                if (collision.getDirections().containsKey(Direction.UP)) {
                    verticalOverlap = collision.getDirections().get(Direction.UP);
                    isOnTop = true;
                }
                if (collision.containsDirection(Direction.DOWN)) {
                    verticalOverlap = collision.getDirectionValue(Direction.DOWN);
                    isOnTop = false;
                }
                if (collision.containsDirection(Direction.LEFT)) {
                    horizontalOverlap = collision.getDirectionValue(Direction.LEFT);
                    isOnLeft = true;
                }
                if (collision.containsDirection(Direction.RIGHT)) {
                    horizontalOverlap = collision.getDirectionValue(Direction.RIGHT);
                    isOnLeft = false;
                }

                final Obstacle overlapingObstacle = collision.getOverlapingObstacle();
                if (horizontalOverlap < verticalOverlap) {
                    if (isOnLeft) {
                        clampPositionToLeftObstacleBorder(player, map, overlapingObstacle);
                        continue;
                    }
                    clampPositionToRightObstacleBorder(player, map, overlapingObstacle);
                    continue;
                }
                if (isOnTop) {
                    clampPositionToTopObstacleBorder(player, map, overlapingObstacle);
                    continue;
                }
                clampPostionToBottomObstacleBorder(player, map, overlapingObstacle);
            }
        }
    }

    public void clampToMapBorders(PlayerModel player, MapModel map) {
        final float minX = 0f;
        final float maxX = map.getWidth();
        final float playerPosX = player.getPosX();
        final float playerClampedX = MathUtils.clamp(playerPosX, minX, maxX);
        player.setPosX(playerClampedX);

        final float minY = 0f;
        final float maxY = map.getHeight();
        final float playerPosY = player.getPosY();
        final float playerClampedY = MathUtils.clamp(playerPosY, minY, maxY);
        player.setPosY(playerClampedY);
    }

    private void clampPositionToRightObstacleBorder(PlayerModel player, MapModel map, Obstacle obstacle) {
        final float rightObstacleBorder = obstacle.getWidth() + obstacle.getPosX();
        player.setPosX(rightObstacleBorder);
        if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
            Gdx.app.debug(LOG_TAG, "Player pos after clamp right of obstacle: " + player);
        }
    }

    private void clampPositionToLeftObstacleBorder(PlayerModel player, MapModel map, Obstacle obstacle) {
        final float leftObstacleBorder = obstacle.getPosX();
        final float playerWidthAdjustedBorder = leftObstacleBorder - player.getWidth();
        player.setPosX(playerWidthAdjustedBorder);
        if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
            Gdx.app.debug(LOG_TAG, "Player pos after clamp left of obstacle: " + player);
        }
    }

    private void clampPostionToBottomObstacleBorder(PlayerModel player, MapModel map, Obstacle obstacle) {
        final float bottomObstacleBorder = obstacle.getPosY();
        final float playerHeightAdjustedBorder = bottomObstacleBorder - player.getHeight();
        player.setPosY(playerHeightAdjustedBorder);
        player.setVerticalVelocity(0f);
        if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
            Gdx.app.debug(LOG_TAG, "Player pos after clamp bottom of obstacle: " + player);
        }
    }

    private void clampPositionToTopObstacleBorder(PlayerModel player, MapModel map, Obstacle obstacle) {
        final float topObstacleBorder = obstacle.getPosY() + obstacle.getHeight();
        player.setPosY(topObstacleBorder);
        player.setMidAir(false);
        player.setVerticalVelocity(0f);
        player.setCurrentPlatform(obstacle);
        if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
            Gdx.app.debug(LOG_TAG, "Player pos after clamp top of obstacle: " + player);
        }
    }

    public void handleGravitation(PlayerModel player, float delta) {
        if (!player.isMidAir()) {
            return;
        }
        final float verticalVelocity = player.getVerticalVelocity();
        final float verticalDistance = verticalVelocity * delta;
        player.translateY(verticalDistance);
        player.decreaseVerticalVelocity(GRAVITATION_CONSTANT * delta);
    }

}
