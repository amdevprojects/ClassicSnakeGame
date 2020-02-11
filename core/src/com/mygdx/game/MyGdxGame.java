package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.game.gesture.Direction;
import com.mygdx.game.gesture.DirectionListener;
import com.mygdx.game.gesture.GestureDirectionAdapter;
import com.mygdx.game.snake.SnakeBodyPart;
import com.mygdx.game.snake.SnakeBodyType;
import com.mygdx.game.snake.SnakeFood;
import com.mygdx.game.snake.SnakeTurn;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

import static com.mygdx.game.snake.SnakeUtilsKt.SNAKE_BODY_RADIUS;
import static com.mygdx.game.snake.SnakeUtilsKt.SNAKE_FOOD_RADIUS;
import static com.mygdx.game.snake.SnakeUtilsKt.SNAKE_HEAD_RADIUS;
import static com.mygdx.game.snake.SnakeUtilsKt.SNAKE_MOVE_SPEED_NORMAL;
import static com.mygdx.game.snake.SnakeUtilsKt.getDefaultSnake;
import static com.mygdx.game.snake.SnakeUtilsKt.getSnakeBodyPart;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Direction currentDirection = Direction.RIGHT.INSTANCE;
    LinkedList<SnakeBodyPart> snake;
    SnakeBodyPart snakeHead;
    Queue<SnakeTurn> snakeTurns;
    boolean isCollided = false;
    SnakeFood snakeFood;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        snake = getDefaultSnake();
        snakeTurns = new LinkedList<>();
        snakeFood = new SnakeFood();
        font = new BitmapFont();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDirectionAdapter(directionListener)));
    }

    private DirectionListener directionListener = new DirectionListener() {
        @Override
        public void onDirectionChanged(@NotNull Direction direction) {
            if (!(((snakeHead.getDirection() == Direction.RIGHT.INSTANCE || snakeHead.getDirection() == Direction.LEFT.INSTANCE)
                    && (direction == Direction.RIGHT.INSTANCE || direction == Direction.LEFT.INSTANCE))
                    || ((snakeHead.getDirection() == Direction.UP.INSTANCE || snakeHead.getDirection() == Direction.DOWN.INSTANCE)
                    && (direction == Direction.UP.INSTANCE || direction == Direction.DOWN.INSTANCE)))) {
                currentDirection = direction;
                snakeTurns.add(
                        new SnakeTurn(
                                snakeHead.getPosX(),
                                snakeHead.getPosY(),
                                currentDirection,
                                0
                        )
                );
            }
        }
    };

    @Override
    public void render() {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, position.x, position.y);
