package com.seiii.collect.serviceimpl.python;

import com.alibaba.fastjson.JSONObject;
import com.seiii.collect.config.PythonConfig;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.service.python.PythonService;
import com.seiii.collect.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@Service
public class PythonServiceImpl implements PythonService {

    private final PythonConfig pythonConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public PythonServiceImpl(PythonConfig pythonConfig) {
        this.pythonConfig = pythonConfig;
    }

    @Override
    public ResponseVO<List<Double>> getSimilarity(List<String> contents) {
        String url = pythonConfig.getUrl() + "docsim";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("strings", contents);

        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, getHttpEntity(jsonObject), Map.class);

        if (mapResponseEntity.getStatusCode() == HttpStatus.OK) {
            // 统一保留两位小数
            List<Double> res = (List<Double>) mapResponseEntity.getBody().get("res");
            for (int i = 0; i < res.size(); i++) {
                Double d = res.get(i);
                BigDecimal b = new BigDecimal(d);
                d = b.setScale(Constant.RESERVED_DECIMAL_NUM, RoundingMode.HALF_UP).doubleValue();
                res.set(i, d);
            }
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
        }
        System.err.println("Error:" + mapResponseEntity.getStatusCode());
        return new ResponseVO<>(Constant.REQUEST_FAIL, "python请求出错");
    }

    @Override
    public ResponseVO<Map<String, Double>> getTFIDF(List<String> contents){
        String url = pythonConfig.getUrl() + "tfidf";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("strings", contents);

        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, getHttpEntity(jsonObject), Map.class);

        if (mapResponseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Double> tfidfRes = new HashMap<>();
            // 统一保留两位小数
            List<List<Object>> res = (List<List<Object>>) mapResponseEntity.getBody().get("tfidf");
            for(int i=0;i<res.size();i++){
                List<Object> temp = res.get(i);
                String word = (String) temp.get(0);
                Double value = (Double) temp.get(1);
                BigDecimal b = new BigDecimal(value);
                value = b.setScale(Constant.RESERVED_DECIMAL_NUM, RoundingMode.HALF_UP).doubleValue();
                tfidfRes.put(word,value);
            }
            tfidfRes = tfidfRes
                    .entrySet()
                    .stream()
                    .sorted(comparingByValue(Comparator.reverseOrder()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", tfidfRes);
        }
        System.err.println("Error:" + mapResponseEntity.getStatusCode());
        return new ResponseVO<>(Constant.REQUEST_FAIL, "python请求出错");
    }

    private HttpEntity<JSONObject> getHttpEntity(JSONObject jsonObject){
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置请求类型
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 封装参数和头信息
        HttpEntity<JSONObject> httpEntity = new HttpEntity(jsonObject, httpHeaders);
        return httpEntity;
    }

}
