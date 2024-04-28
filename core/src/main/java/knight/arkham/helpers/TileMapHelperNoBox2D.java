package knight.arkham.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.SimplePlayer;

public class TileMapHelperNoBox2D {
    private final TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Array<Rectangle> collisionRectangles;

    public TileMapHelperNoBox2D(String mapFilePath) {

        tiledMap = new TmxMapLoader().load(mapFilePath);
        collisionRectangles = new Array<>();
        mapRenderer = setupMap();
    }

    public OrthogonalTiledMapRenderer setupMap() {

        MapLayers mapLayers = tiledMap.getLayers();

        for (MapLayer mapLayer : mapLayers)
            parseMapObjectsToBox2DBodies(mapLayer.getObjects());

        return new OrthogonalTiledMapRenderer(tiledMap, 1);
    }

    private void parseMapObjectsToBox2DBodies(MapObjects mapObjects) {

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            collisionRectangles.add(rectangle);
        }
    }

    public void draw(OrthographicCamera camera, SimplePlayer player){

        for (Rectangle collision : collisionRectangles) {

            if (player.getBounds().overlaps(collision)) {

                player.hasCollision = true;
                //This break its extremely necessary for this code to work
                break;
            }
            else
                player.hasCollision = false;
        }

        mapRenderer.setView(camera);

        mapRenderer.render();

        mapRenderer.getBatch().setProjectionMatrix(camera.combined);

        mapRenderer.getBatch().begin();

        player.draw(mapRenderer.getBatch());

        mapRenderer.getBatch().end();
    }
}
