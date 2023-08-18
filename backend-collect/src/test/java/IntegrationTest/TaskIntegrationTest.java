package IntegrationTest;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.dto.task.TaskDTO;
import com.seiii.collect.model.po.task.Task;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.task.TaskServiceImpl;
import com.seiii.collect.util.Constant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskIntegrationTest {
    @Mock
    TaskMapper taskMapper;
    @InjectMocks
    TaskServiceImpl taskServiceImpl;
    @Mock
    TaskUserMapper taskUserMapper;
    @Mock
    UserMapper userMapper;

    @Test
    public void testViewAllUnfinishedTasks() throws Exception{
/*        when(taskMapper.selectBeforeEndTimeAndAfterStartTime(any(),anyInt(),anyInt())).thenReturn(new LinkedList<>());
//        when(taskUserMapper.selectByTaskId(anyInt())).thenReturn(new LinkedList<>());
        ResponseVO<List<TaskViewVO>> result = taskServiceImpl.viewAllUnfinishedTasks(1);
        Assert.assertEquals(new ResponseVO<List<TaskViewVO>>(Integer.valueOf(Constant.REQUEST_SUCCESS), "请求成功", new LinkedList<>()), result);*/
    }

    @Test
    public void testViewTaskDetails() throws Exception{
        when(taskMapper.selectByPrimaryKey(anyInt())).thenReturn(new Task());
//        when(userMapper.selectByPrimaryKey(anyInt())).thenReturn(new User());
//        when(taskUserMapper.selectByUserId(anyInt())).thenReturn(new LinkedList<>());
        ResponseVO<TaskVO> result = taskServiceImpl.viewTaskDetails(0,0);
        Assert.assertEquals(new ResponseVO<TaskVO>(Constant.REQUEST_FAIL,"任务查找失败！",null),result);
    }

    @Test
    public void testCreateTask() throws Exception{
        when(taskMapper.insert(any())).thenReturn(anyInt());
        ResponseVO<TaskVO> result = taskServiceImpl.createTask(new TaskDTO());
        Assert.assertEquals(new ResponseVO<TaskVO>(Constant.REQUEST_FAIL,"任务创建失败！",null),result);
    }

    @Test
    public void testPickTask() throws Exception{
        when(taskUserMapper.insert(any())).thenReturn(anyInt());
        ResponseVO<TaskVO> result = taskServiceImpl.pickTask(0,0);
        Assert.assertEquals(new ResponseVO<TaskVO>(Constant.REQUEST_FAIL,"选取任务失败！",null),result);
    }

}
