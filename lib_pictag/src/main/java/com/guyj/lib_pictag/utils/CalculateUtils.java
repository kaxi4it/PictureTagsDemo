package com.guyj.lib_pictag.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by guyj on 2018/11/18.
 * 描述:
 */

public class CalculateUtils {

	public static int MoneyToFen(double cash){
		int i=new BigDecimal(cash+"").multiply(new BigDecimal(100)).intValue();

		return i;
	}

	public static String FenToYuan(int cash){
		return new BigDecimal(cash).divide(new BigDecimal(100)).setScale(2).toString();
	}

	/**
	 * add 的实现描述：加法
	 *
	 * @param para1 加数
	 * @param para2 被加数
	 * @return 和
	 * @author yltang 2015-8-25 下午4:52:01
	 */
	public static String add(String para1, String para2) {
		if (TextUtils.isEmpty(para1)) {
			para1 = "0.00";
		}
		if (TextUtils.isEmpty(para2)) {
			para2 = "0.00";
		}
		BigDecimal val1 = new BigDecimal(para1);
		BigDecimal val2 = new BigDecimal(para2);
		return val1.add(val2).toString();
	}

	/**
	 * subtract 的实现描述：减法
	 *
	 * @param para1 减数
	 * @param para2 被减数
	 * @return 参数说明
	 * @author yltang 2015-8-27 上午9:57:47
	 */
	public static String subtract(String para1, String para2) {
		if (TextUtils.isEmpty(para1)) {
			para1 = "0.00";
		}
		if (TextUtils.isEmpty(para2)) {
			para2 = "0.00";
		}
		BigDecimal val1 = new BigDecimal(para1);
		BigDecimal val2 = new BigDecimal(para2);
		return val1.subtract(val2).toString();
	}

	/**
	 * multiply 的实现描述：乘法
	 *
	 * @param para1 乘数
	 * @param para2 被乘数
	 * @return 乘积
	 * @author yltang 2015-8-25 下午4:53:21
	 */
	public static String multiply(String para1, String para2) {
		if (TextUtils.isEmpty(para1)) {
			para1 = "0.00";
		}
		if (TextUtils.isEmpty(para2)) {
			para2 = "0.00";
		}
		BigDecimal val1 = new BigDecimal(para1);
		BigDecimal val2 = new BigDecimal(para2);
		return val1.multiply(val2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * divide 的实现描述：除法
	 *
	 * @param para1 除数
	 * @param para2 被除数
	 * @return 结果
	 * @author yltang 2015-8-25 下午5:32:14
	 */
	public static String divide(String para1, String para2) {
		if (TextUtils.isEmpty(para1)) {
			para1 = "0.00";
		}
		if (TextUtils.isEmpty(para2)) {
			para2 = "0.00";
		}
		// MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
		BigDecimal val1 = new BigDecimal(para1);
		BigDecimal val2 = new BigDecimal(para2);
		// 除法保留两位小数并且四舍五入
		return val1.divide(val2, 2, BigDecimal.ROUND_HALF_UP).toString();
	}

    /**
     * divide 的实现描述：除法  todo 0 - 1 取1   99 - 100  取99
     *
     * @param para1 除数
     * @param para2 被除数
     * @return 结果
     */
    public static String divideForSeckill(String para1, String para2) {
        if (TextUtils.isEmpty(para1)) {
            para1 = "0.00";
        }
        if (TextUtils.isEmpty(para2)) {
            para2 = "0.00";
        }
        // MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal val1 = new BigDecimal(para1);
        BigDecimal val2 = new BigDecimal(para2);
        String result = val1.divide(val2, 4, BigDecimal.ROUND_CEILING).toString();
        if (CalculateUtils.compare(result, "0.01") < 0) {
            return val1.divide(val2, 2, BigDecimal.ROUND_UP).toString();
        }
	    // 除法保留两位小数
	    return val1.divide(val2, 2, BigDecimal.ROUND_FLOOR).toString();
    }

    /**
     * compare 的实现描述：比较两个数字的大小
     *
     * @param para1 第一个数字
     * @param para2 第二个数字
     * @return 若para1大于等于para2则返回true
     */
    public static int compare(String para1, String para2) {
        if (TextUtils.isEmpty(para1)) {
            para1 = "0.00";
        }
        if (TextUtils.isEmpty(para2)) {
            para2 = "0.00";
        }
        BigDecimal val1 = new BigDecimal(para1);
        BigDecimal val2 = new BigDecimal(para2);
        return val1.compareTo(val2);
    }

	/**
	 * 四舍五入
	 *
	 * @param param
	 * @param num   保留位数
	 * @return
	 */
	public static String cutNumber(String param, int num) {
		if (!TextUtils.isEmpty(param)) {
			BigDecimal val1 = new BigDecimal(param);
			return val1.setScale(num, RoundingMode.HALF_UP).toString();
		}
		return "0";
	}
}
