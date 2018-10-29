package cn.ted.shoot;
import java.awt.image.BufferedImage;

public class Bee extends FlyingObject implements award{
	private int xStep; 
	private int yStep; 
	private int awardType;
	private static BufferedImage[] images;
	
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImages("bee"+i+".png");
		}
	}
	 
	public Bee(){
		super(60, 50);
		x = (int)(Math.random()*(World.WIDTH-this.width));
		y = this.height;
		xStep = 2;
		yStep = 2;
		awardType = (int)(Math.random()*2);
	}

	public void step(){
		x+=xStep;
		y+=yStep;
		if(x>=World.WIDTH-this.width || x<=0){
			xStep *= -1;
		}
		System.out.println("小蜜蜂的X坐标移动了" + xStep);
		System.out.println("小蜜蜂的Y坐标移动了" + yStep);
	}

	int deadIndex = 1;
	public BufferedImage getImage(){
	if(isLife()){
			return images[0];
		}else if(isDead()){
			BufferedImage img = images[deadIndex++];
			if(deadIndex==images.length){
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public int getType(){
		return awardType;
	}
	
	/*int index =0;
	public BufferedImage getImages(){
		if(isLife()){
			return images[0];
		}else if(isDead()) {
			BufferedImage img = images[deadIndex ++];
			if(deadIndex == images.length){
				state = REMOVE;
			}
			return img;
		}
		return null;
	}*/
	
	int index = 0;
	public BufferedImage getImages() {
		if (isLife()){
			return images[0];
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
	
	//判断outOfBounds越界
	public boolean outOfBounds(){
		return this.y >= World.HEIGHT;	
	}

}










