package cn.ted.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;;

public class Sky extends FlyingObject{
	private int speed;
	private int y1;
	private static BufferedImage img;			
	static{
		img = loadImages("background.png");
	}
	
	public Sky(){
		super(World.WIDTH, World.HEIGHT, 0 ,0);
		/*width = 500;
		height = 900;
		x = 0;
		y = 0;*/
		speed = 1;
		y1 = -height;     //窗口的高
	}
	public void step(){
		y += speed;				//y 向下
		y1 += speed;			//y1 向下
		if (y >= this.height){			//若y >= 天空的高
			y = -this.height;			//设置 y 为负的高
		}
		if (y1 >= this.height){
			y1 = -this.height;
		}
		System.out.println("天空移动了：" + speed);
	}
	public BufferedImage getImages() {
		return img;
	}
	public void paintObject(Graphics g){
		//画图片
		g.drawImage(getImages(), x, y, null);
		g.drawImage(getImages(), x, y1, null);
	}
	//判断outOfBounds越界
	public boolean outOfBounds(){
		return false;
	}

}