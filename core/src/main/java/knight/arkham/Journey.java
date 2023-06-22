package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.*;

public class Journey extends Game {
    public static Journey INSTANCE;

    public SpriteBatch batch;
    public BitmapFont font;

    public OrthographicCamera globalCamera;

    public Viewport viewport;

    public Box2DDebugRenderer debugRenderer;

    public World globalWorld;

    private int screenWidth;
    private int screenHeight;

    public Journey() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        globalWorld = new World(new Vector2(0, -40), true);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        font = new BitmapFont();

        globalCamera = new OrthographicCamera();

//        It is better to avoid using PPM to set up my viewport.
        viewport = new FitViewport(FULL_SCREEN_WIDTH/32f, FULL_SCREEN_HEIGHT/32f, globalCamera);

        debugRenderer = new Box2DDebugRenderer();

        setScreen(new GameScreen());
    }

    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
