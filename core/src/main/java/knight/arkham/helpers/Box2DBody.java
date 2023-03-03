package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DBody {

    public Rectangle bounds;
    public BodyDef.BodyType bodyType;
    public float density;
    public World world;
    public ContactType contactType;

    public Box2DBody(Rectangle bounds, BodyDef.BodyType bodyType, float density, World world, ContactType contactType) {

        this.bounds = bounds;
        this.bodyType = bodyType;
        this.density = density;
        this.world = world;
        this.contactType = contactType;
    }
}
