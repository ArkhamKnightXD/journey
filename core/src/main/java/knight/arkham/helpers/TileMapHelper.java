package knight.arkham.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import knight.arkham.screens.GameScreen;
import knight.arkham.objects.Enemy;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final GameScreen gameScreen;

    private final Array<Enemy> enemies;

    private final TextureRegion enemyRegion;


    public TileMapHelper(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
        enemies = new Array<>();
        enemyRegion = gameScreen.getTextureAtlas().findRegion("goomba");
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/test2.tmx");

        parseMapObjectsToBox2DBodies(tiledMap, "Collisions");
        parseMapObjectsToBox2DBodies(tiledMap, "Enemies");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            if (objectsName.equals("Enemies")) {

                Enemy actualEnemy = new Enemy(
                        new Rectangle(
                                rectangle.x + rectangle.width / 2,
                                rectangle.y + rectangle.height / 2,
                                rectangle.width, rectangle.height
                        ),
                        gameScreen.getWorld(), enemyRegion);

                enemies.add(actualEnemy);
            }

            else {
                Box2DHelper.createBody(

                        new Box2DBody(

                                new Rectangle(
                                        rectangle.x + rectangle.width / 2,
                                        rectangle.y + rectangle.height / 2,
                                        rectangle.width, rectangle.height
                                ),
                                BodyDef.BodyType.StaticBody, 0,
                                gameScreen.getWorld(), ContactType.FLOOR
                        )
                );
            }
        }

        gameScreen.setEnemies(enemies);
    }
}
