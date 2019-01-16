package cn.aegisa.demo.content.config;

import cn.aegisa.demo.content.resolver.PostEntityHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-01-15 14:46
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerAdapter ha;

    private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor = null;
    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = null;

    @PostConstruct
    private void init() {
        List<HandlerMethodArgumentResolver> argumentResolvers = ha.getArgumentResolvers();
        for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
            if (argumentResolver instanceof ServletModelAttributeMethodProcessor) {
                servletModelAttributeMethodProcessor = (ServletModelAttributeMethodProcessor) argumentResolver;
            } else if (argumentResolver instanceof RequestResponseBodyMethodProcessor) {
                requestResponseBodyMethodProcessor = (RequestResponseBodyMethodProcessor) argumentResolver;
            }
            if (servletModelAttributeMethodProcessor != null && requestResponseBodyMethodProcessor != null) {
                break;
            }
        }
        PostEntityHandlerMethodArgumentResolver postEntityHandlerMethodArgumentResolver = new PostEntityHandlerMethodArgumentResolver(requestResponseBodyMethodProcessor, servletModelAttributeMethodProcessor);
        List<HandlerMethodArgumentResolver> newList = new ArrayList<>(argumentResolvers.size() + 1);
        newList.add(postEntityHandlerMethodArgumentResolver);
        newList.addAll(argumentResolvers);
        ha.setArgumentResolvers(newList);
    }

}
