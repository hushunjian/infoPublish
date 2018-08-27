package com.info.publish.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * 根据条件查询用户分数request
 */
@Data
public class GetAllUserScoreRequest {
	
	// 自由添加其他查询条件
	
	@ApiModelProperty(value = "当前页", required = true)
	private Long pageIndex;
	
	@ApiModelProperty(value = "每页条数", required = true)
	private Long pageSize;
}
