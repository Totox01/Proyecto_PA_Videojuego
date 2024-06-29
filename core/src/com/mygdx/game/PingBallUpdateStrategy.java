package com.mygdx.game;

public class PingBallUpdateStrategy implements UpdateStrategy {
    @Override
    public void update(GameObject gameObject, float deltaTime) {
        PingBall pingBall = (PingBall) gameObject;
        if (!pingBall.isStill()) {
            pingBall.setX(pingBall.getX() + pingBall.getSpeed() * deltaTime);
            pingBall.setY(pingBall.getY() + pingBall.getSpeed() * deltaTime);
        }
    }
}
