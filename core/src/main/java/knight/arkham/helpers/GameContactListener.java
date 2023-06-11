package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;

import static knight.arkham.helpers.Constants.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

       Fixture fixtureA = contact.getFixtureA();
       Fixture fixtureB = contact.getFixtureB();

        Gdx.app.log("fixtureA",String.valueOf(fixtureA.getFilterData().categoryBits));
        Gdx.app.log("fixtureB",String.valueOf(fixtureB.getFilterData().categoryBits));


        int collisionDefinition = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (collisionDefinition) {

            case ENEMY_HEAD_BIT | MARIO_BIT:

                Gdx.app.log("enter","Case");

                if (fixtureA.getFilterData().categoryBits == ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).hitOnHead((Player) fixtureB.getUserData());

                else
                    ((Enemy) fixtureB.getUserData()).hitOnHead((Player) fixtureA.getUserData());
                break;

        }


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
