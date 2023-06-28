package knight.arkham.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.structures.Block;
import knight.arkham.objects.structures.Checkpoint;
import knight.arkham.objects.Enemy;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.MID_SCREEN_WIDTH;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;
    private final TiledMap tiledMap;
    private final TextureRegion enemyRegion;
    private final Array<Enemy> enemies;

    public TileMapHelper(GameScreen gameScreen, String mapFilePath) {

        this.gameScreen = gameScreen;
        this.enemyRegion = gameScreen.getTextureAtlas().findRegion("goomba");
        tiledMap = new TmxMapLoader().load(mapFilePath);
        enemies = new Array<>();
    }

    public OrthogonalTiledMapRenderer setupMap() {

        MapLayers mapLayers = tiledMap.getLayers();

        for (MapLayer mapLayer : mapLayers){

            parseMapObjectsToBox2DBodies(mapLayer.getObjects(), mapLayer.getName());
        }

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(MapObjects mapObjects, String objectsName) {

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            Rectangle parsedRectangle = parseRectangle(rectangle);

            switch (objectsName) {

                case "Enemies":
                    createEnemyBody(parsedRectangle);
                    break;

                case "Blocks":
                    new Block(parsedRectangle, gameScreen, tiledMap);
                    break;

                case "Checkpoints":
                    new Checkpoint(parsedRectangle, gameScreen, tiledMap);
                    break;

                default:
                    Box2DHelper.createBody(new Box2DBody(parsedRectangle, gameScreen.getWorld()));
                    break;
            }
        }
    }

    private Rectangle parseRectangle(Rectangle rectangle){
        return new Rectangle(
            rectangle.x + rectangle.width / 2,
            rectangle.y + rectangle.height / 2,
            rectangle.width, rectangle.height
        );
    }

    private void createEnemyBody(Rectangle rectangle) {

        Enemy actualEnemy = new Enemy(rectangle, gameScreen, enemyRegion);

        enemies.add(actualEnemy);
    }

    public boolean isPlayerInsideMapBounds(Vector2 playerPixelPosition) {

        MapProperties properties = tiledMap.getProperties();

        int mapWidth = properties.get("width", Integer.class);
        int tilePixelWidth = properties.get("tilewidth", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;

        return playerPixelPosition.x > MID_SCREEN_WIDTH && playerPixelPosition.x < mapPixelWidth - MID_SCREEN_WIDTH;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
