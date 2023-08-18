package com.seiii.collect.controller.admin;

import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.admin.AdminService;
import com.seiii.collect.service.recommend.RecommendService;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "AdminController")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @Resource
    private RecommendService recommendService;

    @Resource
    private UserService userService;

    @ApiOperation("管理员预览所有任务")
    @GetMapping("/task/all")
    public ResponseVO<List<TaskViewVO>> viewAllTasks(@RequestParam Integer pageId) {
        return adminService.getAllTasks(pageId);
    }

    @ApiOperation("管理员查看任务详细信息")
    @GetMapping("/task/detail/{taskId}")
    public ResponseVO<TaskVO> viewTaskDetails(@PathVariable Integer taskId) {
        return adminService.viewTaskDetails(taskId);
    }

    @ApiOperation("管理员查看推荐规则列表")
    @GetMapping("/recommendRule/list")
    public ResponseVO<List<RecommendRuleVO>> viewRecommendRules() {
        return adminService.getRecommendRules();
    }

    @ApiOperation("管理员查看可选的推荐影响因素列表")
    @GetMapping("/priorityFactor/list")
    public ResponseVO<List<RecommendRuleFactorVO>> viewRecommendRuleFactors() {
        return adminService.getRecommendRuleFactors();
    }

    @ApiOperation("管理员删除推荐规则")
    @DeleteMapping("/recommendRule/delete/{ruleId}")
    public ResponseVO<Object> deleteRecommendRule(@PathVariable Integer ruleId) {
        return adminService.deleteRecommendRule(ruleId);
    }

    @ApiOperation("管理员添加推荐规则")
    @PostMapping("/recommendRule/add")
    public ResponseVO<List<RecommendRuleVO>> addRecommendRule(@RequestBody RecommendRuleDTO recommendRule) {
        return adminService.addRecommendRule(recommendRule);
    }

    @ApiOperation("管理员启用推荐规则")
    @PostMapping("/recommendRule/select/{ruleId}")
    public ResponseVO<Object> selectRecommendRule(@PathVariable Integer ruleId) {
        return adminService.selectRecommendRule(ruleId);
    }

    @ApiOperation("管理员手动调用刷新数据库中的推荐结果")
    @GetMapping("/refreshRecommendResult")
    public ResponseVO<Object> refreshRecommendResult() {
        ResponseVO<Object> responseVO = userService.refreshWorkerAbility();
        if (responseVO.getCode() == Constant.REQUEST_SUCCESS) {
            return recommendService.refreshRecommendationResult();
        } else {
            return responseVO;
        }
    }
}
