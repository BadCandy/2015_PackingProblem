package Packing;

import java.io.*;
import java.util.*;

public class DoPacking {
	public static void main(String[] args) {
		try {
//			String fileName = "rsc\\another_input.txt";
			String fileName = "rsc\\packing_input.txt";
			Scanner sc = new Scanner(new File(fileName));
			int T = sc.nextInt();		//테스트케이스 수
			WholeRectangle wholeSteel = null;
			
			List<WholeRectangle[]> list = new ArrayList<>();
			int repeatNum = 1; //알고리즘 반복횟수
			for (int i = 0; i < repeatNum; i++) {			
				WholeRectangle[] array = new WholeRectangle[T];
				for (int t = 0; t < T; t++) {
					List<Rectangle> rectList = new ArrayList<>();
					int N = sc.nextInt();	//철판의 개수
					int L = sc.nextInt();	//후판의 너비
					wholeSteel = new WholeRectangle(L, 0);	//후판
					for (int i1 = 0; i1 < N; i1++) {
						int width = sc.nextInt(); 				
						int height = sc.nextInt();
						rectList.add(new Rectangle(width, height));	//철판
					}
					PackingProcess process = new PackingProcess(rectList, wholeSteel);
					process.process();
					array[t] = wholeSteel;
				}
				list.add(array);
				sc = new Scanner(new File(fileName));
				T = sc.nextInt();		//테스트케이스 수
			}
			
			WholeRectangle[] minSteel = null;
			minSteel = new WholeRectangle[T];
			for (int k = 0; k < T; k++) {
				minSteel[k] = new WholeRectangle(0,500);
 			}
			
			for (int i = 0; i < list.size() ; i++) {
				WholeRectangle[] array = list.get(i);
				for (int j = 0; j < array.length; j++) {
					if(array[j].getHeight() < minSteel[j].getHeight()){
						minSteel[j] = array[j];
					}
				}
			}
			
			for (int i = 0; i < minSteel.length; i++) {
				new DrawRectFrame(minSteel[i]);
				System.out.println((i+1) + "번째 후판의 높이 : " + minSteel[i].getHeight());
				System.out.println((i+1) + "번째 후판에서의 각 철판들의 위치(왼쪽하단, 오른쪽상단 좌표)");
				List<Rectangle> rectList = minSteel[i].getRectList();
				for (int j = 0; j < rectList.size() ; j++) {
					System.out.print((j+1) + "번째 : " + "(" + rectList.get(j).getLeftBottomPoint().x + ", " + rectList.get(j).getLeftBottomPoint().y + "), "
									+ "(" + rectList.get(j).getRightTopPoint().x + ", " + rectList.get(j).getRightTopPoint().y + ")" + '\t');
					
					if(j%4 == 0 && j != 0){
						System.out.println();
					}
				}
				System.out.println('\n');
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
