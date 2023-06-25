package knight.arkham.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class InteractiveStructure {

    private final GameScreen gameScreen;
    private final Fixture fixture;
    private final Body body;
    private final TiledMap tiledMap;

    public InteractiveStructure(Rectangle rectangle, GameScreen gameScreen, TiledMap tiledMap) {

        this.tiledMap = tiledMap;

        fixture = Box2DHelper.createStaticFixture(
            new Box2DBody(
                rectangle, BodyDef.BodyType.StaticBody, 0, gameScreen.getWorld(), this
            )
        );

        this.gameScreen = gameScreen;

        body = fixture.getBody();
    }


    public void hitByPlayer() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;

        fixture.setFilterData(filter);

        getObjectCellInTheTileMap().setTile(null);

        Sound sound = gameScreen.getAssetManager().get("sound/bump.wav");

        sound.play();
    }

    private TiledMapTileLayer.Cell getObjectCellInTheTileMap() {

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");

        return mapLayer.getCell((int) (body.getPosition().x * PIXELS_PER_METER / 16),
            (int) (body.getPosition().y * PIXELS_PER_METER / 16));
    }
}
