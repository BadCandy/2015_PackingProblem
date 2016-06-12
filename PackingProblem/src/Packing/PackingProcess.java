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
		for (int i = 0; i < rectList.size(); i++) {		//�� ����Ŭ �κ�
			Rectangle curRect = rectList.get(i);
			afterWidth = beforeWidth + curRect.getWidth();
			if(afterWidth > wholeSteel.getWidth()){	
				afterWidth = beforeWidth;
				curRect.rotate();						//ȸ��
				afterWidth = beforeWidth + curRect.getWidth();
				if(afterWidth > wholeSteel.getWidth()){	//ȸ���� �ߴµ��� �ȵǸ� �����ϰ� �Ѿ��. 
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
		for (int i = 0; i < rectList.size(); i++) {		//�� ����Ŭ �κ�
			Rectangle curRect = rectList.get(i);
			afterWidth = beforeWidth + curRect.getWidth();
			if(afterWidth > wholeSteel.getWidth()){	
				afterWidth = beforeWidth;
				curRect.rotate();						//ȸ��
				afterWidth = beforeWidth + curRect.getWidth();
				if(afterWidth > wholeSteel.getWidth()){	//ȸ���� �ߴµ��� �ȵǸ� �����ϰ� �Ѿ��. 
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
		List<Rectangle> curRectList = new ArrayList<>();				//������ beforeheight���� afterheight ���̿����� ���Ե� ö�� ����Ʈ
		List<Rectangle> insertedRectList = wholeSteel.getRectList();	//���ǿ� ���Ե� ��ü ö�� ����Ʈ
		List<Point> wholePoint = new ArrayList<>();						//������ ��ü��

		int height = wholeSteel.getHeight();							//������ ���� ����
		int width = wholeSteel.getWidth();								//������ ��ü �ʺ�
		int beforeHeight = wholeSteel.getBeforeHeight();				//������ beforeHeight
		int tempBottomMaxX = 0;
		int rightBottomY = 0;
		int rightBottomX = 0;

		for (int i = 0; i < insertedRectList.size() ; i++) {
			rightBottomY = (int)insertedRectList.get(i).getRightBottomPoint().getY();
			rightBottomX = (int)insertedRectList.get(i).getRightBottomPoint().getX();
			if(rightBottomY >= beforeHeight){	//ö�ǵ��� ���� �� y��ǥ�� beforeHeight���� Ŭ ��
				curRectList.add(insertedRectList.get(i));					//���ο� �ִ� ö�ǵ��� ����Ʈ�� ����
				wholePoint.add(insertedRectList.get(i).getLeftTopPoint()); 	//���ο� �ִ� ö�ǵ��� ���� ��� �� ����

				if(rightBottomY == beforeHeight){							//���ο� �ִ� ö����ǥ �� �� ���� left�ϰ� ���� bottom�� ��ǥ ����
					if(tempBottomMaxX < rightBottomX){
						tempBottomMaxX = rightBottomX;
					}
				}
			}	
		}
		rightBottomX = tempBottomMaxX;
		if(rightBottomX != wholeSteel.getWidth())
			wholePoint.add(new Point(rightBottomX, rightBottomY));			//���ο� �ִ� ö����ǥ �� �� ���� left�ϰ� ���� bottom�� ��ǥ ����

		int checkX = 0, checkY = 0;
		for (int i = 0; i < wholePoint.size(); i++) {
			checkX = (int)wholePoint.get(i).getX();		//���ο� �ִ����� ù��°�� X
			checkY = (int)wholePoint.get(i).getY();		//���ο� �ִ����� ù��°�� Y (checkX, checkY)
			//			System.out.println(checkX + " " + checkY);
			for (int x = checkX; x < width; x++) {
				for (int y = checkY; y < height; y++) {
					iRect.setLeftBottomPoint(x, y);
					rotRect.setLeftBottomPoint(x, y);

					//���⼭ ������ ��� �����ϸ� true ��ȯ
					int j;
					boolean bool = false;
					for (j = 0; j < curRectList.size(); j++) {
						if(iRect.getRightTopPoint().y <= height && iRect.getRightTopPoint().x <= width){ //iRect�� �����ȿ� ������
							if(checkCollision(iRect, curRectList.get(j))){		//�浹 ���� ��
								bool = false;
								break;
							}else{												//�浹 ������ ��
								bool = true;
							}
						}else{				//�����ȿ� ���� ��
							bool = false;
							break;
						}
					}
					if(bool == true){
						wholeSteel.addRect(iRect);
						return true;
					}
					
					for (j = 0; j < curRectList.size(); j++) {
						if(rotRect.getRightTopPoint().y <= height && rotRect.getRightTopPoint().x <= width){ //iRect�� �����ȿ� ������
							if(checkCollision(rotRect, curRectList.get(j))){		//�浹 ���� ��
								bool = false;
								break;
							}else{												//�浹 ������ ��
								bool = true;
							}
						}else{				//�����ȿ� ���� ��
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

	public void rectListSort(){					//���̸� �������� ������������ ����
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
		while(rectList.size() != 0){						//��ü �κ�
			if(wholeSteel.getHeight() == 0){
				firstRowInsert();
			}else{
				nextRowInsert();
			}
		}
	}
}
