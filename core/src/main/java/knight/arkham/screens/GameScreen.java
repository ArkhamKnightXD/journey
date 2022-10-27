package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.sprites.Player;

import static knight.arkham.helpers.Constants.*;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class GameScreen extends ScreenAdapter {

	private final SpriteBatch batch;

	private final OrthographicCamera camera;

	private final Texture blank;
	
	private final World world;

	private final Box2DDebugRenderer debugRenderer;

	private final OrthogonalTiledMapRenderer mapRenderer;

	private final FitViewport viewport;
	
	private final Player player;

	public GameScreen() {

		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new GameContactListener());

		debugRenderer = new Box2DDebugRenderer();


		batch = new SpriteBatch();
		blank = new Texture("images/blank.png");

		camera = new OrthographicCamera();

		viewport = new FitViewport(VIRTUAL_WIDTH / PIXELS_PER_METER,
				VIRTUAL_HEIGHT / PIXELS_PER_METER, camera);

		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

		mapRenderer = new TileMapHelper(this).setupMap();
		player = new Player(this, 32, 32);
	}

	private void cameraUpdate(){

		Vector3 cameraPosition = camera.position;

		cameraPosition.x = Math.round(player.getBody().getPosition().x * PIXELS_PER_METER * 10) / 10f;
		cameraPosition.y = Math.round(player.getBody().getPosition().y * PIXELS_PER_METER * 10) / 10f;

		camera.position.set(cameraPosition);
		camera.update();
	}

	private void update(){

		world.step(1 / 60f, 6, 2);


		cameraUpdate();

		player.update();

		camera.update();

		mapRenderer.setView(camera);

	}


	@Override
	public void render(float delta) {

		update();

		ScreenUtils.clear(0, 0, 0, 0);

		mapRenderer.render();


		batch.begin();


		batch.draw(blank, player.getBody().getPosition().x +570,
				player.getBody().getPosition().y +293, player.getWidth(), player.getHeight());

		batch.end();

		debugRenderer.render(world, camera.combined.scl(PIXELS_PER_METER));

	}

	@Override
	public void resize(int width, int height) {

		viewport.update(width, height);
	}


	@Override
	public void hide() {

		dispose();
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}

	public World getWorld() {return world;}
}