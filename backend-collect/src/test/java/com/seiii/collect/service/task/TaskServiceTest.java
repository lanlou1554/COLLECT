package com.seiii.collect.service.task;

import com.seiii.collect.model.dto.task.TaskDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskServiceTest {
    @Autowired
    TaskService taskService;

    @Transactional
    @Rollback
    @Test
    void viewAllUnfinishedTasks() {
        ResponseVO<List<TaskViewVO>> res1 = taskService.viewAllUnfinishedTasks(1, 3);
        assertEquals(Constant.REQUEST_SUCCESS, res1.getCode());
        ResponseVO<List<TaskViewVO>> res2 = taskService.viewAllUnfinishedTasks(2000, 3);
        assertEquals(Constant.REQUEST_SUCCESS, res2.getCode());
        assertEquals(0, res2.getData().size());
    }

    @Transactional
    @Rollback
    @Test
    void viewTaskDetails() {
        ResponseVO<TaskVO> res = taskService.viewTaskDetails(4, 9);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
    }

    @Transactional
    @Rollback
    @Test
    void createTask() {
        Date startTime = new Timestamp(System.currentTimeMillis());
        Integer userId = 2;
        Integer type = 1;
        String title = "title";
        String description = "description";
        Integer workNum = 10;
        String exeFileName = "exeFileName";
        String reqDocName = "reqDocName";
        Date endTime = new Timestamp(System.currentTimeMillis());
        TaskDTO taskDTO = new TaskDTO(userId, type, title, description, startTime, endTime, workNum, exeFileName, reqDocName, new ArrayList<>(),new ArrayList<>());
        ResponseVO<TaskVO> res = taskService.createTask(taskDTO);
        assertEquals(Constant.REQUEST_FAIL, res.getCode());
//        assertNotNull(res.getData());
//        TaskVO taskVO = res.getData();
//        assertEquals(userId, taskVO.getUserId());
//        assertEquals(type, taskVO.getType());
//        assertEquals(title, taskVO.getTitle());
//        assertEquals(startTime, taskVO.getStartTime());
//        assertEquals(endTime, taskVO.getEndTime());
//        assertEquals(description, taskVO.getDescription());
//        assertEquals(workNum, taskVO.getWorkerNum());
//        assertEquals(exeFileName, taskVO.getExeFileName());
//        assertEquals(reqDocName, taskVO.getReqDocName());
    }

    @Transactional
    @Rollback
    @Test
    void pickTask() {
        ResponseVO<TaskVO> res = taskService.pickTask(4, 3);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void getReportId() {
        ResponseVO<Integer> res = taskService.getReportId(8, 3);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertEquals(4, res.getData());
    }

}
