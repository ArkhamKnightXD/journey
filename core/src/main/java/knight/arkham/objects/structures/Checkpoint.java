package knight.arkham.objects.structures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.GameData;
import knight.arkham.helpers.GameDataHelper;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class Checkpoint extends InteractiveStructure {

    public Checkpoint(Rectangle rectangle, World world, TiledMap tiledMap) {
        super(rectangle, world, tiledMap);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(
                actualBounds, BodyDef.BodyType.StaticBody, 0, actualWorld, this
            )
        );
    }

    public void createCheckpoint() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;
        fixture.setFilterData(filter);

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/coin.wav"));
        sound.play();

        GameData gameDataToSave = new GameData("GameScreen", body.getPosition());
        GameDataHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);

        getObjectCellInTheTileMap().setTile(null);
    }
}
