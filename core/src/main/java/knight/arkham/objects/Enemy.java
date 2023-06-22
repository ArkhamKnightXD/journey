package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.screens.GameScreen;

public class Enemy extends GameObject {

    private final Animation<TextureRegion> runningAnimation;
    private float stateTimer;
    public boolean isMovingRight;
    private boolean setToDestroy;
    private boolean isDestroyed;
    private final TextureRegion region;

    public Enemy(Rectangle rectangle, GameScreen gameScreen, TextureRegion actualRegion) {
        super(
            rectangle, gameScreen,
            new TextureRegion(actualRegion, 0, 0, 16, 16)
        );

        this.region = actualRegion;

        body.setActive(false);

        setToDestroy = false;
        isDestroyed = false;
        isMovingRight = true;

        stateTimer = 0;

        runningAnimation = makeAnimationByFrameRange(actualRegion, 0, 1, 0.4f);
    }

    @Override
    protected Body createBody() {

        return Box2DHelper.createBody(

            new Box2DBody(actualBounds, BodyDef.BodyType.DynamicBody, 10, gameScreen.getWorld(), this)
        );
    }

    private void destroyEnemy() {

        gameScreen.getWorld().destroyBody(body);
        isDestroyed = true;

        setActualRegion(new TextureRegion(region, 32, 0, 16, 16));

        stateTimer = 0;
    }

    public void update(float deltaTime) {

        stateTimer += deltaTime;

        if (setToDestroy && !isDestroyed)
            destroyEnemy();

        else if (!isDestroyed && body.isActive()) {

            setActualRegion(runningAnimation.getKeyFrame(stateTimer, true));

            if (isMovingRight && body.getLinearVelocity().x <= 4)
                body.applyLinearImpulse(new Vector2(300*deltaTime, 0), body.getWorldCenter(), true);

            else if (!isMovingRight && body.getLinearVelocity().x >= -4)
                body.applyLinearImpulse(new Vector2(-300*deltaTime, 0), body.getWorldCenter(), true);

            if (getPixelPosition().y < -50)
                setToDestroy = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!isDestroyed || stateTimer < 1)
            super.draw(batch);
    }

    public void hitOnHead() {

        setToDestroy = true;
    }
}
