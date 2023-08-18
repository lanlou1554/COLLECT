package com.seiii.collect.service.file;

import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.file.OSSCallbackResultVO;
import com.seiii.collect.model.vo.file.OSSPolicyVO;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FileServiceTest {
    @Autowired
    FileService fileService;

    @Transactional
    @Rollback
    @Test
    void policy() {
        ResponseVO<OSSPolicyVO> res = fileService.policy();
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
    }

    @Transactional
    @Rollback
    @Test
    void callback() {
        String filename = "test.png";
        String size = "10MB";
        String mimeType = "h";
        String width = "100px";
        String height = "120px";
        ResponseVO<OSSCallbackResultVO> res = fileService.callback(filename, size, mimeType, width, height);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
        assertEquals("https://seiiicollect.oss-cn-beijing.aliyuncs.com/" + filename, res.getData().getFilename());
        assertEquals(size, res.getData().getSize());
        assertEquals(mimeType, res.getData().getMimeType());
        assertEquals(width, res.getData().getWidth());
        assertEquals(height, res.getData().getHeight());
    }
}
