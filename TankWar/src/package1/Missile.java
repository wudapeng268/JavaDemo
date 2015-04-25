package package1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import package1.Tank.direction;

public class Missile {
	int x,y;
	direction dir;
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=10;
	public static final int HEIGTH=10;
	
	private  boolean live=true;
	
	private boolean good;
	
	TankFrame tf;
	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y, direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Missile(int x,int y,boolean good,direction dir,TankFrame tf){
		this(x,y,dir);
		this.good=good;
		this.tf=tf;
	}
	
	public void draw(Graphics g){
		if(!live){
			tf.missiles.remove(this);
			return ;
		}
		g.setColor(Color.BLACK);
		g.fillOval(x, y,10,10);
		move();
	}

	private void move() {
		switch(dir){
		case L:
			x-=XSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case UR:
			y-=YSPEED;
			x+=XSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		case LD:
			y+=YSPEED;
			x-=XSPEED;
			break;
		
		}
		if(x<0||y<0||x>TankFrame.GAME_WIDTH||y>TankFrame.GAME_HEIGHT){
			tf.missiles.remove(this);
		}
	}
	
	public Rectangle getrect(){
		return new Rectangle(x, y, WIDTH, HEIGTH);
	}
	
	public boolean hitTank(Tank t){
		if(this.live&&this.getrect().intersects(t.getrect())&&t.isLive()&&this.good!=t.isGood())
		{
			t.setLive(false);
			live=false;
			Explode e=new Explode(x, y, tf);
			tf.explodes.add(e);

			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i)))
				return true;
		}
		return false;
	}
	
	public boolean hitWall(Wall wall){
		if(this.live&&this.getrect().intersects(wall.getrect())){
			live=false;
			return true;
		}
			
		return false;
	}
	
}
