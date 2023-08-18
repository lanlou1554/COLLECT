package com.seiii.collect.service.python;


import com.seiii.collect.model.vo.ResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PythonServiceTest {
    @Autowired
    PythonService pythonService;

    @Transactional
    @Rollback
    @Test
    void getTFIDF() {
        List<String> content = new LinkedList<>();
        content.add("点击抽奖按钮三秒 点击停止抽奖 预期结果：转盘成功停止转动，抽奖成功 实际结果： 抽奖失败");
        content.add("点击“发现”按钮进入“发现”页面 点击“积分抽奖”按钮 点击“开始抽奖”按钮 点击“停止抽奖”按钮 无法出现抽奖结果，出现错误：很抱歉，抽奖失败，请检查网络[java.lang.lllegalStateException:Expected BEGIN_OBJECT but was STRING at line 1 column 2]");
        ResponseVO<Map<String,Double>> res = pythonService.getTFIDF(content);
        assertEquals(res.data.size(),10);
        assertEquals(res.data.get("点击"),1.0);
    }
}
