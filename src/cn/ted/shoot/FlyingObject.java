package cn.ted.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.Graphics;

public abstract class FlyingObject {
	protected int width;		
	protected int height;	
	protected int x;		
	protected int y;			
	
	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	protected int state = LIFE;    		//当前状态
	
	//构造方法适用对象：Sky、Hero、bullet
	public FlyingObject(int width, int height, int x, int y){	
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	//构造方法适用对象：小敌机、大敌机、小蜜峰
	
	public FlyingObject(int width, int height){
		this.width = width;
		this.height = height;
		Random ran = new Random();	
		x = ran.nextInt(World.WIDTH - this.width);
		y = -this.height;
	}
	

	//判断活着
	public boolean isLife(){
		return state==LIFE;
	}
	//判断死了
	public boolean isDead(){
		return state==DEAD;
	}
	//判断删除
	public boolean isRemove(){
		return state==REMOVE;
	}
	
	public abstract boolean outOfBounds();
	
	/**检查敌人与子弹和英雄机的碰撞      this: 敌人   ob: 英雄机和子弹     */
	public boolean hit(FlyingObject ob){
		int x1 = this.x - ob.width;
		int x2 = this.x + this.width;
		int y1 = this.y - ob.width;
		int y2 = this.y + this.height;
		int x = ob.x;			//子弹的x
		int y = ob.y;			//子弹的y
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}
	
	public void goDead(){
		state = DEAD;                                                                         
	}
	
	public abstract void step();
	
	public abstract BufferedImage getImages();
	
	//画对象 g:画笔
	public void paintObject(Graphics g){
		g.drawImage(getImages(), x, y, null);
	}
	
	public static BufferedImage loadImages(String fileName){
		try {
			BufferedImage image = ImageIO.read(FlyingObject.class.getResource(fileName));
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
