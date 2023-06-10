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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.Enemy;

import static knight.arkham.helpers.Constants.MID_SCREEN_WIDTH;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final TiledMap tiledMap;
    private final World world;
    private final TextureRegion enemyRegion;
    private final Array<Enemy> enemies;

    public TileMapHelper(World world, TextureRegion enemyRegion, Array<Enemy> enemies, String mapFilePath) {

        tiledMap = new TmxMapLoader().load(mapFilePath);

        this.world = world;
        this.enemyRegion = enemyRegion;
        this.enemies = enemies;
    }

    public OrthogonalTiledMapRenderer setupMap() {

        parseMapObjectsToBox2DBodies(tiledMap, "Collisions");
        parseMapObjectsToBox2DBodies(tiledMap, "Enemies");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            if (objectsName.equals("Enemies"))
                createEnemyBody(rectangle);

            else
                Box2DHelper.createCollisionBody(rectangle, world);
        }
    }

    private void createEnemyBody(Rectangle rectangle) {

        Enemy actualEnemy = new Enemy(
            new Rectangle(
                rectangle.x + rectangle.width / 2,
                rectangle.y + rectangle.height / 2,
                rectangle.width, rectangle.height
            ),
            world, enemyRegion
        );

        enemies.add(actualEnemy);
    }

    public boolean isPlayerIsInsideMapBounds(Vector2 playerPixelPosition){

        MapProperties properties = tiledMap.getProperties();

        int mapWidth = properties.get("width", Integer.class);
        int tilePixelWidth = properties.get("tilewidth", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;

        return playerPixelPosition.x > MID_SCREEN_WIDTH && playerPixelPosition.x < mapPixelWidth-MID_SCREEN_WIDTH;
    }
}
