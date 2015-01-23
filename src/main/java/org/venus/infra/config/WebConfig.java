package org.venus.infra.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.venus.web.ControllerMarker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = ControllerMarker.class)
public class WebConfig extends WebMvcConfigurerAdapter {

////	@Bean
//	public ViewResolver jspViewResolver() {
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/WEB-INF/views/jsp/");
//		resolver.setSuffix(".jsp");
//		return resolver;
//	}
//
////	@Bean
//	public ViewResolver jsonViewResolver() {
//		return new ViewResolver() {
//			@Override
//			public View resolveViewName(String viewName, Locale locale) throws Exception {
//				MappingJackson2JsonView view = new MappingJackson2JsonView();
//				view.setPrettyPrint(true);
//				return view;
//			}
//		};
//	}
//
////    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
//        cnvr.setContentNegotiationManager(manager);
// 
//        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
//        resolvers.add(jspViewResolver());
//        resolvers.add(jsonViewResolver());
//         
//        cnvr.setViewResolvers(resolvers);
//        return cnvr;
//    }
    
	/**
	 * Setup a simple strategy: 1. Only path extension is taken into account,
	 * Accept headers are ignored. 2. Return HTML by default when not sure.
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
	}

    @Override
    public void configureViewResolvers(ViewResolverRegistry vrr) {
    	
    	vrr.jsp("/WEB-INF/views/jsp/", ".jsp");
		
    	MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
        vrr.enableContentNegotiation(jsonView);
    }

}
