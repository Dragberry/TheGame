package net.dragberry.thegame.game;

import java.text.MessageFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import net.dragberry.thegame.game.objects.AbstractGameObject;
import net.dragberry.thegame.game.objects.BunnyHead;
import net.dragberry.thegame.game.objects.Clouds;
import net.dragberry.thegame.game.objects.Feather;
import net.dragberry.thegame.game.objects.GoldCoin;
import net.dragberry.thegame.game.objects.Mountains;
import net.dragberry.thegame.game.objects.Rock;
import net.dragberry.thegame.game.objects.WaterOverlay;

public class Level {
	
	private static final String TAG = Level.class.getName();
	
	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0),						// black
		ROCK(0, 255, 0),					// green
		PLAYER_SPAWNPOINT(255, 255, 255),	// white
		ITEM_FEATHER(255, 0, 255),			// purple
		ITEM_GOLD_COIN(255, 255, 0);		// yellow
		
		private int color;
		
		private BLOCK_TYPE(int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		public boolean sameColor(int color) {
			return this.color == color;
		}
		
		public int getColor() {
			return color;
		}
	}
	
	public BunnyHead bunnyHead;
	public Array<GoldCoin> goldCoins;
	public Array<Feather> feathers; 
	
	public Array<Rock> rocks;
	
	public Clouds clouds;
	public Mountains mountains;
	public WaterOverlay waterOverlay;
	
	public Level(String fileName) {
		init(fileName);
	}
	
	private void init(String fileName) {
		bunnyHead = null;
		
		rocks = new Array<>();
		goldCoins = new Array<>();
		feathers = new Array<>();
		
		Pixmap pixmap = new Pixmap(Gdx.files.internal(fileName));
		int lastPixel = -1;
		
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				float baseHeight = pixmap.getHeight() - pixelY;
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					// do nothing
				} else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new Rock();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
						rocks.add((Rock) obj);
					} else {
						rocks.get(rocks.size - 1).increaseLength(1);
					}
				} else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
					obj = new BunnyHead();
					offsetHeight = -3.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					bunnyHead = (BunnyHead) obj;
				} else if (BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {
					obj = new Feather();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					feathers.add((Feather) obj);
				} else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {
					obj = new GoldCoin();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					goldCoins.add((GoldCoin) obj);
				} else {
					int r = 0xff & (currentPixel >>> 24);
					int g = 0xff & (currentPixel >>> 16);
					int b = 0xff & (currentPixel >>> 8);
					int a = 0xff & currentPixel;
					Gdx.app.error(TAG, MessageFormat.format(
							"Unknown object at x<{0}> y<{1}>: r<{2}> g<{3}> b<{4}>", pixelX, pixelY, r, g, b));
				}
				lastPixel = currentPixel;
			}
		}
		
		clouds = new Clouds(pixmap.getWidth());
		clouds.position.set(0, 2);
		mountains = new Mountains(pixmap.getWidth());
		mountains.position.set(-1, -1);
		waterOverlay = new WaterOverlay(pixmap.getWidth());
		waterOverlay.position.set(0.0f,  -3.75f);
		
		pixmap.dispose();
		Gdx.app.debug(TAG, "Level '" + fileName + "' loaded");
	}
	
	public void render(SpriteBatch batch) {
		mountains.render(batch);
		rocks.forEach(rock -> rock.render(batch));
		goldCoins.forEach(goldCoin -> goldCoin.render(batch));
		feathers.forEach(feather -> feather.render(batch));
		bunnyHead.render(batch);
		waterOverlay.render(batch);
		clouds.render(batch);
	}
	
	public void update(float deltaTime) {
		bunnyHead.update(deltaTime);
		rocks.forEach(rock -> rock.update(deltaTime));
		goldCoins.forEach(coin -> coin.update(deltaTime));
		feathers.forEach(feather -> feather.update(deltaTime));
		clouds.update(deltaTime);
	}
	
	

}
