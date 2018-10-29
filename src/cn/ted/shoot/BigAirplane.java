package cn.ted.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BigAirplane extends FlyingObject implements Emeny{
	private static BufferedImage[] ime;
	private int speed;
	
	static{
		ime = new BufferedImage[5];
		for (int i=0; i<ime.length; i++){
			ime[i] = loadImages("bigplane" + i + ".png");
		}
	}

	public BigAirplane() {
		super(69, 99);
		speed = 2;           
	}
	public void step(){
		y += speed;
		System.out.println("大敌机的坐标移动了" + speed);
	}
	
	int index = 1;
	public BufferedImage getImages() {
		if (isLife()){
			return ime[0];
		}else if(isDead()){
			//图片10ms 更换一张
			BufferedImage img = ime[index++];		//返回图片从第二张图片
			if (index == ime.length){
				//修改当前状态删除	
				state = REMOVE;
			}
			return img;
			//10ms 		
		}
		return null;
	}
	//判断outOfBounds越界
	public boolean outOfBounds(){
		return this.y >= World.HEIGHT;
		
	}
	
	public int getScore() { 
		return 3;				//打掉一个大敌机 得 3 分
	}

}
