package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Journey;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;

public class GameScreen extends ScreenAdapter {
    private final Journey game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Player player;
    private final TileMapHelper tileMap;
    private final TextureAtlas textureAtlas;

    public GameScreen() {
        game = Journey.INSTANCE;

        camera = game.globalCamera;

        world = game.globalWorld;

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        player = new Player(new Rectangle(500, 200, 32, 32), this, playerRegion);

        TextureRegion enemyRegion = textureAtlas.findRegion("goomba");

        tileMap = new TileMapHelper(this, enemyRegion, "maps/test.tmx");

        mapRenderer = tileMap.setupMap();
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }

    private void update(float deltaTime){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update(deltaTime);

        for (Enemy enemy : new Array.ArrayIterator<>(tileMap.getEnemies())){

            if (player.getPixelPosition().x < enemy.getPixelPosition().x +200)
                enemy.getBody().setActive(true);

            enemy.update(deltaTime);
        }

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        boolean isPlayerInsideMapBounds = tileMap.isPlayerIsInsideMapBounds(player.getPixelPosition());

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getWorldPosition().x,9.5f, 0);

        camera.update();

        mapRenderer.setView(camera);
    }


    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0,0,0,0);

        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(game.batch);

        for (Enemy enemy : new Array.ArrayIterator<>(tileMap.getEnemies()))
            enemy.draw(game.batch);

        game.batch.end();

       game.debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        mapRenderer.dispose();
        player.getSprite().dispose();

        for (Enemy enemy : new Array.ArrayIterator<>(tileMap.getEnemies()))
            enemy.getSprite().dispose();
    }

    public World getWorld() {return world;}

    public TextureAtlas getTextureAtlas() {return textureAtlas;}
}
