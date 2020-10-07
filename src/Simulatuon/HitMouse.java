package Simulatuon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;


public class HitMouse extends JFrame implements ActionListener,MouseListener{
    boolean isOver=false;//設定標記，遊戲是否結束
    private String dir="D:/專案資料夾/JAVA/TESTJAR/out/artifacts/TESTJAR/ImageFile/";//圖片目錄，當前工程下

    //Kevin:考慮後覺得還是不要用final,目前彈性比穩定更重要
    private  static String GRADEBOARD_PIC_PATH="money.png";
    private  static String BEFORE_HIT="cat.png",AFTER_HIT="skull.png";
    private  static String MOUSE_IDLE="hand1.png",MOUSE_HIT="hand2.png";
    private  static String DEFAULT_BACKGROUND="bg.png";
    private  static int LABEL_WEIGHT=92,LABEL_HEIGHT=80;
    private final int DEFAULT_FRAME_HEIGHT=26;//Kevin:參考此篇https://www.itdaan.com/tw/57312abf3ae14f5674778815166e88e9
    private  static Dimension dimension =new Dimension(512,512);
    private static String backGround_path;
    private static boolean mouseAlive=false;
    JLabel jlbMouse;//地鼠
    Timer timer;//時間定時器
    Random random;//隨機數物件，即生成地鼠的位置
    int delay;//延遲時間
    final int DEFAULT_DELAY=1100;
    Toolkit tk;
    Image image;
    Cursor myCursor;
    JLabel showNum,currentGrade,hitNum;
    JMenuItem jItemPause;
    int showNumber=0,hitNumber=0,currentGrades=1;

    public HitMouse(){
        super("打地鼠");

        this.setIconImage(new ImageIcon(getJarPath(BEFORE_HIT)).getImage());
        backGround_path="bg.png";

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(dimension.width,dimension.height+DEFAULT_FRAME_HEIGHT);

//        this.setSize(449, 395);
        this.setLocationRelativeTo(null);//設定視窗在螢幕中心
        setbackground();//設定背景
        this.getContentPane().setLayout(null);//設定框架佈局模式為空，只有這樣，才能知道圖片的真正位置
        //設定滑鼠為錘子圖片
        tk = Toolkit.getDefaultToolkit();
        image = tk.createImage(getJarPath(MOUSE_IDLE) );
        Dimension dimension=tk.getBestCursorSize(HitMouse.dimension.width, HitMouse.dimension.height);

        System.out.printf("Best Cursor Size:%d/%d\n",dimension.width,dimension.height);
        myCursor = tk.createCustomCursor(image, new Point(0,0), "xxx");
        this.setCursor(myCursor);

        setMessage();//設定一些提示資訊
        //在背景圖片的基礎上設定地鼠圖片
//        ImageIcon imageMouse = new ImageIcon(dir+"cat.png");
        jlbMouse = new JLabel();//imageMouse
        jlbMouse.setSize(80,80);//Kevin:所以用這種方式抓滑鼠點擊事件,只能是矩形判斷?
        jlbMouse.setIcon(new ImageIcon(reSizeIconImage(jlbMouse,getJarPath(BEFORE_HIT))));
        this.getContentPane().add(jlbMouse);
        jlbMouse.setVisible(false);//Kevin:專案產生實例時,就產生了實際上唯一的一支地鼠,並設為不可見
        jlbMouse.addMouseListener(this);//為這個JLabel設定滑鼠事件監聽
        random = new Random();
        //定時器
//        timer = new Timer(DEFAULT_DELAY,this);
        ;
//        timer.start();//Kevin:不要程式一開啟就開始跑

        addMenu();//新增選單


        this.setResizable(true);//設定視窗大小不能改變,Kevin:先改為可變方便測試
        this.setVisible(true);
    }

