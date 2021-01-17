package com.coderman.uploader.config;

import com.coderman.uploader.response.RestApiResponse;
import com.coderman.uploader.response.error.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2021/1/16 14:05
 * @Version 1.0
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add((httpServletRequest, httpServletResponse, o, e) -> {
            String requestUrl = httpServletRequest.getRequestURL().toString();
            final String defaultErrorMsg = "服务器冒烟了,请联系管理员";
            HashMap<String, Object> errorMap = new HashMap<>();
            logger.info("请求地址:{}", requestUrl);
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                logger.info("业务异常,code:{},errorMsg:{}", businessException.getErrorCode().getErrorCode()
                        , businessException.getErrorCode().getErrorMsg());
                errorMap.put("errorCode", businessException.getErrorCode().getErrorCode());
                errorMap.put("errorMsg", businessException.getErrorCode().getErrorMsg());
            }else if(e instanceof NoHandlerFoundException){
                logger.info("接口不存在,code:{},errorMsg:{}",HttpStatus.NOT_FOUND.value(),e.getMessage());
                errorMap.put("errorCode", HttpStatus.NOT_FOUND.value());
                errorMap.put("errorMsg", "接口: ["+httpServletRequest.getServletPath()+"] 不存在");
            } else {
                logger.info("系统异常,code:{},errorMsg:{}", HttpStatus.INTERNAL_SERVER_ERROR.value()
                        , e.getMessage(),e);
                errorMap.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
                errorMap.put("errorMsg", defaultErrorMsg);
            }
            responseError(httpServletResponse,RestApiResponse.error(errorMap));
            return new ModelAndView();
        });
    }

    private void responseError(HttpServletResponse httpServletResponse, RestApiResponse<HashMap<String, Object>> error) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            String json = objectMapper.writeValueAsString(error);
            httpServletResponse.getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置跨域
     * @return
     */
    private CorsConfiguration corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }
}
