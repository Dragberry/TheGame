package net.dragberry.thegame.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

import java.text.MessageFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import net.dragberry.thegame.util.CameraHelper;
import net.dragberry.thegame.util.Constants;

/**
 * Created by maksim on 11.01.17.
 */
public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getName();
    
    public CameraHelper cameraHelper;
    
    public Level level;
    public int lives;
    public int score;
    

    public WorldController() {
        init();
    }

    private void init() {
    	Gdx.input.setInputProcessor(this);
    	cameraHelper = new CameraHelper();
    	lives = Constants.LIVES_START;
    	initLevel();
    }
    
    private void initLevel() {
    	score = 0;
    	level = new Level(Constants.LEVEL_01);
    }
    
    @Override
    public boolean keyUp(int keycode) {
    	switch (keycode) {
		case  Keys.R:
			init();
    		Gdx.app.debug(TAG, "Game world has been resetted!");
			break;
		case Keys.SPACE:
			break;
		case Keys.ENTER:
			break;
		default:
			break;
		}
    	return false;
    }


    public void update(float deltaTime) {
    	handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			return;
		}
		
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			moveCamera(-camMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			moveCamera(camMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			moveCamera(0, camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			moveCamera(0, -camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			cameraHelper.setPosition(0, 0);
		}
		
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationfactor = 5;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camZoomSpeed *= camZoomSpeedAccelerationfactor;
		}
		if (Gdx.input.isKeyPressed(Keys.COMMA)) {
			cameraHelper.addZoom(camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) {
			cameraHelper.addZoom(-camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.SLASH)) {
			cameraHelper.setZoom(1);
		}
		
	}
    
    private void moveCamera(float x, float y) {
    	x += cameraHelper.getPosition().x;
    	y += cameraHelper.getPosition().y;
    	cameraHelper.setPosition(x, y);
    }


}
