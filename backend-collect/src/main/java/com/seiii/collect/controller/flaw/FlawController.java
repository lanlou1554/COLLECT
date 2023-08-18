package com.seiii.collect.controller.flaw;


import com.seiii.collect.model.dto.flaw.FlawAppendDTO;
import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.flaw.FlawEvaluationDTO;
import com.seiii.collect.model.dto.flaw.FlawScoreDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawEvaluationVO;
import com.seiii.collect.model.vo.flaw.FlawMapVO;
import com.seiii.collect.model.vo.flaw.FlawTreeNodeVO;
import com.seiii.collect.model.vo.flaw.SimilarFlawVO;
import com.seiii.collect.service.flaw.FlawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "FlawController")
@RestController
@RequestMapping("/flaw")
public class FlawController {
    @Resource
    private FlawService flawService;

    @ApiOperation("完善一个缺陷")
    @PostMapping("/refine/{flawId}")
    public ResponseVO<Object> refineFlaw(@PathVariable Integer flawId,
                                         @RequestParam Integer forkedFlawId,
                                         @RequestBody FlawDTO flawDTO) {
        return flawService.refineFlaw(flawId, forkedFlawId, flawDTO);
    }

    @ApiOperation("不fork任何一个缺陷")
    @GetMapping("/nofork/{flawId}")
    public ResponseVO<Object> noforkFlaw(@PathVariable Integer flawId) {
        return flawService.noforkFlaw(flawId);
    }

    @ApiOperation("获取与该缺陷关联的缺陷树")
    @GetMapping("/tree/{flawId}")
    public ResponseVO<FlawTreeNodeVO> getFlawTree(@PathVariable Integer flawId) {
        return flawService.getFlawTree(flawId);
    }

    @ApiOperation("获取与该缺陷相似的缺陷列表")
    @GetMapping("/similarFlaw/{flawId}")
    public ResponseVO<List<SimilarFlawVO>> getSimilarFlaws(@PathVariable Integer flawId) {
        return flawService.getSimilarFlaws(flawId);
    }

    @ApiOperation("获取用户对该缺陷的评分")
    @GetMapping("/myScore/{flawId}")
    public ResponseVO<Integer> getFlawScore(@PathVariable Integer flawId, @RequestParam Integer userId) {
        return flawService.getFlawScore(flawId, userId);
    }

    @ApiOperation("对缺陷进行评分")
    @PostMapping("/score")
    public ResponseVO<Object> scoreFlaw(@RequestBody FlawScoreDTO flawScoreDTO) {
        return flawService.scoreFlaw(flawScoreDTO.getFlawId(), flawScoreDTO.getUserId(), flawScoreDTO.getScore());
    }

    @ApiOperation("获取某个任务的缺陷图")
    @GetMapping("/flawMap/{taskId}")
    public ResponseVO<FlawMapVO> getTaskFlawMap(@PathVariable Integer taskId) {
        return flawService.getTaskFlawMap(taskId);
    }

    @ApiOperation("获取缺陷的所有评价")
    @GetMapping("/evaluations/{flawId}")
    public ResponseVO<List<FlawEvaluationVO>> getAllEvaluations(@PathVariable Integer flawId,@RequestParam Integer userId) {
        return flawService.getAllEvaluations(flawId,userId);
    }

    @ApiOperation("给非自己的报告中的缺陷增加一条评价")
    @PostMapping("/add/evaluation/{flawId}")
    public ResponseVO<List<FlawEvaluationVO>> addEvaluation(@PathVariable Integer flawId, @RequestBody FlawEvaluationDTO flawEvaluationDTO) {
        return flawService.addEvaluation(flawId, flawEvaluationDTO);
    }

    @ApiOperation("给某个评论点赞")
    @PostMapping("/evaluation/like/{evaluationId}")
    public ResponseVO<FlawEvaluationVO> supportEvalution(@PathVariable Integer evaluationId,@RequestParam Integer userId){
        return flawService.supportEvalution(evaluationId,userId);
    }

    @ApiOperation("给某个评论点踩")
    @PostMapping("/evaluation/dislike/{evaluationId}")
    public ResponseVO<FlawEvaluationVO> opposeEvalution(@PathVariable Integer evaluationId,@RequestParam Integer userId){
        return flawService.opposeEvalution(evaluationId,userId);
    }

    @ApiOperation("增加或修改补充内容")
    @PostMapping("/add/append")
    public ResponseVO<Object> addAppendContent(@RequestBody FlawAppendDTO flawAppendDTO) {
        return flawService.addAppendContent(flawAppendDTO.getFlawId(), flawAppendDTO.getAppendContent());
    }
}
