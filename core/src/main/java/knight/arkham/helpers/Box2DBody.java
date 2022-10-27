package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DBody {

    public Vector2 position;

    public World world;

    public Rectangle rectangle;


    public Box2DBody(Rectangle rectangle, World world) {

        this.rectangle = rectangle;
        this.world = world;
    }

    public Box2DBody(Vector2 position, World world) {

        this.position = position;
        this.world = world;
    }
}
