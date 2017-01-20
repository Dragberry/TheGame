package net.dragberry.thegame.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.dragberry.thegame.game.Assets;

public class Rock extends AbstractGameObject {
	
	private TextureRegion regEdge;
	private TextureRegion regMiddle;
	
	private int length;
	
	public Rock() {
		init();
	}
	
	@Override
	protected void init() {
		dimension.set(1, 1.5f);
		
		regEdge = Assets.instance.rock.edge;
		regMiddle= Assets.instance.rock.middle;
		
		setLength(1);
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		float relX = 0;
		float relY = 0;
		
		reg = regEdge;
		relX -= dimension.x / 4.0f;
		batch.draw(reg.getTexture(),
				position.x + relX, position.y + relY,
				origin.x, origin.y,
				dimension.x / 4, dimension.y,
				scale.x, scale.y, 
				rotation,
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	
		relX = 0;
		reg = regMiddle;
		for (int index = 0; index < length; index++){
			batch.draw(reg.getTexture(),
					position.x + relX, position.y + relY,
					origin.x, origin.y,
					dimension.x, dimension.y,
					scale.x, scale.y,
					rotation,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
			relX += dimension.x;
		}
		
		reg = regEdge;
		batch.draw(reg.getTexture(),
				position.x + relX, position.y + relY,
				origin.x + dimension.x / 8, origin.y,
				dimension.x / 4, dimension.y,
				scale.x, scale.y,
				rotation,
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				true, false);

	}
	
	public void setLength(int length) {
		this.length = length;
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}
	
	public void increaseLength(int amount) {
		setLength(length + amount);
	}

}
