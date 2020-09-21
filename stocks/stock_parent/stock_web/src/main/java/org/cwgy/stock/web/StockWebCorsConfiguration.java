package org.cwgy.stock.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class StockWebCorsConfiguration {
	/**
     * 跨域過濾器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); 
        return new CorsFilter(source);
    }
    
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允許跨網域請求的來源
        corsConfiguration.addAllowedOrigin("*");
        //允許跨域攜帶cookie資訊，預設跨網域請求是不攜帶cookie資訊的。
        corsConfiguration.setAllowCredentials(true);
        //允許哪些Header
        corsConfiguration.addAllowedHeader("*");
        //允許使用那些請求方式
        corsConfiguration.addAllowedMethod("*");
        // 預檢請求的有效期，單位為秒。
        corsConfiguration.setMaxAge(3600L);
        //可獲取哪些Header（因為跨網域預設不能取得全部Header資訊）
        corsConfiguration.addExposedHeader("X-Authorization");
        
        return corsConfiguration;
    }    

}
