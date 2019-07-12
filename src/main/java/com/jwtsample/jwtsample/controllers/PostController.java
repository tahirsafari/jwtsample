//package com.jwtsample.jwtsample.controllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.jwtsample.jwtsample.services.IPostService;
//import com.safari.pg.cbs.def.CbsConstants;
//import com.safari.pg.cbs.def._UserAuthInfo;
//import com.safari.pg.cbsint.CbsAgent;
//import com.safari.pg.cbsint.CbsAuthInterface;
//import com.jwtsample.jwtsample.models.*;
//
//@RestController
//@RequestMapping("/post/")
//public class PostController {
//
//	@Autowired
//	private IPostService postService;
//
//	
//	
//	@GetMapping("/all")
//	public List<Post> getAllPosts(){
//		return postService.getAllPosts();
//	}
//	
//	@PostMapping("/save")
//	public String savePost(@RequestBody Post post){
//		postService.savePost(post);
//		return "Post saved";
//	}
//	
////	@GetMapping("/authenticate")
////	public _UserAuthInfo authenticate() throws Exception {
////	
////		_UserAuthInfo  user = chInterface.authenticate(accessId, password, channelId, authType);
////		System.out.println("result "+user.toString());
////		return user;
////
////	}
//}
