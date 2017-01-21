package net.dragberry.thegame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import net.dragberry.thegame.game.Assets;

public abstract class AbstractGameScreen implements Screen {
	
	protected Game game;
	
	public AbstractGameScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public abstract void render(float deltaTime);
	
	@Override
	public abstract void resize(int width, int height);
	
	@Override
	public abstract void show();
	
	@Override
	public abstract void hide();
	
	@Override
	public abstract void pause();
	
	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
	}
	
	@Override
	public void dispose() {
		Assets.instance.dispose();
	}
	
	

}
