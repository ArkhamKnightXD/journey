package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import knight.arkham.helpers.TileMapCreator;
import static knight.arkham.helpers.Constants.*;

public class GameScreen extends ScreenAdapter {

	private final SpriteBatch batch;

	private final OrthographicCamera camera;

	private final World world;

	private final Box2DDebugRenderer debugRenderer;

	private final OrthogonalTiledMapRenderer mapRenderer;

	private final FitViewport viewport;
	

	public GameScreen() {

		world = new World(new Vector2(0, 0), true);

		batch = new SpriteBatch();

		camera = new OrthographicCamera();

		viewport = new FitViewport(VIRTUAL_WIDTH / PIXELS_PER_METER,
				VIRTUAL_HEIGHT / PIXELS_PER_METER, camera);

		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

		mapRenderer = new TileMapCreator(this).setupMap();

		debugRenderer = new Box2DDebugRenderer();
	}



	private void update(){

		world.step(1 / 60f, 6, 2);

		camera.update();

		mapRenderer.setView(camera);
	}


	@Override
	public void render(float delta) {

		update();

		ScreenUtils.clear(0, 1, 1, 0);

		mapRenderer.render();

		batch.setProjectionMatrix(camera.combined);

		debugRenderer.render(world, camera.combined);
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

		batch.dispose();
		world.dispose();
		debugRenderer.dispose();
		mapRenderer.dispose();
	}

	public World getWorld() {return world;}
}