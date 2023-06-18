package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.InteractiveStructure;
import knight.arkham.objects.Player;

import static knight.arkham.helpers.Constants.*;

public class Box2DHelper {

    public static void createCollisionBody(Rectangle terrainBound, World world) {

        Box2DBody box2DBody = new Box2DBody(

            new Rectangle(
                terrainBound.x + terrainBound.width / 2,
                terrainBound.y + terrainBound.height / 2,
                terrainBound.width, terrainBound.height
            ), world
        );

        createStaticBody(box2DBody);
    }

    public static void createStaticBody(Box2DBody box2DBody){

        Body body = createBox2DBodyByType(box2DBody);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.bounds.width / 2 /PIXELS_PER_METER, box2DBody.bounds.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = GROUND_BIT;

        body.createFixture(fixtureDef);

        shape.dispose();

    }


    private static Body createBox2DBodyByType(Box2DBody box2DBody) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.bodyType;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y / PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        return box2DBody.world.createBody(bodyDef);
    }


    public static Body createBody(Box2DBody box2DBody) {

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.bounds.width / 2 / PIXELS_PER_METER, box2DBody.bounds.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        fixtureDef.density = box2DBody.density;

        Body body = createBox2DBodyByType(box2DBody);

        if (box2DBody.userData instanceof Player)
            createPlayerBody(box2DBody, fixtureDef, body);

        else if (box2DBody.userData instanceof Enemy)
            createEnemyBody(box2DBody, fixtureDef, body);

        else if (box2DBody.userData instanceof InteractiveStructure) {

            fixtureDef.filter.categoryBits = BRICK_BIT;

            body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        } else {

            fixtureDef.filter.categoryBits = GROUND_BIT;

            body.createFixture(fixtureDef);
        }

        shape.dispose();

        return body;
    }

    private static void createEnemyBody(Box2DBody box2DBody, FixtureDef fixtureDef, Body body) {

        fixtureDef.filter.categoryBits = ENEMY_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | BRICK_BIT | ENEMY_BIT | PLAYER_BIT);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        PolygonShape headCollider = getEnemyHeadHeadCollider();

        fixtureDef.shape = headCollider;

        fixtureDef.restitution = 1;
        fixtureDef.filter.categoryBits = ENEMY_HEAD_BIT;

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

//        Los shapes deben de ser dispose luego de que el fixture se ha creado, si no el programa fallara.
        headCollider.dispose();
    }

    private static PolygonShape getEnemyHeadHeadCollider() {

        PolygonShape head = new PolygonShape();

        Vector2[] vertices = new Vector2[4];

        vertices[0] = new Vector2(-15, 22).scl(1 / PIXELS_PER_METER);
        vertices[1] = new Vector2(15, 22).scl(1 / PIXELS_PER_METER);
        vertices[2] = new Vector2(-13, 17).scl(1 / PIXELS_PER_METER);
        vertices[3] = new Vector2(13, 17).scl(1 / PIXELS_PER_METER);

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


    private static void createPlayerBody(Box2DBody box2DBody, FixtureDef fixtureDef, Body body) {

        fixtureDef.filter.categoryBits = PLAYER_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | BRICK_BIT | ENEMY_BIT | ENEMY_HEAD_BIT);

        //Nota si se van a definir varios category y maskBit a varios cuerpos, tener pendiente, que se debe de crear fixture antes de agregar
        // los demás mask y category al otro cuerpo
        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        EdgeShape headCollider = getPlayerHeadCollider(fixtureDef);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        headCollider.dispose();
    }
}
