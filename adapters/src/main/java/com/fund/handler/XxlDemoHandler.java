package com.fund.handler;

import com.fund.service.FundCmdService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


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

    @XxlJob(("updateFundInfo"))
    public void updateFundInfo() throws IOException, InterruptedException {
        log.info("----------开始更新第一批基金信息-----------");
        String[] fileNameArray = {
                "fund_001626_spider.py", "fund_005628_spider.py",
                "fund_006603_spider.py", "fund_008282_spider.py",
                "fund_010003_spider.py", "fund_010391_spider.py",
                "fund_011612_spider.py", "fund_012552_spider.py",
                "fund_012696_spider.py", "fund_012837_spider.py"
        };
        Runtime runtime = Runtime.getRuntime();
        for (String fileName : fileNameArray) {
            String execStatement = "python E:\\GraduationProject\\FundBackground\\adapters\\src\\main\\resources\\spider\\" + fileName;
            // 执行py文件
            Process proc = runtime.exec(execStatement);
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(proc.getErrorStream(), "UTF-8"));
            String line;
            log.info("OUTPUT");
            while ((line = stdoutReader.readLine()) != null) {
                log.info(line);
            }
            while ((line = stderrReader.readLine()) != null) {
                log.info(line);
            }
            int exitVal = proc.waitFor();
            log.info("process exit value is " + exitVal);
            log.info("--正在更新：" + fileName.substring(5, 11));
        }
        log.info("----------更新第一批基金信息完毕-----------");
    }

    @XxlJob(("updateFundInfoTwo"))
    public void updateFundInfoTwo() throws IOException, InterruptedException {
        log.info("----------开始更新第二批基金信息-----------");
        String[] fileNameArray = {
                "fund_012970_spider.py", "fund_013445_spider.py",
                "fund_013446_spider.py", "fund_013894_spider.py",
                "fund_014415_spider.py", "fund_014737_spider.py",
                "fund_014854_spider.py", "fund_015878_spider.py",
                "fund_017469_spider.py", "fund_017900_spider.py"
        };
        Runtime runtime = Runtime.getRuntime();
        for (String fileName : fileNameArray) {
            String execStatement = "python E:\\GraduationProject\\FundBackground\\adapters\\src\\main\\resources\\spider\\" + fileName;
            // 执行py文件
            Process proc = runtime.exec(execStatement);
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(proc.getErrorStream(), "UTF-8"));
            String line;
            log.info("OUTPUT");
            while ((line = stdoutReader.readLine()) != null) {
                log.info(line);
            }
            while ((line = stderrReader.readLine()) != null) {
                log.info(line);
            }
            int exitVal = proc.waitFor();
            log.info("process exit value is " + exitVal);
            log.info("--正在更新：" + fileName.substring(5, 11));
        }
        log.info("----------更新第二批基金信息完毕-----------");
    }

    @XxlJob("demo")
    public ReturnT<String> demo() {
        log.info("测试结束");
        return ReturnT.SUCCESS;
    }
}
