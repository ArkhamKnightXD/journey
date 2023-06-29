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
    protected final GameScreen actualGameScreen;
    protected final Body body;
    protected final Rectangle actualBounds;
    private final Rectangle drawBounds;
    private TextureRegion actualRegion;

    protected GameObject(Rectangle bounds, GameScreen gameScreen, TextureRegion region) {

        actualBounds = bounds;
        actualGameScreen = gameScreen;
        actualRegion = region;

        body = createBody();

        drawBounds = new Rectangle(0,0,bounds.width / PIXELS_PER_METER,
            bounds.height / PIXELS_PER_METER);
    }

    protected abstract Body createBody();

    private Vector2 getDrawSpritePosition(){

        return new Vector2(body.getPosition().x - (drawBounds.width / 2),
                body.getPosition().y - (drawBounds.height / 2)
        );
    }

    public void draw(Batch batch) {

        Vector2 drawPosition = getDrawSpritePosition();

        batch.draw(actualRegion, drawPosition.x, drawPosition.y, drawBounds.width, drawBounds.height);
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