    private void addMenu() {
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        JMenu game = new JMenu("遊戲");
        JMenuItem jitemNew = new JMenuItem("新遊戲");
        jitemNew.setActionCommand("new");
        jitemNew.addActionListener(this);
        JMenuItem jitemPause = new JMenuItem("暫停");
        jitemPause.setActionCommand("pause");
        jItemPause=jitemPause;
        jItemPause.setEnabled(false);//Kevin:遊戲有開始才能執行
        jitemPause.addActionListener(this);
        JMenuItem jitemExit = new JMenuItem("退出");
        jitemExit.setActionCommand("exit");
        jitemExit.addActionListener(this);
        game.add(jitemNew);
        game.add(jitemPause);
        game.addSeparator();//選單裡設定分隔線
        game.add(jitemExit);
        menubar.add(game);
        System.out.println("選單高度"+game.getComponent().getHeight());
    }

    private void setbackground() {
        ((JPanel)(this.getContentPane())).setOpaque(false);//如果為 true，則該元件繪製其邊界內的所有畫素。否則該元件可能不繪製部分或所有畫素，從而允許其底層畫素透視出來。

        ImageIcon bgImage = new ImageIcon(getJarPath("bg.png"));//Kevin:我匯入的背景是512*512

        JLabel bgLabel = new JLabel();//bgImage
//        bgLabel.setBounds(0, 25, bgImage.getIconWidth(), bgImage.getIconHeight());//Kevin:這一段也可以看出來,原本的程式碼完全是跟者圖片走的
        System.out.printf("background width:%d,height:%d\n",bgImage.getIconWidth(),bgImage.getIconWidth());
        bgLabel.setBounds(0,DEFAULT_FRAME_HEIGHT,dimension.width,dimension.height);
        System.out.printf("Frame size width:%d,height%d\n",this.getWidth(),this.getHeight());
        bgLabel.setIcon(new ImageIcon(reSizeIconImage(bgLabel,getJarPath(DEFAULT_BACKGROUND))));

        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));//設定背景圖片的層次最低

    }

    private void setMessage() {
//        ImageIcon showNumb = new ImageIcon(dir+ "money.png");
        //Kevin:JLabel預設好像只有水平的擺放方式,所以同時有圖片和文字時,只能決定左右,不知道能不能自訂一個子類,自訂讓圖片在後,圖在前?估計要改寫paint方法
        JLabel showLabel = new JLabel("出現次數",new ImageIcon(reSizeIconImage(LABEL_WEIGHT,LABEL_HEIGHT,getJarPath(GRADEBOARD_PIC_PATH))),SwingConstants.LEADING);//showNumb
        showLabel.setBounds(8, 8, LABEL_WEIGHT, LABEL_HEIGHT);
        showLabel.setVerticalTextPosition(JLabel.CENTER);
        showLabel.setHorizontalTextPosition(JLabel.CENTER);
//        showLabel.setIcon(new ImageIcon(reSizeIconImage(showLabel,dir+GRADEBOARD_PIC_PATH)));
        this.getContentPane().add(showLabel);
        showNum = new JLabel("0");

        showNum.setBounds(110, 8, LABEL_WEIGHT, LABEL_HEIGHT);
        this.getContentPane().add(showNum);

//        ImageIcon hitNumb = new ImageIcon(dir+ "money.png");
//        Image tempImage=hitNumb.getImage();

        JLabel hitLabel = new JLabel();//hitNumb
        hitLabel.setBounds(148, 8, LABEL_WEIGHT, LABEL_HEIGHT);
//        Image resizeImage=tempImage.getScaledInstance(hitLabel.getWidth(),hitLabel.getHeight(),Image.SCALE_SMOOTH);
        hitLabel.setText("打中");
        hitLabel.setIcon(new ImageIcon(reSizeIconImage(hitLabel,getJarPath(GRADEBOARD_PIC_PATH))));
        this.getContentPane().add(hitLabel);
        hitNum = new JLabel("0");
        hitNum.setBounds(251, 8, LABEL_WEIGHT, LABEL_HEIGHT);
        this.getContentPane().add(hitNum);

//        ImageIcon grade = new ImageIcon(dir+ "money.png");
        JLabel gradeLabel = new JLabel();//grade
        gradeLabel.setBounds(288, 8, LABEL_WEIGHT, LABEL_HEIGHT);
        gradeLabel.setIcon(new ImageIcon(reSizeIconImage(gradeLabel,getJarPath(GRADEBOARD_PIC_PATH))));
        this.getContentPane().add(gradeLabel);
        currentGrade = new JLabel("1");//Kevin:酷喔,又硬編碼了
        currentGrade.setBounds(391, 8, LABEL_WEIGHT, LABEL_HEIGHT);
        this.getContentPane().add(currentGrade);
    }


    public static void main(String[] args) {
        new HitMouse();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand()+e.getSource());
        //對選單項註冊事件監聽
        if(e.getSource() instanceof JMenuItem){
            menuItemFun(e);
        }

        //int ran=random.nextInt(9);//隨機生成一個0~9（不包括9）的隨機數

