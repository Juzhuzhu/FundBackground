package com.fund.handler;

import com.fund.service.FundCmdService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * xxl-job拦截器
 * <p>
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、任务开发：在Spring Bean实例中，开发Job方法；
 * 2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 * 4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 * *
 * <p>
 * Create at 2023/04/23 22:30
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/23
 * @since 1.0.0
 */
@Slf4j
@Component
public class XxlDemoHandler {

    private final FundCmdService fundCmdService;

    public XxlDemoHandler(FundCmdService fundCmdService) {
        this.fundCmdService = fundCmdService;
    }

    @XxlJob(("calculateEarnings"))
    public void calculateEarnings() {
        log.info("----------开始计算用户收益-----------");
        fundCmdService.calculateEarnings();
        log.info("----------计算用户收益完毕-----------");
    }

    @XxlJob("demo")
    public ReturnT<String> demo() {
        log.info("测试结束");
        return ReturnT.SUCCESS;
    }
}
