package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.game.gesture.Direction;
import com.mygdx.game.gesture.DirectionListener;
import com.mygdx.game.gesture.GestureDirectionAdapter;
import com.mygdx.game.snake.SnakeBodyPart;
import com.mygdx.game.snake.SnakeBodyType;
import com.mygdx.game.snake.Turn;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

import static com.mygdx.game.snake.SnakeUtilsKt.SNAKE_BODY_RADIUS;
import static com.mygdx.game.snake.SnakeUtilsKt.SNAKE_HEAD_RADIUS;
import static com.mygdx.game.snake.SnakeUtilsKt.getDefaultSnake;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Direction currentDirection = Direction.RIGHT.INSTANCE;
    LinkedList<SnakeBodyPart> snakeBody;
    Queue<Turn> turns;
    boolean isCollided = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        snakeBody = getDefaultSnake();
        turns = new LinkedList<>();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDirectionAdapter(directionListener)));
    }

    private DirectionListener directionListener = new DirectionListener() {
        @Override
        public void onDirectionChanged(@NotNull Direction direction) {
            SnakeBodyPart snakeHead = snakeBody.getFirst();
            if (!(((snakeHead.getDirection() == Direction.RIGHT.INSTANCE || snakeHead.getDirection() == Direction.LEFT.INSTANCE)
                    && (direction == Direction.RIGHT.INSTANCE || direction == Direction.LEFT.INSTANCE))
                    || ((snakeHead.getDirection() == Direction.UP.INSTANCE || snakeHead.getDirection() == Direction.DOWN.INSTANCE)
                    && (direction == Direction.UP.INSTANCE || direction == Direction.DOWN.INSTANCE)))) {
                currentDirection = direction;
                turns.add(
                        new Turn(
                                snakeHead.getPosX(),
                                snakeHead.getPosY(),
                                currentDirection
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
//		System.out.println(position.x + ", " + position.y);

        drawSnake();
        checkCollision();
    }

    private void drawSnake() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        for (SnakeBodyPart snakeBodyPart : snakeBody) {
            shapeRenderer.circle(snakeBodyPart.getPosX(), snakeBodyPart.getPosY(), snakeBodyPart.getSnakeBodyType() == SnakeBodyType.HEAD.INSTANCE ? SNAKE_HEAD_RADIUS : SNAKE_BODY_RADIUS);

            if (!isCollided)
                moveSnakeBodyPart(snakeBodyPart);
        }
        shapeRenderer.end();
    }

    private void moveSnakeBodyPart(SnakeBodyPart snakeBodyPart) {
        for (Turn turn : turns) {
            if (Math.pow(turn.getPosX() - snakeBodyPart.getPosX(), 2) + Math.pow(turn.getPosY() - snakeBodyPart.getPosY(), 2) <= Math.pow(1, 2)) {
                snakeBodyPart.setDirection(turn.getDirection());
            }
        }

        if (snakeBodyPart.getDirection() == Direction.RIGHT.INSTANCE) {
            snakeBodyPart.setPosX(snakeBodyPart.getPosX() + 3);
        } else if (snakeBodyPart.getDirection() == Direction.DOWN.INSTANCE) {
            snakeBodyPart.setPosY(snakeBodyPart.getPosY() - 3);
        } else if (snakeBodyPart.getDirection() == Direction.LEFT.INSTANCE) {
            snakeBodyPart.setPosX(snakeBodyPart.getPosX() - 3);
        } else if (snakeBodyPart.getDirection() == Direction.UP.INSTANCE) {
            snakeBodyPart.setPosY(snakeBodyPart.getPosY() + 3);
        }
    }

    private void checkCollision() {
        SnakeBodyPart snakeHead = snakeBody.getFirst();
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

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
