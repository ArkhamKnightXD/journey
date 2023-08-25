package knight.arkham.objects.structures;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.helpers.AssetsHelper;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class InteractiveStructure {
    protected final Rectangle actualBounds;
    protected final World actualWorld;
    protected final Fixture fixture;
    protected final Body body;
    private final TiledMap tiledMap;
    private final Sound collisionSound;

    public InteractiveStructure(Rectangle rectangle, World world, TiledMap map, String soundPath) {

        actualBounds = rectangle;
        actualWorld = world;
        tiledMap = map;

        fixture = createFixture();

        body = fixture.getBody();
        collisionSound = AssetsHelper.loadSound(soundPath);
    }

    protected abstract Fixture createFixture();

    protected void collisionWithPlayer() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;
        fixture.setFilterData(filter);

        collisionSound.play();
    }

    protected TiledMapTileLayer.Cell getObjectCellInTheTileMap() {

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");

        return mapLayer.getCell((int) (body.getPosition().x * PIXELS_PER_METER / 16),
            (int) (body.getPosition().y * PIXELS_PER_METER / 16));
    }
}
