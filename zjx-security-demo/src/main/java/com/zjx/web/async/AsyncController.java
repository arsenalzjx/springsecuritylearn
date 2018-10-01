package com.zjx.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * <p>@ClassName: AsyncController </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/30 9:57</p>
 */
@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @RequestMapping("/order")
    public DeferredResult<String> order() throws Exception{
        logger.info("主线程开始");
        //随机生成一个订单号
        String orderNumber = RandomStringUtils.randomNumeric(8);
        //将随机生成的订单号放了模拟消息队列中,表示产生一个订单消息
        mockQueue.setPlaceOrder(orderNumber);
        //创建DeferredResult,并将其放入deferredResultHolder中管理
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber,result);


//        //开启副线程执行业务
//        Callable<String> result = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                logger.info("副线程开始");
//                //模拟业务执行时间
//                Thread.sleep(1000);
//                logger.info("副线程结束");
//                return "success";
//            }
//        };

        logger.info("主线程结束");
        //释放主线程
        return result;
    }




}
