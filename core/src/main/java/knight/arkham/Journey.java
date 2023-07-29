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

import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class Journey extends Game {
    public static Journey INSTANCE;
    public OrthographicCamera globalCamera;
    public Viewport viewport;
    public Box2DDebugRenderer debugRenderer;
    public boolean setToDispose;

    public Journey() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        debugRenderer = new Box2DDebugRenderer();

        globalCamera = new OrthographicCamera();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

//        It is better to avoid using PPM to set up my viewport.
        viewport = new FitViewport(screenWidth / 32f, screenHeight / 32f, globalCamera);

        setScreen(new GameScreen());
    }

    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    public void saveGameData(String screenName, Vector2 playerWorldPosition) {

        GameData gameDataToSave = new GameData(screenName, playerWorldPosition);
        GameDataHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);
    }
}
