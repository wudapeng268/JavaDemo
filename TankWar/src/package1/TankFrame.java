package package1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame implements KeyListener {
	public static final int GAME_WIDTH=600;
	public static final int GAME_HEIGHT=500;
	
	Tank mytank=new Tank(50, 50,true,Tank.direction.STOP,this);
	
	Wall wall=new Wall(300, 100);
	List<Tank> tanks=new ArrayList<Tank>();
	List<Explode> explodes=new ArrayList<Explode>();
	List<Missile> missiles=new ArrayList<Missile>();
	public TankFrame(){
		for(int i=0;i<10;i++){
			tanks.add(new Tank(50+40*(i+1), 50, false, Tank.direction.D,this));
		}
		Toolkit kit =Toolkit.getDefaultToolkit();
		Dimension di=kit.getScreenSize();
		int screenHight=di.height;
		int screenwidth=di.width;
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setLocation(screenwidth/4, screenHight/4-50);
		this.setTitle("Ì¹¿Ë´óÕ½");
		this.setBackground(Color.green);
		
		this.addKeyListener(this);

	
		new Thread(new PaintThread()).start();
	
		
		//paintthread();
		
		this.setResizable(false);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}
	
	public void paint(Graphics g){
		g.drawString("Missile count:"+missiles.size(), 10, 50);
		g.drawString("tanks count:"+tanks.size(), 10, 70);
		mytank.draw(g);
		wall.draw(g);
		mytank.hitWall(wall);
		mytank.collideTank(tanks);
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			t.draw(g);
			t.hitWall(wall);
			t.collideTank(tanks);
		}
		for(int i=0;i<missiles.size();i++){
			Missile m=missiles.get(i);
			m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(mytank);
			m.hitWall(wall);
			
		}
		for(int i=0;i<explodes.size();i++){
			Explode e=explodes.get(i);
			e.draw(g);
			
		}
		
		
	}
	
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
//	public void paintthread(){
//		Thread th=new Thread(){
//			public void run() {
//				while(true){
//					repaint();
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		};
//		th.start();
//	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		mytank.keyPressed(e);
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		mytank.keyReleased(e);
	}
	
}

	
