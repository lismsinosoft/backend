package com.gfk.common.util;

import com.gfk.common.vo.LinearRegressionData;

import java.util.List;

/**
 * 线性回归的工具类
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class LinearRegression {

    private List<LinearRegressionData> dataList;

    private double alpha;
    private double beta;

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public LinearRegression(List<LinearRegressionData> dataList){
        this.dataList = dataList;
        this.calculate();
    }

    private void calculate(){
        int n = dataList.size();
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;

        for (LinearRegressionData data : dataList){
            sumX += data.getX();
            sumY += data.getY();
            sumXY += data.getX() * data.getY();
            sumXX += Math.pow(data.getX(), 2);
        }
        this.beta = (((sumY * sumX) / n) - sumXY) / (((sumX * sumX) / n) - sumXX);
        if (Double.isNaN(this.beta)){
            this.beta = 0.0;
        }
        this.alpha = (sumY - this.beta * sumX) / n;
        if (Double.isNaN(this.alpha)){
            this.alpha = 0.0;
        }
    }
}
