package net.dragberry.thegame.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import net.dragberry.thegame.game.Assets;

public class Clouds extends AbstractGameObject {
	
	private class Cloud extends AbstractGameObject {
		
		private TextureRegion regCloud;
		
		public Cloud() {}
		
		public void setRegion(TextureRegion region) {
			regCloud = region;
		}

		@Override
		public void render(SpriteBatch batch) {
			TextureRegion reg = regCloud;
			batch.draw(reg.getTexture(),
					position.x + origin.x, position.y + origin.y,
					origin.x, origin.y,
					dimension.x, dimension.y,
					scale.x, scale.y,
					rotation,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
			
		}
		
	}
	
	private float length;
	
	private Array<TextureRegion> regClouds;
	private Array<Cloud> clouds;
	
	public Clouds(float length) {
		this.length = length;
		init();
	}

	@Override
	protected void init() {
		dimension.set(3.0f, 1.5f);
		regClouds = new Array<>();
		regClouds.addAll(
				Assets.instance.levelDecoration.cloud01,
				Assets.instance.levelDecoration.cloud02,
				Assets.instance.levelDecoration.cloud03
				);
		int distFac = 5;
		int numClouds = (int) (length / distFac);
		clouds = new Array<>(2 * numClouds);
		for (int index = 0; index < numClouds; index++) {
			Cloud cloud = spawnCloud();
			cloud.position.x = index * distFac;
			clouds.add(cloud);
		}
	}

	private Cloud spawnCloud(){
		Cloud cloud = new Cloud();
		cloud.dimension.set(dimension);
		cloud.setRegion(regClouds.random());
		Vector2 pos = new Vector2();
		pos.x = length + 10;
		pos.y += 1.75f;
		pos.y += MathUtils.random(0.0f, 0.2f) * (MathUtils.randomBoolean() ? 1 : -1);
		cloud.position.set(pos);
		return cloud;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		clouds.forEach(cloud -> cloud.render(batch));
	}

}
