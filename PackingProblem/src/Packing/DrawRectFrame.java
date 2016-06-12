package Packing;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;

public class DrawRectFrame extends JFrame {
	Container contentPane;
	List<Rectangle> rectList;
	WholeRectangle wholeSteel;
	
	public DrawRectFrame(WholeRectangle wholeSteel) {
		rectList = wholeSteel.getRectList();
		this.wholeSteel = wholeSteel;
		setTitle("6조 철판자르기 프로젝트!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		DrawRectPanel panel = new DrawRectPanel();
		contentPane.add(panel);
		setSize(1200,1200);
		setVisible(true);
	}
	
	class DrawRectPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int a = 6;
			g.drawRect(wholeSteel.getLeftBottomPoint().x*a, wholeSteel.getLeftBottomPoint().y*a, wholeSteel.getWidth()*a, wholeSteel.getHeight()*a);
			for (int i = 0; i < rectList.size(); i++) {
				Color color = null;
				while(color == null || color == new Color(0, 0, 0)){
					color = new Color((int) (Math.random() * 255.0), (int) (Math.random() * 255.0), (int) (Math.random() * 255.0));
				}
				g.setColor(color);
				Rectangle rect = rectList.get(i);
				g.fillRect(rect.getLeftBottomPoint().x*a, rect.getLeftBottomPoint().y*a, 
						rect.getWidth()*a, rect.getHeight()*a);
						
			}
		}
	}
} 