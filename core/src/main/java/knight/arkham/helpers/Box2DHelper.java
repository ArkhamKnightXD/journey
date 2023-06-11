package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;

import static knight.arkham.helpers.Constants.*;

public class Box2DHelper {

    public static void createCollisionBody(Rectangle terrainBound, World world) {

        Box2DBody box2DBody = new Box2DBody(

            new Rectangle(
                terrainBound.x + terrainBound.width / 2,
                terrainBound.y + terrainBound.height / 2,
                terrainBound.width, terrainBound.height
            ),
            BodyDef.BodyType.StaticBody, 0,
            world, ContactType.FLOOR
        );

        createBody(box2DBody);
    }

    public static Fixture createBody(Box2DBody box2DBody) {

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.bounds.width / 2 / PIXELS_PER_METER, box2DBody.bounds.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = box2DBody.density;

        Body body = createBox2DBodyByType(box2DBody);

        Fixture fixture = body.createFixture(fixtureDef);

        fixture.setUserData(box2DBody.userData);

        if (box2DBody.userData instanceof Player)
            fixture = createPlayerBody(box2DBody, fixtureDef, body);

        else if (box2DBody.userData instanceof Enemy)
            fixture = createEnemyBody(box2DBody, fixtureDef, body);

        shape.dispose();

        return fixture;
    }

    private static Fixture createEnemyBody(Box2DBody box2DBody, FixtureDef fixtureDef, Body body) {
        Fixture fixture;
        fixtureDef.filter.categoryBits = ENEMY_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | COIN_BIT | OBJECT_BIT | ENEMY_BIT | MARIO_BIT);

        fixture = body.createFixture(fixtureDef);

        fixture.setUserData(box2DBody.userData);

        fixtureDef.shape = getEnemyHeadHeadCollider();

        fixtureDef.restitution = 1;
        fixtureDef.filter.categoryBits = ENEMY_HEAD_BIT;

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);
        return fixture;
    }

    private static PolygonShape getEnemyHeadHeadCollider() {

        PolygonShape head = new PolygonShape();

        Vector2[] vertices = new Vector2[4];

        vertices[0] = new Vector2(-15 , 22).scl(1/ PIXELS_PER_METER);
        vertices[1] = new Vector2(15 , 22).scl(1/ PIXELS_PER_METER);
        vertices[2] = new Vector2(-13 , 17).scl(1/ PIXELS_PER_METER);
        vertices[3] = new Vector2(13 , 17).scl(1/ PIXELS_PER_METER);

        head.set(vertices);

        return head;
    }


    private static EdgeShape getPlayerHeadCollider(FixtureDef fixtureDefinition) {

        EdgeShape headCollider = new EdgeShape();

        headCollider.set(new Vector2(-15 / PIXELS_PER_METER, 19 / PIXELS_PER_METER),
            new Vector2(15 / PIXELS_PER_METER, 19 / PIXELS_PER_METER));

        fixtureDefinition.shape = headCollider;

        fixtureDefinition.isSensor = true;

        fixtureDefinition.filter.categoryBits = MARIO_HEAD_BIT;

        return headCollider;
    }


    private static Fixture createPlayerBody(Box2DBody box2DBody, FixtureDef fixtureDef, Body body) {
        Fixture fixture;
        EdgeShape headCollider = getPlayerHeadCollider(fixtureDef);

        fixtureDef.filter.categoryBits = MARIO_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | COIN_BIT | OBJECT_BIT | ENEMY_BIT | ENEMY_HEAD_BIT);

        fixture = body.createFixture(fixtureDef);

        fixture.setUserData(box2DBody.userData);

        headCollider.dispose();
        return fixture;
    }

    private static Body createBox2DBodyByType(Box2DBody box2DBody) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.bodyType;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y / PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        return box2DBody.world.createBody(bodyDef);
    }
}
