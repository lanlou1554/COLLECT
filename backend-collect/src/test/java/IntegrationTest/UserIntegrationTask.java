package IntegrationTest;

import com.seiii.collect.controller.user.UserController;
import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.mapper.user.UserTagMapper;
import com.seiii.collect.model.dto.user.UserDTO;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.model.vo.user.UserVO;
import com.seiii.collect.model.vo.user.UserViewVO;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.serviceimpl.user.UserServiceImpl;
import com.seiii.collect.util.Constant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserIntegrationTask {
    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    ReportMapper reportMapper;
    @Mock
    TaskMapper taskMapper;
    @Mock
    TaskUserMapper taskUserMapper;
    @Mock
    UserTagMapper userTagMapper;
    @Mock
    TaskTagMapper taskTagMapper;
    @Mock
    FlawMapper flawMapper;

    @Test
    public void testUserRegister() throws Exception {
        when(userMapper.selectByUsername("temp")).thenReturn(new User());
        when(userMapper.insert(new User())).thenReturn(anyInt());
        ResponseVO<Object> result = userServiceImpl.userRegister(new UserDTO());
        Assert.assertEquals(new ResponseVO<Object>(Integer.valueOf(Constant.REQUEST_FAIL), "用户名或密码不能为空！", null), result);
    }

    @Test
    public void testUserLogin() throws Exception {
        when(userMapper.selectByUsername("temp")).thenReturn(new User());
        ResponseVO<UserViewVO> result = userServiceImpl.userLogin("aaaaa", "admin123");
        Assert.assertEquals(new ResponseVO<UserViewVO>(Constant.REQUEST_FAIL, "该用户名不存在！", null), result);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        ResponseVO<UserViewVO> result = userServiceImpl.getUserInfo(1);
        Assert.assertEquals(new ResponseVO<UserViewVO>(Constant.REQUEST_SUCCESS, "请求成功", new UserViewVO(null, null, null)), result);
    }

    @Test
    public void testViewUser() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        ResponseVO<UserVO> result = userServiceImpl.viewUser(1);
        // Assert.assertEquals(new ResponseVO<UserVO>(Constant.REQUEST_SUCCESS, "请求成功", new UserVO()), result);

    }

    @Test
    public void testGetUserInfoList() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        ResponseVO<List<UserViewVO>> result = userServiceImpl.getUserInfoList(new LinkedList<>());
        Assert.assertEquals(new ResponseVO<List<UserViewVO>>(Constant.REQUEST_SUCCESS, "请求成功", new LinkedList<>()), result);
    }

    @Test
    public void testViewFinishedTasks1() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        when(reportMapper.selectByUserIdAndPage(anyInt(), anyInt(), anyInt())).thenReturn(new LinkedList<>());
        when(taskMapper.selectByIds(new LinkedList<>())).thenReturn(new LinkedList<>());
        ResponseVO<List<TaskViewVO>> result = userServiceImpl.viewFinishedTasks(1, 1);
        Assert.assertEquals(new ResponseVO<List<TaskViewVO>>(Integer.valueOf(Constant.REQUEST_FAIL), "管理员没有任务", null), result);
    }

    @Test
    public void testViewUnfinishedTasks1() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        when(taskUserMapper.selectByUserId(anyInt())).thenReturn(new LinkedList<>());
        when(taskMapper.selectByIds(new LinkedList<>())).thenReturn(new LinkedList<>());
        ResponseVO<List<TaskViewVO>> result = userServiceImpl.viewUnfinishedTasks(1, 1);
        Assert.assertEquals(new ResponseVO<List<TaskViewVO>>(Integer.valueOf(Constant.REQUEST_FAIL), "管理员没有任务", null), result);
    }

    @Test
    public void testViewExpiredTasks1() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        when(taskUserMapper.selectByUserId(anyInt())).thenReturn(new LinkedList<>());
        when(taskMapper.selectByIds(new LinkedList<>())).thenReturn(new LinkedList<>());
        ResponseVO<List<TaskViewVO>> result = userServiceImpl.viewExpiredTasks(1, 1);
        Assert.assertEquals(new ResponseVO<List<TaskViewVO>>(Integer.valueOf(Constant.REQUEST_FAIL), "管理员没有任务", null), result);
    }

//    @Test
//    public void viewReportsFromTask1() throws Exception{
//        when(reportMapper.selectByTaskId(anyInt())).thenReturn(new LinkedList<>());
//        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
//        ResponseVO<List<ReportViewVO>> result = userServiceImpl.viewReportsFromTask(1,1,1);
//        Assert.assertEquals(new ResponseVO<List<ReportViewVO>>(Integer.valueOf(Constant.REQUEST_FAIL), "管理员没有报告", null), result);
//    }

    @Test
    public void viewWorkerAllReports() throws Exception {
        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
        when(reportMapper.selectByUserIdAndPage(anyInt(), anyInt(), anyInt())).thenReturn(new LinkedList<>());
        ResponseVO<List<ReportViewVO>> result = userServiceImpl.viewWorkerAllReports(1, 1);
        Assert.assertEquals(new ResponseVO<List<ReportViewVO>>(Integer.valueOf(Constant.REQUEST_FAIL), "管理员没有报告", null), result);
    }

}
