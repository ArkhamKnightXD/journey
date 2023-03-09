package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

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

        fixture.setUserData(box2DBody.contactType);

        shape.dispose();

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
