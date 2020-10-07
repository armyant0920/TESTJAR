package Simulatuon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 這個類應作為所有幾何圖形共同屬性與方法的模板,
 * 需經過仔細評估測試後再拿給其他圖形繼承
 * 順便熟悉一下抽象類的運用
 */
public abstract class Shape extends  JComponent implements ActionListener {
    //實做ActionListener介面的做法好不好還有待觀察
    private int width;//寬
    private int height;//高
    private int posX;//當前X座標
    private int posY;//當前Y座標
    private int lifeTime;//剩餘生命點
    private int interval;//計算間隔
    private int speed;//移動速度
    private double angle;//目前運度角度
    private Color color;//物件顏色
    private Timer timer;//Swing的計時器

    @Override
    public String toString() {
        return "Shape{" +
                "width=" + width +
                ", height=" + height +
                ", posX=" + posX +
                ", posY=" + posY +
                ", lifeTime=" + lifeTime +
                ", interval=" + interval +
                ", speed=" + speed +
                ", angle=" + angle +
                ", color=" + color +
                ", timer=" + timer +
                '}';
    }

    /**
     * 構造器,原則上如果給繼承,應該用處不大
     */
    public Shape(){


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * 提供外部重設的方法,原則上應該也可以多型,根據狀況傳入不同數量及類型的參數
     * 比如只傳入兩個int就是指改動長寬..
     */
     void reset(){


    }
}
