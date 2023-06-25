package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.GameScreen;

public class Journey extends Game {
    public static Journey INSTANCE;
    public SpriteBatch batch;
    public OrthographicCamera globalCamera;
    public Viewport viewport;
    public World globalWorld;
    public Box2DDebugRenderer debugRenderer;
    public int screenWidth;
    public int screenHeight;

    public Journey() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        globalWorld = new World(new Vector2(0, -40), true);
        debugRenderer = new Box2DDebugRenderer();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();

        globalCamera = new OrthographicCamera();

//        It is better to avoid using PPM to set up my viewport.
        viewport = new FitViewport(screenWidth/32f, screenHeight/32f, globalCamera);

        setScreen(new GameScreen());
    }

    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
