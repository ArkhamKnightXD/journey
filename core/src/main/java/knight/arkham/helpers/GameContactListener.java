package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.screens.GameScreen;

public class GameContactListener implements ContactListener {
    private final GameScreen gameScreen;

    public GameContactListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {

       Fixture fixtureA = contact.getFixtureA();
       Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
