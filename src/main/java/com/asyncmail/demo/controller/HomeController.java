package com.asyncmail.demo.controller;

import com.asyncmail.demo.service.MailService;
import com.asyncmail.demo.service.SlowService;
import com.asyncmail.demo.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RequestMapping("/home")
@Controller
public class HomeController {

    private static Logger log= LoggerFactory.getLogger(HomeController.class);

    @Resource
    private MailService mailService;

    @Resource
    private SlowService slowService;

    //异步发送一封注册成功的邮件
    @GetMapping("/regmail")
    @ResponseBody
    public String regMail(ModelMap modelMap) {
        mailService.sendHtmlMail();
        return "mail sended";
    }

    //同步sleep1秒
    @GetMapping("/sleep")
    @ResponseBody
    public String sleep() {
        System.out.println(TimeUtil.getMilliTimeNow()+" controller begin");
        slowService.sleepawhile();
        System.out.println(TimeUtil.getMilliTimeNow()+" controller   end");
        return "mail sended";
    }

    //异步执行sleep1秒10次
    @GetMapping("/asyncsleep")
    @ResponseBody
    public Map<String, Object> asyncsleep() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<String> future = slowService.asyncsleepawhile(i);
            futures.add(future);
        }
        List<String> response = new ArrayList<>();
        for (Future future : futures) {
            String string = (String) future.get();
            response.add(string);
        }
        map.put("data", response);
        map.put("消耗时间", String.format("任务执行成功,耗时{%s}毫秒", System.currentTimeMillis() - start));
        return map;
    }
}