//        ImageIcon imageMouse = new ImageIcon(dir+BEFORE_HIT);
//        jlbMouse.setIcon(new ImageIcon(imageMouse.getImage().getScaledInstance(jlbMouse.getWidth(),jlbMouse.getHeight(),Image.SCALE_SMOOTH)));

        if(e.getSource() instanceof Timer) {//Kevin:怎麼不寫Timer判斷?
            //保證每次隨機生成的地鼠圖片都是為沒被打時的圖片

            jlbMouse.setIcon(new ImageIcon(reSizeIconImage(jlbMouse, getJarPath( BEFORE_HIT))));

            //位置還沒抓準
            jlbMouse.setLocation(random.nextInt(dimension.width-80),80+random.nextInt(dimension.height-160));//預設上方元件佔Y軸座標80,又考量到標的JLabel長寬80,視窗暫定大小450*500
            mouseAlive=true;
            jlbMouse.setVisible(true);//Timer事件觸發時,地鼠變成可見


            System.out.printf("Target location x:%d,y:%d\n,delay:%d",jlbMouse.getLocation().x,jlbMouse.getLocation().y,delay);
            showNumber++  ;//Kevin:總出現次數增加
            showNum.setText(""+showNumber);//Kevin:顯示到面板上
        }//random.nextInt(340) + 80
