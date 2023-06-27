package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.GameData;
import knight.arkham.helpers.GameDataPreferencesHelper;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;
import static knight.arkham.screens.GameScreen.GAME_DATA_FILENAME;

public class Checkpoint {
    private final Fixture fixture;
    private final Body body;
    private final TiledMap tiledMap;

    public Checkpoint(Rectangle rectangle, GameScreen gameScreen, TiledMap tiledMap) {

        this.tiledMap = tiledMap;

        fixture = Box2DHelper.createStaticFixture(
            new Box2DBody(
                rectangle, BodyDef.BodyType.StaticBody, 0, gameScreen.getWorld(), this
            )
        );

        body = fixture.getBody();
    }


    public void createCheckpoint() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;

        fixture.setFilterData(filter);

        getObjectCellInTheTileMap().setTile(null);

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/breakBlock.wav"));

        GameData gameDataToSave = new GameData("GameScreen", body.getPosition());

        GameDataPreferencesHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);

        sound.play();
    }

    private TiledMapTileLayer.Cell getObjectCellInTheTileMap() {

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");

        return mapLayer.getCell((int) (body.getPosition().x * PIXELS_PER_METER / 16),
            (int) (body.getPosition().y * PIXELS_PER_METER / 16));
    }
}