//		if (Gdx.graphics.getWidth() > position.x + img.getWidth()) {
//			position.x += 3;
//		}
//		if (Gdx.graphics.getHeight() > position.y + img.getHeight()) {
//			position.y += 3;
//		}
//		if (Gdx.input.isTouched()) {
//		    Vector2 touch = syncTouch(Gdx.input);
//			position.x = touch.x - img.getWidth();
//			position.y = touch.y - img.getHeight();
////		System.out.println(position.x + ", " + position.y);
//		}
//		batch.end();

        snakeHead = snake.getFirst();
        drawSnake();
        checkCollisionWithEdges();
        checkFoodValidity();
        checkTurnsValidity();
    }

    private void drawSnake() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);

        drawDetailsOnScreen();

        shapeRenderer.circle(snakeFood.getPosX(), snakeFood.getPosY(), SNAKE_FOOD_RADIUS);

        for (SnakeBodyPart snakeBodyPart : snake) {
            shapeRenderer.circle(snakeBodyPart.getPosX(), snakeBodyPart.getPosY(), snakeBodyPart.getSnakeBodyType() == SnakeBodyType.HEAD.INSTANCE ? SNAKE_HEAD_RADIUS : SNAKE_BODY_RADIUS);

            if (!isCollided)
                moveSnakeBodyPart(snakeBodyPart);
        }
        shapeRenderer.end();
    }

    private void moveSnakeBodyPart(SnakeBodyPart snakeBodyPart) {
        for (SnakeTurn snakeTurn : snakeTurns) {
            if (Math.pow(snakeTurn.getPosX() - snakeBodyPart.getPosX(), 2) + Math.pow(snakeTurn.getPosY() - snakeBodyPart.getPosY(), 2) <= Math.pow(1, 2)) {
                snakeBodyPart.setDirection(snakeTurn.getDirection());
                snakeTurn.setCountApplied(snakeTurn.getCountApplied() + 1);
            }
        }

        if (snakeBodyPart.getDirection() == Direction.RIGHT.INSTANCE) {
            snakeBodyPart.setPosX(snakeBodyPart.getPosX() + SNAKE_MOVE_SPEED_NORMAL);
        } else if (snakeBodyPart.getDirection() == Direction.DOWN.INSTANCE) {
            snakeBodyPart.setPosY(snakeBodyPart.getPosY() - SNAKE_MOVE_SPEED_NORMAL);
        } else if (snakeBodyPart.getDirection() == Direction.LEFT.INSTANCE) {
            snakeBodyPart.setPosX(snakeBodyPart.getPosX() - SNAKE_MOVE_SPEED_NORMAL);
        } else if (snakeBodyPart.getDirection() == Direction.UP.INSTANCE) {
            snakeBodyPart.setPosY(snakeBodyPart.getPosY() + SNAKE_MOVE_SPEED_NORMAL);
        }

        checkCollisionWithBody(snakeBodyPart);
    }

    private void checkCollisionWithBody(SnakeBodyPart snakeBodyPart) {
        if (snakeBodyPart.getSnakeBodyType() == SnakeBodyType.BODY.INSTANCE) {
            double distanceSquared = Math.pow(snakeHead.getPosX() - snakeBodyPart.getPosX(), 2) + Math.pow(snakeHead.getPosY() - snakeBodyPart.getPosY(), 2);
            if (Math.pow(12, 2) >= distanceSquared) {
                isCollided = true;
            }
        }
    }

    private void checkCollisionWithEdges() {
        if (snakeHead.getDirection() == Direction.RIGHT.INSTANCE) {
            if (snakeHead.getPosX() + SNAKE_HEAD_RADIUS >= Gdx.graphics.getWidth()) {
                isCollided = true;
            }
        } else if (snakeHead.getDirection() == Direction.DOWN.INSTANCE) {
            if (snakeHead.getPosY() + SNAKE_HEAD_RADIUS <= 0) {
                isCollided = true;
            }
        } else if (snakeHead.getDirection() == Direction.LEFT.INSTANCE) {
            if (snakeHead.getPosX() + SNAKE_HEAD_RADIUS <= 0) {
                isCollided = true;
            }
        } else if (snakeHead.getDirection() == Direction.UP.INSTANCE) {
            if (snakeHead.getPosY() + SNAKE_HEAD_RADIUS >= Gdx.graphics.getHeight()) {
                isCollided = true;
            }
        }
    }

    private void checkTurnsValidity() {
        boolean isAnyTurnInvalidated = false;
        for (SnakeTurn snakeTurn : snakeTurns) {
            if (snakeTurn.getCountApplied() == snake.size()) {
                isAnyTurnInvalidated = true;
                break;
            }
        }

        if (isAnyTurnInvalidated)
            snakeTurns.remove();
    }

    private void checkFoodValidity() {
        double distanceSquared = Math.pow(snakeHead.getPosX() - snakeFood.getPosX(), 2) + Math.pow(snakeHead.getPosY() - snakeFood.getPosY(), 2);
        if (Math.pow(20, 2) >= distanceSquared) {
            snakeFood.setConsumed(true);
        }

        if (snakeFood.isConsumed()) {
            for (int i = 0; i < 2; i++) {
                snake.add(
                        getSnakeBodyPart(snake.getLast())
                );
            }
            snakeFood = new SnakeFood();
        }
    }

    private void drawDetailsOnScreen() {
        batch.begin();
        font.setColor(Color.DARK_GRAY);
        font.draw(batch, "Size: " + snake.size() + ", Turns: " + snakeTurns.size(), 50f, 100f);
        font.getData().setScale(4);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
