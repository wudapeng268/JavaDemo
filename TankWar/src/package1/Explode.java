package package1;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	int x,y;
	private boolean live=true;
	int[] diameter={4,7,13,20,28,37,49,30,18,6};
	int step=0;
	TankFrame tf;
	
	public Explode(int x,int y,TankFrame tf){
		this.x=x;
		this.y=y;
		this.tf=tf;
	}
	
	public void draw(Graphics g){
		if(!live) {
			tf.explodes.remove(this);
			return;
		}
		
		if(step==diameter.length){
			step=0;
			live=false;
			return;
		}
		g.setColor(Color.orange);
		g.fillOval(x, y,diameter[step],diameter[step]);
		step++;
	}
}
