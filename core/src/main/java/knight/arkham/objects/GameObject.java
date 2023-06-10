package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class GameObject {

    protected final World world;
    protected final Body body;
    protected Fixture fixture;
    protected final Rectangle bounds;
    private TextureRegion actualRegion;

    protected GameObject(Rectangle rectangle, World globalWorld, TextureRegion region) {

        world = globalWorld;
        bounds = rectangle;
        actualRegion = region;

        fixture = createFixture();
        body = fixture.getBody();
    }

    protected abstract Fixture createFixture();

    private Rectangle getBoundsWithPPMCalculation(){

        return new Rectangle(
                body.getPosition().x - (bounds.width / 2 / PIXELS_PER_METER),
                body.getPosition().y - (bounds.height / 2 / PIXELS_PER_METER),
                bounds.width / PIXELS_PER_METER,
                bounds.height / PIXELS_PER_METER
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
