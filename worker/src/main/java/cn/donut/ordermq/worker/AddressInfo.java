/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: addressInfo
 * Author:   LiYuAn
 * Date:     2018/6/11 11:05
 * Description: 产品id对应的接口地址
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.worker;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈产品id对应的接口地址〉
 *
 * @author LiYuAn
 * @create 2018/6/11
 * @since 1.0.0
 */
public class AddressInfo {

    //年课node接口地址
//    private static final String NODE_URL_PRODUCT = "https://abc.leanapp.cn/api/v1/web/yc/apply/status";
//    //年课升级包 299
//    private static final String NODE_URL_MEMBER = "https://abc.leanapp.cn/api/v1/web/yc/member/status";
//    //9.9活动
//    private static final String NODE_URL_DROP = "https://api.donut.cn/api/wk/apply/drop";

    private static final String test = "https://doabc.leanapp.cn/api/v1/web/yc/apply/status";

    static Map<Integer, String> map = Maps.newHashMap();

    static {
//        map.put(5960, NODE_URL_PRODUCT);
//        map.put(5856, NODE_URL_PRODUCT);
//        map.put(5866, NODE_URL_PRODUCT);
//        map.put(5868, NODE_URL_PRODUCT);
//        map.put(5869, NODE_URL_PRODUCT);
//        map.put(5860, NODE_URL_PRODUCT);
//        map.put(6258, NODE_URL_PRODUCT);
//        map.put(7054, NODE_URL_PRODUCT);
//        map.put(7563, NODE_URL_PRODUCT);
//        map.put(7564, NODE_URL_PRODUCT);
//        map.put(8310, NODE_URL_PRODUCT);
//        map.put(8283, NODE_URL_PRODUCT);
//        map.put(8431, NODE_URL_PRODUCT);
//        map.put(8492, NODE_URL_PRODUCT);
//        map.put(8496, NODE_URL_PRODUCT);
//        map.put(8493, NODE_URL_MEMBER);
//        map.put(8691, NODE_URL_DROP);
//        map.put(8713, NODE_URL_PRODUCT);//华为899【彩虹会员】
//        map.put(8711, NODE_URL_PRODUCT);// 华为609
//        map.put(8712, NODE_URL_MEMBER);

    }
}