package com.seiii.collect.controller.user;

import com.seiii.collect.model.dto.user.UserDTO;
import com.seiii.collect.model.dto.user.UserFormDTO;
import com.seiii.collect.model.dto.user.worker.WorkerContextDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.model.vo.user.UserVO;
import com.seiii.collect.model.vo.user.UserViewVO;
import com.seiii.collect.model.vo.user.worker.WorkerActivationVO;
import com.seiii.collect.model.vo.user.worker.WorkerCloudVO;
import com.seiii.collect.model.vo.user.worker.WorkerContextVO;
import com.seiii.collect.model.vo.user.worker.WorkerRadarVO;
import com.seiii.collect.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResponseVO<Object> register(@RequestBody UserDTO user) {
        return userService.userRegister(user);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseVO<UserViewVO> login(@RequestBody UserFormDTO userForm) {
        return userService.userLogin(userForm.getUsername(), userForm.getPassword());
    }

    @ApiOperation("根据id获取用户简略信息")
    @GetMapping("/info/{userId}")
    public ResponseVO<UserViewVO> getUserInfo(@PathVariable Integer userId) {
        return userService.getUserInfo(userId);
    }

    @ApiOperation("根据id列表获取简略信息列表")
    @PostMapping("/info/list")
    public ResponseVO<List<UserViewVO>> getUserInfoList(@RequestBody List<Integer> userIds) {
        return userService.getUserInfoList(userIds);
    }

    @ApiOperation("查看用户个人信息")
    @GetMapping("/view/{userId}")
    public ResponseVO<UserVO> viewUser(@PathVariable Integer userId) {
        return userService.viewUser(userId);
    }

    @ApiOperation("预览已完成任务")
    @GetMapping("/task/finished/{userId}")
    public ResponseVO<List<TaskViewVO>> viewFinishedTasks(@PathVariable Integer userId, @RequestParam Integer pageId) {
        return userService.viewFinishedTasks(userId, pageId);
    }

    @ApiOperation("预览进行中任务")
    @GetMapping("/task/unfinished/{userId}")
    public ResponseVO<List<TaskViewVO>> viewUnfinishedTasks(@PathVariable Integer userId, @RequestParam Integer pageId) {
        return userService.viewUnfinishedTasks(userId, pageId);
    }

    @ApiOperation("预览逾期未交报告的任务")
    @GetMapping("/task/expired/{userId}")
    public ResponseVO<List<TaskViewVO>> viewExpiredTasks(@PathVariable Integer userId, @RequestParam Integer pageId) {
        return userService.viewExpiredTasks(userId, pageId);
    }

    @ApiOperation("从任务中预览报告")
    @GetMapping("/task/report/{userId}")
    public ResponseVO<List<ReportViewVO>> viewReportsFromTask(@PathVariable Integer userId, @RequestParam Integer taskId, @RequestParam Integer pageId) {
        return userService.viewReportsFromTask(userId, taskId, pageId);
    }

    @ApiOperation("预览自己的所有报告")
    @GetMapping("/report/{userId}")
    public ResponseVO<List<ReportViewVO>> viewWorkerAllReports(@PathVariable Integer userId, @RequestParam Integer pageId) {
        return userService.viewWorkerAllReports(userId, pageId);
    }

    @ApiOperation("众包工人获取自己关于某个任务的报告")
    @GetMapping("/workerreport/{userId}")
    public ResponseVO<ReportViewVO> viewWorkerTaskReport(@PathVariable Integer userId, @RequestParam Integer taskId) {
        return userService.viewWorkerTaskReport(userId, taskId);
    }

    @ApiOperation("用户添加标签")
    @PostMapping("/tag/add")
    public ResponseVO<Object> addUserTag(@RequestParam Integer userId, @RequestParam Integer tag) {
        return userService.addUserTag(userId, tag);
    }

    @ApiOperation("用户删除标签")
    @DeleteMapping("/tag/delete")
    public ResponseVO<Object> deleteUserTag(@RequestParam Integer userId, @RequestParam Integer tag) {
        return userService.deleteUserTag(userId, tag);
    }

    @ApiOperation("工人获取个人测试的环境")
    @GetMapping("/worker/context/{userId}")
    public ResponseVO<WorkerContextVO> getWorkerContext(@PathVariable Integer userId){
        return userService.getWorkerContext(userId);
    }

    @ApiOperation("工人修改个人测试的环境")
    @PostMapping("/worker/contextUpdate/{userId}")
    public ResponseVO<WorkerContextVO> updateWorkerContext(@PathVariable Integer userId, @RequestBody WorkerContextDTO workerContextDTO){
        return userService.updateWorkerContext(userId,workerContextDTO);
    }

    @ApiOperation("获取工人对应的雷达图")
    @GetMapping("/worker/radar/{userId}")
    public ResponseVO<WorkerRadarVO> getWorkerRadar(@PathVariable Integer userId){
        return userService.getWorkerRadar(userId);
    }

    @ApiOperation("获取所有工人的平均雷达图")
    @GetMapping("/worker/radar/avg")
    public ResponseVO<WorkerRadarVO> getWorkerAvgRadar(){
        return userService.getWorkerAvgRadar();
    }

    @ApiOperation("获取工人对应的活跃度指数")
    @GetMapping("/worker/activation/{userId}")
    public ResponseVO<WorkerActivationVO> getWorkerActiveness(@PathVariable Integer userId){
        return userService.getWorkerActiveness(userId);
    }

    @ApiOperation("获取工人对应的关键词词云")
    @GetMapping("/worker/wordcloud/{userId}")
    public ResponseVO<List<WorkerCloudVO>> getWorkerWordCloud(@PathVariable Integer userId){
        return userService.getWorkerWordCloud(userId);
    }

}
