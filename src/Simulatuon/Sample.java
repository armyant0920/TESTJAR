package Simulatuon;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 初始化面板<p>
 * 1.設定操作按鍵<br>
 * 2.點擊新增按鍵後,新增物件到Panel上<br>
 */

public class Sample implements ActionListener {
    static class myCanvas extends Canvas {
        public myCanvas() {
            setSize(Sample.WIDTH, Sample.HEIGHT);

        }

        public void paint(Graphics g) {
            g.setColor(Color.RED);;
            g.fillRect(0, 0, 200, 200);
        }
    }


    private static final int WIDTH = 480;//控制面板寬度
    private static final int HEIGHT = 480;//控制面板高度
    private JButton btn;
    private JFrame frame;
    JPanel drawPanel, controlPanel,buttonPanel;
//    private Timer timer;
    private JComboBox box;//下拉選單容器
    private static final String shapes[] = {"Rect", "Circle"};
    private static int shape=0;//預設值
    private static final int HINT_TEXT_SIZE = 20;
    private JLabel label;
    //所有產生物件透過此變數操作,目前型別先固定,未來應該放父類shapes
    private ArrayList<Square> items = new ArrayList<>();
    private Square square;
    private ItemListener itemListener;



    public Sample() {
        //置中可從Swing常數取得,



        //建立視窗並命名
        frame = new JFrame("SAMPLE");

        //取得容器
        Container container = frame.getContentPane();
        container.setBackground(Color.black);
        //設置面板
        drawPanel = new JPanel() {

            //覆寫paint
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                /*for (Square s : items) {


                        g.setColor(s.color);
                        g.fillRect(s.posX, s.posY, (int) s.width, (int) s.height);

                        //設定文字顏色
                        g.setColor(Color.WHITE);
                        g.setFont(cat("华文彩云", Font.BOLD, 30));
                        g.drawString("remain:" + s.lifetime, s.middleX, s.middleY);

                    if (s!=null && s.lifetime>0) {


                    }else{

                        s.stop();

                        System.out.println("移除了"+s.toString());
                        items.remove(s);
                        System.out.printf("目前還有%d個item",items.size());
                        s=null;


                    }

                }*/
                if (items.isEmpty()) {
                    System.out.println("item is empty");
//                    g.clearRect(0,0,WIDTH,HEIGHT);
                }
                for (int i = 0; i < items.size(); i++) {
                    g.setColor(items.get(i).color);
                    switch (shape){
                        case 0:g.fillRect(items.get(i).posX, items.get(i).posY, (int) items.get(i).width, (int) items.get(i).height);
                        break;
                        case 1:g.fillOval(items.get(i).posX, items.get(i).posY, (int) items.get(i).width, (int) items.get(i).height);
                        break;

                    }

                    //設定文字顏色
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("???", Font.BOLD, 30));
                    g.drawString("remain:" + items.get(i).lifetime, items.get(i).middleX, items.get(i).middleY);
                    if (items.get(i) != null && items.get(i).lifetime > 0) {

                    } else {

                        System.out.println("移除了" + items.get(i).toString());
                        items.remove(items.get(i));
                        i--;
                        System.out.printf("目前還有%d個item\n", items.size());

                    }
                }
            }
        };
        //設定面板初始背景為灰色
        drawPanel.setBackground(Color.gray);
        //設定面板大小
//        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        buttonPanel =new JPanel();
        buttonPanel.setSize(new Dimension(480,200));
        buttonPanel.setLayout(new GridLayout(0,2));

        controlPanel = new JPanel();

//        controlPanel.setLayout(null);


        //設定格狀排版
