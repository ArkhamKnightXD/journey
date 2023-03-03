package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class GameObject {

    protected final Body body;
    protected final Fixture fixture;
    private final Rectangle bounds;
    private TextureRegion actualRegion;

    protected GameObject(Box2DBody gameObjectStructure, TextureRegion region) {
        fixture =  Box2DHelper.createBody(gameObjectStructure);
        body = fixture.getBody();
        bounds = gameObjectStructure.bounds;
        actualRegion = region;
    }

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

    public Texture getSprite() {return actualRegion.getTexture();}

    protected void setActualRegion(TextureRegion actualRegion) {this.actualRegion = actualRegion;}
}
