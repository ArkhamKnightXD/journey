package knight.arkham.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//De esta forma indico que esta clase hereda de GameObject
public class Player extends GameObject {

    public Player(GameScreen gameScreen, float width, float height) {
        super(gameScreen, width, height);

        speed = 50;
    }

    @Override
    public void update() {

        positionX = body.getPosition().x * PIXELS_PER_METER;
        positionY = body.getPosition().y * PIXELS_PER_METER;

        playerMovement();
    }

    private void playerMovement() {

        velocityX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velocityX = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            velocityX = -1;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            velocityY = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.S))
            velocityY = -1;


        body.setLinearVelocity(velocityX * speed, velocityY * speed);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
