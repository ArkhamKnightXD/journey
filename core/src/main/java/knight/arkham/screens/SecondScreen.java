package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Journey;
import knight.arkham.helpers.*;
import knight.arkham.objects.Player;

public class SecondScreen extends ScreenAdapter {
    private final Journey game;
    private final OrthographicCamera camera;
    private final Player player;
    private final TileMapHelper tileMapHelper;
    private final TextureAtlas textureAtlas;
    private final Music music;

    public SecondScreen() {
        game = Journey.INSTANCE;

        camera = game.globalCamera;

        World world = new World(new Vector2(0, -40), true);

        GameContactListener contactListener = new GameContactListener();

        world.setContactListener(contactListener);

        textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        player = new Player(new Rectangle(450, 200, 32, 32), world, playerRegion);

        game.saveGameData("SecondScreen", player.getWorldPosition());

        tileMapHelper = new TileMapHelper(world, textureAtlas, "maps/playground/test2.tmx");

        tileMapHelper.setupMap();

        music = AssetsHelper.loadMusic("mario_music.ogg");

        music.play();
        music.setLooping(true);
        music.setVolume(0.3f);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void render(float delta) {

        tileMapHelper.update(delta, player, camera);

        draw();
    }

    private void draw() {

        ScreenUtils.clear(0, 0, 0, 0);

        tileMapHelper.draw(camera, player);
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
        tileMapHelper.dispose();
    }
}
