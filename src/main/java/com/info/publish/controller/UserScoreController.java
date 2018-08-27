package com.info.publish.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.info.publish.request.GetAllUserScoreRequest;
import com.info.publish.request.UserScoreRequest;
import com.info.publish.response.Response;
import com.info.publish.service.IUserScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/infopublish/userScore")
@Api(value = "UserScoreController", description = "用户评分",produces = MediaType.ALL_VALUE)
public class UserScoreController   {
	
	@Autowired
	private IUserScoreService userScoreService;
	
	@ResponseBody
    @GetMapping(value = "getUserScoreByUserId")
    @ApiOperation(value="根据用户主键id获取用户分数(app)")
	public Response getUserScoreByUserId(@NotNull @RequestParam Long userId){
		return userScoreService.getUserScoreByUserId(userId);
	}
	
	@ResponseBody
    @PostMapping(value = "getAllUserScore")
    @ApiOperation(value="获取所有用户分数(后台管理)")
	public Response getAllUserScore(@Validated @RequestBody GetAllUserScoreRequest request){
		return userScoreService.getAllUserScore(request);
	}
	
	@ResponseBody
    @PostMapping(value = "doUserScore")
    @ApiOperation(value="用户对其他用户进行点赞/踩(app)")
	public Response doUserScore(@Validated @RequestBody UserScoreRequest request){
		return userScoreService.doUserScore(request);
	}
	
}
