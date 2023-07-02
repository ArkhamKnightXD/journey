package knight.arkham.objects.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class MovingStructure {
    private final Body body;
    private final Texture sprite;
    private final Rectangle drawBounds;
    private float stateTimer;

    public MovingStructure(Rectangle rectangle, World world) {

        body = Box2DHelper.createBody(

            new Box2DBody(
                rectangle, BodyDef.BodyType.KinematicBody,
                0, world, this
            )
        );

        sprite = new Texture("images/MovingStructure.jpg");

        drawBounds = new Rectangle(
            rectangle.width / 2 / PIXELS_PER_METER, rectangle.height / 2 / PIXELS_PER_METER,
            rectangle.width / PIXELS_PER_METER, rectangle.height / PIXELS_PER_METER
        );
    }

    private Vector2 getDrawSpritePosition(){

        return new Vector2(body.getPosition().x - drawBounds.x,
            body.getPosition().y - drawBounds.y
        );
    }

//    Any moving object will always need a draw method.
    public void draw(Batch batch) {

        Vector2 drawPosition = getDrawSpritePosition();

        batch.draw(sprite, drawPosition.x, drawPosition.y, drawBounds.width, drawBounds.height);
    }

    public void update(float deltaTime){

        stateTimer += deltaTime;

        if (stateTimer < 5)
            body.setLinearVelocity(2,0);

        else if(stateTimer > 5 && stateTimer < 10)
            body.setLinearVelocity(-2,0);

        else
            stateTimer = 0;
    }
}
