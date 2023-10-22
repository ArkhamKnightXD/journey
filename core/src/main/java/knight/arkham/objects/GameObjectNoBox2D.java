package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObjectNoBox2D {
    protected float actualSpeed;
    protected final Rectangle actualBounds;

    private final Texture sprite;

    protected GameObjectNoBox2D(Rectangle rectangle, float speed) {
        actualBounds = rectangle;
        actualSpeed = speed;
        sprite = new Texture("images/flag.png");
    }

    public void draw(Batch batch) {

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Rectangle getBounds() {return actualBounds;}

    public Vector2 getPosition() {return new Vector2(actualBounds.x, actualBounds.y);}
}
