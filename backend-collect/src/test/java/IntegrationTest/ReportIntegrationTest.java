package IntegrationTest;

import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.model.dto.report.ReportDTO;
import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.serviceimpl.report.ReportServiceImpl;
import com.seiii.collect.util.Constant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportIntegrationTest {
    @Mock
    ReportMapper reportMapper;
    @InjectMocks
    ReportServiceImpl reportServiceImpl;

    @Test
    public void testViewReportDetails() throws Exception{
        when(reportMapper.selectByPrimaryKey(anyInt())).thenReturn(new Report());
//        when(flawMapper.selectByReportId(anyInt())).thenReturn(new LinkedList<>());
//        when(flawPicMapper.selectByFlawId(anyInt())).thenReturn(new LinkedList<>());
        ResponseVO<ReportVO> result = reportServiceImpl.viewReportDetails(0);
        Assert.assertEquals(new ResponseVO<>(Constant.REQUEST_FAIL,"报告查找失败！",null),result);
    }

    @Test
    public void testCreateReport() throws Exception{
        when(reportMapper.insert(any())).thenReturn(0);
//        when(flawPicMapper.insert(any())).thenReturn(0);
//        when(flawMapper.insert(any())).thenReturn(0);
        ResponseVO<ReportVO> result = reportServiceImpl.createReport(new ReportDTO());
        Assert.assertEquals(new ResponseVO<>(Constant.REQUEST_FAIL,"测试报告提交失败！",null),result);
    }
}
