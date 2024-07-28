package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SimplePlayer extends GameObjectNoBox2D {

    public boolean isPlayerGrounded;
    public final Vector2 velocity = new Vector2(0,0);

    public SimplePlayer(Rectangle bounds) {
        super(bounds, 50);
    }

    public void update(float deltaTime) {

        actualBounds.y = actualBounds.y + velocity.y;
        velocity.y -= 20.8f * deltaTime;

        if (isPlayerGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE))
            velocity.y = 500 * deltaTime;

        if(actualBounds.y < 0) {

            actualBounds.y = 600 - actualBounds.height;
            velocity.y = 0;
        }

        actualBounds.x = actualBounds.x + velocity.x;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velocity.x += actualSpeed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            velocity.x -= actualSpeed * deltaTime;

        velocity.x *= 0.9f;

//        if (Gdx.input.isKeyPressed(Input.Keys.W))
//            actualBounds.y += actualSpeed * deltaTime;
//
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
//            actualBounds.y += gravity * 2 * deltaTime;
    }
}
