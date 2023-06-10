package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class Enemy extends GameObject {

    private final Animation<TextureRegion> runningAnimation;
    private float animationTimer;
    public boolean isMovingRight;

    public Enemy(Rectangle rectangle, World world, TextureRegion actualRegion) {
        super(
                rectangle, world,
                new TextureRegion(actualRegion ,0, 0, 16, 16)
        );

        isMovingRight = true;

        animationTimer = 0;

        runningAnimation = makeAnimationByFrameRange(actualRegion, 0, 1, 0.4f);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createBody(

            new Box2DBody(actualBounds, BodyDef.BodyType.DynamicBody,10, world, this)
        );
    }

    public void update(float deltaTime) {

        animationTimer += deltaTime;

        setActualRegion(runningAnimation.getKeyFrame(animationTimer, true));

        if (isMovingRight && body.getLinearVelocity().x <= 3)
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        else if(!isMovingRight && body.getLinearVelocity().x >= -3)
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
    }
}
