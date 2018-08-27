package com.info.publish.service;

import com.info.publish.request.GetAllUserScoreRequest;
import com.info.publish.request.UserScoreRequest;
import com.info.publish.response.Response;

public interface IUserScoreService {

	Response getUserScoreByUserId(Long userId);

	Response getAllUserScore(GetAllUserScoreRequest request);

	Response doUserScore(UserScoreRequest request);

}
