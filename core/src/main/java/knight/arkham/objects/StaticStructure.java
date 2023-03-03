package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;
public class StaticStructure extends GameObject {
    public StaticStructure(Rectangle rectangle, World world, ContactType contactType, String spritePath) {

        super(
                new Box2DBody(rectangle, BodyDef.BodyType.StaticBody, 0, world, contactType),
                new TextureRegion(new Texture(spritePath))
        );
    }
}
