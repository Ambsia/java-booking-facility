package com.bookingsystem.helpers;


/**
 * Created by Alex on 14/08/2015
 */
public class Pair<Left,Right> {
    private Left leftItem;
    private Right rightItem;

    public Pair(Left leftItem, Right rightItem) {
        this.leftItem = leftItem;
        this.rightItem = rightItem;
    }

    @Override
    public int hashCode() {
        int result = leftItem != null ? leftItem.hashCode() : 0;
        result = 31 * result + (rightItem != null ? rightItem.hashCode() : 0);
        return result;
    }

    public Left getLeftItem() {
        return leftItem;
    }

    public void setLeftItem(Left leftItem) {
        this.leftItem = leftItem;
    }

    public Right getRightItem() {
        return rightItem;
    }

    public void setRightItem(Right rightItem) {
        this.rightItem = rightItem;
    }

    public static <L,R> Pair<L,R> of(L left, R right){
        return new Pair<L,R>(left, right);
    }
}
