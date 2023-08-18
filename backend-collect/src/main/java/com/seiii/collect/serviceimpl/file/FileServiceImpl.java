package com.seiii.collect.serviceimpl.file;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.seiii.collect.config.OSSConfig;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.file.OSSCallbackParamVO;
import com.seiii.collect.model.vo.file.OSSCallbackResultVO;
import com.seiii.collect.model.vo.file.OSSPolicyVO;
import com.seiii.collect.service.file.FileService;
import com.seiii.collect.util.Constant;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    private final OSSConfig oss;

    private final OSSClient ossClient;

    @Autowired
    public FileServiceImpl(OSSConfig oss,
                           OSSClient ossClient) {
        this.oss = oss;
        this.ossClient = ossClient;
    }

    @Override
    @SneakyThrows
    public ResponseVO<OSSPolicyVO> policy() {
        // 文件大小
        long maxSize = Long.parseLong(oss.getMaxSize()) * 1024 * 1024;
        // 存储目录
        String dir = oss.getDir();
        // 签名有效期
        Date expiration = new Date(System.currentTimeMillis() + 300 * 1000);

        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        // base64 encoding
        String policy = BinaryUtil.toBase64String(postPolicy.getBytes(StandardCharsets.UTF_8));

        // 提交节点
        String action = "https://" + oss.getBucketName() + "." + oss.getEndPoint();
        String signature = ossClient.calculatePostSignature(postPolicy);

        OSSPolicyVO ossPolicyVO = new OSSPolicyVO(
                ossClient.getCredentialsProvider().getCredentials().getAccessKeyId(),
                policy,
                signature,
                dir,
                action,
                new OSSCallbackParamVO(oss.getCallback()));
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", ossPolicyVO);
    }

    @Override
    public ResponseVO<OSSCallbackResultVO> callback(String filename, String size, String mimeType, String width, String height) {
        String file = "https://".concat(oss.getBucketName()).concat(".").concat(oss.getEndPoint()).concat("/").concat(filename);
        OSSCallbackResultVO ossCallbackResultVO = new OSSCallbackResultVO(file, size, mimeType, width, height);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", ossCallbackResultVO);
    }
}
