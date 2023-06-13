package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.screens.GameScreen;

public class InteractiveStructure extends GameObject {
    public InteractiveStructure(Rectangle rectangle, GameScreen gameScreen, String spritePath) {

        super(
                rectangle, gameScreen,
                new TextureRegion(new Texture(spritePath))
        );
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createBody(

            new Box2DBody(actualBounds, BodyDef.BodyType.StaticBody,0, gameScreen.getWorld(), this)
        );
    }

    public void destroy(Player player) {

        Gdx.app.log("enter","collision");
    }
}
