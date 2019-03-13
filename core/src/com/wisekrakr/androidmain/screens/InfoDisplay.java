package com.wisekrakr.androidmain.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.helpers.LabelHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.TimeKeeper;

import java.util.Iterator;


public class InfoDisplay implements Disposable {


    private Integer levelNumber;
    private Integer score;
    private Integer bounces;
    private Integer lives;
    private Integer balls;

    private Label scoreCountLabel;
    private Label scoreAddedLabel;
    private Label multiplierLabel;
    private Integer worldTimer;
    private Label timeCountLabel;
    private Label levelNumberLabel;
    private Label ballNumberLabel;
    private Label bounceNumberLabel;
    private Label livesNumberLabel;

    private AndroidGame game;

    private Stage stage;
    private float timeCounter;
    private Integer currentScore = 0;
    private Integer multi = 0;

    InfoDisplay(AndroidGame game) {
        this.game = game;

        worldTimer = 0;

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale(GameConstants.FONT_SCALE);

        Label levelLabel = LabelHelper.label("Level", font, Color.WHITE);
        levelNumberLabel = LabelHelper.label(levelNumber != null ? levelNumber.toString() : null, font, Color.GOLDENROD);
        Label timeLabel = LabelHelper.label("TIME", font, Color.WHITE);
        timeCountLabel =LabelHelper.label(worldTimer != null ? worldTimer.toString() : null, font, Color.GOLDENROD);
        Label scoreLabel = LabelHelper.label("Score", font, Color.WHITE);
        scoreCountLabel =LabelHelper.label(score != null ? score.toString() : null, font, Color.GOLDENROD);
        scoreAddedLabel =LabelHelper.label(currentScore != null ? currentScore.toString() : null, font, Color.GREEN);
        multiplierLabel = LabelHelper.label(multi != null ? multi.toString() : null, font, Color.PINK);
        Label ballNameLabel = LabelHelper.label("Balls to Dodge", font, Color.WHITE);
        ballNumberLabel = LabelHelper.label(balls != null ? balls.toString() : null, font, Color.GOLDENROD);
        Label bounceLabel = LabelHelper.label("Bounces", font, Color.WHITE);
        bounceNumberLabel = LabelHelper.label(bounces != null ? bounces.toString() : null, font, Color.GOLDENROD);
        Label livesLabel = LabelHelper.label("Lives left", font, Color.WHITE);
        livesNumberLabel = LabelHelper.label(lives != null ? lives.toString() : null, font, Color.GOLDENROD);

        Table tableLeft = new Table();
        tableLeft.setFillParent(true);
        tableLeft.bottom().left().padLeft(20).padBottom(20);

        Table tableRight = new Table();
        tableRight.setFillParent(true);
        tableRight.bottom().right().padRight(20).padBottom(20);

        tableRight.add(levelLabel).padTop(2);
        tableRight.row();
        tableRight.add(levelNumberLabel).padTop(2);
        tableRight.row();
        tableRight.add(ballNameLabel);
        tableRight.row();
        tableRight.add(ballNumberLabel);
        tableRight.row();
        tableRight.add(bounceLabel).padBottom(2);
        tableRight.row();
        tableRight.add(bounceNumberLabel).padBottom(2);

        tableLeft.add(livesLabel).padTop(2);
        tableLeft.row();
        tableLeft.add(livesNumberLabel).padTop(2);
        tableLeft.row();
        tableLeft.add(timeLabel);
        tableLeft.row();
        tableLeft.add(timeCountLabel);
        tableLeft.row();
        tableLeft.add(scoreLabel).padBottom(2);
        tableLeft.row();
        tableLeft.add(scoreCountLabel).padBottom(2);
        tableLeft.add(scoreAddedLabel).padBottom(2).padLeft(scoreCountLabel.getWidth());
        tableLeft.add(multiplierLabel).padBottom(2).padLeft(scoreCountLabel.getWidth() + scoreAddedLabel.getWidth());

        stage.addActor(tableLeft);
        stage.addActor(tableRight);
    }

    void renderDisplay(TimeKeeper timer, float delta){

        stage.act();
        stage.draw();

        timeCounter += delta;
        if (timeCounter >= 1) {
            timeCounter = 0;
            worldTimer = (int) timer.time;

            timeCountLabel.setText(String.format("%s",worldTimer));

            scoreCountLabel.setText(Integer.toString(ScoreKeeper.getScore()));
            scoreAdded();

            levelNumberLabel.setText(Integer.toString(game.getGameThread().getLevelGenerationSystem().getMainLevel()));
            ballNumberLabel.setText(Integer.toString(game.getGameThread().getEntityFactory().getGameObjects().size()));

            bounces();
            multiplier();
            lives();
        }
    }

    private void scoreAdded(){
        int newScore = ScoreKeeper.getScore();

        if (newScore > currentScore) {

            scoreAddedLabel.setText(" +" + ScoreKeeper.getPointsToGive() * ScoreKeeper.getMultiplier());
            scoreAddedLabel.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.fadeOut(0.5f)));

            currentScore = newScore;
        }
    }

    private void bounces(){
        Iterator<Entity>iterator = game.getGameThread().getEntityFactory().getGameObjects().iterator();
        if (iterator.hasNext()){
            bounceNumberLabel.setText(Integer.toString(iterator.next().getComponent(CollisionComponent.class).bounces));
        }else {
            bounceNumberLabel.setText("");
        }
    }

    private void multiplier(){
        int mul = ScoreKeeper.getMultiplier();

        if (mul > multi){
            multiplierLabel.setText(" x" + ScoreKeeper.getMultiplier());
            multiplierLabel.addAction(Actions.sequence(Actions.fadeIn(1.5f), Actions.fadeOut(1.5f)));

            multi = mul;
        }
    }

    private void lives(){
        livesNumberLabel.setText(ScoreKeeper.lives);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