//        controlPanel.setLayout(new GridLayout(1,2));

        //將預設選項加入下拉清單
        box = new JComboBox(shapes);
        box.setBounds(20,400,200,100);

        itemListener = new

                ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (ItemEvent.SELECTED == e.getStateChange()) {

                            String selectedItem = e.getItem().toString();
                            label.setText(selectedItem);

                            System.out.printf("new selected item : %s%n", selectedItem);
                            if(selectedItem==shapes[0]){shape=0;}else if(selectedItem==shapes[1]){shape=1;}else if(selectedItem==shapes[2]){shape=2;}
                        }
                        if (ItemEvent.DESELECTED == e.getStateChange()) {
                            String selectedItem = e.getItem().toString();
                            System.out.printf("deselected item : %s%n", selectedItem);
                        }

                    }
                }

        ;
        box.addItemListener(itemListener);

        controlPanel.add(box);
        JMenuBar menuBar=new JMenuBar();
        menuBar.setName("我叫選單爸");

        MenuListener menuListener=new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if(e.getSource() instanceof JMenu){
                    JMenu menu=(JMenu)e.getSource();
                    System.out.println(menu.getName()+"已選取");
                }


            }

            @Override
            public void menuDeselected(MenuEvent e) {
                if(e.getSource() instanceof JMenu){
                    JMenu menu=(JMenu)e.getSource();
                    System.out.println(menu.getName()+"已取消選取");
                }
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                if(e.getSource() instanceof JMenu){
                    JMenu menu=(JMenu)e.getSource();
                    System.out.println(menu.getName()+"已取消");
                }

            }
        };
        JMenu menuA=new JMenu("測試選單A");
        menuA.setName("我叫選單A");
        menuA.addActionListener(this);//看不出實際作用
        menuA.addMenuListener(menuListener);
        for(int i=0;i<3;i++){
            JMenuItem menuItem=new JMenuItem("A選單項目");
            menuItem.setName("item"+i);
            menuItem.addActionListener(this);
            menuA.add(menuItem);
            menuA.addSeparator();//添加分隔線
        }

//        JMenuItem menuItem1=new JMenuItem("選單項目1",new ImageIcon("mark.png"));

        JMenu menuB=new JMenu("測試選單B");
        menuB.setDelay(5000);
//        menuB.setInheritsPopupMenu(true);
        menuB.setName("我叫選單B");
        menuB.addMenuListener(menuListener);
        for(int i=0;i<4;i++){
            JMenuItem menuItem=new JMenuItem("B選單項目");
            menuItem.setName("item"+i);
            menuItem.addActionListener(this);
            menuB.add(menuItem);
            menuB.addSeparator();
        }
        menuBar.add(menuA);
        menuBar.add(menuB);
        buttonPanel.add(menuBar);







        //設定按鈕
        btn = new

                JButton("加入物件");

        btn.setForeground(Color.RED);
        btn.setIcon(new ImageIcon("src/ImageFile/plusicon.png"));
        btn.setBounds(320,400,200,100);

        btn.addActionListener(this);
        //把按鈕加入面板
        controlPanel.add(btn);
        buttonPanel.add(controlPanel);

        //加入文字區域
        JTextArea jTextArea=new JTextArea("this is TextArea");

        //把面板加入視窗
        //繪圖面板放中央
        frame.add(drawPanel, BorderLayout.CENTER);
        //控制面板放底部
        frame.add(buttonPanel, BorderLayout.SOUTH);
//        buttonPanel.add(new JLabel("test1",SwingConstants.CENTER));
//        buttonPanel.add(new JLabel("test2",SwingConstants.CENTER));
//        buttonPanel.add(new JLabel("test3",SwingConstants.CENTER));
//        buttonPanel.add(new JLabel("test4",SwingConstants.CENTER));
//        buttonPanel.add(new JLabel("test5",SwingConstants.CENTER));
//        buttonPanel.add(new JLabel("test6",SwingConstants.CENTER));

        //把測試的Canvas也加進來
//        frame.add(new myCanvas());
        //加入label
        label   = new JLabel("JAVA介面練習",new ImageIcon("src/ImageFile/KevinDragon.png"),JLabel.CENTER
        );
        label.setToolTipText("文字提示");
        label.setFont(new Font("標楷體", Font.BOLD, 20));

        //label預設背景是透明的,必須先改為true->不透明
        label.setOpaque(true);
        //字體顏色可以直接設定
        label.setForeground(Color.BLUE);
        label.setBackground(Color.ORANGE);

        frame.add(label, BorderLayout.NORTH);


