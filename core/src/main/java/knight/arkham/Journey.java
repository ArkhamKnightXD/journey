package knight.arkham;

import com.badlogic.gdx.Game;
import knight.arkham.screens.GameScreen;

public class Journey extends Game {


	@Override
	public void create() {

		setScreen(new GameScreen());
	}
}