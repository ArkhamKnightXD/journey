package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.screens.GameScreen;

public class Player extends GameObject {
    private final TextureRegion jumpingRegion;
    private final TextureRegion standingRegion;
    private PlayerAnimationState currentState;
    private PlayerAnimationState previousState;
    private final Animation<TextureRegion> runningAnimation;
    private float stateTimer;
    private boolean isPlayerRunningRight;
    private final Controller controller;

    public Player(Rectangle bounds, GameScreen gameScreen, TextureRegion actualRegion) {
        super(
            bounds, gameScreen,
            new TextureRegion(actualRegion, 0, 0, 16, 16)
        );

        previousState = PlayerAnimationState.STANDING;
        currentState = PlayerAnimationState.STANDING;

        stateTimer = 0;

        standingRegion = new TextureRegion(actualRegion, 0, 0, 16, 16);
        jumpingRegion = new TextureRegion(actualRegion, 80, 0, 16, 16);

        runningAnimation = makeAnimationByFrameRange(actualRegion, 1, 3, 0.1f);

        controller = Controllers.getCurrent();
    }

    @Override
    protected Body createBody() {

        return Box2DHelper.createBody(

            new Box2DBody(
                actualBounds, BodyDef.BodyType.DynamicBody,
                10, gameScreen.getWorld(), this
            )
        );
    }

    public void update(float deltaTime) {

        setActualRegion(getActualRegion(deltaTime));
        //Todo try to organize this conditions better.
        if ((controller != null && (controller.getButton(controller.getMapping().buttonDpadRight)) || Gdx.input.isKeyPressed(Input.Keys.D)) && body.getLinearVelocity().x <= 10)
            applyLinealImpulse(new Vector2(5, 0));

        else if ((controller != null && (controller.getButton(controller.getMapping().buttonDpadLeft)) || Gdx.input.isKeyPressed(Input.Keys.A)) && body.getLinearVelocity().x >= -10)
            applyLinealImpulse(new Vector2(-5, 0));

        if ((controller != null && (controller.getButton(controller.getMapping().buttonA)) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && body.getLinearVelocity().y == 0)
            applyLinealImpulse(new Vector2(0, 170));

        playerFallToDead();
    }

    private void playerFallToDead() {

        if (getPixelPosition().y < -100) {

            setPosition(500, 200);

            body.setLinearVelocity(0, 0);
        }
    }

    private PlayerAnimationState getPlayerCurrentState() {

        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == PlayerAnimationState.JUMPING))
            return PlayerAnimationState.JUMPING;

        else if (body.getLinearVelocity().x != 0)
            return PlayerAnimationState.RUNNING;

        else if (body.getLinearVelocity().y < 0)
            return PlayerAnimationState.FALLING;

        else
            return PlayerAnimationState.STANDING;
    }


    private TextureRegion getActualRegion(float deltaTime) {

        currentState = getPlayerCurrentState();

        TextureRegion region;

        switch (currentState) {

            case JUMPING:
                region = jumpingRegion;
                break;

            case RUNNING:
                region = runningAnimation.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = standingRegion;
        }

        flipPlayerOnXAxis(region);

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    private void flipPlayerOnXAxis(TextureRegion region) {

        if ((body.getLinearVelocity().x < 0 || !isPlayerRunningRight) && !region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || isPlayerRunningRight) && region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = true;
        }
    }

    public void getHitByEnemy() {

        applyLinealImpulse(new Vector2(500, 0));
    }

    public float getDistanceInBetween(Vector2 finalPosition) {

//        .dst utiliza la fórmula de calcular la distancia entre 2 puntos.
        return getPixelPosition().dst(finalPosition);
    }
}
