package com.seiii.collect.service.admin;

import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;

import java.util.List;

public interface AdminService {
    //获取所有任务
    ResponseVO<List<TaskViewVO>> getAllTasks(Integer pageId);

    // 管理员查看任务详细信息
    ResponseVO<TaskVO> viewTaskDetails(Integer taskId);

    //管理员获取推荐规则列表
    ResponseVO<List<RecommendRuleVO>> getRecommendRules();

    //管理员获取推荐影响因素列表
    ResponseVO<List<RecommendRuleFactorVO>> getRecommendRuleFactors();

    //管理员删除推荐规则
    ResponseVO<Object> deleteRecommendRule(Integer ruleId);

    //管理员添加推荐规则
    ResponseVO<List<RecommendRuleVO>> addRecommendRule(RecommendRuleDTO recommendRuleDTO);

    //管理员启用推荐规则
    ResponseVO<Object> selectRecommendRule(Integer ruleId);
}
