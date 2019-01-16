package cn.aegisa.demo.content.controller;

import cn.aegisa.demo.content.vo.Person;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-01-14 15:53
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestWebController {

    @RequestMapping("/form")
    public Person testFormData(Person person, HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        log.info("Content-Type:" + contentType);
        log.info("入参值：{}", JSON.toJSONString(person));
        return person;
    }

    @RequestMapping("/entity")
    public Person testFromEntity(@RequestBody Person person, HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        log.info("Content-Type:" + contentType);
        log.info("入参值：{}", JSON.toJSONString(person));
        return person;
    }

    @RequestMapping("/post")
    public Person testCustom(Person person, HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        log.info("Content-Type:" + contentType);
        log.info("入参值：{}", JSON.toJSONString(person));
        return person;
    }
}
