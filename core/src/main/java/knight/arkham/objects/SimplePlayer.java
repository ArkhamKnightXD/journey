package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class SimplePlayer extends GameObjectNoBox2D {
    public boolean hasCollision;

    public SimplePlayer(Rectangle bounds) {
        super(bounds, 500);
    }

    public void update(float deltaTime) {

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            actualBounds.x += actualSpeed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            actualBounds.x -= actualSpeed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.W))
            actualBounds.y += actualSpeed * deltaTime;

        else if (!hasCollision && Gdx.input.isKeyPressed(Input.Keys.S))
            actualBounds.y -= actualSpeed * deltaTime;
    }
}
