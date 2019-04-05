package com.wisekrakr.androidmain.components;

import com.badlogic.gdx.math.Vector2;

public class RowComponent {

    private Vector2 position;
    private int numberOfBricks;
    private EntityColor rowColor;

    public RowComponent(Vector2 position, int numberOfBricks, EntityColor rowColor) {
        this.position = position;
        this.numberOfBricks = numberOfBricks;

        this.rowColor = rowColor;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getNumberOfBricks() {
        return numberOfBricks;
    }

    public void setNumberOfBricks(int numberOfBricks) {
        this.numberOfBricks = numberOfBricks;
    }

    public EntityColor getRowColor() {
        return rowColor;
    }

    public void setRowColor(EntityColor rowColor) {
        this.rowColor = rowColor;
    }

    public RowColorContext getRowColorContext() {
        return rowColorContext;
    }

    private RowColorContext rowColorContext = new RowColorContext() {
        @Override
        public EntityColor getRowColor() {
            return rowColor;
        }

        @Override
        public void setRowColor(EntityColor color) {
            rowColor = color;
        }
    };

    public void reset(){
        position = new Vector2();
        numberOfBricks =  0;
        rowColor = null;
    }
}
