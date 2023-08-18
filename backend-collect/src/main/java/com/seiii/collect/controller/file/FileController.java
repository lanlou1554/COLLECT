package com.seiii.collect.controller.file;

import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.file.OSSCallbackResultVO;
import com.seiii.collect.model.vo.file.OSSPolicyVO;
import com.seiii.collect.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "FileController")
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @ApiOperation("oss的policy请求接口")
    @GetMapping("/policy")
    public ResponseVO<OSSPolicyVO> policy() {
        return fileService.policy();
    }

    @ApiOperation("oss的回调接口，由oss服务器调用")
    @PostMapping("/callback")
    public ResponseVO<OSSCallbackResultVO> callback(@RequestParam String filename,
                                                    @RequestParam String size,
                                                    @RequestParam String mimeType,
                                                    @RequestParam String width,
                                                    @RequestParam String height) {
        return fileService.callback(filename, size, mimeType, width, height);
    }

}
