package com.jqh.SpringSecurityDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

	//  可以直接访问
	@RequestMapping("/")
	public String home(){
		return "this is home page";
	}


	// 需要认证
	@RequestMapping("/hello")
	public  String hello(){
		return "hello world";
	}

	// 只有管理员权限才能进入  ROLE_角色名
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin")
	public String adminEnter(){
		return "this is admin page";
	}

	// 普通或则管理员用户能进入
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")  // 请求前验证
	//@PostAuthorize("hasRole(''ROLE_USER)")                          //  请求后验证
//	@PreFilter("")
//	@PostFilter("")
	@RequestMapping("/user")
	public String userEnter(){
		return "this is user page";
	}


	// 对参数进行检测 , id必须小于10，username必须是当期的登录的id
	//http://127.0.0.1:8080/checkparams?id=9&username=user
	@PreAuthorize("#id<10 and principal.username.equals(#username)")
	//	@PreAuthorize("#id<10 and pricipal.username.equals(#username) and #user.username.equals('abc')")
	@RequestMapping("/checkparams")
	public String checkParams(Integer id , String username /**, User user**/){
		return "check success";
	}

	//  对数组进行限制
	@PreFilter("filterObject%2==0")
	@PostFilter("filterObject%4==0")
	@RequestMapping("/checklist")
	public List<Integer> checkList(List<Integer> idList){
		return idList;
	}
}
