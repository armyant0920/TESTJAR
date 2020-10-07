package Simulatuon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this is description
 */
public class Basic extends JFrame implements ActionListener {
    private JButton jbt;
    private int vx, vy;
    private Timer timer;
    private int width, height;


    public static void main(String args[]) {
        Basic test = new Basic();



    }


    public Basic() {
        vx = 3;                        //按鍵移動的距離
        vy = 3;
        Container c = getContentPane();
        c.setLayout(null);           //取消layout manager
        jbt = new JButton("開始");     //建立一個JButton
        jbt.addActionListener(this); //設定ActionListener
        c.add(jbt);                  //將JButton放進JFrame
        jbt.setBounds(0, 0, 60, 40);    //設定位置及大小
        setResizable(false);         //不允許使用者調整JFrame的大小
        setSize(300, 200);
        setVisible(true);
        width = c.getWidth();          //取得content pane的寬度
        height = c.getHeight();        //取得content pane的高度
        timer = new Timer(50, this);   //建立一個Timer元件,設定為反應間隔
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbt) {   //產生事件的是JButton
            if (jbt.getText() == "開始") {
                jbt.setText("停止");
                timer.start();          //啟動Timer
            } else {
                jbt.setText("開始");
                timer.stop();           //停止Timer
            }
        } else {  //產生事件的是Timer
            //移動JButton
            jbt.setLocation(jbt.getX() + vx, jbt.getY() + vy);
            //碰到左右邊界的處理
            if (jbt.getX() <= 0 || jbt.getX() + jbt.getWidth() >= width)
                vx = -vx;
            //碰到上下邊界的處理
            if (jbt.getY() <= 0 || jbt.getY() + jbt.getHeight() >= height)
                vy = -vy;
        }
    }
}


