package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class GameObject {
    protected final GameScreen gameScreen;
    protected final Body body;
    protected final Rectangle actualBounds;
    private TextureRegion actualRegion;

    protected GameObject(Rectangle bounds, GameScreen gameScreen, TextureRegion region) {

        this.gameScreen = gameScreen;
        actualBounds = bounds;
        actualRegion = region;

        body = createBody();
    }

    protected abstract Body createBody();

    private Rectangle getBoundsWithPPMCalculation(){

        return new Rectangle(
                body.getPosition().x - (actualBounds.width / 2 / PIXELS_PER_METER),
                body.getPosition().y - (actualBounds.height / 2 / PIXELS_PER_METER),
                actualBounds.width / PIXELS_PER_METER,
                actualBounds.height / PIXELS_PER_METER
        );
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = getBoundsWithPPMCalculation();

        batch.draw(actualRegion, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    protected Animation<TextureRegion> makeAnimationByFrameRange(TextureRegion characterRegion, int initialFrame, int finalFrame, float frameDuration) {

        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = initialFrame; i <= finalFrame; i++)
            animationFrames.add(new TextureRegion(characterRegion, i * 16, 0, 16, 16));

        return new Animation<>(frameDuration, animationFrames);
    }

    public Body getBody() {return body;}

    protected void applyLinealImpulse(Vector2 impulseDirection) {
        body.applyLinearImpulse(impulseDirection, body.getWorldCenter(), true);
    }

    public void setPosition(float positionX, float positionY) {
        body.setTransform(positionX / PIXELS_PER_METER, positionY / PIXELS_PER_METER, 0);
    }

    public Vector2 getPixelPosition() {

        return new Vector2(body.getPosition().x * PIXELS_PER_METER, body.getPosition().y * PIXELS_PER_METER);
    }

    public Vector2 getWorldPosition() {return body.getPosition();}

    public Texture getSprite() {return actualRegion.getTexture();}

    protected void setActualRegion(TextureRegion actualRegion) {this.actualRegion = actualRegion;}
}
