package com.info.publish.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.info.publish.service.IRedisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/infopublish")
@Api(value = "RedisController", description = "测试redis",produces = MediaType.ALL_VALUE)
public class RedisController   {
	
	@Autowired
	private IRedisService redisService;
	
    @ResponseBody
    @GetMapping(value = "setRedisValue")
    @ApiOperation(value="测试redis设值")
	public void setRedisValue(@NotNull @RequestParam String key,
							  @NotNull @RequestParam String value){
    	redisService.set(key, value);
	}
    
    @ResponseBody
    @GetMapping(value = "getRedisValue")
    @ApiOperation(value="测试redis取值")
	public String getRedisValue(@NotNull @RequestParam String key){
    	return redisService.get(key);
	}
    
    @ResponseBody
    @GetMapping(value = "setScoreLog")
    @ApiOperation(value="测试redis设list值")
	public void setScoreLog(@NotNull @RequestParam String key,
			                @NotNull @RequestParam String value){
    	key = "userScoreLog:" + key;
    	redisService.lpush(key, value);
	}
    
    @ResponseBody
    @GetMapping(value = "getScoreLog")
    @ApiOperation(value="测试redis取list值")
	public List<String> getScoreLog(@NotNull @RequestParam String key){
    	key = "userScoreLog:" + key;
    	return redisService.lrange(key);
	}
    
    @ResponseBody
    @GetMapping(value = "deleteScoreLog")
    @ApiOperation(value="测试redis删除list所有值")
	public void deleteScoreLog(){
    	String pattern = "userScoreLog:*";
    	redisService.delete(pattern);
	}
}
