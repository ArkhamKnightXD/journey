package knight.arkham.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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

        for (MapLayer mapLayer : mapLayers){

            parseMapObjectsToBox2DBodies(mapLayer.getObjects(), mapLayer.getName());
        }

        return new OrthogonalTiledMapRenderer(tiledMap, 1);
    }

    private void parseMapObjectsToBox2DBodies(MapObjects mapObjects, String objectsName) {

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            Rectangle box2dRectangle = createBox2dRectangle(rectangle);

            collisionRectangles.add(box2dRectangle);
        }
    }

    private Rectangle createBox2dRectangle(Rectangle rectangle){
        return new Rectangle(
            rectangle.x + rectangle.width / 2,
            rectangle.y + rectangle.height / 2,
            rectangle.width, rectangle.height
        );
    }

    public void draw(OrthographicCamera camera){

        mapRenderer.setView(camera);

        mapRenderer.render();

        mapRenderer.getBatch().setProjectionMatrix(camera.combined);

        mapRenderer.getBatch().begin();

        mapRenderer.getBatch().end();
    }
}
