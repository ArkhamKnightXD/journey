package knight.arkham.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.Enemy;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final World world;
    private final TextureRegion enemyRegion;
    private final Array<Enemy> enemies;

    public TileMapHelper(World world, TextureRegion enemyRegion, Array<Enemy> enemies) {

        this.world = world;
        this.enemyRegion = enemyRegion;
        this.enemies = enemies;
    }

    public OrthogonalTiledMapRenderer setupMap(String mapFilePath) {

        TiledMap tiledMap = new TmxMapLoader().load(mapFilePath);

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
}
