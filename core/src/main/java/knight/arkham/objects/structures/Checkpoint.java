package knight.arkham.objects.structures;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.Journey;
import knight.arkham.helpers.*;

public class Checkpoint extends InteractiveStructure {

    public Checkpoint(Rectangle rectangle, World world, TiledMap tiledMap) {
        super(rectangle, world, tiledMap, "coin.wav");
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds, actualWorld, this)
        );
    }

    public void createCheckpoint() {

        collisionWithPlayer();

        Journey.INSTANCE.saveGameData("GameScreen", body.getPosition());

        getObjectCellInTheTileMap().setTile(null);
    }
}
