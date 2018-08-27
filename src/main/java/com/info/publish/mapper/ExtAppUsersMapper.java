package com.info.publish.mapper;

import java.util.List;

import com.info.publish.domain.AppUsers;
import com.info.publish.request.GetAllUserScoreRequest;

public interface ExtAppUsersMapper {

	List<AppUsers> getAllUserScore(GetAllUserScoreRequest request);

}
