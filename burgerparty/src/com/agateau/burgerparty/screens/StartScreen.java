package com.agateau.burgerparty.screens;

import com.agateau.burgerparty.BurgerPartyGame;
import com.agateau.burgerparty.utils.AnchorGroup;
import com.agateau.burgerparty.utils.FileUtils;
import com.agateau.burgerparty.utils.MusicController;
import com.agateau.burgerparty.utils.RefreshHelper;
import com.agateau.burgerparty.view.BurgerPartyUiBuilder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StartScreen extends BurgerPartyScreen {

	public StartScreen(BurgerPartyGame game) {
		super(game);
		Image bgImage = new Image(getTextureAtlas().findRegion("ui/menu-bg"));
		setBackgroundActor(bgImage);
		setupWidgets();
		new RefreshHelper(getStage()) {
			@Override
			protected void refresh() {
				getGame().showStartScreen();
				dispose();
			}
		};
	}

	private void setupWidgets() {
		BurgerPartyUiBuilder builder = new BurgerPartyUiBuilder(getGame().getAssets());
		builder.build(FileUtils.assets("screens/start.gdxui"));
		AnchorGroup root = builder.getActor("root");
		getStage().addActor(root);
		root.setFillParent(true);

		builder.<ImageButton>getActor("startButton").addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent Event, Actor actor) {
				getGame().showWorldListScreen();
			}
		});
		builder.<ImageButton>getActor("aboutButton").addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent Event, Actor actor) {
				getGame().showAboutScreen();
			}
		});
		mMuteButton = builder.<ImageButton>getActor("muteButton");
		mMuteButton.addListener(new ChangeListener() {
			public void changed(ChangeListener.ChangeEvent Event, Actor actor) {
				MusicController controller = getGame().getMusicController();
				controller.setMuted(!controller.isMuted());
				updateMuteButton();
			}
		});
		updateMuteButton();
	}

	@Override
	public void onBackPressed() {
		Gdx.app.exit();
	}

	private void updateMuteButton() {
		boolean muted = getGame().getMusicController().isMuted();
		TextureRegion region = getTextureAtlas().findRegion(muted ? "ui/icon-sound-off" : "ui/icon-sound-on");
		mMuteButton.getImage().setDrawable(new TextureRegionDrawable(region));
	}

	private ImageButton mMuteButton;
}
