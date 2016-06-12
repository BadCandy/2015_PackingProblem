package Packing;

import java.util.ArrayList;
import java.util.List;

class WholeRectangle extends Rectangle{
	private List<Rectangle> rectList;
	private int beforeHeight;
	public WholeRectangle(int width, int height) {
		super(width, height);
		rectList = new ArrayList<>();
	}
	
	public void addRect(Rectangle rect){
		rectList.add(rect);
	}
	
	public List<Rectangle> getRectList(){
		return rectList;
	}

	
	public void setBeforeHeight(int bHeight){
		beforeHeight = bHeight;
	}
	
	public int getBeforeHeight(){
		return beforeHeight;
	}
}