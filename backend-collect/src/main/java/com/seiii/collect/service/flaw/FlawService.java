package com.seiii.collect.service.flaw;

import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.flaw.FlawEvaluationDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawEvaluationVO;
import com.seiii.collect.model.vo.flaw.FlawMapVO;
import com.seiii.collect.model.vo.flaw.FlawTreeNodeVO;
import com.seiii.collect.model.vo.flaw.SimilarFlawVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface FlawService {
    // 完善一个缺陷
    ResponseVO<Object> refineFlaw(Integer flawId, Integer forkedFlawId, FlawDTO flawDTO);

    // 不fork任何一个缺陷
    ResponseVO<Object> noforkFlaw(Integer flawId);

    //获取与该缺陷关联的缺陷树
    ResponseVO<FlawTreeNodeVO> getFlawTree(Integer flawId);

    //获取与该缺陷相似的缺陷列表
    ResponseVO<List<SimilarFlawVO>> getSimilarFlaws(Integer flawId);

    //获取用户对该缺陷的评分
    ResponseVO<Integer> getFlawScore(Integer flawId, Integer userId);

    //对缺陷进行评分
    ResponseVO<Object> scoreFlaw(Integer flawId, Integer userId, Integer score);

    //获取某个任务的缺陷图
    ResponseVO<FlawMapVO> getTaskFlawMap(Integer taskId);

    // 获取缺陷的所有评价
    ResponseVO<List<FlawEvaluationVO>> getAllEvaluations(Integer flawId,Integer userId);

    // 给非自己的报告中的缺陷增加一条评价
    ResponseVO<List<FlawEvaluationVO>> addEvaluation(Integer flawId, FlawEvaluationDTO flawEvaluationDTO);

    //给某个评论点赞
    ResponseVO<FlawEvaluationVO> supportEvalution(Integer evaluationId,Integer userId);

    //给某个评论点踩
    ResponseVO<FlawEvaluationVO> opposeEvalution(Integer evaluationId,Integer userId);

    // 增加或修改补充内容
    ResponseVO<Object> addAppendContent(Integer flawId, String content);

}
