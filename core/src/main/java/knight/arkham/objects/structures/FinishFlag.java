package knight.arkham.objects.structures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.Journey;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.screens.SecondScreen;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;

public class FinishFlag extends InteractiveStructure {

    private final Rectangle bounds;

    private final Texture sprite;

    public FinishFlag(Rectangle rectangle, World world, TiledMap tiledMap) {
        super(rectangle, world, tiledMap);

        bounds = rectangle;
        sprite = new Texture("images/flag.png");
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds, actualWorld, this)
        );
    }

    public void finishLevel() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;
        fixture.setFilterData(filter);

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/powerup.wav"));
        sound.play();

        Journey.INSTANCE.setScreen(new SecondScreen());
    }

    public void draw(Batch batch) {

        Rectangle drawBounds = Box2DHelper.getDrawBounds(bounds, body);

        batch.draw(sprite, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height);
    }
}
