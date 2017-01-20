package net.dragberry.thegame.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.dragberry.thegame.game.Assets;
import net.dragberry.thegame.util.Constants;

public class BunnyHead extends AbstractGameObject {
	
	private static final String TAG = BunnyHead.class.getName();
	
	private static final float JUMP_TIME_MAX = 0.3f;
	private static final float JUMP_TIME_MIN = 0.1f;
	private static final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}
	
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	private TextureRegion regHead;
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasFeatherPowerup;
	public float timeLeftFeatherPowerUp;

	public BunnyHead() {
		init();
	}
	
	
	@Override
	protected void init() {
		dimension.set(1, 1);
		regHead = Assets.instance.bunny.head;
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f,  0.0f);
		acceleration.set(0.0f, -0.25f);
		viewDirection = VIEW_DIRECTION.RIGHT;
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		hasFeatherPowerup = false;
		timeLeftFeatherPowerUp = 0;
	}
	
	public void setJumping(boolean jumpKeyPressed) {
		switch (jumpState) {
		case GROUNDED:
			if (jumpKeyPressed) {
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		case JUMP_RISING:
			if (!jumpKeyPressed) {
				jumpState = JUMP_STATE.JUMP_FALLING;
			}
			break;
		case FALLING:
		case JUMP_FALLING:
			if (jumpKeyPressed && hasFeatherPowerup) {
				timeJumping = JUMP_TIME_OFFSET_FLYING;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public void setFeatherPowerup(boolean pickedUp) {
		hasFeatherPowerup = pickedUp;
		if (pickedUp) {
			timeLeftFeatherPowerUp = Constants.ITEM_FEATHER_POWERUP_DURATION;
		}
	}
	
	public boolean hasFeatherUp() {
		return hasFeatherPowerup && timeLeftFeatherPowerUp > 0;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		if (hasFeatherPowerup) {
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		reg = regHead;
		batch.draw(reg.getTexture(),
				position.x, position.y,
				origin.x, origin.y,
				dimension.x, dimension.y,
				scale.x, scale.y,
				rotation,
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.LEFT, false);
		batch.setColor(1, 1, 1, 1);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (velocity.x != 0) {
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		if (timeLeftFeatherPowerUp > 0) {
			timeLeftFeatherPowerUp -= deltaTime;
			if (timeLeftFeatherPowerUp < 0) {
				// disable power-up
				timeLeftFeatherPowerUp = 0;
				setFeatherPowerup(false);
			}
		}
	}
	
	@Override
	protected void updateMotionY(float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			break;
		case JUMP_RISING:
			timeJumping += deltaTime;
			if (timeJumping <= JUMP_TIME_MAX) {
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			timeJumping += deltaTime;
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
			break;
		default:
			throw new IllegalArgumentException();
		}
		if (jumpState != JUMP_STATE.GROUNDED) {
			super.updateMotionY(deltaTime);
		}
	}
	

}
