package knight.arkham.objects.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.Journey;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

public class FinishFlag extends InteractiveStructure {
    private final Rectangle drawBounds;
    private final Texture sprite;

    public FinishFlag(Rectangle rectangle, World world, TiledMap tiledMap) {
        super(rectangle, world, tiledMap, "powerup.wav");

        sprite = new Texture("images/flag.png");

        drawBounds = Box2DHelper.getDrawBounds(rectangle, body);
    }

    public void draw(Batch batch) {

        batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds, actualWorld, this)
        );
    }

    public void finishLevel() {

        collisionWithPlayer();

        Journey.INSTANCE.setToDispose = true;
    }

    public void dispose() {sprite.dispose();}
}
