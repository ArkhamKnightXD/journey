package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class InteractiveStructure {

    private final Fixture fixture;
    private final Body body;
    private final TiledMap tiledMap;

    public InteractiveStructure(Rectangle rectangle, World world, TiledMap tiledMap) {

        this.tiledMap = tiledMap;

        fixture = Box2DHelper.createStaticFixture(
            new Box2DBody(
                rectangle, BodyDef.BodyType.StaticBody, 0, world, this
            )
        );

        body = fixture.getBody();
    }


    public void hitByPlayer() {

        Filter filter = new Filter();

        Gdx.app.log("Filter Before", String.valueOf(fixture.getFilterData().categoryBits));

        filter.categoryBits = DESTROYED_BIT;

        fixture.setFilterData(filter);

        Gdx.app.log("Filter After", String.valueOf(fixture.getFilterData().categoryBits));

        getObjectCellInTheTileMap().setTile(null);
    }

    private TiledMapTileLayer.Cell getObjectCellInTheTileMap() {

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");

        return mapLayer.getCell((int) (body.getPosition().x * PIXELS_PER_METER / 16),
            (int) (body.getPosition().y * PIXELS_PER_METER / 16));
    }
}
