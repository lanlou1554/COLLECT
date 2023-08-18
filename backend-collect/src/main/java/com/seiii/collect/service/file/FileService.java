package com.seiii.collect.service.file;


import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.file.OSSCallbackResultVO;
import com.seiii.collect.model.vo.file.OSSPolicyVO;

public interface FileService {
    // oss的policy请求接口
    ResponseVO<OSSPolicyVO> policy();

    // oss的回调接口，由oss服务器调用
    ResponseVO<OSSCallbackResultVO> callback(String filename, String size, String mimeType, String width, String height);

}
