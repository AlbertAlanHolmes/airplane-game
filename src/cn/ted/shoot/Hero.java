package cn.ted.shoot;
import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	private int life;			
	private int doubFile;
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[6];
		for (int i=0; i<images.length; i++){
			images[i] = loadImages("hero" + i + ".png");
		}
	}
	public Hero(){		
		super(97, 124, 140, 480);
		/*super.width = 97;			
		super.height = 124;
		super.x = 140;
		super.y = 400;	*/
		this.life = 3;				
		this.doubFile = 1;
	}
	public void step(){
	}
	public int getLife(){
		return life;
	}
	public int addLife(){
		return life ++;
	}
	public void subtractLife(){
		//声明 --
		life --;
	}
	//清空英雄机的火力值
	public void clearFile(){
		doubFile = 0;
	}
	
	public void moveTo(int x, int y){
		this.x = x - this.width/2;			//英雄机的 x 坐标
		this.y = y - this.height/2;			//英雄机的 y 坐标
		System.out.println("英雄机移动了！");
	}
	
	int index = 0;				//活着的下标
	int deadIndex = 2;		//死了的下标
	
	//重写getImages()方法			
	public BufferedImage getImages() {
		if (isLife()){
			return images[index++ %2];
		}else if(isDead()){
			//图片10ms 更换一张
			BufferedImage img = images[deadIndex++];
			if (deadIndex == images.length){
				//修改当前状态删除	
				state = REMOVE;
			}
			return img;
			//10ms 		
		}
		return null;
	}
	
	public Bullet[] shoot(){
		int xStep = this.width / 4;
		int yStep = 20;
		if(doubFile > 0){
			Bullet[] u = new Bullet[2];
			u[0] = new Bullet(this.x + xStep*1, this.y - yStep);
			u[1] = new Bullet(this.x + xStep*3, this.y - yStep);
			doubFile -= 2;
			return u;
		}else {
			Bullet[] e = new Bullet[1];
			e[0] = new Bullet(this.x + xStep*2, this.y - yStep);
			return e;
		}
	}
	/*//英雄机发射子弹
	public Bullet[] shoot(){
		int xStep= this.width / 4;		//英雄机的宽度的 1/4
		int yStep = 20;		//固定的20
		if (doubFile > 0){		//双倍
			Bullet[] u = new Bullet[2];
			u[0]  = new Bullet(this.x + 1*xStep, this.y - yStep);
			u[1]  = new Bullet(this.x + 3*xStep, this.y - yStep);
			doubFile -= 2;					//发射一次双倍火力，减2
			return u;
		}else {						//单倍
			 Bullet[] e = new Bullet[1];
			 e[0]  = new Bullet(this.x + 2*xStep, this.y - yStep);
			 return e;
		}
	}*/
	
	//判断outOfBounds越界
	public boolean outOfBounds(){
		return false;
	}
	public void addDoubLife(){
		doubFile += 50;
	}
	
}
