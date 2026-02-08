package com.jinx.otp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.jinx.otp.constants.Direction;
import com.jinx.otp.exceptions.InvalidArgumentException;
import com.jinx.otp.map.MapModel;
import com.jinx.otp.map.Obstacle;
import com.jinx.otp.map.ObstacleCollision;

public class MapService {

    private static final String LOG_TAG = MapService.class.getName();

    private static MapService mapService;

    public static MapService getMapService() {
        if (null == mapService) {
            mapService = new MapService();
        }
        return mapService;
    }

    private MapService() {

    }

    public List<ObstacleCollision> getOverlapingObstacles(MapModel map, Rectangle object) {
        if (null == object) {
            String message = "Cannot determine overlaping obstacles, if rectangle is null!";
            throw new InvalidArgumentException(message);
        }
        List<ObstacleCollision> overlappingObstacles = new ArrayList<>();
        for (final Obstacle obstacle : map.getObstacles()) {
            Rectangle obstacleRectangle = obstacle.getBoundingRectangle();
            if (object.overlaps(obstacleRectangle)) {
                final Map<Direction, Float> overlaps = new HashMap<>();
                setHorizontalOverlap(overlaps, object, obstacleRectangle);
                setVerticalOverlap(overlaps, object, obstacleRectangle);
                ObstacleCollision collision = new ObstacleCollision(obstacle, overlaps);
                overlappingObstacles.add(collision);
            }
        }
        return overlappingObstacles;
    }

    private void setHorizontalOverlap(Map<Direction, Float> overlaps, Rectangle object, Rectangle obstacle) {
        final float objectLeftBorder = object.getX();
        final float objectRightBorder = object.getX() + object.getWidth();
        final float obstacleLeftBorder = obstacle.getX();
        final float obstacleRightBorder = obstacle.getX() + obstacle.getWidth();

        final float rightOverlap = obstacleRightBorder - objectLeftBorder;
        final float leftOverlap = objectRightBorder - obstacleLeftBorder;

        if (rightOverlap < leftOverlap) {
            overlaps.put(Direction.RIGHT, rightOverlap);
            return;
        }

        overlaps.put(Direction.LEFT, leftOverlap);
    }

    private void setVerticalOverlap(Map<Direction, Float> overlaps, Rectangle object, Rectangle obstacle) {
        final float objectBottomBorder = object.getY();
        final float objectTopBorder = object.getY() + object.getHeight();
        final float obstacleBottomBorder = obstacle.getY();
        final float obstacleTopBorder = obstacle.getY() + obstacle.getHeight();

        final float bottomOverlap = objectTopBorder - obstacleBottomBorder;
        final float topOverlap = obstacleTopBorder - objectBottomBorder;

        if (Application.LOG_DEBUG == Gdx.app.getLogLevel()) {
            final String message = "objectBottomBorder: " + objectBottomBorder + 
                                   "\nobjectTopBorder: " + objectTopBorder +
                                   "\nobstacleBottomBorder: " + obstacleBottomBorder +
                                   "\nobstacleTopBorder: " + obstacleTopBorder +
                                   "\nBottom overlap: " + bottomOverlap + 
                                   "\nTop overlap: " + topOverlap;
            Gdx.app.debug(LOG_TAG, message);
        }

        if (topOverlap < bottomOverlap) {
            overlaps.put(Direction.UP, topOverlap);
            return;
        }
        overlaps.put(Direction.DOWN, bottomOverlap);
    }
}
