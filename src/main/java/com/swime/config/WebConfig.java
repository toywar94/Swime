package com.swime.config;

import com.swime.util.CheckOS;
import com.swime.util.CustomCorsFilter;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, SecurityConfig.class, WebSocketConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //첨부파일
    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        CheckOS checkOS = CheckOS.getInstance();

        final String uploadFolder = checkOS.withoutDeleteFilePath();

        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");

        MultipartConfigElement multipartConfig = new MultipartConfigElement
                (uploadFolder + "temp", 20971520,
                        41943040, 20971520);
        registration.setMultipartConfig(multipartConfig);
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter =
                new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

//        CustomCorsFilter customCorsFilter = new CustomCorsFilter();

        return new Filter[]{characterEncodingFilter};
    }

}


