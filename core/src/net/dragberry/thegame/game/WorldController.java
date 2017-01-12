package net.dragberry.thegame.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

import java.text.MessageFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import net.dragberry.thegame.util.CameraHelper;

/**
 * Created by maksim on 11.01.17.
 */
public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getName();
    
    public CameraHelper cameraHelper;

    public Sprite[] testSprites;
    public int selectedSprite;

    public WorldController() {
        init();
    }

    private void init() {
    	Gdx.input.setInputProcessor(this);
    	cameraHelper = new CameraHelper();
        initTestObjects();
    }
    
    @Override
    public boolean keyUp(int keycode) {
    	switch (keycode) {
		case  Keys.R:
			init();
    		Gdx.app.debug(TAG, "Game world has been resetted!");
			break;
		case Keys.SPACE:
			selectedSprite = (selectedSprite + 1) % testSprites.length;
			if (cameraHelper.hasTarget()) {
				cameraHelper.setTarget(testSprites[selectedSprite]);
			}
			Gdx.app.debug(TAG, MessageFormat.format("Sprite #{0} is selected", selectedSprite));
			break;
		case Keys.ENTER:
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
			Gdx.app.debug(TAG, MessageFormat.format("Camera follow enabled: {0}", cameraHelper.hasTarget()));
			break;
		default:
			break;
		}
    	return false;
    }

    private void initTestObjects() {
        testSprites = new Sprite[5];
        int width = 32;
        int height = 32;

        Pixmap pixmap = createProceduralPixmap(width, height);
        Texture texture = new Texture(pixmap);
        for (int index = 0; index < testSprites.length; index++) {
            Sprite sprite = new Sprite(texture);
            sprite.setSize(1, 1);
            sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            sprite.setPosition(randomX, randomY);
            testSprites[index] = sprite;
        }

        selectedSprite = 1;
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    public void update(float deltaTime) {
    	handleDebugInput(deltaTime);
        updateTestObjects(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			return;
		}
		
		float sprMoveSpeed = 5 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveSelectedSprite(-sprMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveSelectedSprite(sprMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			moveSelectedSprite(0, sprMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			moveSelectedSprite(0, -sprMoveSpeed);
		}
		
	}

	private void moveSelectedSprite(float x, float y) {
		testSprites[selectedSprite].translate(x, y);
	}

	private void updateTestObjects(float deltaTime) {
        float rotation = testSprites[selectedSprite].getRotation();
        rotation += 90 * deltaTime;
        rotation %= 360;
        testSprites[selectedSprite].setRotation(rotation);
        testSprites[selectedSprite].setY(testSprites[selectedSprite].getY() - deltaTime);
    }

}