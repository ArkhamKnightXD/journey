package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import static knight.arkham.helpers.Constants.*;

public class Box2DBodyCreator {


    public static Body createPlayerBody(Box2DBody box2DBody) {

        Body body = getPreparedDynamicBody(box2DBody);

        FixtureDef fixtureDefinition = getPreparedFixtureDefinition();

        body.createFixture(fixtureDefinition);

        return body;
    }


    private static FixtureDef getPreparedFixtureDefinition() {

        FixtureDef fixtureDefinition = new FixtureDef();

        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(6 / PIXELS_PER_METER);

        fixtureDefinition.shape = circleShape;

        circleShape.dispose();

        fixtureDefinition.density = 100;
        fixtureDefinition.friction = 0.1f;

        return fixtureDefinition;
    }


    private static Body getPreparedDynamicBody(Box2DBody box2DBody) {

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.DynamicBody;

        bodyDefinition.fixedRotation = true;

        bodyDefinition.position.set(box2DBody.position.x /PIXELS_PER_METER, box2DBody.position.y /PIXELS_PER_METER);

        return box2DBody.world.createBody(bodyDefinition);
    }


    public static void createStaticBody(Box2DBody box2DBody){

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.type = BodyDef.BodyType.StaticBody;
        bodyDefinition.position.set(box2DBody.rectangle.x / PIXELS_PER_METER, box2DBody.rectangle.y / PIXELS_PER_METER);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.rectangle.width / 2 /PIXELS_PER_METER,
                box2DBody.rectangle.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        Body body = box2DBody.world.createBody(bodyDefinition);

        body.createFixture(fixtureDef);

        shape.dispose();
    }
}
