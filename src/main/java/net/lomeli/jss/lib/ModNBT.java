package net.lomeli.jss.lib;

import net.lomeli.jss.JSS;

public class ModNBT {
    // Ink NBT
    public static final String INK_NBT = JSS.MOD_ID + "_ink";

    // Bounded Item NBT
    public static final String ITEM_OWNER_LEAST = JSS.MOD_ID + "_item_owner_least";
    public static final String ITEM_OWNER_MOST = JSS.MOD_ID + "_item_owner_most";
    public static final String ITEM_OWNER_NAME = JSS.MOD_ID + "_item_owner_name";

    // Health Modifier NBT
    public static final String HEART_COUNT = JSS.MOD_ID + "_heart_count";
    public static final String HEART_LOSS = JSS.MOD_ID + "_heart_loss";

    // Unborn NBT
    private static final String UNBORN_TAG = JSS.MOD_ID + "_unborn_";
    public static final String UNBORN_ATTACK = UNBORN_TAG + "attack";
    public static final String UNBORN_TP_DELAY = UNBORN_TAG + "tp_delay";
    public static final String UNBORN_BASE_X = UNBORN_TAG + "x";
    public static final String UNBORN_BASE_Y = UNBORN_TAG + "y";
    public static final String UNBORN_BASE_Z = UNBORN_TAG + "z";
    public static final String UNBORN_SPAWN_MOD = UNBORN_TAG + "spawn_mod";
    public static final String UNBORN_TARGET_UUID = UNBORN_TAG + "target_uuid";
    public static final String UNBORN_LOGGED_OUT = UNBORN_TAG + "player_logged_out";
}
