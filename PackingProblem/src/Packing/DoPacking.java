package Packing;

import java.io.*;
import java.util.*;

public class DoPacking {
	public static void main(String[] args) {
		try {
//			String fileName = "rsc\\another_input.txt";
			String fileName = "rsc\\packing_input.txt";
			Scanner sc = new Scanner(new File(fileName));
			int T = sc.nextInt();		//�׽�Ʈ���̽� ��
			WholeRectangle wholeSteel = null;
			
			List<WholeRectangle[]> list = new ArrayList<>();
			int repeatNum = 1; //�˰��� �ݺ�Ƚ��
			for (int i = 0; i < repeatNum; i++) {			
				WholeRectangle[] array = new WholeRectangle[T];
				for (int t = 0; t < T; t++) {
					List<Rectangle> rectList = new ArrayList<>();
					int N = sc.nextInt();	//ö���� ����
					int L = sc.nextInt();	//������ �ʺ�
					wholeSteel = new WholeRectangle(L, 0);	//����
					for (int i1 = 0; i1 < N; i1++) {
						int width = sc.nextInt(); 				
						int height = sc.nextInt();
						rectList.add(new Rectangle(width, height));	//ö��
					}
					PackingProcess process = new PackingProcess(rectList, wholeSteel);
					process.process();
					array[t] = wholeSteel;
				}
				list.add(array);
				sc = new Scanner(new File(fileName));
				T = sc.nextInt();		//�׽�Ʈ���̽� ��
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
				System.out.println((i+1) + "��° ������ ���� : " + minSteel[i].getHeight());
				System.out.println((i+1) + "��° ���ǿ����� �� ö�ǵ��� ��ġ(�����ϴ�, �����ʻ�� ��ǥ)");
				List<Rectangle> rectList = minSteel[i].getRectList();
				for (int j = 0; j < rectList.size() ; j++) {
					System.out.print((j+1) + "��° : " + "(" + rectList.get(j).getLeftBottomPoint().x + ", " + rectList.get(j).getLeftBottomPoint().y + "), "
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
