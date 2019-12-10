
package test;
import java.util.Arrays;

/**
 * 快速排序：通过一趟排序将待排序记录分割成独立的两部分，其中一部分记录的关键字均比另一部分关键字小，则分别
 * 对这两部分继续进行排序，直到整个序列有序。
 *
 * @author BaoZi
 * @create 2019-05-15-18:15
 */
public class QuickSort {
	private static final char[] DATA={
		'零','壹','贰','叁','肆','伍','陆','染','捌','玖'
	};
	private static final char[] UNITS={
		'元','拾','佰','仟','万','拾','佰','仟', '亿'
	};
    public static void main(String[] args) {
    	QuickSort qSort=new QuickSort();
    	System.out.println(qSort.convert(100200388));
    }
    public static String convert(int number)
    {
    	StringBuffer sb=new StringBuffer();
    	int unit=0;
    	while (number!=0) {
    		sb.insert(0, UNITS[unit++]);
    		int num=number%10;
    		sb.insert(0, DATA[num]);
    		number/=10;
    		
    					
		}
    	System.out.println(sb);
    	return sb.toString().replaceAll("零[拾佰仟]", "零").replaceAll("零+万", "万").replaceAll("零+零", "零");
    }
    
}