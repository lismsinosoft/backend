package com.gfk.common.vo;

/**
 * 线性回归对象
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class LinearRegressionData {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public LinearRegressionData(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
