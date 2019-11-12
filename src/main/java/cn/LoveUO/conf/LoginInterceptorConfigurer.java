package cn.LoveUO.conf;

import java.util.ArrayList;
import java.util.List;

import cn.LoveUO.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class LoginInterceptorConfigurer
	implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截白名单
		List<String> patterns = new ArrayList<>();
		patterns.add("/bootstrap3/**");
		patterns.add("/css/**");
		patterns.add("/images/**");
		patterns.add("/js/**");
		patterns.add("/upload/**");
		patterns.add("/users/reg");
		patterns.add("/users/login");
		patterns.add("/users/getCode");
		patterns.add("/users/checkUsername");
		patterns.add("/users/toFindPassword/**");
		patterns.add("/web/toFindPassword3.html");
		patterns.add("/web/404.html");
		patterns.add("/web/register.html");
		patterns.add("/web/login.html");
		patterns.add("/web/index.html");
		patterns.add("/web/diary.html");
		patterns.add("/web/changePasswordByEmail.html");
		patterns.add("/diaries/allInfo");
		patterns.add("/diaries/getUid");
		patterns.add("/diaries/getPage");
		patterns.add("/users/checkCode");
		patterns.add("/web/header1.html");
		patterns.add("/web/header2.html");
		patterns.add("/web/footer.html");
		patterns.add("/lovedata/lovetime/index.html");
		patterns.add("/lovedata/lovetime/renxi/*");
		patterns.add("/index.html");
		// 添加拦截器
		registry.addInterceptor(new LoginInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns(patterns);
	}
	
}





