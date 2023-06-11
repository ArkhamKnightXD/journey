package knight.arkham.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class Enemy extends GameObject {

    private final Animation<TextureRegion> runningAnimation;
    private float animationTimer;
    public boolean isMovingRight;

    private boolean setToDestroy;
    private boolean isDestroyed;

    public Enemy(Rectangle rectangle, World world, TextureRegion actualRegion) {
        super(
                rectangle, world,
                new TextureRegion(actualRegion ,0, 0, 16, 16)
        );

        setToDestroy = false;
        isDestroyed = false;
        isMovingRight = true;

        animationTimer = 0;

        runningAnimation = makeAnimationByFrameRange(actualRegion, 0, 1, 0.4f);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createBody(

            new Box2DBody(actualBounds, BodyDef.BodyType.DynamicBody,10, globalWorld, this)
        );
    }

    private void destroyEnemy() {

        // Destruyo el body
        globalWorld.destroyBody(body);
        isDestroyed = true;

//            Cambio el sprite de mi goomba por el sprite de goomba aplastado.
//        setRegion(new TextureRegion(gameScreen.getTextureAtlas()
//            .findRegion("goomba"), 32, 0, 16, 16));

        animationTimer = 0;
    }

    public void update(float deltaTime) {

        if (setToDestroy && !isDestroyed)
            destroyEnemy();

        animationTimer += deltaTime;

        setActualRegion(runningAnimation.getKeyFrame(animationTimer, true));

//        if (isMovingRight && body.getLinearVelocity().x <= 3)
//            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);
//
//        else if(!isMovingRight && body.getLinearVelocity().x >= -3)
//            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
    }

    @Override
    public void draw(Batch batch) {
        if (!isDestroyed)
            super.draw(batch);
    }

    public void hitOnHead(Player mario) {

        setToDestroy = true;

//        gameScreen.getAssetManager().get("sound/stomp.wav", Sound.class).play();
    }
}
