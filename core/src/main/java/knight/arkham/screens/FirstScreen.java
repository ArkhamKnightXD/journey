package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScreen extends ScreenAdapter {

	private final SpriteBatch batch;

	private final Texture blank;

	public FirstScreen() {

		batch = new SpriteBatch();
		blank = new Texture("images/blank.png");

	}


	@Override
	public void render(float delta) {

		batch.begin();

		batch.draw(blank, 400, 400, 64, 64);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// Resize your screen here. The parameters represent the new window size.
	}


	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}
}