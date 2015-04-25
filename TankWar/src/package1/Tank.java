package package1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;


public class Tank {
	private boolean bl=false,bu=false,bd=false,br=false;
	
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH=30;
	public static final int HEIGTH=30;
	
	TankFrame tf;

	private  boolean good;
	
	private int oldX,oldY;
	
	public boolean isGood() {
		return good;
	}

	private boolean live=true;
	
	private static  Random ra=new Random();
	
	private int step=ra.nextInt(12)+5;
	
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}

	enum direction {L,U,R,D,LU,UR,RD,LD,STOP};
	direction dir=direction.STOP;
	direction ptDir=direction.R;
	
	public int x,y;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good=good;
	}
	public Tank(int x,int y, boolean good,Tank.direction dir,TankFrame tf){
		this(x,y,good);
		this.oldX=x;
		this.oldY=y;
		this.dir=dir;
		this.tf=tf;
	}
	public void draw(Graphics g){
		if(!live) {
			if(!good){
				tf.tanks.remove(this);
				
			}
			return;
		}
		if(good) g.setColor(Color.red);
		else g.setColor(Color.BLUE);
		g.fillOval(x,y, WIDTH,HEIGTH);
		g.setColor(Color.black);
		switch(ptDir){
		case L:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x, y+Tank.HEIGTH/2);
			break;
		case U:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x+Tank.WIDTH/2, y);
			break;
		case R:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x+Tank.WIDTH, y+Tank.HEIGTH/2);

			break;
		case D:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x+Tank.WIDTH/2, y+Tank.HEIGTH);

			break;
		case LU:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x, y);

			break;
		case UR:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x+Tank.WIDTH, y);

			break;
		case RD:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x+Tank.WIDTH, y+Tank.HEIGTH);

			break;
		case LD:
			g.drawLine(x+Tank.WIDTH/2,y+Tank.HEIGTH/2,x, y+Tank.HEIGTH);

			break;
		}
		move();
		if(x<0) x=0;
		if(y<30) y=30;
		if(x>TankFrame.GAME_WIDTH-Tank.WIDTH) x=TankFrame.GAME_WIDTH-Tank.WIDTH;
		if(y>TankFrame.GAME_HEIGHT-Tank.HEIGTH) y=TankFrame.GAME_HEIGHT-Tank.HEIGTH;
	}
	void move(){
		oldX=x;
		oldY=y;
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
		case STOP:
			break;
			
		}
		if(dir!=direction.STOP){
			ptDir=dir;
		}
		if(!good){
			direction[] dirs=direction.values();
			if(step==0){
				step=ra.nextInt(12)+5;
				dir=dirs[ra.nextInt(dirs.length)];
			}
			if(ra.nextInt(40)>36){
				tf.missiles.add(fire());
			}
			
			
			step--;
		}
	}
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_LEFT:
			bl=true;
			break;
		case KeyEvent.VK_UP:
			bu=true;
			break;
		case KeyEvent.VK_RIGHT:
			br=true;
			break;
		case KeyEvent.VK_DOWN:
			bd=true;
			break;
		default:
			break;
		}
		direction();
		
	}
	
	void direction(){
		if(bl&&!bu&&!br&&!bd) dir=direction.L;
		else if(!bl&&bu&&!br&&!bd) dir=direction.U;
		else if(!bl&&!bu&&br&&!bd) dir=direction.R;
		else if(!bl&&!bu&&!br&&bd) dir=direction.D;
		else if(bl&&bu&&!br&&!bd) dir=direction.LU;
		else if(!bl&&bu&&br&&!bd) dir=direction.UR;
		else if(!bl&&!bu&&br&&bd) dir=direction.RD;
		else if(bl&&!bu&&!br&&bd) dir=direction.LD;
		else if(!bl&&!bu&&!br&&!bd) dir=direction.STOP;
	}
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			if(fire()!=null) tf.missiles.add(fire());
			break;
		case KeyEvent.VK_LEFT:
			bl=false;
			break;
		case KeyEvent.VK_UP:
			bu=false;
			break;
		case KeyEvent.VK_RIGHT:
			br=false;
			break;
		case KeyEvent.VK_DOWN:
			bd=false;
			break;
		case KeyEvent.VK_A:
			superfire();
			break;
			
		default:
			break;
		}
		direction();
	}
	private Missile fire() {
		if(!live) return null ;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGTH/2-Missile.HEIGTH/2;
		Missile m=new Missile(x,y,good, ptDir,tf);
		return m;
	}
	
	private Missile fire(direction dir){
		if(!live) return null ;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGTH/2-Missile.HEIGTH/2;
		Missile m=new Missile(x,y,good, dir,tf);
		return m;
	}
	
	public Rectangle getrect(){
		return new Rectangle(x, y, WIDTH, HEIGTH);
	}
	
	private void stay(){
		x=oldX;
		y=oldY;
	}
	
	public boolean hitWall(Wall wall){
		
		if(this.live&&this.getrect().intersects(wall.getrect())){
			this.stay();
			return true;
		}
		
		return false;
	}
	
	public boolean collideTank(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			if(this!=t){
				if(this.live&&t.isLive()&&this.getrect().intersects(t.getrect())){
					this.stay();
					t.stay();
					return true;
				}
				
			
			}
		}
		return false;
		
	}
	
	private void superfire(){
		direction[] dirs=direction.values();
		for(int i=0;i<dirs.length;i++){
			fire(dirs[i]);
		}
	}
	
}
