package com.info.publish.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.publish.Constant;
import com.info.publish.domain.AppUsers;
import com.info.publish.mapper.AppUsersMapper;
import com.info.publish.mapper.ExtAppUsersMapper;
import com.info.publish.request.GetAllUserScoreRequest;
import com.info.publish.request.UserScoreRequest;
import com.info.publish.response.Response;
import com.info.publish.service.IRedisService;
import com.info.publish.service.IUserScoreService;

@Service
public class UserScoreService implements IUserScoreService {
	
	@Autowired
	private IRedisService redisService;
	@Autowired
	private AppUsersMapper appUsersMapper;
	@Autowired
	private ExtAppUsersMapper extAppUsersMapper;
	
	/*
	 * 根据用户主键id获取用户分数
	 */
	@Override
	public Response getUserScoreByUserId(Long userId) {
		String key = Constant.USER_SCORE + userId;
		// 判断该用户是否存在
		AppUsers appUsers = appUsersMapper.selectByPrimaryKey(userId);
		if(appUsers == null){
			return Response.INSTANCE.failure("用户不存在,请确认参数");
		}
		if(!redisService.exists(key)){
			redisService.set(key, String.valueOf(appUsers.getUserScore()));
		}
		return Response.INSTANCE.success(Constant.SUCCESS_CODE, "获取用户分数成功", redisService.get(key));
	}
	
	/*
	 * 获取所有用户分数
	 */
	@Override
	public Response getAllUserScore(GetAllUserScoreRequest request) {
		if(request.getPageIndex() == null || request.getPageIndex() <= 0){
			request.setPageIndex(1L);
		}
		if(request.getPageSize() == null || request.getPageSize() <= 0){
			request.setPageSize(10L);
		}
		List<AppUsers> appUsers = extAppUsersMapper.getAllUserScore(request);
		return Response.INSTANCE.success(Constant.SUCCESS_CODE,"获取所有用户分数成功", appUsers);
	}
	
	/*
	 * 用户对其他用户进行点赞/踩
	 */
	@Override
	public Response doUserScore(UserScoreRequest request) {
		AppUsers operatorUser = appUsersMapper.selectByPrimaryKey(request.getOperatorUserId());
		if(operatorUser == null){
			return Response.INSTANCE.failure("操作用户不存在,请确认参数");
		}
		AppUsers byOperatorUser = appUsersMapper.selectByPrimaryKey(request.getByOperatorUserId());
		if(byOperatorUser == null){
			return Response.INSTANCE.failure("被操作用户不存在,请确认参数");
		}
		// 判断redis中是否存在操作用户的操作缓存
		String key = Constant.USER_SCORE_LOG + request.getOperatorUserId();
		if(!redisService.exists(key)){
			// 不存在该用户的操作记录
			redisService.lpush(key, String.valueOf(request.getByOperatorUserId()));
		}else{
			// 判断操作用户是否已经对被操作用户进行过操作
			List<String> operatorUserScoredUser = redisService.lrange(key);
			if(operatorUserScoredUser.contains(String.valueOf(request.getByOperatorUserId()))){
				// 如果已存在操作者对被操作者的评分记录
				return Response.INSTANCE.failure("每个用户每天只能对同一用户进行一次评分操作,请确认");
			}
		}
		// 判断是否存在被操作人的分数缓存
		String byOperatorUserScoreKey = Constant.USER_SCORE + request.getByOperatorUserId();
		if(!redisService.exists(byOperatorUserScoreKey)){
			redisService.set(byOperatorUserScoreKey, String.valueOf(byOperatorUser.getUserScore()));
		}
		if(request.getIsPraise()){
			// 点赞(被操作者分数在redis缓存中自动递增1)
			redisService.incr(byOperatorUserScoreKey);
		}else{
			// 踩(被操作者分数在redis缓存中自动递减1)
			redisService.decr(byOperatorUserScoreKey);
		}
		return Response.INSTANCE.success("评分成功");
	}

}
