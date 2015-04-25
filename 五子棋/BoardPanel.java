package com.tarena.demo.case2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardPanel extends JPanel implements MouseListener{
	
	Image imgBoard, imgBlack, imgWhite;
	static int[][] board = new int[15][15];
	static final int BLACK = 1;
	static final int WHITE = -1;
	static int currentColor = BLACK;
	List<String> steps = new ArrayList<String>();
	boolean yes = false;
	
	public BoardPanel(){
		imgBoard = new ImageIcon("chessboard.jpg").getImage();
		imgBlack = new ImageIcon("b.gif").getImage();
		imgWhite = new ImageIcon("w.gif").getImage();
	}
	
	public void playChess(int row, int col) {
		if (row < 0 || col < 0 || row > 14 || col > 14)
			return;
		if (board[row][col] != 0)
			return;
		board[row][col] = currentColor;
		currentColor = -currentColor;
		steps.add(row + "," + col);
	}

	public void mouseClicked(MouseEvent e) {
		if(yes) return;
		playChess(e.getY() / 35, e.getX() / 35);
		repaint();
		JOptionPane jp = new JOptionPane();
		if (wasWin(e.getY() / 35,e.getX() / 35)) {
			yes = true;
			if (currentColor == BLACK) {
				JOptionPane.showMessageDialog(jp, "白棋获胜");
			} else {
				JOptionPane.showMessageDialog(jp, "黑棋获胜");
			}
		}		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(imgBoard, 0, 0, null);
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				if (board[row][col] != 0) {
					Image img = imgBlack;
					if (board[row][col] == WHITE) {
						img = imgWhite;
					}
					g.drawImage(img, col * 35+4 , row * 35+4, null);
				}
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {	
	}
	public void mouseExited(MouseEvent e) {	
	}
	public void mousePressed(MouseEvent e) {		
	}
	public void mouseReleased(MouseEvent e) {	
	}
	
	public static boolean wasWin(int r,int c){
		return wasWinAtV(r,c)||wasWinAtH(r,c)||wasWinAtLD(r,c)||wasWinAtRD(r,c);
	}
	
	/**
	 * 判断水平方向是否有5子连成一线
	 */
	//横向
	public static boolean wasWinAtV(int r,int c){
		int v=1;//同方向上的棋子个数，从一开始，省去反向遍历需要棋子减一的麻烦
		for(int i=c-1;i>0;i--){
			if(board[r][i]==board[r][c]){
				v++;
			}else{
				break;
			}
		}//省略单向遍历棋子达到五个的判断，从统计概率上看，省略可以提高效率，并减小体积
		for(int i=c+1;i<board[0].length;i++){
			if(board[r][i]==board[r][c]){
				v++;
			}else{
				break;
			}
		}
		return v>=5;
		
	}
	//竖向
	public static boolean wasWinAtH(int r,int c){
		int h=1;
		for(int i=r-1;i>0;i--){
			if(board[i][c]==board[r][c]){
				h++;
			}else{
				break;
			}
		}
		for(int i=r+1;i<board.length;i++){
			if(board[i][c]==board[r][c]){
				h++;
			}else{
				break;
			}
		}
		return h>=5;
	}
	//左斜
	public static boolean wasWinAtLD(int r,int c){
		int l=1;
		for(int i=r-1, j=c+1;i>0 && j<board[0].length;i--,j++){
			if(board[i][j]==board[r][c]){
				l++;
			}else{
				break;
			}
		}
		for(int i=r+1, j=c-1;i<board.length && j>0;i++,j--){
			if(board[i][j]==board[r][c]){
				l++;
			}else{
				break;
			}
		}
		
		return l>=5;
	}
	//右斜
	public static boolean wasWinAtRD(int r,int c){
		int d=1;
		for(int i=r-1, j=c-1;i>0 && j>0;i--,j--){
			if(board[i][j]==board[r][c]){
				d++;
			}else{
				break;
			}
		}
		for(int i=r+1, j=c+1;i<board.length && j<board[0].length;i++,j++){
			if(board[i][j]==board[r][c]){
				d++;
			}else{
				break;
			}
		}
		return d>=5;
	}
	public void back() {
		if(yes){
			return;
		}
		if (steps.isEmpty()){
			return;
		}	
		String s = steps.remove(steps.size() - 1);
		String[] arr = s.split(",");
		int row = Integer.parseInt(arr[0]);
		int col = Integer.parseInt(arr[1]);
		board[row][col] = 0;
		currentColor = -currentColor;
		repaint();
	}

	public void start() {
		steps = new ArrayList<String>();
		yes = false;
		board = new int[15][15];
		currentColor = BLACK;
		repaint();
	}
}
