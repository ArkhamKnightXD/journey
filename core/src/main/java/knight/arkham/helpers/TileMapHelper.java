package knight.arkham.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.Block;
import knight.arkham.objects.Checkpoint;
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

        parseMapObjectsToBox2DBodies(tiledMap, "Collisions");
        parseMapObjectsToBox2DBodies(tiledMap, "Enemies");
        parseMapObjectsToBox2DBodies(tiledMap, "Blocks");
        parseMapObjectsToBox2DBodies(tiledMap, "Checkpoints");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            if (objectsName.equals("Enemies"))
                createEnemyBody(rectangle);

            else if (objectsName.equals("Blocks"))
                createBlockBody(rectangle);

            else if (objectsName.equals("Checkpoints"))
                createCheckpointBody(rectangle);

            else
                Box2DHelper.createStaticCollisionBody(rectangle, gameScreen.getWorld());
        }
    }

    private void createBlockBody(Rectangle rectangle) {

        new Block(
            new Rectangle(
                rectangle.x + rectangle.width / 2,
                rectangle.y + rectangle.height / 2,
                rectangle.width, rectangle.height
            ),
            gameScreen, tiledMap
        );
    }

    private void createCheckpointBody(Rectangle rectangle) {

        new Checkpoint(
            new Rectangle(
                rectangle.x + rectangle.width / 2,
                rectangle.y + rectangle.height / 2,
                rectangle.width, rectangle.height
            ),
            gameScreen, tiledMap
        );
    }

    private void createEnemyBody(Rectangle rectangle) {

        Enemy actualEnemy = new Enemy(
            new Rectangle(
                rectangle.x + rectangle.width / 2,
                rectangle.y + rectangle.height / 2,
                rectangle.width, rectangle.height
            ),
            gameScreen, enemyRegion
        );

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
