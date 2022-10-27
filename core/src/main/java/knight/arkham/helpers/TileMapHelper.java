package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.screens.GameScreen;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/test.tmx");

        parseMapObjectsToStaticBodies(tiledMap, "Ground");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToStaticBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Gdx.app.log("enter","test");

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            BodyHelper.createStaticBody(

                    new Box2DBody(

                            new Rectangle(rectangle.x + rectangle.width / 2,
                                    rectangle.y + rectangle.height / 2, rectangle.width,
                                    rectangle.height), gameScreen.getWorld()
                    )
            );

            break;
        }
    }

}



