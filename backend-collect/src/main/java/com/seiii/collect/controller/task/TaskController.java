package com.seiii.collect.controller.task;

import com.seiii.collect.model.dto.task.TaskDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.task.TaskFlawDetectionPredictionVO;
import com.seiii.collect.model.vo.task.TaskRadarVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.task.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "TaskController")
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @ApiOperation("预览所有任务列表")
    @GetMapping("/unfinished")
    public ResponseVO<List<TaskViewVO>> viewAllUnfinishedTasks(@RequestParam Integer pageId, @RequestParam Integer userId) {
        return taskService.viewAllUnfinishedTasks(pageId, userId);
    }

    @ApiOperation("查看任务详细信息")
    @GetMapping("/detail/{taskId}")
    public ResponseVO<TaskVO> viewTaskDetails(@PathVariable Integer taskId, @RequestParam Integer userId) {
        return taskService.viewTaskDetails(taskId, userId);
    }

    @ApiOperation("通过请求体发布任务")
    @PostMapping("/issue")
    public ResponseVO<TaskVO> createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @ApiOperation("选取任务")
    @PostMapping("/pick/{taskId}")
    public ResponseVO<TaskVO> pickTask(@PathVariable Integer taskId, @RequestParam Integer userId) {
        return taskService.pickTask(taskId, userId);
    }

    @ApiOperation("获取报告Id")
    @GetMapping("/detail/getreport/{taskId}")
    public ResponseVO<Integer> getReportId(@PathVariable Integer taskId, @RequestParam Integer userId) {
        return taskService.getReportId(taskId, userId);
    }

    @ApiOperation("发包方查看可选的带默认权重的推荐元素列表")
    @GetMapping("/taskRecommendFactor/list")
    public ResponseVO<List<RecommendRuleFactorVO>> getRecommendElements(){
        return taskService.getRecommendElements();
    }

    @ApiOperation("发包方更新此任务的推荐权重")
    @PostMapping("/taskRecommendFactor/set/{taskId}")
    public ResponseVO<List<RecommendRuleFactorVO>> updateRecommendElementsWeights(@PathVariable Integer taskId,@RequestBody List<RecommendRuleFactorVO> recommendRuleFactorVOs){
        return taskService.updateRecommendElementsWeights(taskId,recommendRuleFactorVOs);
    }

    @ApiOperation("发包方查看此任务的推荐权重")
    @GetMapping("/taskRecommendFactor/get/{taskId}")
    public ResponseVO<List<RecommendRuleFactorVO>> getRecommendElementsWeights(@PathVariable Integer taskId){
        return taskService.getRecommendElementsWeights(taskId);
    }


    @ApiOperation("发包方查看此任务的当前工人的雷达图值，包括工人能力、活跃度、相关性、多样性")
    @GetMapping("/taskRadar/current/{taskId}")
    ResponseVO<TaskRadarVO> getCurrentTaskRadar(@PathVariable Integer taskId){
        return taskService.getCurrentTaskRadar(taskId);
    }

    @ApiOperation("发包方查看此任务的目标雷达图值，包括工人能力、活跃度、相关性、多样性")
    @GetMapping("/taskRadar/target/{taskId}")
    ResponseVO<TaskRadarVO> getTargetTaskRadar(@PathVariable Integer taskId){
        return taskService.getTargetTaskRadar(taskId);
    }

    @ApiOperation("发包方手动停止招募")
    @PostMapping("/stop/{taskId}")
    ResponseVO<Object> stopTaskRecruit(@PathVariable Integer taskId){
        return taskService.stopTaskRecruit(taskId);
    }

    @ApiOperation("发包方查看此任务的新缺陷发现曲线及预测的总缺陷数")
    @GetMapping("/taskFlawDetection/{taskId}")
    ResponseVO<TaskFlawDetectionPredictionVO> getFlawCurveAndNumPredicted(@PathVariable Integer taskId){
        return taskService.getFlawCurveAndNumPredicted(taskId);
    }
}
