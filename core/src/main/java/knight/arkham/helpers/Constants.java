package knight.arkham.helpers;


import knight.arkham.Journey;

public class Constants {

    public static final float PIXELS_PER_METER = 32.0f;

    public static final int FULL_SCREEN_HEIGHT = Journey.INSTANCE.getScreenHeight();
    public static final float BOX2D_FULL_SCREEN_HEIGHT = FULL_SCREEN_HEIGHT / PIXELS_PER_METER;
    public static final int FULL_SCREEN_WIDTH = Journey.INSTANCE.getScreenWidth();
    public static final float BOX2D_FULL_SCREEN_WIDTH = FULL_SCREEN_WIDTH / PIXELS_PER_METER;
    public static final int MID_SCREEN_HEIGHT = Journey.INSTANCE.getScreenHeight() / 2;
    public static final int MID_SCREEN_WIDTH = Journey.INSTANCE.getScreenWidth() / 2;

    public static final short NOTHING_BIT = 0;

//    Si por defecto no especifico el categoryBits de mi objeto, por defecto este se inicializará en 1,
//    por lo tanto, será evaluado como un ground
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
    public static final short ITEM_BIT = 256;
    public static final short MARIO_HEAD_BIT = 512;
}
