package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.SimplePlayer;

public class TileMapHelperNoBox2D {

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Array<Rectangle> collisionRectangles = new Array<>();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean isDebugRenderer;

    public TileMapHelperNoBox2D(String mapFilePath) {

        TiledMap tiledMap = new TmxMapLoader().load(mapFilePath);

        mapRenderer = setupMap(tiledMap);
    }

    public OrthogonalTiledMapRenderer setupMap(TiledMap tiledMap) {

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

    public void update(SimplePlayer player) {

        for (Rectangle collision : collisionRectangles) {

            if (player.getBounds().overlaps(collision)) {

                player.hasCollision = true;
                //This break its extremely necessary for this code to work
                break;
            }
            else
                player.hasCollision = false;
        }
    }

    public void draw(OrthographicCamera camera, SimplePlayer player){

        mapRenderer.setView(camera);

        mapRenderer.render();

        mapRenderer.getBatch().setProjectionMatrix(camera.combined);

        mapRenderer.getBatch().begin();

        player.draw(mapRenderer.getBatch());

        mapRenderer.getBatch().end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isDebugRenderer = !isDebugRenderer;

        if (isDebugRenderer) {

            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Set the ShapeType to Line for drawing outlines

// Set the color for drawing the rectangles
            shapeRenderer.setColor(Color.RED); // You can
            // choose any color you prefer

            player.debugDraw(shapeRenderer);

            shapeRenderer.setColor(Color.GREEN);

            for (Rectangle rectangle : collisionRectangles)
                shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

            shapeRenderer.end();
        }
    }
}
