package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability;

import com.seiii.collect.mapper.flaw.*;
import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.mapper.user.worker.WorkerAbilityMapper;
import com.seiii.collect.model.po.flaw.*;
import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.po.report.ReportView;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.po.user.worker.WorkerAbility;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.flaw.SimilarFlawVO;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness.ScopeType;
import com.seiii.collect.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BugProbabilityHelper {

    private static FlawMapper flawMapper;
    private static ReportMapper reportMapper;
    private static TaskUserMapper taskUserMapper;
    private static SimilarityMapper similarityMapper;
    private static EvaluationMapper evaluationMapper;
    private static EvaLikeMapper evaLikeMapper;
    private static ScoreMapper scoreMapper;
    private static UserMapper userMapper;
    private static WorkerAbilityMapper workerAbilityMapper;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        BugProbabilityHelper.flawMapper = applicationContext.getBean(FlawMapper.class);
        BugProbabilityHelper.reportMapper = applicationContext.getBean(ReportMapper.class);
        BugProbabilityHelper.taskUserMapper = applicationContext.getBean(TaskUserMapper.class);
        BugProbabilityHelper.similarityMapper = applicationContext.getBean(SimilarityMapper.class);
        BugProbabilityHelper.evaluationMapper = applicationContext.getBean(EvaluationMapper.class);
        BugProbabilityHelper.evaLikeMapper = applicationContext.getBean(EvaLikeMapper.class);
        BugProbabilityHelper.scoreMapper = applicationContext.getBean(ScoreMapper.class);
        BugProbabilityHelper.userMapper = applicationContext.getBean(UserMapper.class);
        BugProbabilityHelper.workerAbilityMapper = applicationContext.getBean(WorkerAbilityMapper.class);
    }

    private BugProbabilityHelper() {
    }

    // 获取该工人提交的缺陷列表
    public static List<Flaw> getSubmitFlawList(Integer userId) {
        List<ReportView> reportViews = reportMapper.selectByUserId(userId);
        List<Flaw> resFlawList = new ArrayList<>();
        for (ReportView reportView : reportViews) {
            List<Flaw> flaws = flawMapper.selectByReportId(reportView.getId());
            for (Flaw flaw : flaws) {
                // 只计算已经完善的缺陷
                if (flaw.getState()) {
                    resFlawList.add(flaw);
                }
            }
        }
        return resFlawList;
    }

    // 获取该工人fork其他缺陷的数量
    public static Integer getForkingFlawNum(Integer userId) {
        Integer flawCnt = 0;
        List<Flaw> submitFlawList = getSubmitFlawList(userId);
        for (Flaw flaw : submitFlawList) {
            String path = flaw.getPath();
            if (path != null && path.contains(",")) {
                flawCnt++;
            }
        }
        return flawCnt;
    }

    // 获取该工人fork其他缺陷的缺陷列表
    public static List<Flaw> getForkingFlawList(Integer userId) {
        List<Flaw> resFlawList = new ArrayList<>();
        List<Flaw> submitFlawList = getSubmitFlawList(userId);
        for (Flaw flaw : submitFlawList) {
            String path = flaw.getPath();
            if (path != null && path.contains(",")) {
                resFlawList.add(flaw);
            }
        }
        return resFlawList;
    }

    // 获取该工人的缺陷中作为根节点的缺陷数量
    public static Integer getRootFlawNum(Integer userId) {
        Integer flawCnt = 0;
        List<Flaw> submitFlawList = getSubmitFlawList(userId);
        for (Flaw flaw : submitFlawList) {
            String path = flaw.getPath();
            Integer flawId = flaw.getId();
            if (path != null && !path.equals("") && path.equals(String.valueOf(flawId))) {
                flawCnt++;
            }
        }
        return flawCnt;
    }

    // 获取该工人的缺陷中，缺陷被直接或间接fork的次数
    public static Integer getForkedFlawNum(Integer userId) {
        Integer forkedCnt = 0;
        List<Flaw> submitFlaws = getSubmitFlawList(userId);
        List<Flaw> allFlaws = flawMapper.selectAll();
        for (Flaw flaw1 : submitFlaws) {
            Integer flawId = flaw1.getId();
            for (Flaw flaw2 : allFlaws) {
                String path = flaw2.getPath();
                if (path != null && !path.equals("")) {
                    List<String> strs = Arrays.asList(path.split(","));
                    List<String> strings = new ArrayList<>(strs); // Arrays$ArrayList和ArrayList都是继承AbstractList，remove，add等method在AbstractList中是默认throw UnsupportedOperationException而且不作任何操作
                    strings.remove(strings.size() - 1); //去除自身
                    if (strings.contains(String.valueOf(flawId))) {
                        forkedCnt++;
                    }
                }
            }
        }
        return forkedCnt;
    }

    // 获取该缺陷所属的缺陷树中的所有缺陷列表
    public static List<Flaw> getAllFlawListFromTree(Integer flawId) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);
        Integer taskId = flaw.getTaskid();
        List<Flaw> sameTaskFlaws = flawMapper.selectByTaskId(taskId);
        String flawPath = flaw.getPath();
        int rootFlawId = Integer.parseInt(flawPath.split(",")[0]);
        List<Flaw> allFlawsFromTree = new ArrayList<>();
        for (Flaw fl : sameTaskFlaws) {
            String path = fl.getPath();
            if (path != null && !path.equals("") &&
                    Integer.parseInt(path.split(",")[0]) == rootFlawId) {
                allFlawsFromTree.add(fl);
            }
        }
        return allFlawsFromTree;
    }

    // 获取该缺陷所属的缺陷树中的所有缺陷个数
    public static Integer getAllFlawNumFromTree(Integer flawId) {
        return getAllFlawListFromTree(flawId).size();
    }

    // 获取该工人提交的缺陷数量
    public static Integer getSubmitFlawNum(Integer userId) {
        return getSubmitFlawList(userId).size();
    }

    // 获取该工人提交的报告数量
    public static Integer getSubmitReportNum(Integer userId) {
        List<ReportView> reportViews = reportMapper.selectByUserId(userId);
        return reportViews.size();
    }

    // 获取该工人参与的任务数量
    public static Integer getParticipateTaskNum(Integer userId) {
        List<Integer> tasks = taskUserMapper.selectByUserId(userId);
        return tasks.size();
    }

    // 获取该工人在一定时间范围内提交的报告数量
    public static Integer getSubmitReportNumByTime(Integer userId, ScopeType scopeType) {
        Integer reportCnt = 0;
        long nd = 1000 * 24 * 60 * 60;
        List<ReportView> reportViews = reportMapper.selectByUserId(userId);
        for (ReportView reportView : reportViews) {
            Report report = reportMapper.selectByPrimaryKey(reportView.getId());
            long diff = Math.abs(report.getCreatetime().getTime() - (new Date()).getTime());
            long day = diff / nd;
            if (day <= scopeType.getDays()) {
                reportCnt++;
            }
        }
        return reportCnt;
    }

    // 获取某个任务下的所有根缺陷的数量（缺陷图中缺陷个数），作为该任务缺陷总类别个数
    public static Integer getAllFlawNumFromTask(Integer taskId) {
        Integer flawCnt = 0;
        List<Flaw> taskFlaws = flawMapper.selectByTaskId(taskId);
        for (Flaw taskFlaw : taskFlaws) {
            //未处理的缺陷不会包含在相似图中
            if (!taskFlaw.getState())
                continue;
            Integer taskFlawId = taskFlaw.getId();
            String taskFlawPath = taskFlaw.getPath();
            if (taskFlawPath.equals(String.valueOf(taskFlawId))) {
                flawCnt++;
            }
        }
        return flawCnt;
    }

    // 该工人在该任务下提交的缺陷个数
    public static Integer getSubmitFlawNumFromTask(Integer userId, Integer taskId) {
        Integer flawCnt = 0;
        List<Flaw> flaws = flawMapper.selectByTaskId(taskId);
        for (Flaw flaw : flaws) {
            if (!flaw.getState())
                continue;
            Integer reportId = flaw.getReportid();
            Report report = reportMapper.selectByPrimaryKey(reportId);
            if (report.getUserid() == userId) {
                flawCnt++;
            }
        }
        return flawCnt;
    }

    // 获取所有缺陷的数量
    public static Integer getAllFlawNum() {
        List<Flaw> flaws = flawMapper.selectAll();
        return flaws.size();
    }

    // 计算给定缺陷和该缺陷所属任务下所有缺陷的相似度列表
    public static List<Double> getSimilarityList(Integer flawId) {
        List<Similarity> similarities = similarityMapper.selectByFlawIdCompared(flawId);
        List<Similarity> similarities1 = similarityMapper.selectByFlawIdSecondCompared(flawId);
        similarities.addAll(similarities1);
        List<Double> resSim = new ArrayList<>();
        for (Similarity s : similarities) {
            Integer tmpFlawId = s.getFlawid2();
            tmpFlawId = tmpFlawId.equals(flawId) ? s.getFlawid1() : tmpFlawId;
            Flaw tmpFlaw = flawMapper.selectByPrimaryKey(tmpFlawId);
            //未处理的相似缺陷不算在相似列表中
            if (tmpFlaw.getState()) {
                resSim.add(s.getSimilarity());
            }
        }
        return resSim;
    }

    // 获取该工人已经提交过完善缺陷的任务id列表
    public static List<Integer> getSubmitTaskIdList(Integer userId) {
        List<Flaw> submitFlawList = BugProbabilityHelper.getSubmitFlawList(userId);
        Set<Integer> resTaskIds = new HashSet<>();
        for (Flaw flaw : submitFlawList) {
            Integer taskid = flaw.getTaskid();
            resTaskIds.add(taskid);
        }
        return new ArrayList<>(resTaskIds);
    }

    // 获取该工人的所有评价的点赞、点踩数，以二维列表形式返回
    public static List<List<Integer>> getAllEvaInfo(Integer userId) {
//        List<Integer> likeAndDislike1 = new ArrayList<>();
//        likeAndDislike1.add(7);
//        likeAndDislike1.add(2);
//        List<Integer> likeAndDislike2 = new ArrayList<>();
//        likeAndDislike2.add(3);
//        likeAndDislike2.add(1);
//        List<List<Integer>> res = new ArrayList<>();
//        res.add(likeAndDislike1);
//        res.add(likeAndDislike2);
//        return res; // [[7,2],[3,1]]

        List<Evaluation> evaluations = evaluationMapper.selectByUserId(userId);
        List<List<Integer>> res = new ArrayList<>();
        for (Evaluation eva : evaluations) {
            Integer evaId = eva.getId();
            EvaLikeStatistic evaLike = evaLikeMapper.selectCountByEva(evaId);
            List<Integer> likeAndDislike = new ArrayList<>();
            // 只返回有点赞点踩记录的评论
            if (evaLike != null) {
                likeAndDislike.add(evaLike.getSupportcnt());
                likeAndDislike.add(evaLike.getOpposecnt());
                res.add(likeAndDislike);
            }
        }
        return res;
    }

    // 获取该工人对其他缺陷的所有评分列表
    public static List<Score> getAllScoreList(Integer userId) {
        return scoreMapper.selectByUserId(userId);
    }

    // 根据缺陷id获取缺陷的评分（即平均得分）, -1.0 表示未评分
    public static Double getScoreFromFlawId(Integer flawId) {
        Flaw flaw = flawMapper.selectByPrimaryKey(flawId);
        return flaw.getScore();
    }

    // 获取该工人最后一次提交报告的时间
    public static Date getLastSubmitTime(Integer userId) {
        List<ReportView> reportViews = reportMapper.selectByUserId(userId);
        if (reportViews.size() == 0) {
            return null;
        }
        ReportView firstView = reportViews.get(0);
        Report firstReport = reportMapper.selectByPrimaryKey(firstView.getId());
        Date maxDate = firstReport.getCreatetime();
        for (int i = 1; i < reportViews.size(); i++) {
            Report report = reportMapper.selectByPrimaryKey(reportViews.get(i).getId());
            if (report.getCreatetime().getTime() > maxDate.getTime()) {
                maxDate = report.getCreatetime();
            }
        }
        return maxDate;
    }

    // 获取当前所有工人的id列表
    public static List<Integer> getAllUserIds() {
        List<User> users = userMapper.selectAll();
        List<Integer> res = new ArrayList<>();
        for (User user : users) {
            // 筛选出工人
            if (user.getType() == Constant.USER_TYPE_WORKER) {
                res.add(user.getId());
            }
        }
        return res;
    }

    // 通过userId获取WorkerAbility
    public static WorkerAbility getWorkerAbilityByUserId(Integer userId) {
        return workerAbilityMapper.selectByPrimaryKey(userId);
    }

}
