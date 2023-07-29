package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Journey;
import knight.arkham.helpers.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.structures.MovingStructure;

public class SecondScreen extends ScreenAdapter {
    private final Journey game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Player player;
    private final TileMapHelper tileMap;
    private final TextureAtlas textureAtlas;
    private final Music music;

    public SecondScreen() {
        game = Journey.INSTANCE;

        camera = game.globalCamera;

        world = new World(new Vector2(0, -40), true);

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        player = new Player(new Rectangle(450, 200, 32, 32), world, playerRegion);

        game.saveGameData("SecondScreen", player.getWorldPosition());

        tileMap = new TileMapHelper(world, textureAtlas, "maps/playground/test2.tmx");

        mapRenderer = tileMap.setupMap();

        music = AssetsHelper.loadMusic("mario_music.ogg");

        music.play();
        music.setLooping(true);
        music.setVolume(0.3f);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }


    private void update(float deltaTime) {

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update(deltaTime);

        for (Enemy enemy : tileMap.getEnemies()) {

            if (player.getDistanceInBetween(enemy.getPixelPosition()) < 170)
                enemy.getBody().setActive(true);

            enemy.update(deltaTime);
        }

        for (MovingStructure structure : tileMap.getMovingStructures())
            structure.update(deltaTime);

        game.manageExitTheGame();
    }

    private void updateCameraPosition() {

        boolean isPlayerInsideMapBounds = tileMap.isPlayerInsideMapBounds(player.getPixelPosition());

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getWorldPosition().x, 9.5f, 0);

        camera.update();
    }


    @Override
    public void render(float delta) {

        update(delta);

        draw();
    }

    private void draw() {

        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.setView(camera);

        mapRenderer.render();

        mapRenderer.getBatch().setProjectionMatrix(camera.combined);

        mapRenderer.getBatch().begin();

        player.draw(mapRenderer.getBatch());

        for (Enemy enemy : tileMap.getEnemies())
            enemy.draw(mapRenderer.getBatch());

        for (MovingStructure structure : tileMap.getMovingStructures())
            structure.draw(mapRenderer.getBatch());

        mapRenderer.getBatch().end();
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        music.dispose();
        player.dispose();
        textureAtlas.dispose();

        world.dispose();
        mapRenderer.dispose();

        for (Enemy enemy : tileMap.getEnemies())
            enemy.dispose();

        for (MovingStructure structure : tileMap.getMovingStructures())
            structure.dispose();
    }
}
