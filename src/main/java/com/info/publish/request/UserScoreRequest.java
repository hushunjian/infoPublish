package com.info.publish.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * 用户点赞/踩request
 */
@Data
public class UserScoreRequest {
	
	@ApiModelProperty(value = "操作人Id", required = true)
	private Long operatorUserId;
	
	@ApiModelProperty(value = "被操作人Id", required = true)
	private Long byOperatorUserId;
	
	@ApiModelProperty(value = "true:点赞;false:踩", required = true)
	private Boolean isPraise;
}
