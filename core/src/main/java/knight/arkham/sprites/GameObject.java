package knight.arkham.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.screens.GameScreen;

public abstract class GameObject {

    protected Body body;
    protected float positionX;
    protected float positionY;

    protected float speed;

    protected float velocityX;
    protected float velocityY;

    protected float width;
    protected float height;


    public GameObject(GameScreen gameScreen, float width, float height) {

        body = BodyHelper.createPlayerBody(

                new Box2DBody(new Vector2(32, 32), gameScreen.getWorld())
        );

        positionX = body.getPosition().x;
        positionY = body.getPosition().y;
        velocityX = 0;
        velocityY = 0;
        speed = 0;

        this.width = width;
        this.height = height;

    }


    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public Body getBody() {return body;}

    public float getPositionX() {return positionX;}

    public float getPositionY() {return positionY;}

    public float getWidth() {return width;}

    public float getHeight() {return height;}
}
