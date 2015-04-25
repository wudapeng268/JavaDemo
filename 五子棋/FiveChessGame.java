package com.tarena.demo.case2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class FiveChessGame extends JFrame implements ActionListener {

	JButton start = new JButton("开始");
	JButton back = new JButton("悔棋");
	JPanel panel = new JPanel();
	BoardPanel boardPanel = new BoardPanel();
	
	public FiveChessGame() {
		start.addActionListener(this);
		panel.add(start);
		back.addActionListener(this);
		panel.add(back);
		this.add(panel, BorderLayout.NORTH);
		boardPanel.addMouseListener(boardPanel);
		this.add(boardPanel);
		this.setResizable(false);
		this.setSize(538, 593);
		this.setLocation(300, 100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("五子棋");
	}

	public static void main(String[] args) throws Exception {
		new FiveChessGame();
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		if ("开始".equals(com)) {
			boardPanel.start();			
		} else if ("悔棋".equals(com)) {
			boardPanel.back();
		}
	}

}
