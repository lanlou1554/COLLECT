package com.seiii.collect.serviceimpl.report;

import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.mapper.flaw.FlawPicMapper;
import com.seiii.collect.mapper.flaw.SimilarityMapper;
import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.report.ReportDTO;
import com.seiii.collect.model.po.flaw.Flaw;
import com.seiii.collect.model.po.flaw.FlawPic;
import com.seiii.collect.model.po.flaw.Similarity;
import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.po.report.ReportView;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.flaw.SimilarFlawVO;
import com.seiii.collect.model.vo.flaw.TBAFlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.service.python.PythonService;
import com.seiii.collect.service.report.ReportService;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    ReportMapper reportMapper;
    @Resource
    FlawMapper flawMapper;
    @Resource
    FlawPicMapper flawPicMapper;
    @Resource
    PythonService pythonService;
    @Resource
    SimilarityMapper similarityMapper;

    @Override
    public ResponseVO<ReportVO> viewReportDetails(Integer reportId) {
        Report report = reportMapper.selectByPrimaryKey(reportId);
        if (report == null || report.getId() == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "报告查找失败！");
        }
        List<Flaw> flaws = flawMapper.selectByReportId(reportId);
        List<FlawVO> flawVOs = new ArrayList<>();
        Double score = 0.0;
        int count = 0;
        boolean hasScore = false;
        for (Flaw flaw : flaws) {
            if (flaw.getScore() >= 0) {
                score += flaw.getScore();
                count++;
                hasScore = true;
            }
            List<String> pictureURLs = flawPicMapper.selectByFlawId(flaw.getId());
            flawVOs.add(new FlawVO(flaw, pictureURLs));
        }
        if (!hasScore) {
            score = -1.0;
        } else {
            score /= count;
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", new ReportVO(report, flawVOs, score));
    }

    @Override
    public ResponseVO<ReportVO> createReport(ReportDTO reportDTO) {
        Report report = new Report(reportDTO);
        List<ReportView> allReports = reportMapper.selectByUserId(reportDTO.getUserId());
        for (ReportView allReport : allReports) {
            if (Objects.equals(allReport.getTaskid(), reportDTO.getTaskId())) {
                return new ResponseVO<>(Constant.REQUEST_FAIL, "您已经提交过该任务下的报告！");
            }
        }
        if (reportMapper.insert(report) > 0) {
            List<FlawDTO> flawDTOs = reportDTO.getFlaws();
            List<FlawVO> flawVOs = new ArrayList<>();
            Integer reportId = report.getId();
            Integer taskId = reportDTO.getTaskId();
            for (FlawDTO flawDTO : flawDTOs) {
                Flaw flaw = new Flaw(flawDTO, reportId, taskId);
                flawMapper.insert(flaw);
                List<String> pictureURLs = flawDTO.getPictureURLs();
                flawVOs.add(new FlawVO(flaw, pictureURLs));
                for (String pictureURL : pictureURLs) {
                    FlawPic flawPic = new FlawPic(flaw.getId(), pictureURL);
                    flawPicMapper.insert(flawPic);
                }
            }
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", new ReportVO(report, flawVOs, -1.0));
        }
        return new ResponseVO<>(Constant.REQUEST_FAIL, "测试报告提交失败！");
    }

    @Override
    public ResponseVO<List<TBAFlawVO>> getToBeRefinedFlawLists(Integer reportId) {
        List<Flaw> myFlaws = flawMapper.selectByReportId(reportId); // 需要计算相似度的，需排除状态为已完成的
        Integer taskId = reportMapper.selectByPrimaryKey(reportId).getTaskid();
        List<Flaw> allFlaws = flawMapper.selectByTaskId(taskId); // 该任务下的所有flaw
        List<SimilarFlawVO> toBeComparedFlaws = new ArrayList<>(); // 过滤未处理的flaw
        List<String> allOtherContents = new ArrayList<>();

        // 过滤状态为已经完成的flaw
        for (int i = 0; i < myFlaws.size(); i++) {
            if (myFlaws.get(i).getState()) {
                myFlaws.remove(i);
                i--;
            }
        }

        for (Flaw allFlaw : allFlaws) {
            if (allFlaw.getState()) { // flaw 需要已被处理
                List<String> picURLs = flawPicMapper.selectByFlawId(allFlaw.getId());
                toBeComparedFlaws.add(new SimilarFlawVO(0.0, new FlawVO(allFlaw, picURLs)));
                allOtherContents.add(allFlaw.getDescription() + allFlaw.getStepdes());
            }
        }

        List<TBAFlawVO> resTBAFlaws = new ArrayList<>();
        // 记录无需处理的缺陷
        List<Flaw> remainFlaws = new ArrayList<>();

        for (Flaw myFlaw : myFlaws) {

            // 避免引用传递，防止覆盖
            List<SimilarFlawVO> tmpToBeComparedFlaws = new ArrayList<>();
            for (SimilarFlawVO tmp : toBeComparedFlaws) {
                tmpToBeComparedFlaws.add(tmp.clone()); // 深拷贝
            }

            List<String> contents = new ArrayList<>();
            contents.add(myFlaw.getDescription() + myFlaw.getStepdes());
            contents.addAll(allOtherContents);

            ResponseVO<List<Double>> responseVO = pythonService.getSimilarity(contents);
            if (responseVO.getCode() == Constant.REQUEST_FAIL) {
                return new ResponseVO<>(Constant.REQUEST_FAIL, "python服务器出错");
            } else {
                List<Double> similarities = responseVO.getData();
                for (int i = 0; i < similarities.size(); i++) {
                    tmpToBeComparedFlaws.get(i).setSimilarity(similarities.get(i));
                }
            }
            Collections.sort(tmpToBeComparedFlaws);
            List<SimilarFlawVO> resSimilarFlaws = new ArrayList<>();
            int cnt = 0;
            for (SimilarFlawVO similarFlawVO : tmpToBeComparedFlaws) {
                if (cnt >= Constant.SIMILAR_FLAW_COUNT ||
                        Double.doubleToLongBits(similarFlawVO.getSimilarity()) < Double.doubleToLongBits(Constant.SIMILARITY_BOUND)) {
                    break;
                }
                resSimilarFlaws.add(similarFlawVO);
                cnt++;
            }

            Integer curFlawId = myFlaw.getId();
            if (!resSimilarFlaws.isEmpty()) {
                List<String> picURLs = flawPicMapper.selectByFlawId(curFlawId);
                resTBAFlaws.add(new TBAFlawVO(new FlawVO(myFlaw, picURLs), resSimilarFlaws));
            } else { // 不需要处理的缺陷
                remainFlaws.add(myFlaw);
                // 更改状态，更改path，并插入similarity表
                myFlaw.setState(true);
                myFlaw.setPath(curFlawId.toString());
                flawMapper.updateByPrimaryKey(myFlaw);
                for (SimilarFlawVO similarFlawVO : tmpToBeComparedFlaws) {
                    Similarity similarity = new Similarity(curFlawId, similarFlawVO.getFlaw().getId(), similarFlawVO.getSimilarity());
                    similarityMapper.insert(similarity);
                }
            }
        }

        // 无需处理的缺陷间计算相似度
        for (int i = 0; i < remainFlaws.size(); i++) {
            Integer curFlawId = remainFlaws.get(i).getId();
            String content = remainFlaws.get(i).getDescription() + remainFlaws.get(i).getStepdes();
            List<String> remainContents = new ArrayList<>();
            List<Integer> remainFlawIds = new ArrayList<>();
            for (int j = i + 1; j < remainFlaws.size(); j++) {
                remainFlawIds.add(remainFlaws.get(j).getId());
                remainContents.add(remainFlaws.get(j).getDescription() + remainFlaws.get(j).getStepdes());
            }
            List<String> contents = new ArrayList<>();
            contents.add(content);
            contents.addAll(remainContents);
            ResponseVO<List<Double>> responseVO = pythonService.getSimilarity(contents);
            if (responseVO.getCode() == Constant.REQUEST_FAIL) {
                return new ResponseVO<>(Constant.REQUEST_FAIL, "python服务器出错");
            } else {
                List<Double> similarities = responseVO.getData();
                for (int k = 0; k < similarities.size(); k++) {
                    Similarity similarity = new Similarity(curFlawId, remainFlawIds.get(k), similarities.get(k));
                    similarityMapper.insert(similarity);
                }
            }
        }

        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", resTBAFlaws);
    }

}
