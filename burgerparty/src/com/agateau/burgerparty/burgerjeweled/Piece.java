package com.agateau.burgerparty.burgerjeweled;

import com.agateau.burgerparty.utils.MaskedDrawable;
import com.agateau.burgerparty.utils.SpriteImage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Piece extends SpriteImage {
	static private final float DEATH_DURATION = 0.5f;
	static private final float FALL_DURATION = 0.1f;

	public void destroy() {
		mDying = true;
		addAction(Actions.sequence(
			Actions.parallel(
				Actions.scaleTo(0, 0, DEATH_DURATION),
				Actions.rotateBy(180, DEATH_DURATION)
			),
			Actions.run(new Runnable() {
				@Override
				public void run() {
					removalRequested.emit();
				}
			})
		));
	}

	public void fallTo(float dstY) {
		clearActions();
		addAction(Actions.moveTo(getX(), dstY, FALL_DURATION, Interpolation.pow2Out));
	}

	public void reset(MaskedDrawable md, int id, float posX, float posY) {
		mTime = 0;
		mId = id;
		mDying = false;
		init(md);
		setOriginX(getWidth() / 2);
		setOriginY(getHeight() / 2);
		setScale(1);
		setRotation(0);
		setPosition(posX, getStage().getHeight() + posY);
		Gdx.app.log("Piece", "x=" + posX + " y=" + posY + " w=" + getWidth() + " h=" + getHeight());
		addAction(
			Actions.moveTo(posX, posY, FALL_DURATION, Interpolation.pow2Out)
			);
	}

	public boolean isDying() {
		return mDying;
	}

	public float getTime() {
		return mTime;
	}

	public int getId() {
		return mId;
	}

	private float mTime;
	private int mId;
	private boolean mDying = false;
}
