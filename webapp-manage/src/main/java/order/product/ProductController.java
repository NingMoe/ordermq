/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductController
 * Author:   LiYuAn
 * Date:     2018/7/6 14:31
 * Description: 产品服务接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package order.product;

import com.koolearn.sharks.model.Product;
import com.koolearn.sharks.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br>
 * 〈产品服务接口〉
 *
 * @author LiYuAn
 * @create 2018/7/6
 * @since 1.0.0
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    //鲨鱼产品查询接口
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/url", method = RequestMethod.GET)
    @ResponseBody
    public String getUrl(@RequestParam Integer productId) {
        if (productId == null) {
            return null;
        }
        Product product = productService.findProductById(productId);
        if (null != product) {
            return product.getProductInfoUrl();
        }

        return null;
    }
}