//        jlbMouse.setIcon(new ImageIcon(reSizeIconImage(jlbMouse,BEFORE_HIT)));
        /*switch(ran){
            //這邊位置的設定大概是根據原本的圖做的,所以寫得很死,比較好的做法,應該連窟窿都弄成物件

            case 0:jlbMouse.setLocation(55, 63);break;
            case 1:jlbMouse.setLocation(321, 204);break;
            case 2:jlbMouse.setLocation(184, 204);break;
            case 3:jlbMouse.setLocation(47, 203);break;
            case 4:jlbMouse.setLocation(297, 133);break;
            case 5:jlbMouse.setLocation(161, 133);break;
            case 6:jlbMouse.setLocation(21, 133);break;
            case 7:jlbMouse.setLocation(310, 63);break;
            case 8:jlbMouse.setLocation(185, 63);break;
        }*/




        if( !gamePlan() ){//判斷遊戲是否結束，並顯示遊戲程序
            //Kevin:原作者把判斷升級也寫在這
            timer.stop();
        }

    }
    //監聽選單功能功能
    private void menuItemFun(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("new")) {//新遊戲
            if(timer!=null)timer.stop();
            showNumber=0;

            hitNumber=0;
            currentGrades=1;
            delay=DEFAULT_DELAY;
            isOver=false;
            showNum.setText("" +showNumber);
            hitNum.setText("" +hitNumber);
            currentGrade.setText(""+ currentGrades);
            timer = new Timer(delay,this);
            jItemPause.setEnabled(true);
            setjItemPause(false);
        }
        if (e.getActionCommand().equalsIgnoreCase("exit")) {//退出
            System.exit(EXIT_ON_CLOSE);
        }

        if (e.getActionCommand().equalsIgnoreCase("pause")) {//暫停
            setjItemPause(true);


        }
        if(e.getActionCommand().equalsIgnoreCase("unpause")){
            setjItemPause(false);

        }
    }

    /**
     *
     * @param b means game pause=T/F
     */
    private void setjItemPause(boolean b){
        if(b){
            jItemPause.setText("繼續");
            jItemPause.setActionCommand("unpause");
            timer.stop();

            JOptionPane.showMessageDialog(this, "繼續請按“確定”");//Kevin:提示窗會讓整個程式被鎖定,無法進行其他操作


        }else{
            jItemPause.setText("暫停");
            jItemPause.setActionCommand("pause");
            timer.start();

        }


    }

    /**
     * 看起來應該是玩家有8次以上沒打中就遊戲結束
     */

    private boolean gamePlan() {

        if(showNumber-hitNumber > 8){
            JOptionPane.showMessageDialog(this, "Game Over !");
            isOver=true;
            return false;
        }
        //應該是打中>5隻會提升當前遊戲等級,delay減少地鼠出現變快,最小降低到0.1秒就出現1隻,所以後面等級數字沒意義
        if(hitNumber > 5){
            //Kevin:應該要重啟timer,才不會有奇怪的出現間隔落差
            hitNumber=0;
            showNumber=0;
            currentGrades++;
            if(delay>100){
                delay-=50;
            }
            timer.setDelay(delay);
            timer.restart();

            hitNum.setText(""+ hitNumber);
            showNum.setText("" +showNumber);
            currentGrade.setText("" +currentGrades);
            jlbMouse.setVisible(false);

        }
        return true;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if(isOver){
            return ;
        }
        image = tk.createImage(getJarPath(MOUSE_HIT));
        myCursor = tk.createCustomCursor(image, new Point(0,0), "xxx");

        this.setCursor(myCursor);//滑鼠按下時，滑鼠顯示打下去的圖片，模擬打的動作
        //如果打中地鼠，則地鼠換成被打中的圖片，模擬地鼠被打
        //Kevin:地鼠如果被打,應該停止此事件被重複執行,否則在下一次Timer改變地鼠位置前,可以瘋狂點滑鼠快速累積hit數
        if(e.getSource()==jlbMouse && timer.isRunning() && mouseAlive==true ){

//            ImageIcon imageIconHit = new ImageIcon(dir+"hand2.png");
            jlbMouse.setIcon(new ImageIcon(reSizeIconImage(jlbMouse,getJarPath(AFTER_HIT))));
//            jlbMouse.setVisible(false);
            hitNumber++ ;
            tk.beep();//Kevin:發出噪音
            mouseAlive=false;
            hitNum.setText(""+hitNumber);

        }



    }

    public void mouseReleased(MouseEvent e) {
        if(isOver){
            return ;
        }
        //當滑鼠放鬆以後，滑鼠變回原來沒按下時的圖片
        image = tk.createImage(getJarPath(MOUSE_IDLE));
        myCursor = tk.createCustomCursor(image, new Point(0,0), "xxx");
        this.setCursor(myCursor);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     *
     * @param jc   要塞進去的JComponent
     * @param path 將預計要轉換的圖片路徑作為參數傳入
     * @return     回傳{@code Image}物件
     */
    public Image reSizeIconImage(JComponent jc,String path){

        ImageIcon icon = new ImageIcon(getJarPath(path));
        Image tempImage=icon.getImage();
        return tempImage.getScaledInstance(jc.getWidth(),jc.getHeight(),Image.SCALE_SMOOTH);

    }
    public Image reSizeIconImage(JComponent jc,java.net.URL path){

        ImageIcon icon = new ImageIcon(path);
        Image tempImage=icon.getImage();
        return tempImage.getScaledInstance(jc.getWidth(),jc.getHeight(),Image.SCALE_SMOOTH);

    }




    public Image reSizeIconImage(int width,int height,String path){

        ImageIcon icon = new ImageIcon(path);
        Image tempImage=icon.getImage();
        return tempImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);

    }
    public Image reSizeIconImage(int width,int height,java.net.URL path){

        ImageIcon icon = new ImageIcon(path);
        Image tempImage=icon.getImage();
        return tempImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);

    }

    public java.net.URL getJarPath(String file){
        return HitMouse.class.getResource("/ImageFile/"+file);

    }



}