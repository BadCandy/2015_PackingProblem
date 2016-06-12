package Packing;

import java.awt.Point;

public class Rectangle implements Comparable<Rectangle>{
	private int width;
	private int height;
	private int area;
	private Point[] point = new Point[4];

	public Rectangle(){
		for (int i = 0; i < point.length; i++) {
			point[i] = new Point();
		}
	}
	
	public Rectangle(int width, int height){
		this.width = width;
		this.height = height;
		area = width*height;
		for (int i = 0; i < point.length; i++) {
			point[i] = new Point();
		}
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getArea(){
		return area;
	}
	public void setWidth(int width){
		this.width = width;
		setArea(this.width, this.height);
	}
	public void setHeight(int height){
		this.height = height;
		setArea(this.width, this.height);
	}
	public void setArea(int width, int height){
		area = width * height;
	}
	public void rotate(){
		int temp = width;
		width = height;
		height = temp;
	}
	
	public void randomRotate(){
		int rNum = (int)(2*Math.random());
		if(rNum == 1) rotate();
	}
	
	public Rectangle getRotRect(){
		int height = this.width;
		int width = this.height;
		return new Rectangle(width, height);
	}
	public void setLeftBottomPoint(int x, int y){
		point[0].setLocation(x, y);
		point[1].setLocation(x, y+height);
		point[2].setLocation(x+width, y+height);
		point[3].setLocation(x+width, y);
	}
	public Point getLeftBottomPoint(){
		return point[0].getLocation();
	}
	public Point getLeftTopPoint(){
		return point[1].getLocation();
	}
	public Point getRightTopPoint(){
		return point[2].getLocation();
	}
	public Point getRightBottomPoint(){
		return point[3].getLocation();
	}
	@Override
	public int compareTo(Rectangle o) {
		if (this.area == o.area) return 0;
		if (this.area > o.area) return 1;
		if (this.area < o.area) return -1;
		return 0;
	}
}