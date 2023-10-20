package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import knight.arkham.Journey;
import knight.arkham.helpers.TileMapHelperNoBox2D;

import static knight.arkham.helpers.Constants.FULL_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.FULL_SCREEN_WIDTH;

public class GameScreenNoBox2D extends ScreenAdapter {
    private final Journey game;
    private final OrthographicCamera camera;
    private final TileMapHelperNoBox2D tileMapHelper;
    private final FitViewport viewport;

    public GameScreenNoBox2D() {
        game = Journey.INSTANCE;

        game.setToDispose = false;

        camera = new OrthographicCamera();

        viewport = new FitViewport(FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT, camera);

        camera.position.set(400, 300, 0);

        tileMapHelper = new TileMapHelperNoBox2D( "maps/playground/test3.tmx");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {

        draw();

        game.quitTheGame();
    }

    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        tileMapHelper.draw(camera);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
