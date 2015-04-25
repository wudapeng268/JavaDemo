package package1;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	private int x,y;
	public static final int WIDTH=20;
	public static final int HEIGHT=200;
	
	//TankFrame tf;
	
	public Wall(int x,int y){
		this.x=x;
		this.y=y;
		//this.tf=tf;
	}
	public void draw(Graphics g){
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
	public Rectangle getrect(){
		return new Rectangle(x, y, WIDTH,HEIGHT);
	}
}
