package net.dragberry.thegame.util;

/**
 * Created by maksim on 11.01.17.
 */

public interface Constants {

    float VIEWPORT_WIDTH = 5.0f;
    float VIEWPORT_HEIGHT = 5.0f;
    
    float VIEWPORT_GUI_WIDTH = 800.0f;
    float VIEWPORT_GUI_HEIGHT = 480.0f;

    String TEXTURE_ATLAS_OBJECTS = "images/canyonbunny.pack";

    String LEVEL_01 = "levels/level-01.png";
    
    int LIVES_START = 3;
	float ITEM_FEATHER_POWERUP_DURATION = 9;
	
	float TIME_DELAY_GAME_OVER = 3;
	
	String TEXTURE_ATLAS_UI = "images/canyonbunny-ui.pack";
	String TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";
	String SKIN_LIBGDX_UI = "images/uiskin.json";
	String SKIN_CANYONBUNNY_UI = "images/canyonbunny-ui.json";
	String PREFERENCES = "canyonbunny.prefs";
	
}
