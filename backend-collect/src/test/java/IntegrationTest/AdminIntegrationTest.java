package IntegrationTest;

import com.seiii.collect.mapper.recommend.RecommendRuleFactorMapper;
import com.seiii.collect.mapper.recommend.RecommendRuleMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import com.seiii.collect.model.po.task.Task;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.admin.AdminServiceImpl;
import com.seiii.collect.util.Constant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminIntegrationTest {

    @Mock
    TaskMapper taskMapper;
    @Mock
    TaskUserMapper taskUserMapper;
    @Mock
    TaskTagMapper taskTagMapper;
    @Mock
    RecommendRuleMapper recommendRuleMapper;
    @Mock
    RecommendRuleFactorMapper recommendRuleFactorMapper;
    @InjectMocks
    AdminServiceImpl adminServiceImpl;

    @Test
    public void testGetAllTasks() throws Exception{
        ResponseVO<List<TaskViewVO>> result = adminServiceImpl.getAllTasks(1);
        Assert.assertEquals(new ResponseVO<>(Integer.valueOf(Constant.REQUEST_SUCCESS), "请求成功", new LinkedList()), result);
    }

    @Test
    public void testViewTaskDetail() throws Exception{
        ResponseVO<TaskVO> result = adminServiceImpl.viewTaskDetails(0);
        Assert.assertEquals(new ResponseVO<>(Constant.REQUEST_FAIL,"任务查找失败！",null),result);
    }


    @Test
    public void getAllRecommendFactors() throws Exception{
        ResponseVO<List<RecommendRuleFactorVO>> res = adminServiceImpl.getRecommendRuleFactors();
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        List<RecommendRuleFactorVO> recommendRuleFactorVOs=new ArrayList<>();
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_FACTORS.get(0),0.0));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_FACTORS.get(1),0.0));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_FACTORS.get(2),0.0));
        assertEquals(recommendRuleFactorVOs, res.getData());
    }

    @Test
    public void deleteRecommendRule() throws Exception{
        ResponseVO<Object> res = adminServiceImpl.deleteRecommendRule(2);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
    }


    @Test
    public void selectRecommendRule() throws Exception{
        ResponseVO<Object> res = adminServiceImpl.selectRecommendRule(1);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
    }
}
