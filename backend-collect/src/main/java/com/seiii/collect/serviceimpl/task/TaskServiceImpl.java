package com.seiii.collect.serviceimpl.task;

import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendFactorMapper;
import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.task.taskrecruitstop.TaskRecruitStopRecommendFactorMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.dto.task.TaskDTO;
import com.seiii.collect.model.po.flaw.Flaw;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.po.report.ReportView;
import com.seiii.collect.model.po.task.Task;
import com.seiii.collect.model.po.task.TaskTag;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.task.*;
import com.seiii.collect.service.recommend.RecommendService;
import com.seiii.collect.service.task.TaskService;
import com.seiii.collect.serviceimpl.recommend.CandidateTasksProvider;
import com.seiii.collect.serviceimpl.recommend.strategy.RecommendStrategy;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    TaskMapper taskMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    TaskUserMapper taskUserMapper;
    @Resource
    ReportMapper reportMapper;
    @Resource
    TaskTagMapper taskTagMapper;
    @Resource
    FlawMapper flawMapper;
    @Resource
    RecommendService recommendService;
    @Resource
    MultiObjectiveRecommendFactorMapper multiObjectiveRecommendFactorMapper;
    @Resource
    TaskRecruitStopRecommendFactorMapper taskRecruitStopRecommendFactorMapper;


    @Override
    public ResponseVO<List<TaskViewVO>> viewAllUnfinishedTasks(Integer pageId, Integer userId) {
        //Date curTime = new Timestamp(System.currentTimeMillis());

        /*// todo 如果start超出长度？
        List<TaskView> taskViews = taskMapper.selectBeforeEndTimeAndAfterStartTime(curTime, start, Constant.PAGE_SIZE);
        List<TaskViewVO> taskViewVOs = new ArrayList<>();
        for (TaskView taskView : taskViews) {
            List<Integer> userIds = taskUserMapper.selectByTaskId(taskView.getId());
            if (userIds.size() < taskView.getWorkernum()&&!userIds.contains(userId)) { // 判断是否已招满
                List<Integer> users = taskUserMapper.selectByTaskId(taskView.getId());
                List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
                taskViewVOs.add(new TaskViewVO(taskView, users.size(), tags));
            }
        }
        taskViewVOs = recommendStrategy.recommend(userId, taskViewVOs);*/
        List<TaskViewVO> recommendResult = recommendService.getAllRecommendedTasksForUser(userId);

        int start = Math.min((pageId - 1) * Constant.PAGE_SIZE, recommendResult.size());
        int postEnd = Math.min(start + Constant.PAGE_SIZE, recommendResult.size());

        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", recommendResult.subList(start, postEnd));
    }

    @Override
    public ResponseVO<TaskVO> viewTaskDetails(Integer taskId, Integer userId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if (task == null || task.getId() == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "任务查找失败！");
        }
        int state = task.getRecruitstop()?Constant.TASK_RECRUIT_STOP:Constant.TASK_UNCLAIMED;
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null || user.getId() == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "没有对应用户！");
        }
        if (user.getType().equals(Constant.USER_TYPE_WORKER)) {
            List<Integer> taskIds = taskUserMapper.selectByUserId(userId); // 所有
            List<Integer> finishedTaskIds = getSubmitReportTasks(userId); // 已完成
            List<Integer> submittedTaskIds = getAllSubmitReportTasks(userId); // 已提交
            if (taskIds.contains(taskId)) { // 已领取
                if (submittedTaskIds.contains(taskId)) { // 已提交
                    if (finishedTaskIds.contains(taskId)) { // 已完成
                        state = Constant.TASK_FINISHED;
                    } else {
                        state = Constant.TASK_TO_BE_IMPROVED;
                    }
                } else {
                    Date curTime = new Timestamp(System.currentTimeMillis());
                    if (taskMapper.selectByPrimaryKey(taskId).getEndtime().before(curTime)) { // 已逾期
                        state = Constant.TASK_EXPIRED;
                    } else {
                        state = Constant.TASK_ONGOING;
                    }
                }
            }
        }
        List<Integer> users = taskUserMapper.selectByTaskId(taskId);
        List<Integer> tags = taskTagMapper.selectByTaskId(taskId);
        TaskVO taskVO = new TaskVO(task, state, users.size(), tags);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskVO);
    }

    @Override
    public ResponseVO<TaskVO> viewTaskDetails(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if (task == null || task.getId() == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "任务查找失败！");
        }
        List<Integer> tags = taskTagMapper.selectByTaskId(taskId);
        TaskVO taskVO = new TaskVO(task, tags);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskVO);
    }

    @Override
    public ResponseVO<TaskVO> createTask(TaskDTO taskDTO) {
        Task task = new Task(taskDTO);
        if (taskMapper.insert(task) > 0) {
            TaskVO taskVO = new TaskVO(task, Constant.TASK_UNCLAIMED, 0, taskDTO.getTagGroups());
            Integer taskId = task.getId();
            ResponseVO<List<RecommendRuleFactorVO>> responseVO = updateRecommendElementsWeights(taskId, taskDTO.getRecommendFactor());
            if (Constant.REQUEST_FAIL.equals(responseVO.getCode())) {
                taskMapper.deleteByPrimaryKey(taskId);
                return new ResponseVO<>(Constant.REQUEST_FAIL, "任务创建失败!");
            }
            for (Integer tag : taskDTO.getTagGroups()) {
                TaskTag taskTag = new TaskTag(taskId, tag);
                taskTagMapper.insert(taskTag);
            }
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskVO);
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "任务创建失败！");
        }
    }

    @Override
    public ResponseVO<TaskVO> pickTask(Integer taskId, Integer userId) {
        TaskUser taskUser = new TaskUser(taskId, userId);
        if (taskUserMapper.insert(taskUser) > 0) {
            Task task = taskMapper.selectByPrimaryKey(taskId);
            if (task != null) {
                List<Integer> users = taskUserMapper.selectByTaskId(taskId);
                List<Integer> tags = taskTagMapper.selectByTaskId(taskId);
                TaskVO taskVO = new TaskVO(task, Constant.TASK_ONGOING, users.size(), tags);
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskVO);
            }
        }
        return new ResponseVO<>(Constant.REQUEST_FAIL, "选取任务失败！");
    }

    @Override
    // todo 暂未考虑其他情况，默认user选择了task并且有已经提交的报告
    public ResponseVO<Integer> getReportId(Integer taskId, Integer userId) {
        List<ReportView> reportViews = reportMapper.selectByUserId(userId);
        for (ReportView reportView : reportViews) {
            if (reportView.getTaskid() == taskId) {
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", reportView.getId());
            }
        }
        return new ResponseVO<>(Constant.REQUEST_FAIL, "错误");
    }

    @Override
    public ResponseVO<List<RecommendRuleFactorVO>> getRecommendElements() {
        List<RecommendRuleFactorVO> recommendRuleFactorVOs = new ArrayList<>();
        for (int i = 0; i < Constant.RECOMMEND_ELEMENTS.size(); i++) {
            recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_ELEMENTS.get(i), Constant.RECOMMEND_ELEMENTS_WEIGHTS.get(i)));
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", recommendRuleFactorVOs);
    }

    @Override
    public ResponseVO<List<RecommendRuleFactorVO>> updateRecommendElementsWeights(Integer taskId, List<RecommendRuleFactorVO> recommendRuleFactorVOs) {
        if (recommendRuleFactorVOs.size() != Constant.RECOMMEND_ELEMENTS.size()) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "请求失败");
        }
        MultiObjectiveRecommendFactor multiObjectiveRecommendFactor = new MultiObjectiveRecommendFactor();
        multiObjectiveRecommendFactor.setTaskid(taskId);
        for (RecommendRuleFactorVO recommendRuleFactorVO : recommendRuleFactorVOs) {
            switch (recommendRuleFactorVO.getName()) {
                case "工人能力":
                    multiObjectiveRecommendFactor.setWorkerAbility(recommendRuleFactorVO.getWeight() == null ? Constant.RECOMMEND_ELEMENTS_WEIGHTS.get(0) : recommendRuleFactorVO.getWeight());
                    break;
                case "工人活跃度":
                    multiObjectiveRecommendFactor.setActiveness(recommendRuleFactorVO.getWeight() == null ? Constant.RECOMMEND_ELEMENTS_WEIGHTS.get(1) : recommendRuleFactorVO.getWeight());
                    break;
                case "工人相关性":
                    multiObjectiveRecommendFactor.setTaskRelevance(recommendRuleFactorVO.getWeight() == null ? Constant.RECOMMEND_ELEMENTS_WEIGHTS.get(2) : recommendRuleFactorVO.getWeight());
                    break;
                case "工人多样性":
                    multiObjectiveRecommendFactor.setWorkerDiversity(recommendRuleFactorVO.getWeight() == null ? Constant.RECOMMEND_ELEMENTS_WEIGHTS.get(3) : recommendRuleFactorVO.getWeight());
                    break;
                case "开销":
                    multiObjectiveRecommendFactor.setWorkerCost(recommendRuleFactorVO.getWeight() == null ? Constant.RECOMMEND_ELEMENTS_WEIGHTS.get(4) : recommendRuleFactorVO.getWeight());
                    break;
            }
        }
        if (multiObjectiveRecommendFactorMapper.selectByPrimaryKey(taskId) != null) {
            multiObjectiveRecommendFactorMapper.updateByPrimaryKey(multiObjectiveRecommendFactor);
        } else {
            multiObjectiveRecommendFactorMapper.insert(multiObjectiveRecommendFactor);
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", recommendRuleFactorVOs);

    }

    @Override
    public ResponseVO<List<RecommendRuleFactorVO>> getRecommendElementsWeights(Integer taskId) {
        MultiObjectiveRecommendFactor multiObjectiveRecommendFactor = multiObjectiveRecommendFactorMapper.selectByPrimaryKey(taskId);
        if (multiObjectiveRecommendFactor == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "请求失败");
        }
        List<RecommendRuleFactorVO> recommendRuleFactorVOs = new ArrayList<>();
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_ELEMENTS.get(0), multiObjectiveRecommendFactor.getWorkerAbility()));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_ELEMENTS.get(1), multiObjectiveRecommendFactor.getActiveness()));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_ELEMENTS.get(2), multiObjectiveRecommendFactor.getTaskRelevance()));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_ELEMENTS.get(3), multiObjectiveRecommendFactor.getWorkerDiversity()));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_ELEMENTS.get(4), multiObjectiveRecommendFactor.getWorkerCost()));
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", recommendRuleFactorVOs);
    }

    @Override
    public ResponseVO<TaskRadarVO> getCurrentTaskRadar(Integer taskId) {
        TaskRecruitStopRecommendFactor factor = taskRecruitStopRecommendFactorMapper.selectByPrimaryKey(taskId);
        if (factor == null) return new ResponseVO<>(Constant.REQUEST_FAIL, "暂无该任务的相关数据");
        TaskRadarVO taskRadarVO = new TaskRadarVO(factor.getAbilityactual(), factor.getActivenessactual(), factor.getRelevanceactual(), factor.getDiversityactual(), factor.getAvgtargetactual());
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskRadarVO);
    }


    @Override
    public ResponseVO<TaskRadarVO> getTargetTaskRadar(Integer taskId) {
        TaskRecruitStopRecommendFactor factor = taskRecruitStopRecommendFactorMapper.selectByPrimaryKey(taskId);
        if (factor == null) return new ResponseVO<>(Constant.REQUEST_FAIL, "暂无该任务的相关数据");
        TaskRadarVO taskRadarVO = new TaskRadarVO(
                factor.getAbilityexpected(),
                factor.getActivenessexpected(),
                factor.getRelevanceactual(), factor.getDiversityexpected(), factor.getAvgtargetexpected());
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskRadarVO);
    }

    @Override
    public ResponseVO<Object> stopTaskRecruit(Integer taskId){
        taskMapper.updateRecruitStopByPrimaryKey(taskId);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功");
    }

    @Override
    public ResponseVO<TaskFlawDetectionPredictionVO> getFlawCurveAndNumPredicted(Integer taskId){
        List<Flaw> taskFlaws = flawMapper.selectByTaskId(taskId);
        List<Report> taskReports=reportMapper.selectReportsByTaskId(taskId);
        HashMap<Integer,LocalDate> taskReportsTimeMap=new HashMap<>();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for(Report taskReport:taskReports){
            Date taskReportCreateTime=taskReport.getCreatetime();

            ZoneId zoneId = ZoneId.systemDefault();
            //atZone()方法返回在指定时区,从该Instant生成的ZonedDateTime
            ZonedDateTime zonedDateTime = taskReportCreateTime.toInstant().atZone(zoneId);
            LocalDate taskReportTime = zonedDateTime.toLocalDate();

            taskReportsTimeMap.put(taskReport.getId(),taskReportTime);
        }
//        List<Flaw> taskRootFlaws = new ArrayList<>();
        List<TaskFlawDetectionCurveVO> taskFlawDetectionCurveVOs1=new ArrayList<>();
        List<TaskFlawDetectionCurveVO> taskFlawDetectionCurveVOS2=new ArrayList<>();
        for (Flaw taskFlaw : taskFlaws) {
            //未处理的缺陷不会包含
            if (taskFlaw.getState() == false)
                continue;
            Integer taskFlawId = taskFlaw.getId();
            String taskFlawPath = taskFlaw.getPath();
            if (taskFlawPath.equals(String.valueOf(taskFlawId))) {
                TaskFlawDetectionCurveVO taskFlawDetectionCurveVO=new TaskFlawDetectionCurveVO(taskReportsTimeMap.get(taskFlaw.getReportid()),1);
                taskFlawDetectionCurveVOs1.add(taskFlawDetectionCurveVO);
            }
        }
        Collections.sort(taskFlawDetectionCurveVOs1, new Comparator<TaskFlawDetectionCurveVO>() {
            @Override
            public int compare(TaskFlawDetectionCurveVO o1, TaskFlawDetectionCurveVO o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        int metaFlawNumSoFar=0;
        for(int i=0,j=0;i<taskFlawDetectionCurveVOs1.size();){
            TaskFlawDetectionCurveVO tmpCurveVO=taskFlawDetectionCurveVOs1.get(i);
            LocalDate curveDateCur=tmpCurveVO.getTime();
            if(i==0){
                taskFlawDetectionCurveVOS2.add(tmpCurveVO);
                metaFlawNumSoFar++;
                i++;
                continue;
            }
            TaskFlawDetectionCurveVO tmpCurveVO2=taskFlawDetectionCurveVOS2.get(j);
            LocalDate curveDateCur2=tmpCurveVO2.getTime();
            if(curveDateCur.equals(curveDateCur2)){
                int tmpNum=tmpCurveVO2.getNum();
                metaFlawNumSoFar=tmpNum+1;
                tmpCurveVO2.setNum(tmpNum+1);
                i++;
            }else{
                curveDateCur2=curveDateCur2.plusDays(1);
                tmpCurveVO=new TaskFlawDetectionCurveVO(curveDateCur2,tmpCurveVO2.getNum());
                taskFlawDetectionCurveVOS2.add(tmpCurveVO);
                j++;
            }
        }
        Integer flawNumPredicted=getFlawNumPredicted(taskId);
        if(flawNumPredicted<metaFlawNumSoFar+2){
            flawNumPredicted=metaFlawNumSoFar+2;
        }
        TaskFlawDetectionPredictionVO taskFlawDetectionPredictionVO=new TaskFlawDetectionPredictionVO(flawNumPredicted,taskFlawDetectionCurveVOS2);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",taskFlawDetectionPredictionVO);
    }

    //预测任务可以发现的flaw总数
    private Integer getFlawNumPredicted(Integer taskId){
        List<Flaw> taskFlaws = flawMapper.selectByTaskId(taskId);
        List<Report> taskReports=reportMapper.selectReportsByTaskId(taskId);
        HashMap<Integer,HashSet<Integer>> reportMetaFlawMap=new HashMap<>();
        List<Integer> reportRowMetaFlawList=new ArrayList<>();
        HashMap<Integer,Integer> metaFlawOccurCntMap=new HashMap<>();
        HashMap<Integer,Integer> occurCntFlawNumMap=new HashMap<>();
        for(Flaw taskFlaw:taskFlaws){
            Integer reportId=taskFlaw.getReportid();
            Integer metaFlawId=taskFlaw.getId();
            String flawPath=taskFlaw.getPath();
            if(!flawPath.equals(String.valueOf(metaFlawId))){
                String metaFlawIdStr=flawPath.substring(0,flawPath.indexOf(","));
                metaFlawId=Integer.parseInt(metaFlawIdStr);
            }

            if(reportMetaFlawMap.containsKey(reportId)){
                HashSet<Integer> metaFlawIds=reportMetaFlawMap.get(reportId);
                metaFlawIds.add(metaFlawId);
                reportMetaFlawMap.put(reportId,metaFlawIds);
            }else{
                HashSet<Integer> metaFlawIds=new HashSet<>();
                metaFlawIds.add(metaFlawId);
                reportMetaFlawMap.put(reportId,metaFlawIds);
            }

            Integer metaFlawOccurCnt=metaFlawOccurCntMap.containsKey(metaFlawId)?(metaFlawOccurCntMap.get(metaFlawId)+1):1;
            metaFlawOccurCntMap.put(metaFlawId,metaFlawOccurCnt);
        }

        for(Report report:taskReports){
            if(reportMetaFlawMap.containsKey(report.getId())){
                reportRowMetaFlawList.add(reportMetaFlawMap.get(report.getId()).size());
            }
        }

        for(Integer metaFlawId:metaFlawOccurCntMap.keySet()){
            Integer occurCnt=metaFlawOccurCntMap.get(metaFlawId);
            Integer flawNum=occurCntFlawNumMap.containsKey(occurCnt)?(occurCntFlawNumMap.get(occurCnt)+1):1;
            occurCntFlawNumMap.put(occurCnt,flawNum);
        }


        double D=metaFlawOccurCntMap.size();
        double t=reportRowMetaFlawList.size();
        double f1=occurCntFlawNumMap.containsKey(1)?occurCntFlawNumMap.get(1):0;
        double co1=0,co2=0,co3=0;

        for(Integer k:occurCntFlawNumMap.keySet()){
            co1+=k*(k-1)*occurCntFlawNumMap.get(k);
        }

        for(int j=0;j<t;j++){
            for(Integer k:occurCntFlawNumMap.keySet()){
                if(j<k){
                    co2+=((double) reportRowMetaFlawList.get(j))*reportRowMetaFlawList.get(k-1);
                }
            }
        }

        for(Integer k:occurCntFlawNumMap.keySet()){
            co3+=k*occurCntFlawNumMap.get(k);
        }

        double C=1-f1/co3;
        double coefficient=(((D/C)*co1)/(2*co2))-1;
        coefficient=Math.max(coefficient,0);
        double N=(D/C)+((f1/C)*coefficient);

        return (int)Math.ceil(N);
    }



    //找到众包工人所有已提交报告的taskIds，包括已经处理完缺陷的和未处理完缺陷的
    private List<Integer> getAllSubmitReportTasks(Integer userId) {
        List<ReportView> reports = reportMapper.selectByUserId(userId);
        Set<Integer> tempTaskIds = new HashSet<>();
        for (ReportView report : reports) {
            tempTaskIds.add(report.getTaskid());
        }
        return new LinkedList<>(tempTaskIds);
    }

    //找到众包工人所有已提交报告的taskIds ---迭代二：且所有缺陷已被处理完
    private List<Integer> getSubmitReportTasks(Integer userId) {
        List<ReportView> reports = reportMapper.selectByUserId(userId);
        Set<Integer> tempTaskIds = new HashSet<>();
        for (ReportView report : reports) {
            List<Flaw> flaws = flawMapper.selectByReportId(report.getId());
            boolean isAllImproved = true;
            for (Flaw flaw : flaws) {
                if (!flaw.getState()) {
                    isAllImproved = false;
                    break;
                }
            }
            if (isAllImproved) {
                tempTaskIds.add(report.getTaskid());
            }
        }
        return new LinkedList<>(tempTaskIds);
    }


}
