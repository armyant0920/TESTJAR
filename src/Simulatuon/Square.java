package Simulatuon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * 矩形物件特性簡述.<p>
 * </p>
 * show 詳細說明第一行<br>
 * show 詳細說明第二行
 */

public class Square implements ActionListener {//extends Canvas

    float width;//寬
    float height;//高
    int lifetime;//計數器
    float interval;//計時器間隔
    int speed = 20;//移動速度
    int posX;//X座標
    int posY;//Y座標
    int middleX, middleY;
    Sample sample;
    JPanel panel;
    Color color;//矩形的顏色
    Timer timer;//此物件的計時器(注意!!不是unit的Timer,是Swing的)
    ActionListener listener;

   /* @Override
    public void print(Graphics g) {
        super.print(g);
        g.setColor(this.color);
        g.fillRect(0,0, (int) this.width, (int) this.height);

        //設定文字顏色
        g.setColor(Color.WHITE);

        g.drawString("remain:"+this.lifetime, this.middleX, this.middleY);

    }*/

    @Override
    public String toString() {
        return "Square{" +
                "width=" + width +
                ", height=" + height +
                ", lifetime=" + lifetime +
                ", posX=" + posX +
                ", posY=" + posY +
                ", color=" + color +
                '}';
    }

    public Square(Sample sample, JPanel panel) {

        //將產生此物件的JFrame的Listener設定物件
        this.listener = this;
        this.sample = sample;
        this.panel = panel;
        Random rnd = new Random();

        //設定初始位置
        this.posX = rnd.nextInt(panel.getWidth());//根據面板大小決定隨機X座標
        /*
        推測如果在執行過程中執行最大最小化,會連帶改動到panel的大小

         */
        this.posY = rnd.nextInt(panel.getHeight());//rnd.nextInt(480);
        //隨機寬
        this.width = rnd.nextInt(200) + 100;
        //隨機高
        this.height = rnd.nextInt(200) + 100;
        //測試直接設定畫布
//        setSize((int)this.width,(int)this.height);

        //初始化中心座標
        this.middleX = (int) ((posX + width) / 2);
        this.middleY = (int) ((posY + height) / 2);
        //隨機顏色
        this.color = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256), 128);
        //隨機計數,至少為10,每次間隔減少該值,歸零時將此物件刪除
        this.lifetime = rnd.nextInt(10) + 10;

        //設定計時器;

        this.timer = new Timer(100 + 100 * rnd.nextInt(5), listener);//反應間格
        timer.start();


    }

    public void stop() {
        timer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            refresh();
//            panel.repaint();
            sample.drawPanel.repaint();


        }
    }

    /**
     * 把更新狀態的功能獨立寫出來
     * 之後應該把這個類的變數改成private
     * <li>測試項目標籤
     * <br>所以先來設定一些外部可以調用的方法
     */
    public void refresh() {

        if (lifetime > 0) {
            lifetime--;//壽命處理
            int angle = new Random().nextInt(360);
            posX += speed * Math.cos(angle);

            posY += speed * Math.sin(angle);
            middleX = (int) ((posX + width) / 2);
            middleY = (int) ((posY + height) / 2);

        } else {
            stop();
        }

    }

    public void reset(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public void reset(int lifetime) {
        this.lifetime = lifetime;
    }
}
