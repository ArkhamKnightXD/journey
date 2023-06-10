package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
public class StaticStructure extends GameObject {
    public StaticStructure(Rectangle rectangle, World world, String spritePath) {

        super(
                rectangle, world,
                new TextureRegion(new Texture(spritePath))
        );
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createBody(

            new Box2DBody(bounds, BodyDef.BodyType.StaticBody,0, world, this)
        );
    }
}
