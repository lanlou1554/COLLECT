package com.seiii.collect.service.task;

import com.seiii.collect.model.dto.task.TaskDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.task.TaskFlawDetectionPredictionVO;
import com.seiii.collect.model.vo.task.TaskRadarVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TaskService {
    // 预览所有未完成的任务列表
    ResponseVO<List<TaskViewVO>> viewAllUnfinishedTasks(Integer pageId, Integer userId);

    // 查看任务详细信息
    ResponseVO<TaskVO> viewTaskDetails(Integer taskId, Integer userId);

    // 推荐算法调用，无需userid
    ResponseVO<TaskVO> viewTaskDetails(Integer taskId);

    // 通过请求体发布任务
    ResponseVO<TaskVO> createTask(TaskDTO taskDTO);

    // 选取任务
    ResponseVO<TaskVO> pickTask(Integer taskId, Integer userId);

    // 获取报告Id
    ResponseVO<Integer> getReportId(Integer taskId, Integer userId);

    //发包方查看可选的带默认权重的推荐元素列表
    ResponseVO<List<RecommendRuleFactorVO>> getRecommendElements();

    //发包方更新此任务的推荐权重
    ResponseVO<List<RecommendRuleFactorVO>> updateRecommendElementsWeights(Integer taskId, List<RecommendRuleFactorVO> recommendRuleFactorVOs);

    //发包方查看此任务的推荐权重
    ResponseVO<List<RecommendRuleFactorVO>> getRecommendElementsWeights(Integer taskId);

    //发包方查看此任务的当前工人的雷达图值，包括工人能力、活跃度、相关性、多样性
    ResponseVO<TaskRadarVO> getCurrentTaskRadar(Integer taskId);

    //发包方查看此任务的目标雷达图值，包括工人能力、活跃度、相关性、多样性
    ResponseVO<TaskRadarVO> getTargetTaskRadar(Integer taskId);

    //发包方手动停止招募
    ResponseVO<Object> stopTaskRecruit(Integer taskId);

    //发包方查看此任务的新缺陷发现曲线及预测的总缺陷数
    ResponseVO<TaskFlawDetectionPredictionVO> getFlawCurveAndNumPredicted(Integer taskId);

}
