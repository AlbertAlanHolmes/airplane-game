package cn.ted.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Airplane extends FlyingObject implements Emeny{
	private int speed;		
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[5];
		for (int i=0; i<images.length; i++){
			images[i] = loadImages("airplane" + i + ".png");
		}
	}
	public Airplane(){
		super(49, 36);
		speed = 2;
	}
	public void step(){
		y += speed;
		System.out.println("小敌机的坐标移动了" + speed);
	}
	
	public int getScore() {
		return 1;				//打掉一个小敌机 得 1 分
	}
	
	int index = 1;			//小敌机死了的下标
	public BufferedImage getImages() {
		if (isLife()){
			return images[0];
		}else if(isDead()){
			//图片10ms 更换一张
			BufferedImage img = images[index++];		//返回图片从第二张图片
			if (index == images.length){
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
}
