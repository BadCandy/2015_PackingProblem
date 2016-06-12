package Packing;

import java.util.Comparator;

public class OrderDescSorting implements Comparator<Rectangle> {
	public int compare(Rectangle item1, Rectangle item2) {
		if (item1.getArea() == item2.getArea()) return 0;
		if (item1.getArea() > item2.getArea()) return -1;
		if (item1.getArea() < item2.getArea()) return 1;
		return 0;
	}
}
