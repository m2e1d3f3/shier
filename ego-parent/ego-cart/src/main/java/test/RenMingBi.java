package test;
public class RenMingBi {


    /**
     * 中文数字
     */
    private static final char[] DATA = new char[]{
            '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'
    };

    /**
     * 中文货币单位
     */
    private static final char[] UNITS = new char[]{
            '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿'
    };

    public static void main(String[] args) {
        //输出:壹亿叁仟伍佰陆拾捌万玖仟壹佰贰拾叁元
        System.out.println(convert(135689123));
        //输出:不去零 肆亿伍仟贰佰零拾零万肆仟伍佰零拾伍元
        //输出:  去零 肆亿伍仟贰零万肆仟伍零拾伍元
        System.out.println(convert(452004505));
    }

    /**
     * 转换方法
     *
     * @param money 阿拉伯数字金钱数量
     * @return 中文金钱表示
     */
    public static String convert(int money) {
        StringBuilder sb = new StringBuilder();
        int unit = 0;
        while (money != 0) {
            sb.insert(0, UNITS[unit++]);
            int number = money % 10;
            sb.insert(0, DATA[number]);
            money /= 10;
        }
System.out.println(sb+"sb");
        //return sb.toString();
        //去零的代码：
       /* String noZero = sb.reverse().toString().replaceAll("零[拾佰仟]", "零").replaceAll("零+万", "万")
                .replaceAll("零+元", "元").replaceAll("零+", "零");*/
        String noZero = sb.toString().replaceAll("零[拾佰仟]", "零").replaceAll("零+万", "万")
        		.replaceAll("零+元", "元").replaceAll("零+", "零");
      //  return new StringBuilder(noZero)./*reverse().*/toString();
        return noZero;
    }
}