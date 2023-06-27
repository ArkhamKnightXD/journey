package knight.arkham.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class InteractiveStructure {

    protected final GameScreen gameScreen;
    protected final Rectangle bounds;
    protected final Fixture fixture;
    private final Body body;
    private final TiledMap tiledMap;

    public InteractiveStructure(Rectangle rectangle, GameScreen gameScreen, TiledMap tiledMap) {

        this.bounds = rectangle;
        this.gameScreen = gameScreen;
        this.tiledMap = tiledMap;

        fixture = createFixture();

        body = fixture.getBody();
    }

    protected abstract Fixture createFixture();


    protected TiledMapTileLayer.Cell getObjectCellInTheTileMap() {

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");

        return mapLayer.getCell((int) (body.getPosition().x * PIXELS_PER_METER / 16),
            (int) (body.getPosition().y * PIXELS_PER_METER / 16));
    }
}
