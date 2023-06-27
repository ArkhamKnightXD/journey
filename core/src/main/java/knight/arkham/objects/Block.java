package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;

public class Block extends InteractiveStructure{

    public Block(Rectangle rectangle, GameScreen gameScreen, TiledMap tiledMap) {
        super(rectangle, gameScreen, tiledMap);
    }

    @Override
    protected Fixture createFixture() {
        return Box2DHelper.createStaticFixture(
           new Box2DBody(
               bounds, BodyDef.BodyType.StaticBody, 0, gameScreen.getWorld(), this
           )
       );
    }

    public void hitByPlayer() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;

        fixture.setFilterData(filter);

        getObjectCellInTheTileMap().setTile(null);

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/breakBlock.wav"));

        sound.play();
    }
}