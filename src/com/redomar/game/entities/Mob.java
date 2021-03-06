package com.redomar.game.entities;

import java.util.Random;

import com.redomar.game.level.LevelHandler;
import com.redomar.game.level.tiles.Tile;

public abstract class Mob extends Entity {

	protected String name;
	protected Random random = new Random();
	protected double speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	protected boolean isSwimming = false;
	protected boolean isMagma = false;
	protected boolean isMuddy = false;
	protected boolean changeLevels = false;
	protected int ticker;

	public Mob(LevelHandler level, String name, int x, int y, double speed) {
		super(level);
		this.name = name;
		this.setX(x);
		this.setY(y);
		this.speed = speed;
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa, ya)) {
			if (ya < 0) {
				movingDir = 0;
			}
			if (ya > 0) {
				movingDir = 1;
			}
			if (xa < 0) {
				movingDir = 2;
			}
			if (xa > 0) {
				movingDir = 3;
			}
			setX(getX() + xa * (int) speed);
			setY(getY() + ya * (int) speed);
		}
	}

	public abstract boolean hasCollided(int xa, int ya);

	protected boolean isSolid(int xa, int ya, int x, int y) {

		if (level == null) {
			return false;
		}

		Tile lastTile = level.getTile((this.getX() + x) >> 3,
				(this.getY() + y) >> 3);
		Tile newtTile = level.getTile((this.getX() + x + xa) >> 3, (this.getY()
				+ y + ya) >> 3);

		if (!lastTile.equals(newtTile) && newtTile.isSolid()) {
			return true;
		}

		return false;
	}

	protected void followMovementAI(int x, int y, int px, int py, int xa,
			int ya, Mob mob) {
		ya = 0;
		xa = 0;
		if (px > x)
			xa++;
		if (px < x)
			xa--;
		if (py > y)
			ya++;
		if (py < y)
			ya--;
		moveMob(xa, ya, mob);
	}

	protected void moveMob(int xa, int ya, Mob mob) {
		if (xa != 0 || ya != 0) {
			mob.move(xa, ya);
			mob.isMoving = true;
		} else {
			mob.isMoving = false;
		}
	}

	public String getName() {
		return name;
	}

	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}

}
