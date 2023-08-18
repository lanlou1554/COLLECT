package IntegrationTest;

import com.seiii.collect.mapper.flaw.EvaluationMapper;
import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.mapper.flaw.ScoreMapper;
import com.seiii.collect.model.po.flaw.Score;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.serviceimpl.flaw.FlawServiceImpl;
import com.seiii.collect.util.Constant;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlawIntegrationTest {
    @Mock
    FlawMapper flawMapper;
    @Mock
    EvaluationMapper evaluationMapper;
    @Mock
    ScoreMapper scoreMapper;
    @InjectMocks
    FlawServiceImpl flawServiceImpl;

    @Test
    public void testGetFlawScore() throws Exception {
        when(scoreMapper.selectByPrimaryKey(anyInt(), anyInt())).thenReturn(new Score());
        ResponseVO<Integer> res = flawServiceImpl.getFlawScore(1, 3);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
    }
}
