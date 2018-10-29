package cn.ted.shoot;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class World extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 680;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private  int state = START;				//当前状态（默认值）
	
	private static BufferedImage start;			//启动图
	private static BufferedImage pause;			
	private static BufferedImage game_over;
	
	static{
		start = FlyingObject.loadImages("start.png");
		pause = FlyingObject.loadImages("pause.png");
		game_over = FlyingObject.loadImages("gameover.png");
	}
	
	int score = 0;
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enem = {};		
	private Bullet[] bu = {};				
	
	//创建敌人对象（小敌机、大敌机、小蜜蜂）
	public FlyingObject nextOne(){
		//随机生成小 0~19之间的整数
		Random r = new Random();
		int type = r.nextInt(20);
		if (type < 6){
			return new Bee();
		}else if(type < 12){
			return new Airplane();
		}else{
			return new BigAirplane();
		}
	}
	
	int enterIndex = 0;
	public void enterAction(){
		enterIndex++;
		if (enterIndex%30 == 0){
			FlyingObject obj = nextOne();
			enem = Arrays.copyOf(enem, enem.length + 1);
			enem[enem.length - 1] = obj;			//将最后一个值赋值给 obj
		}
	}
	
	int shootIndex = 0;
	public void shootAction(){
		shootIndex ++;				//每十个毫秒增 1
		if (shootIndex%30 == 0){		
			System.out.println("wenxue");
			Bullet[] t = hero.shoot();				//获取子弹对象
			bu = Arrays.copyOf(bu, bu.length + t.length );
			System.arraycopy(t,	0,	bu,	bu.length - t.length,	t.length);
		}
	}
	
		public void paint(Graphics g){
				sky.paintObject(g);		//画天空对象
				hero.paintObject(g);		//画英雄机对象
				System.out.println();
				for (int i=0; i<enem.length; i++){
					enem[i].paintObject(g);			//画敌机对象
				}
				for (int j=0; j<bu.length; j++){
					bu[j].paintObject(g);
				}
				g.drawString("SCORE" + score, 10, 20);				//画分 
				g.drawString("LIFE" + hero.getLife(), 10, 50);			//画命
				switch (state) {
				case START:
					g.drawImage(start, 0, 0, null );
					break;
				case PAUSE:
					g.drawImage(pause, 0, 0, null);
					break;
				case GAME_OVER:
					g.drawImage(game_over, 0, 0, null);
					break;
				}
		}
		
	public static void main(String[] args) {	
		//创建一个方框对象
		JFrame frame = new JFrame();	
		//创建一个内容对象
		World world = new World();
		//方框加载内容
		frame.add(world);
		//设置方框关闭操作
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置方框尺寸
		frame.setSize(WIDTH, HEIGHT);
		//设置窗口居中显示
		frame.setLocationRelativeTo(null); 
		//frame.setUndecorated(true);		//设置窗口不需要装饰
		frame.setVisible(true); 		//设置窗口可见		2）尽快画对象
		
		world.action();
	}
	
	public void stepAction(){				//10 毫秒走一次
		sky.step();
		for (int i=0; i<enem.length; i++){
			enem[i].step();
		}
		for (int j=0; j<bu.length; j++){
			bu[j].step();
		}
	}
		
	//删除越界的飞行物
	public void outOfBounds(){
		//遍历敌人数组，将不越界的敌人保存下来
		int index = 0;			//不越界敌人个数
		FlyingObject[] en = new FlyingObject[enem.length];			//不越界的敌人
		for (int i=0; i<enem.length; i++){
			FlyingObject f = enem[i];		//获取每一个敌人
			System.out.println("蜜蜂的个数：" + i);
			if (!f.outOfBounds() && !f.isRemove()){				//不越界
				en[index] = f;					//
				index ++;							//不越界数组下标 +1
				System.out.println("蜜蜂的个数：" + index);
			}
		}
		en = Arrays.copyOf(en, index);
		index = 0;
		
		int bullet = 0;
		Bullet[] be = new Bullet[bu.length];			//不越界的子弹个数
		for (int j=0; j<bu.length; j++){				//不越界的子弹
			Bullet t = bu[j];
			if (t.outOfBounds() && ! t.isRemove()){
				be[bullet] = t;
				bullet ++;
			}
		}
		be = Arrays.copyOf(be, bullet);
		bullet = 0;
	}
	
	public void heroBangAction(){
		for (int i=0; i<enem.length; i++){
			FlyingObject f = enem[i];
			if (f.isLife() && f.hit(hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearFile();
				/*if (hero.getLife() == 0){
					hero.goDead();
				}*/                                                                                                                                                                                                                                           
			}
		}		
	}
	
	public void checkGameOverAction(){
		if(hero.getLife() <= 0){
			state = GAME_OVER;
		}
	}
	
	public void bulletBangAction() {		
		for (int i=0; i<bu.length; i++){
			Bullet b = bu[i];				//获取每颗子弹
			for (int j=0; j<enem.length; j++){
				FlyingObject f = enem[j];				//获取每个敌人
				if (b.isLife() && f.isLife() && f.hit(b)){
					b.goDead();			//子弹去死
					f.goDead();			//敌人去死
					if (f instanceof Emeny){
						 Emeny e = (Emeny) f;			//将 f 强制转换为 敌人类型
						score += e.getScore();
					}
					if (f instanceof award){
						award a = (award) f;
						int type = a.getType();
						switch (type) {
						case award.DOUB_FILE:
							hero.addDoubLife();
							break;
						case award.LIFE:
							hero.addLife();
						default:
							System.out.println("null");
							break;
						}
					}
				}
			}
		}
	}

	public void action(){		
		MouseAdapter mo = new MouseAdapter(){
					//重写mouseMoved() 方法
			public void mouseMoved(MouseEvent m){
					if (state == RUNNING){
						int x = m.getX();
						int y = m.getY();
						hero.moveTo(x, y);	
					}
			}
			
			public void mouseClicked(MouseEvent m){
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					sky = new Sky();
					hero = new Hero();
					enem = new FlyingObject[0];
					bu = new Bullet[0];
					state = START;
				}
			}
		
			public void mouseExited(MouseEvent m){
				if (state == RUNNING){			//运行状态
					state = PAUSE;						//修改状态为暂停
				}
			}
			
			public void mouseEntered(MouseEvent m){
				if (state == PAUSE){					//暂停状态
					state = RUNNING;				//修改为运行
				}
			}
		};
		this.addMouseListener(mo);
		this.addMouseMotionListener(mo);
		//创建定时器对象
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {	
				if (state == RUNNING){		//运行状态
					enterAction();		//敌人
					stepAction();		//飞行物移动
					shootAction();		//子弹入场
					outOfBounds();			//删除越界的飞行物
					bulletBangAction();		//子弹碰撞
					heroBangAction();			//英雄机碰撞
					
					checkGameOverAction();
				}
				repaint();				//重画
				// System.out.println("Alber·Holmes");
			}
		}, 10, 10);
		
	}

}
