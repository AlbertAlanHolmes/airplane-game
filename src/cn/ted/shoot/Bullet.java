package cn.ted.shoot;
import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject{
	private int speed;
	private static BufferedImage ima;
	
	static{
		ima = loadImages("bullet.png");
	}
	public Bullet(int x, int y){
		super(8, 14, x, y);
		speed = 3;
	}
	public void step(){
		y -= speed;		// y-向上
		System.out.println("子弹的X坐标移动了" + speed);
		System.out.println("子弹的Y坐标移动了" + speed);
	}
	public BufferedImage getImages() {
		//判断活着
		if (isLife()){
			//返回图片
			return ima;
			//判断死了
		}else if(isDead()){
			//将对象修改为删除
			state = REMOVE;
		}
		return  null;
	}
	//判断outOfBounds越界
	public boolean outOfBounds(){
		return this.y <= -this.height;
	}

}
