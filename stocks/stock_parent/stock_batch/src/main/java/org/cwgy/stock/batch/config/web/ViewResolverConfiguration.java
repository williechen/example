package org.cwgy.stock.batch.config.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring Boot前端Jsp與thymeleaf共存
 * 
 * @author willie chen
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan
public class ViewResolverConfiguration implements WebMvcConfigurer {
	Logger log = LogManager.getLogger(getClass());

	@Bean
	public InternalResourceViewResolver viewResolver() {
		log.info("viewResolver start ...");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		// 不在此加上jsp檔案所在資料目錄，而在controller回傳值才加上檔案所在目錄
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");
		resolver.setViewNames("jsp/*");
		resolver.setOrder(1);
		return resolver;
	}

	@Bean
	public ITemplateResolver templateResolver() {
		log.info("templateResolver start ...");
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setTemplateMode("HTML");
		// 不在此加上jsp檔案所在資料目錄，而在controller回傳值才加上檔案所在目錄
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("utf-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		log.info("templateEngine start ...");
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolverThymeLeaf() {
		log.info("viewResolverThymeLeaf start ...");
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("utf-8");
		viewResolver.setOrder(2);
		// 網頁檔案存放目錄需符合viewNames中的值才可被成功解析顯示
		viewResolver.setViewNames(new String[] { "html/*", "vue/*", "templates/*", "th/*" });
		return viewResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		log.info("configureDefaultServletHandling start ...");
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("addResourceHandlers start ...");
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/static/");
	}
}
