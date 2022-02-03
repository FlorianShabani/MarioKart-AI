package kart.entity;

public class CheckPoint extends Block{

	public int checkPoint;
	public static int checkPointOrder = 0;
	
	public CheckPoint(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.checkPoint = checkPointOrder;
		checkPointOrder++;
	}

}
