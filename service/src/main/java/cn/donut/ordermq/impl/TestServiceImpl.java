package cn.donut.ordermq.impl;

import cn.donut.ordermq.mapper.TestMapper;
import cn.donut.ordermq.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjiahao
 * @date 2018/5/15 15:47
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public String test(){
        return "testService";
    }

}
