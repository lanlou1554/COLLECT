package com.seiii.collect.service.user;

import com.seiii.collect.model.dto.user.UserDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.model.vo.user.UserVO;
import com.seiii.collect.model.vo.user.UserViewVO;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Transactional
    @Rollback
    @Test
    void register() {
        UserDTO userDTO = new UserDTO();
        LocalDateTime now = LocalDateTime.now();
        userDTO.setUsername(now.toString());
        userDTO.setPassword("123456");
        userDTO.setType(1);
        userDTO.setTagGroups(new ArrayList<>());
        assertEquals(Constant.REQUEST_SUCCESS, userService.userRegister(userDTO).getCode());
    }

    @Transactional
    @Rollback
    @Test
    void login() {
        ResponseVO<UserViewVO> vo = userService.userLogin("admin", "admin123");
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
        assertNotNull(vo.getData());
        assertEquals(1, vo.getData().getId());
        vo = userService.userLogin("admin", "admin123_error");
        assertEquals(Constant.REQUEST_FAIL, vo.getCode());
        assertNull(vo.getData());
        vo = userService.userLogin("admin_error", "admin123");
        assertNull(vo.getData());
        assertEquals(Constant.REQUEST_FAIL, vo.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void getUserInfo() {
        ResponseVO<UserViewVO> res = userService.getUserInfo(3);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
        assertEquals("worker", res.getData().getUsername());
        assertEquals(1, res.getData().getType());
        assertEquals(3, res.getData().getId());
    }

    @Transactional
    @Rollback
    @Test
    void getUserInfoList() {
        List<Integer> ids = new ArrayList<>();
        ids.add(2);
        ids.add(3);
        ids.add(5);
        ResponseVO<List<UserViewVO>> res = userService.getUserInfoList(ids);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
        assertEquals(3, res.getData().size());

        UserViewVO user1 = res.getData().get(0);
        UserViewVO user2 = res.getData().get(1);
        UserViewVO user3 = res.getData().get(2);

        assertEquals("employer", user1.getUsername());
        assertEquals(0, user1.getType());
        assertEquals(2, user1.getId());

        assertEquals("worker", user2.getUsername());
        assertEquals(1, user2.getType());
        assertEquals(3, user2.getId());

        assertEquals("workerTest1", user3.getUsername());
        assertEquals(1, user3.getType());
        assertEquals(5, user3.getId());
    }

    @Transactional
    @Rollback
    @Test
    void viewUser() {
        ResponseVO<UserVO> res = userService.viewUser(3);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
        assertEquals("worker", res.getData().getUsername());
        assertEquals(1, res.getData().getType());
        assertEquals(3, res.getData().getId());
    }

    @Transactional
    @Rollback
    @Test
    void viewFinishedTasks() {
        ResponseVO<List<TaskViewVO>> vos = userService.viewFinishedTasks(5, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void viewUnfinishedTasks() {
        ResponseVO<List<TaskViewVO>> vos = userService.viewUnfinishedTasks(3, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        vos = userService.viewUnfinishedTasks(5, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        vos = userService.viewUnfinishedTasks(5, 2000);
        assertEquals(0, vos.getData().size());
    }

    @Transactional
    @Rollback
    @Test
    void viewExpiredTasks() {
        ResponseVO<List<TaskViewVO>> vos = userService.viewExpiredTasks(5, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        vos = userService.viewUnfinishedTasks(5, 2000);
        assertEquals(0, vos.getData().size());
    }

    @Transactional
    @Rollback
    @Test
    void viewReportsFromTask() {
        ResponseVO<List<ReportViewVO>> vos = userService.viewReportsFromTask(5, 8, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        vos = userService.viewReportsFromTask(5, 8, 2000);
        assertEquals(0, vos.getData().size());
        vos = userService.viewReportsFromTask(3, 9, 2000);
        assertEquals(0, vos.getData().size());
    }

    @Transactional
    @Rollback
    @Test
    void viewAllWorkerReports() {
        ResponseVO<List<TaskViewVO>> vos = userService.viewExpiredTasks(5, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        vos = userService.viewExpiredTasks(3, 1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
//        assertEquals(1,vos.getData().size());
        vos = userService.viewUnfinishedTasks(3, 2000);
        assertEquals(0, vos.getData().size());
    }

//    查看众包工人关于某个任务的报告
    @Transactional
    @Rollback
    @Test
    void viewWorkerTaskReport() {
        Integer userId=3;
        Integer taskId=8;
        ResponseVO<ReportViewVO> vo=userService.viewWorkerTaskReport(userId,taskId);
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
    }

    //用户添加某个标签
    @Transactional
    @Rollback
    @Test
    void addUserTag() {
        Integer userId = 3;
        Integer tag = 3;
        assertEquals(Constant.REQUEST_SUCCESS, userService.addUserTag(userId, tag).getCode());
        tag = 4;
//        assertEquals(Constant.REQUEST_FAIL, userService.addUserTag(userId, tag).getCode());
    }

    //用户删除某个标签
    @Transactional
    @Rollback
    @Test
    void deleteUserTag() {
        Integer userId = 3;
        Integer tag = 3;
        assertEquals(Constant.REQUEST_FAIL, userService.deleteUserTag(userId, tag).getCode());
        tag = 4;
//        assertEquals(Constant.REQUEST_SUCCESS, userService.deleteUserTag(userId, tag).getCode());
    }

    //用户词云
    @Transactional
    @Rollback
    @Test
    void getWorkerWordCloud() {
        Integer userId = 3;
        System.out.println(userService.getWorkerWordCloud(userId));
    }
}
