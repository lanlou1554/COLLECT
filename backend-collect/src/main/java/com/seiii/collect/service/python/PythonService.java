package com.seiii.collect.service.python;

import com.seiii.collect.model.vo.ResponseVO;

import java.util.List;
import java.util.Map;

public interface PythonService {
    // 获取相似度
    ResponseVO<List<Double>> getSimilarity(List<String> contents);

    // 获取TFIDF
    ResponseVO<Map<String, Double>> getTFIDF(List<String> contents);
}
