package com.seiii.collect.serviceimpl.flaw;

import com.seiii.collect.mapper.flaw.*;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.flaw.FlawEvaluationDTO;
import com.seiii.collect.model.po.flaw.*;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.*;
import com.seiii.collect.model.vo.user.UserViewVO;
import com.seiii.collect.service.flaw.FlawService;
import com.seiii.collect.service.python.PythonService;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FlawServiceImpl implements FlawService {
    @Resource
    FlawMapper flawMapper;
    @Resource
    FlawPicMapper flawPicMapper;
    @Resource
    SimilarityMapper similarityMapper;
    @Resource
    ScoreMapper scoreMapper;
    @Resource
    PythonService pythonService;
    @Resource
    EvaluationMapper evaluationMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    EvaLikeMapper evaLikeMapper;

    // 计算当前flaw和该任务下所有已处理的flaw的相似度并存入similarity表
    public ResponseVO<Object> insertSimilarity(Flaw flaw) {
        Integer taskId = flaw.getTaskid();
        List<Flaw> allFlaws = flawMapper.selectByTaskId(taskId); // 该任务下的所有flaw
        List<Integer> flawIds = new ArrayList<>(); // 过滤未处理的flaw
        List<String> allOtherContents = new ArrayList<>();

        for (Flaw allFlaw : allFlaws) {
            if (allFlaw.getState()) { // flaw 需要已被处理
                flawIds.add(allFlaw.getId());
                allOtherContents.add(allFlaw.getDescription() + allFlaw.getStepdes());
            }
        }

        List<String> contents = new ArrayList<>();
        contents.add(flaw.getDescription() + flaw.getStepdes());
        contents.addAll(allOtherContents);

        ResponseVO<List<Double>> responseVO = pythonService.getSimilarity(contents);
        if (responseVO.getCode() == Constant.REQUEST_FAIL) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "python服务器出错");
        } else {
            List<Double> similarities = responseVO.getData();
            for (int i = 0; i < similarities.size(); i++) {
                Similarity similarity = new Similarity(flaw.getId(), flawIds.get(i), similarities.get(i));
                similarityMapper.insert(similarity);
            }
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功");
    }

    @Override
    public ResponseVO<Object> refineFlaw(Integer flawId, Integer forkedFlawId, FlawDTO flawDTO) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);

        flaw.setDescription(flawDTO.getDescription());
        flaw.setStepdes(flawDTO.getStepDes());
        flaw.setDeviceinfo(flawDTO.getDeviceInfo());

        ResponseVO<Object> responseVO = insertSimilarity(flaw); // 插入similarity表
        if (responseVO.getCode() == Constant.REQUEST_FAIL) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, responseVO.getMsg());
        }

        Flaw forkedFlaw = flawMapper.selectByPrimaryKey(forkedFlawId);
        flaw.setPath(forkedFlaw.getPath() + "," + flawId.toString());
        flaw.setState(true);

        flawMapper.updateByPrimaryKey(flaw);
        List<String> picURLs = flawPicMapper.selectByFlawId(flawId);
        for (String url : picURLs) {
            flawPicMapper.deleteByPrimaryKey(flawId, url);
        }
        List<String> newPicURLs = flawDTO.getPictureURLs();
        for (String newUrl : newPicURLs) {
            FlawPic flawPic = new FlawPic(flaw.getId(), newUrl);
            flawPicMapper.insert(flawPic);
        }

        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "操作成功");
    }

    @Override
    public ResponseVO<Object> noforkFlaw(Integer flawId) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);

        ResponseVO<Object> responseVO = insertSimilarity(flaw); // 插入similarity表
        if (responseVO.getCode() == Constant.REQUEST_FAIL) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, responseVO.getMsg());
        }

        flaw.setPath(flawId.toString());
        flaw.setState(true);
        flawMapper.updateByPrimaryKey(flaw);

        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "操作成功");
    }

    @Override
    public ResponseVO<FlawTreeNodeVO> getFlawTree(Integer flawId) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);
        if (flaw == null || flaw.getId() == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "缺陷查找失败");
        }