//        timer=new Timer(100,this);
        frame.setIconImage(new

                ImageIcon("src/ImageFile/KevinDragon.png").

                getImage());
//        frame.setSize(WIDTH,HEIGHT);
//        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//視窗最大化狀態
        /*或者直接抓大小
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocation(0,0);
         */
        /*
         或者像這樣直接設定環境
         GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
         */
        frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));//設定最小視窗大小


//        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //採用DISPOSE_ON_CLOSE可以確定關閉終端機
//        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame, "確定要結束程式嗎?",
                        "確認訊息",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                System.out.println(e.getNewState());
                if (e.getNewState() == 1 || e.getNewState() == 7) {
                    System.out.println("視窗最小化");

                } else if (e.getNewState() == 0) {
                    System.out.println("回復初始狀態");
                } else if (e.getNewState() == 6) {
                    System.out.println("視窗最大化");
                }

            }
        });
        //設定滑鼠監聽事件
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("滑鼠已點擊");

            }

            @Override
            public void mousePressed(MouseEvent e) {

                System.out.printf("滑鼠已按下%s按鍵,位置在(%d,%d),絕對位置在(%d,%d)",e.getButton(),e.getX(),e.getY(),e.getXOnScreen(),e.getYOnScreen());

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("滑鼠已放開");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("滑鼠已進入");

            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("滑鼠已離開");

            }

        });
        //滑鼠滾輪事件
        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                System.out.println(e.getWheelRotation()==1?"向下滾動":"向上滾動");
            }
        });

        //設定視窗的相對位置,從說明文字可以看出,如果沒有相對目標,就是全螢幕中心
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
     /*   if (e.getSource() instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) e.getSource();
            String fontName = comboBox.getSelectedItem().toString();
            label.setText(fontName);
            label.setFont(new Font("細明體", Font.BOLD, 20));
            System.out.printf("%s%n", "actionPerformed called");
        }*/
        if(e.getSource() instanceof  JMenu){
            JMenu menu=(JMenu)e.getSource();
            System.out.println(menu.getName()+ "is clicked");

        }
        /**
         * 此段設定按鈕物件的對應事件
         */
        if(e.getSource() instanceof JMenuItem){

            JMenuItem menuItem=(JMenuItem)e.getSource();

            String hintText=menuItem.getText()+ menuItem.getName();
            System.out.println(hintText+" is clicked");

        }

        if (e.getSource() == btn) {//如果事件觸發來源是加入按鈕
            System.out.printf("按下了%s按鈕\n", btn.getText());
            if (btn.getText() == "加入物件") {
                square = new Square(this, drawPanel);//, this
//                frame.add(square);

                System.out.println("矩形資訊" + square.toString());
                items.add(square);
              /*  for (Square s : items) {
                    System.out.println(s.toString());

                }*/

//                timer = square.timer;//把物件的timer拿來用看看?

           /* }else{
                square=null;
                panel.repaint();
            }*/
            }

        }

//        drawPanel.repaint();
        System.out.printf("actionPerformed called:%s,事件時間:%tT\n",e.getActionCommand(),e.getWhen());


//            }else{
//            System.out.println(String.valueOf(e.getSource())+e.getClass());
    }

    /**
     *
     */
    private void addItem() {


    }


}
        /*
        JFrame frame = new JFrame("利用JFrame建立視窗"); // 建立指定標題的JFrame視窗物件
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 關閉按鈕的動作為退出視窗
        frame.setSize(400, 300);                          // 設定視窗大小
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 獲得顯示器大小物件
        Dimension frameSize = frame.getSize();             // 獲得視窗大小物件
        if (frameSize.width > displaySize.width)
        frameSize.width = displaySize.width;           // 視窗的寬度不能大於顯示器的寬度

        frame.setLocation((displaySize.width - frameSize.width) / 2,
        (displaySize.height - frameSize.height) / 2); // 設定視窗居中顯示器顯示
        frame.setVisible(true);                          // 設定視窗為可見的,預設為不可見
         */




