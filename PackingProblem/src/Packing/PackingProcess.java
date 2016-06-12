package Packing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackingProcess{
	private List<Rectangle> rectList;
	private WholeRectangle wholeSteel;

	public PackingProcess(List<Rectangle> rectList, WholeRectangle wholeSteel){
		this.rectList = rectList;
		this.wholeSteel = wholeSteel;
	}

	public void firstRowInsert(){
		int maxHeight = 0;
		int beforeWidth = 0;	//beforeWidth
		int afterWidth = 0;
		for (int i = 0; i < rectList.size(); i++) {		//한 사이클 부분
			Rectangle curRect = rectList.get(i);
			afterWidth = beforeWidth + curRect.getWidth();
			if(afterWidth > wholeSteel.getWidth()){	
				afterWidth = beforeWidth;
				curRect.rotate();						//회전
				afterWidth = beforeWidth + curRect.getWidth();
				if(afterWidth > wholeSteel.getWidth()){	//회전을 했는데도 안되면 보류하고 넘어간다. 
					afterWidth = beforeWidth;
					continue;
				}
			}
			int curHeight = curRect.getHeight();
			if(maxHeight < curHeight){
				maxHeight = curHeight;
			}
			curRect.setLeftBottomPoint(beforeWidth, 0);
			beforeWidth = afterWidth;
			wholeSteel.addRect(rectList.remove(i));
			i -= 1;
		}
		wholeSteel.setHeight(maxHeight);

		for (int i = 0; i < rectList.size(); i++) {
			if(checkSpaceAndInsert(rectList.get(i))){
				rectList.remove(i);
				i -= 1;
			}
		}
	}

	public void nextRowInsert(){
		int maxHeight = wholeSteel.getHeight();
		int bHeight = maxHeight;
		int beforeWidth = 0;	//beforeWidth
		int afterWidth = 0;
		for (int i = 0; i < rectList.size(); i++) {		//한 사이클 부분
			Rectangle curRect = rectList.get(i);
			afterWidth = beforeWidth + curRect.getWidth();
			if(afterWidth > wholeSteel.getWidth()){	
				afterWidth = beforeWidth;
				curRect.rotate();						//회전
				afterWidth = beforeWidth + curRect.getWidth();
				if(afterWidth > wholeSteel.getWidth()){	//회전을 했는데도 안되면 보류하고 넘어간다. 
					afterWidth = beforeWidth;
					continue;
				}
			}
			int curHeight = curRect.getHeight();
			int tempHeight = bHeight+curHeight;
			if(maxHeight < tempHeight){
				maxHeight = tempHeight;
			}
			curRect.setLeftBottomPoint(beforeWidth, bHeight);
			beforeWidth = afterWidth;
			wholeSteel.addRect(rectList.remove(i));
			i -= 1;
		}
		wholeSteel.setBeforeHeight(bHeight);
		wholeSteel.setHeight(maxHeight);
		for (int i = 0; i < rectList.size(); i++) {
			if(checkSpaceAndInsert(rectList.get(i))){
				rectList.remove(i);
				i -= 1;
			}
		}
	}

	public boolean checkSpaceAndInsert(Rectangle iRect){
		Rectangle rotRect = iRect.getRotRect();
		List<Rectangle> curRectList = new ArrayList<>();				//후판의 beforeheight에서 afterheight 사이에서의 삽입된 철판 리스트
		List<Rectangle> insertedRectList = wholeSteel.getRectList();	//후판에 삽입된 전체 철판 리스트
		List<Point> wholePoint = new ArrayList<>();						//후판의 전체점

		int height = wholeSteel.getHeight();							//후판의 현재 높이
		int width = wholeSteel.getWidth();								//후판의 전체 너비
		int beforeHeight = wholeSteel.getBeforeHeight();				//후판의 beforeHeight
		int tempBottomMaxX = 0;
		int rightBottomY = 0;
		int rightBottomX = 0;

		for (int i = 0; i < insertedRectList.size() ; i++) {
			rightBottomY = (int)insertedRectList.get(i).getRightBottomPoint().getY();
			rightBottomX = (int)insertedRectList.get(i).getRightBottomPoint().getX();
			if(rightBottomY >= beforeHeight){	//철판들의 왼쪽 밑 y좌표가 beforeHeight보다 클 때
				curRectList.add(insertedRectList.get(i));					//내부에 있는 철판들을 리스트에 저장
				wholePoint.add(insertedRectList.get(i).getLeftTopPoint()); 	//내부에 있는 철판들의 왼쪽 상단 점 저장

				if(rightBottomY == beforeHeight){							//내부에 있는 철판좌표 들 중 가장 left하고 가장 bottom한 좌표 저장
					if(tempBottomMaxX < rightBottomX){
						tempBottomMaxX = rightBottomX;
					}
				}
			}	
		}
		rightBottomX = tempBottomMaxX;
		if(rightBottomX != wholeSteel.getWidth())
			wholePoint.add(new Point(rightBottomX, rightBottomY));			//내부에 있는 철판좌표 들 중 가장 left하고 가장 bottom한 좌표 저장

		int checkX = 0, checkY = 0;
		for (int i = 0; i < wholePoint.size(); i++) {
			checkX = (int)wholePoint.get(i).getX();		//내부에 있는점의 첫번째점 X
			checkY = (int)wholePoint.get(i).getY();		//내부에 있는점의 첫번째점 Y (checkX, checkY)
			//			System.out.println(checkX + " " + checkY);
			for (int x = checkX; x < width; x++) {
				for (int y = checkY; y < height; y++) {
					iRect.setLeftBottomPoint(x, y);
					rotRect.setLeftBottomPoint(x, y);

					//여기서 루프가 모두 성공하면 true 반환
					int j;
					boolean bool = false;
					for (j = 0; j < curRectList.size(); j++) {
						if(iRect.getRightTopPoint().y <= height && iRect.getRightTopPoint().x <= width){ //iRect가 범위안에 있을때
							if(checkCollision(iRect, curRectList.get(j))){		//충돌 했을 때
								bool = false;
								break;
							}else{												//충돌 안했을 때
								bool = true;
							}
						}else{				//범위안에 없을 때
							bool = false;
							break;
						}
					}
					if(bool == true){
						wholeSteel.addRect(iRect);
						return true;
					}
					
					for (j = 0; j < curRectList.size(); j++) {
						if(rotRect.getRightTopPoint().y <= height && rotRect.getRightTopPoint().x <= width){ //iRect가 범위안에 있을때
							if(checkCollision(rotRect, curRectList.get(j))){		//충돌 했을 때
								bool = false;
								break;
							}else{												//충돌 안했을 때
								bool = true;
							}
						}else{				//범위안에 없을 때
							bool = false;
							break;
						}
					}
					if(bool == true){
						wholeSteel.addRect(rotRect);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkCollision(Rectangle r1, Rectangle r2){
		int leftA, leftB;
		int rightA, rightB;
		int topA, topB;
		int bottomA, bottomB;
		leftA = r1.getLeftBottomPoint().x;
		rightA = r1.getLeftBottomPoint().x + r1.getWidth();
		topA = r1.getLeftBottomPoint().y;
		bottomA = r1.getLeftBottomPoint().y + r1.getHeight();

		leftB = r2.getLeftBottomPoint().x;
		rightB = r2.getLeftBottomPoint().x + r2.getWidth();
		topB = r2.getLeftBottomPoint().y;
		bottomB = r2.getLeftBottomPoint().y + r2.getHeight();
		if( bottomA <= topB ) return false;
		if( topA >= bottomB ) return false;
		if( rightA <= leftB ) return false;
		if( leftA >= rightB ) return false;
		return true;
	}

	public void rectListSort(){					//넓이를 기준으로 내림차순으로 정렬
		List<Rectangle> tempList = new ArrayList<>();
		Collections.sort(rectList, new OrderDescSorting());
		for (int i = 0; i < rectList.size(); i++) {
			rectList.get(i).randomRotate();
			if(rectList.get(i).getWidth() > wholeSteel.getWidth() && rectList.get(i).getHeight() > wholeSteel.getWidth()){
				rectList.remove(i);
				i--;
				continue;
			}
			if(rectList.get(i).getHeight() > wholeSteel.getWidth()){
				tempList.add(rectList.remove(i));
				i -= 1;
				continue;
			}
			if(rectList.get(i).getWidth() > wholeSteel.getWidth()){
				rectList.get(i).rotate();
				tempList.add(rectList.remove(i));
				i -= 1;
				continue;
			}
		}
		Collections.sort(tempList, new OrderDescSorting());
		rectList.addAll(0, tempList);
	}

	public void process(){
		rectListSort();
		while(rectList.size() != 0){						//전체 부분
			if(wholeSteel.getHeight() == 0){
				firstRowInsert();
			}else{
				nextRowInsert();
			}
		}
	}
}
