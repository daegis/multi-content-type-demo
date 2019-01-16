package cn.aegisa.demo.content.resolver;

import cn.aegisa.demo.content.vo.PostEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-01-15 14:19
 */
@Slf4j
@AllArgsConstructor
public class PostEntityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor;

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        String parameterName = parameter.getParameterName();
        if (PostEntity.class.isAssignableFrom(parameterType)) {
            log.info("name:{},type:{}", parameterName, parameterType.getName());
            log.info("matched");
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        String contentType = request.getContentType();
        log.debug("Content-Type:{}", contentType);
        if (APPLICATION_JSON.equalsIgnoreCase(contentType)) {
            return requestResponseBodyMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        } else {
            return servletModelAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }
    }
}
