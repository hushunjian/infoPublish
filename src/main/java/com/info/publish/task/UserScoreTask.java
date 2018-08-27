package com.info.publish.task;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.info.publish.Constant;
import com.info.publish.domain.AppUsers;
import com.info.publish.mapper.AppUsersMapper;
import com.info.publish.service.IRedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
public class UserScoreTask {
	
	@Autowired
	private IRedisService redisService;
	@Autowired
	private AppUsersMapper appusersMapper;
	
    @Scheduled(cron = "${task.execute.userScoreTask}")
    public void execute() {
    	log.info("开始执行用户分数任务");
    	// 将redis中的数据同步到用户记录中
    	//1: 获取到redis中所有的用户分数缓存
    	String userScorePattern = Constant.USER_SCORE + "*";
    	Set<String> keys = redisService.keys(userScorePattern);
    	for(String key : keys){
    		Long userScore = Long.valueOf(redisService.get(key));
    		Long userId = Long.valueOf(key.replace(Constant.USER_SCORE, ""));
    		// 根据userId判断用户是否存在
    		AppUsers appUsers = appusersMapper.selectByPrimaryKey(userId);
    		if(appUsers != null){
    			appUsers.setUserScore(userScore);
    			// 更新数据库中的用户记录
    			appusersMapper.updateByPrimaryKeySelective(appUsers);
    			log.info("更新[{}]的分数为[{}]",appUsers.getNickName(),appUsers.getUserScore());
    		}
    	}
    	//2: 将用户评分记录删除
    	String userScoreLogPattern = Constant.USER_SCORE_LOG + "*";
    	log.info("删除用户评分记录");
    	redisService.delete(userScoreLogPattern);
    	log.info("用户评分每日任务完成");
    }
}
