package com.seiii.collect.service.user;

import com.seiii.collect.model.dto.user.UserDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    // 用户注册
    ResponseVO<Object> userRegister(UserDTO user);

    // 用户登录验证
    ResponseVO<UserViewVO> userLogin(String username, String password);

    // 根据id获取用户简略信息
    ResponseVO<UserViewVO> getUserInfo(Integer userId);

    // 根据id列表获取简略信息列表
    ResponseVO<List<UserViewVO>> getUserInfoList(List<Integer> userIds);

    // 查看用户个人信息
    ResponseVO<UserVO> viewUser(Integer userId);

    //众包工人查看个人历史完成已交报告的任务列表/发包方查看已发布的完成的任务列表（预览）
    ResponseVO<List<TaskViewVO>> viewFinishedTasks(Integer userId, Integer pageId);

    //众包工人查看正在执行的任务列表/发包方查看已发布的正在执行中的任务列表（预览）
    ResponseVO<List<TaskViewVO>> viewUnfinishedTasks(Integer userId, Integer pageId);

    //众包工人查看个人逾期未交报告的任务
    ResponseVO<List<TaskViewVO>> viewExpiredTasks(Integer userId, Integer pageId);

    //预览任务中的报告列表。对于众包工人和发包方点进任务可看到相应的测试报告
    ResponseVO<List<ReportViewVO>> viewReportsFromTask(Integer userId, Integer taskId, Integer pageId);

    //预览众包工人自己的所有报告
    ResponseVO<List<ReportViewVO>> viewWorkerAllReports(Integer userId, Integer pageId);

    //查看众包工人关于某个任务的报告
    ResponseVO<ReportViewVO> viewWorkerTaskReport(Integer userId, Integer taskId);

    //用户添加某个标签
    ResponseVO<Object> addUserTag(Integer userId, Integer tag);

    //用户删除某个标签
    ResponseVO<Object> deleteUserTag(Integer userId, Integer tag);

    //工人获取个人测试的环境
    ResponseVO<WorkerContextVO> getWorkerContext(Integer userId);

    //工人修改个人测试的环境
    ResponseVO<WorkerContextVO> updateWorkerContext(Integer userId, WorkerContextDTO workerContextDTO);

    // 获取工人对应的雷达图
    ResponseVO<WorkerRadarVO> getWorkerRadar(Integer userId);

    // 获取所有工人的平均雷达图
    ResponseVO<WorkerRadarVO> getWorkerAvgRadar();

    // 获取工人对应的活跃度指数
    ResponseVO<WorkerActivationVO> getWorkerActiveness(Integer userId);

    // 获取用户对应词云
    ResponseVO<List<WorkerCloudVO>> getWorkerWordCloud(@PathVariable Integer userId);

    // 定期刷新工人能力
    ResponseVO<Object> refreshWorkerAbility();
}