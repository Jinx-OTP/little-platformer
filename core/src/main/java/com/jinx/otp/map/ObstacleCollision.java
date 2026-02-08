package com.jinx.otp.map;

import java.util.Map;

import com.jinx.otp.constants.Direction;

public class ObstacleCollision {

    private Obstacle overlapingObstacle;
    private Map<Direction, Float> directions;
    
    public ObstacleCollision() {

    }

    public ObstacleCollision(Obstacle obstacle, Map<Direction, Float> directions) {
        this.overlapingObstacle = obstacle;
        this.directions = directions;
    }

    public Obstacle getOverlapingObstacle() {
        return overlapingObstacle;
    }
    
    public void setOverlapingObstacle(Obstacle overlapingObstacle) {
        this.overlapingObstacle = overlapingObstacle;
    }
    
    public Map<Direction, Float> getDirections() {
        return directions;
    }
    
    public void setDirections(Map<Direction, Float> directions) {
        this.directions = directions;
    }

    public boolean containsDirection(Direction direction) {
        return directions.containsKey(direction);
    }

    public float getDirectionValue(Direction direction) {
        return directions.get(direction);
    }

    @Override
    public String toString() {
        return "ObstacleCollision [overlapingObstacle=" + overlapingObstacle + ", directions=" + directions + "]";
    }

    

}
