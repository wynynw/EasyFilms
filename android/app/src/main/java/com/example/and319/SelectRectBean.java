package com.example.and319;
import android.graphics.Rect;

public class SelectRectBean {
    private Rect rect;

    private int row;

    private int column;

    private int seatState;

    /*the current state */
    private int realRow;
    /* current*/
    private int realColumn;

    public int getRealRow() {
        return realRow;
    }

    public void setRealRow(int realRow) {
        this.realRow = realRow;
    }

    public int getRealColumn() {
        return realColumn;
    }

    public void setRealColumn(int realColumn) {
        this.realColumn = realColumn;
    }

    public int getSeatState() {
        return seatState;
    }

    public void setSeatState(int seatState) {
        this.seatState = seatState;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
