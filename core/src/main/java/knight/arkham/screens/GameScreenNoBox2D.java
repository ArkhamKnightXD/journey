package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import knight.arkham.Journey;
import knight.arkham.helpers.TileMapHelperNoBox2D;
import knight.arkham.objects.SimplePlayer;

import static knight.arkham.helpers.Constants.*;

public class GameScreenNoBox2D extends ScreenAdapter {
    private final Journey game;
    private final OrthographicCamera camera;
    private final TileMapHelperNoBox2D tileMapHelper;
    private final FitViewport viewport;
    private final SimplePlayer player;
    private boolean isDebugCamera;

    public GameScreenNoBox2D() {
        game = Journey.INSTANCE;

        game.setToDispose = false;

        camera = new OrthographicCamera();

        viewport = new FitViewport(FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT, camera);

        camera.position.set(MID_SCREEN_WIDTH, MID_SCREEN_HEIGHT, 0);

        tileMapHelper = new TileMapHelperNoBox2D( "maps/playground/test3.tmx");

        player = new SimplePlayer(new Rectangle(300, 500, 32, 32));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.F2))
            isDebugCamera = true;

        if (isDebugCamera)
            cameraController();
        else
            camera.position.set(player.getPosition().x, 300, 0);

        camera.update();

        player.update(delta);

        draw();

        game.quitTheGame();
    }

    private void cameraController() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.position.x += 5;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.position.x -= 5;

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.position.y += 5;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.position.y -= 5;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3))
            camera.zoom += 0.2f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F4))
            camera.zoom -= 0.2f;

        if (Gdx.input.isKeyPressed(Input.Keys.F5))
            camera.rotate(1);
    }

    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        tileMapHelper.update(player);
        tileMapHelper.draw(camera, player);
    }

    @Override
    public void hide() {
        dispose();
    }
}
