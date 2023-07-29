package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Journey;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.Player;

public class GameScreen extends ScreenAdapter {
    private final Journey game;
    private final OrthographicCamera camera;
    private final World world;
    private final Player player;
    private final TileMapHelper tileMapHelper;
    private final TextureAtlas textureAtlas;
    private boolean isDebug;

    public GameScreen() {
        game = Journey.INSTANCE;

        camera = game.globalCamera;

        world = new World(new Vector2(0, -40), true);

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        player = new Player(new Rectangle(450, 50, 32, 32), world, playerRegion);

        game.saveGameData("GameScreen", player.getWorldPosition());

        tileMapHelper = new TileMapHelper(world, textureAtlas, "maps/playground/test.tmx");

        tileMapHelper.setupMap();

        isDebug = false;

        Journey.INSTANCE.setToDispose = false;
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }


    @Override
    public void render(float delta) {

        if (game.setToDispose)
            game.setScreen(new SecondScreen());

        else {

            tileMapHelper.update(delta, player, camera);

            draw();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isDebug = !isDebug;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
            game.setToDispose = true;

        game.manageExitTheGame();
    }

    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        if (!isDebug)
            tileMapHelper.draw(camera, player);

        else
            game.debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();
        textureAtlas.dispose();
        tileMapHelper.dispose();
    }
}
