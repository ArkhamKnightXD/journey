package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.helpers.GameData;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.screens.GameScreen;
import knight.arkham.screens.MainMenuScreen;
import knight.arkham.screens.SecondScreen;

public class Journey extends Game {
    public static Journey INSTANCE;
    public OrthographicCamera camera;
    public Viewport viewport;
    public Box2DDebugRenderer debugRenderer;
    public boolean setToDispose;

    public Journey() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

//        It is better to avoid using PPM to set up my viewport.
        viewport = new FitViewport(screenWidth / 32f, screenHeight / 32f, camera);

        setScreen(new MainMenuScreen());
    }

    public void changeScreen(int screenIndex){
        if (screenIndex == 1)
            setScreen(new GameScreen());
        else if (screenIndex == 2)
            setScreen(new SecondScreen());
        else
            setScreen(new MainMenuScreen());
    }

    public void quitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    public void saveGameData(String screenName, Vector2 playerWorldPosition) {

        GameDataHelper.saveGameData(new GameData(screenName, playerWorldPosition));
    }
}