//        List<String> flawPicURLs = flawPicMapper.selectByFlawId(flawId);
//        FlawVO flawVO = new FlawVO(flaw, flawPicURLs);

        Integer flawTaskId = flaw.getTaskid();
        List<Flaw> sameTaskFlaws = flawMapper.selectByTaskId(flawTaskId);
        String flawPath = flaw.getPath();
        String rootFlawIdStr = flawPath.split(",")[0];
        Integer rootFlawId = Integer.valueOf(rootFlawIdStr);

        FlawTreeNodeVO currentFlawNodeVO = getFlawChildrenTree(rootFlawId, sameTaskFlaws);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", currentFlawNodeVO);
    }

    private FlawTreeNodeVO getFlawChildrenTree(Integer flawId, List<Flaw> sameTaskFlaws) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);
        List<String> flawPicURLs = flawPicMapper.selectByFlawId(flawId);
        FlawVO flawVO = new FlawVO(flaw, flawPicURLs);

        List<FlawTreeNodeVO> childrenFlawNodeVOs = new ArrayList<>();
        List<Integer> childrenFlawIds = new ArrayList<>();
        for (Flaw tmpFlaw : sameTaskFlaws) {
            String tmpFlawPath = tmpFlaw.getPath();
            String[] tmpFlawPathFlaws = tmpFlawPath.split(",");
            int sz = tmpFlawPathFlaws.length;
            if (sz - 2 >= 0 && (tmpFlawPathFlaws[sz - 2].equals(String.valueOf(flawId)))) {
                childrenFlawIds.add(tmpFlaw.getId());
            }
        }

        for (Integer childFlawId : childrenFlawIds) {
            FlawTreeNodeVO childFlawNodeVO = this.getFlawChildrenTree(childFlawId, sameTaskFlaws);
            childrenFlawNodeVOs.add(childFlawNodeVO);
        }
        FlawTreeNodeVO currentFlawNodeVO = new FlawTreeNodeVO(childrenFlawNodeVOs, flawVO);
        return currentFlawNodeVO;
    }

    @Override
    public ResponseVO<List<SimilarFlawVO>> getSimilarFlaws(Integer flawId) {
        List<Similarity> similarities = similarityMapper.selectByFlawIdCompared(flawId);
        List<Similarity> similarities1 = similarityMapper.selectByFlawIdSecondCompared(flawId);
        similarities.addAll(similarities1);
        Collections.sort(similarities);
        List<SimilarFlawVO> similarFlawVOs = new ArrayList<>();
        int cnt = 0;
        for (Similarity s1 : similarities) {
            //获取的相似缺陷列表的项数限制在5个以内
            if (cnt >= Constant.SIMILAR_FLAW_COUNT)
                break;
            Integer tmpFlawId = s1.getFlawid2();
            tmpFlawId = tmpFlawId.equals(flawId) ? s1.getFlawid1() : tmpFlawId;
            Flaw tmpFlaw = flawMapper.selectByPrimaryKey(tmpFlawId);
            //未处理的相似缺陷不算在相似列表中
            if (tmpFlaw.getState() == false)
                continue;
            List<String> tmpFlawPicURLs = flawPicMapper.selectByFlawId(tmpFlawId);
            FlawVO tmpFlawVO = new FlawVO(tmpFlaw, tmpFlawPicURLs);
            SimilarFlawVO similarFlawVO = new SimilarFlawVO(s1.getSimilarity(), tmpFlawVO);
            similarFlawVOs.add(similarFlawVO);
            cnt++;
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", similarFlawVOs);
    }

    @Override
    public ResponseVO<Integer> getFlawScore(Integer flawId, Integer userId) {
        Score score = scoreMapper.selectByPrimaryKey(userId, flawId);
        if (score == null) {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", Integer.valueOf(-1));
        } else {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", score.getSocre());
        }
    }

    @Override
    public ResponseVO<Object> scoreFlaw(Integer flawId, Integer userId, Integer score) {
        Score scoreRecord = new Score();
        scoreRecord.setUserid(userId);
        scoreRecord.setFlawid(flawId);
        scoreRecord.setSocre(score);
        Flaw flawToUpdate = flawMapper.selectByPrimaryKey(flawId);
        Integer numToUpdate = flawToUpdate.getScorenum();
        Double scoreToUpdate = flawToUpdate.getScore();
        Double total = scoreToUpdate * numToUpdate + score;
        numToUpdate++;
        scoreToUpdate = total / numToUpdate;
        flawToUpdate.setScore(scoreToUpdate);
        flawToUpdate.setScorenum(numToUpdate);
        flawMapper.updateByPrimaryKey(flawToUpdate);
        scoreMapper.insert(scoreRecord);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "评分成功");
    }

    @Override
    public ResponseVO<FlawMapVO> getTaskFlawMap(Integer taskId) {
        List<Flaw> taskFlaws = flawMapper.selectByTaskId(taskId);
        List<Integer> taskRootFlawIds = new ArrayList<>();
        for (Flaw taskFlaw : taskFlaws) {
            //未处理的缺陷不会包含在相似图中
            if (taskFlaw.getState() == false)
                continue;
            Integer taskFlawId = taskFlaw.getId();
            String taskFlawPath = taskFlaw.getPath();
            if (taskFlawPath.equals(String.valueOf(taskFlawId))) {
                taskRootFlawIds.add(taskFlawId);
            }
        }
        int sz = taskRootFlawIds.size();
        List<List<Double>> similarityMatrix = new ArrayList<>();
        for (int i = 0; i < sz; i++) {
            List<Double> similarityMatrixRow = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (i == j) {
                    similarityMatrixRow.add(1.0);
                } else {
                    Similarity tmpSimilarity = similarityMapper.selectByPrimaryKey(taskRootFlawIds.get(i), taskRootFlawIds.get(j));
                    if (tmpSimilarity == null) {
                        tmpSimilarity = similarityMapper.selectByPrimaryKey(taskRootFlawIds.get(j), taskRootFlawIds.get(i));
                    }
                    Double tmpSimilarityValue = tmpSimilarity == null ? 0.0 : tmpSimilarity.getSimilarity();
                    similarityMatrixRow.add(tmpSimilarityValue);
                }
            }
            similarityMatrix.add(similarityMatrixRow);
        }
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                Double mapDouble = similarityMatrix.get(j).get(i);
                similarityMatrix.get(i).add(mapDouble);
            }
        }
        FlawMapVO flawMapVO = new FlawMapVO(taskRootFlawIds, similarityMatrix);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", flawMapVO);
    }

    @Override
    public ResponseVO<List<FlawEvaluationVO>> getAllEvaluations(Integer flawId,Integer userId) {
        List<Evaluation> evaluations = evaluationMapper.selectByFlawId(flawId);
        List<FlawEvaluationVO> res = new ArrayList<>();
        if(evaluations==null)
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
        for (Evaluation e : evaluations) {
            res.add(getFlawEvaluationVO(e,userId));
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
    }

    @Override
    public ResponseVO<List<FlawEvaluationVO>> addEvaluation(Integer flawId, FlawEvaluationDTO flawEvaluationDTO) {
        Evaluation evaluation = new Evaluation(flawEvaluationDTO, flawId);
        evaluationMapper.insert(evaluation);
        return getAllEvaluations(flawId,flawEvaluationDTO.getUserId());
    }

    private FlawEvaluationVO getFlawEvaluationVO(Evaluation e,Integer userId){
        User user = userMapper.selectByPrimaryKey(e.getUserid());
        UserViewVO userViewVO = new UserViewVO(user);
        EvaLikeStatistic evaLiStat=evaLikeMapper.selectCountByEva(e.getId());
        FlawEvaLikeVO flawEvaLikeVO;
        if(evaLiStat==null){
            flawEvaLikeVO=new FlawEvaLikeVO(0,0,0);
        }else{
            flawEvaLikeVO=new FlawEvaLikeVO(evaLiStat.getSupportcnt(),evaLiStat.getOpposecnt(),0);
        }
        if(e.getUserid().equals(userId)){
            flawEvaLikeVO.setStatus(3);
        }else if(evaLiStat!=null){
            EvaLike evaLike=evaLikeMapper.selectByPrimaryKey(e.getId(),userId);
            if(evaLike!=null){
                if(evaLike.getSupport()==1){
                    flawEvaLikeVO.setStatus(1);
                }else{
                    flawEvaLikeVO.setStatus(2);
                }
            }
        }
        return new FlawEvaluationVO(e.getId(),userViewVO, e.getContent(),flawEvaLikeVO);
    }

    @Override
    public ResponseVO<FlawEvaluationVO> supportEvalution(Integer evaluationId,Integer userId){
        EvaLike evaLike=new EvaLike();
        evaLike.setEvaid(evaluationId);
        evaLike.setUserid(userId);
        evaLike.setSupport(1);
        evaLike.setOppose(0);
        evaLikeMapper.insert(evaLike);
        Evaluation e=evaluationMapper.selectByPrimaryKey(evaluationId);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功",getFlawEvaluationVO(e,userId));
    }

    @Override
    public ResponseVO<FlawEvaluationVO> opposeEvalution(Integer evaluationId,Integer userId){
        EvaLike evaLike=new EvaLike();
        evaLike.setEvaid(evaluationId);
        evaLike.setUserid(userId);
        evaLike.setSupport(0);
        evaLike.setOppose(1);
        evaLikeMapper.insert(evaLike);
        Evaluation e=evaluationMapper.selectByPrimaryKey(evaluationId);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功",getFlawEvaluationVO(e,userId));

    }

    @Override
    public ResponseVO<Object> addAppendContent(Integer flawId, String content) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);
        // 先判断该缺陷是否为已处理状态
        if (!flaw.getState()) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "请先处理该缺陷！");
        }
        flaw.setAppendcontent(content);
        flawMapper.updateByPrimaryKey(flaw);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功");
    }
}
