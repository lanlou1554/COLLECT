package com.seiii.collect.serviceimpl.user;

import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.mapper.user.UserTagMapper;
import com.seiii.collect.mapper.user.worker.WorkerAbilityMapper;
import com.seiii.collect.mapper.user.worker.WorkerContextMapper;
import com.seiii.collect.model.dto.user.UserDTO;
import com.seiii.collect.model.dto.user.worker.WorkerContextDTO;
import com.seiii.collect.model.po.flaw.Flaw;
import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.po.report.ReportView;
import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.po.user.UserTag;
import com.seiii.collect.model.po.user.worker.WorkerAbility;
import com.seiii.collect.model.po.user.worker.WorkerContext;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.model.vo.user.UserVO;
import com.seiii.collect.model.vo.user.UserViewVO;
import com.seiii.collect.model.vo.user.worker.WorkerActivationVO;
import com.seiii.collect.model.vo.user.worker.WorkerCloudVO;
import com.seiii.collect.model.vo.user.worker.WorkerContextVO;
import com.seiii.collect.model.vo.user.worker.WorkerRadarVO;
import com.seiii.collect.service.python.PythonService;
import com.seiii.collect.service.report.ReportService;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.FormatUtil;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness.WorkerActiveness;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.ComprehensiveAbility;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    TaskMapper taskMapper;
    @Resource
    ReportMapper reportMapper;
    @Resource
    TaskUserMapper taskUserMapper;
    @Resource
    UserTagMapper userTagMapper;
    @Resource
    TaskTagMapper taskTagMapper;
    @Resource
    FlawMapper flawMapper;
    @Resource
    WorkerContextMapper workerContextMapper;
    @Resource
    PythonService pythonService;
    @Resource
    ReportService reportService;
    @Resource
    WorkerAbilityMapper workerAbilityMapper;
    @Resource
    ComprehensiveAbility comprehensiveAbility;

    @Override
    public ResponseVO<Object> userRegister(UserDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            if (userMapper.selectByUsername(username) == null) {
                User user = new User(dto);
                userMapper.insert(user);
                Integer userId = user.getId();
                if (user.getType() == 1) {
                    WorkerContext workerContext = new WorkerContext();
                    workerContext.setWorkerid(userId);
                    workerContext.setDevicetype("");
                    workerContext.setOsinfo("");
                    workerContext.setRamsize("");
                    workerContext.setNetenv("");
                    workerContextMapper.insert(workerContext);
                }
                for (Integer tag : dto.getTagGroups()) {
                    UserTag userTag = new UserTag(userId, tag);
                    userTagMapper.insert(userTag);
                }
                // 如果该用户为工人，则加入工人能力表
                if (dto.getType() == Constant.USER_TYPE_WORKER) {
                    List<Double> allAbilityValue = comprehensiveAbility.calAllAbilityValue(userId);
                    WorkerAbility workerAbility = new WorkerAbility(userId, allAbilityValue);
                    workerAbilityMapper.insert(workerAbility);
                }
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "注册成功！");
            } else {
                return new ResponseVO<>(Constant.REQUEST_FAIL, "该用户名已存在！");
            }
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "用户名或密码不能为空！");
        }
    }

    @Override
    public ResponseVO<UserViewVO> userLogin(String username, String password) {
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            User user = userMapper.selectByUsername(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    UserViewVO userViewVO = new UserViewVO(user);
                    return new ResponseVO<>(Constant.REQUEST_SUCCESS, "登陆成功！", userViewVO);
                } else {
                    return new ResponseVO<>(Constant.REQUEST_FAIL, "账号或密码错误！");
                }
            } else {
                return new ResponseVO<>(Constant.REQUEST_FAIL, "该用户名不存在！");
            }
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "用户名或密码不能为空！");
        }
    }

    @Override
    public ResponseVO<UserViewVO> getUserInfo(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            UserViewVO userViewVO = new UserViewVO(user);
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", userViewVO);
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "没有对应用户！");
        }
    }

    @Override
    public ResponseVO<List<UserViewVO>> getUserInfoList(List<Integer> userIds) {
        List<UserViewVO> res = new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                res.add(new UserViewVO(user));
            } else {
                res.add(new UserViewVO());
            }
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
    }

    @Override
    public ResponseVO<UserVO> viewUser(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            List<Integer> tags = userTagMapper.selectByUserId(userId);
            UserVO userVO = new UserVO(user, tags);
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", userVO);
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "没有对应用户！");
        }
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

    //众包工人查看个人历史完成已交报告的任务列表/发包方查看已发布的完成的任务列表（预览）
    //对于众包工人，用userId找到report表中的taskId；对于发包方，用userId和currentTime查找task表
    @Override
    public ResponseVO<List<TaskViewVO>> viewFinishedTasks(Integer userId, Integer pageId) {
        User user = userMapper.selectByPrimaryKey(userId);
        int start = (pageId - 1) * Constant.PAGE_SIZE;
        if (user.getType() == Constant.USER_TYPE_CONTRACTOR) {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", contractorViewTasks(userId, start, true));
        } else if (user.getType() == Constant.USER_TYPE_WORKER) {
            List<ReportView> reports = reportMapper.selectByUserIdAndPage(userId, start, Constant.PAGE_SIZE);
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
            List<Integer> taskIds = new LinkedList<>(tempTaskIds);
            if (taskIds.size() == 0) return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", new LinkedList<>());
            List<TaskView> taskViews = taskMapper.selectByIds(taskIds);
            List<TaskViewVO> res = new LinkedList<>();
            for (TaskView taskView : taskViews) {
                List<Integer> users = taskUserMapper.selectByTaskId(taskView.getId());
                List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
                res.add(new TaskViewVO(taskView, users.size(), tags));
            }
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "管理员没有任务");
        }
    }

    private List<TaskViewVO> contractorViewTasks(Integer userId, Integer start, boolean isFinished) {
        Date curTime = new Timestamp(System.currentTimeMillis());
        List<TaskView> taskViews = new LinkedList<>();
        if (isFinished) {
            taskViews = taskMapper.selectByUserIdAndAfterEndTime(userId, curTime, start, Constant.PAGE_SIZE);
        } else {
            taskViews = taskMapper.selectByUserIdAndBeforeEndTime(userId, curTime, start, Constant.PAGE_SIZE);
        }

        List<TaskViewVO> res = new LinkedList<>();
        for (TaskView taskView : taskViews) {
            List<Integer> users = taskUserMapper.selectByTaskId(taskView.getId());
            List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
            res.add(new TaskViewVO(taskView, users.size(), tags));
        }
        return res;
    }

    private List<TaskViewVO> workerViewUnfinishedAndExpiredTasks(Integer userId, Integer start, boolean isUnfinished) {
        List<Integer> taskIds = taskUserMapper.selectByUserId(userId);
        List<Integer> finishedTaskIds = getSubmitReportTasks(userId);
        taskIds.removeAll(finishedTaskIds);
        if (taskIds.size() == 0) return new LinkedList<>();
        List<TaskView> taskViews = taskMapper.selectByIds(taskIds);
        List<TaskViewVO> resUf = new LinkedList<>();
        List<TaskViewVO> resEx = new LinkedList<>();
        Date curTime = new Timestamp(System.currentTimeMillis());
        int resUfC = 0, resExC = 0;
        for (TaskView taskView : taskViews) {
            List<Integer> users = taskUserMapper.selectByTaskId(taskView.getId());
            if (taskView.getEndtime().before(curTime)) {
                if (resExC >= start && resExC < (Constant.PAGE_SIZE + start)) {
                    List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
                    resEx.add(new TaskViewVO(taskView, users.size(), tags));
                }
                resExC++;
            } else {
                if (resUfC >= start && resUfC < (Constant.PAGE_SIZE + start)) {
                    List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
                    resUf.add(new TaskViewVO(taskView, users.size(), tags));
                }
                resUfC++;
            }
        }
        if (isUnfinished) return resUf;
        else return resEx;
    }

    //众包工人查看正在执行的任务列表/发包方查看已发布的正在执行中的任务列表（预览）
    //TODO 目前众包工人交了报告就算完成 ---迭代二：还要处理完所有缺陷
    //对于众包工人，拿userId找到他选取的所有任务，然后再拿userId找到他提交的所有报告对应的taskId，返回其中没有逾期的
    //对于发包方，用userId和currentTime查找task表
    @Override
    public ResponseVO<List<TaskViewVO>> viewUnfinishedTasks(Integer userId, Integer pageId) {
        User user = userMapper.selectByPrimaryKey(userId);
        int start = (pageId - 1) * Constant.PAGE_SIZE;
        if (user.getType() == Constant.USER_TYPE_CONTRACTOR) {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", contractorViewTasks(userId, start, false));
        } else if (user.getType() == Constant.USER_TYPE_WORKER) {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", workerViewUnfinishedAndExpiredTasks(userId, start, true));
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "管理员没有任务");
        }
    }

    //众包工人查看个人逾期未交报告的任务
    @Override
    public ResponseVO<List<TaskViewVO>> viewExpiredTasks(Integer userId, Integer pageId) {
        User user = userMapper.selectByPrimaryKey(userId);
        int start = (pageId - 1) * Constant.PAGE_SIZE;
        if (user.getType() == Constant.USER_TYPE_CONTRACTOR) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "发包方没有逾期任务");
        } else if (user.getType() == Constant.USER_TYPE_WORKER) {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", workerViewUnfinishedAndExpiredTasks(userId, start, false));
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "管理员没有任务");
        }
    }

    //预览任务中的报告列表。对于众包工人和发包方点进任务可看到相应的测试报告
    //发包方点击任务可以看到所有的测试报告;众包工人可以看到所有报告，但不包括未完成的
    @Override
    public ResponseVO<List<ReportViewVO>> viewReportsFromTask(Integer userId, Integer taskId, Integer pageId) {
        List<ReportView> reportViews = reportMapper.selectByTaskId(taskId);
        User user = userMapper.selectByPrimaryKey(userId);
        //必须在此处判断该user是否能调此接口，如果是发包方，则检查有没有发布此任务；如果是众包工人，则判断有无提交过报告
        if (user.getType().equals(Constant.USER_TYPE_CONTRACTOR)) { // 发包方
            if (taskMapper.selectByPrimaryKey(taskId).getUserid() != userId) {
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功，该发包方未发布过此任务", new ArrayList<>());
            }
        } else { // 众包工人
            List<Integer> finishedTaskIds = getSubmitReportTasks(userId);
            if (!finishedTaskIds.contains(taskId)) {
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功，该众包工人未提交过报告", new ArrayList<>());
            }
        }
        int start = (pageId - 1) * Constant.PAGE_SIZE;
        List<ReportViewVO> res = new LinkedList<>();
        int count = 0;
        for (ReportView reportView : reportViews) {
            if (count >= start && count < (start + Constant.PAGE_SIZE)) {
                List<Flaw> flaws = flawMapper.selectByReportId(reportView.getId());
                boolean isAllImproved = true;
                for (Flaw flaw : flaws) {
                    if (!flaw.getState()) {
                        isAllImproved = false;
                        break;
                    }
                }
                if (isAllImproved) {
                    Double score = calculateReportScore(reportView.getId());
                    Integer flawNum = flaws.size();
                    res.add(new ReportViewVO(reportView, score, flawNum));
                    count++;
                }
            }
            if (count >= (start + Constant.PAGE_SIZE)) break;
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
//        if (user.getType() == Constant.USER_TYPE_CONTRACTOR) {
//            List<ReportViewVO> res = new LinkedList<>();
//            for (int i = 0; i < reportViews.size(); i++) {
//                if (i >= start && i < (start + Constant.PAGE_SIZE)) {
//                    res.add(new ReportViewVO(reportViews.get(i)));
//                }
//                if (i >= (start + Constant.PAGE_SIZE)) break;
//            }
//            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
//        } else if (user.getType() == Constant.USER_TYPE_WORKER) {
//            List<ReportViewVO> res = new LinkedList<>();
//            int count = 0;
//            for (int i = 0; i < reportViews.size(); i++) {
//                if (reportViews.get(i).getUserid() == userId) {
//                    if (count >= start && count < (start + Constant.PAGE_SIZE)) {
//                        res.add(new ReportViewVO(reportViews.get(i)));
//                    }
//                    count++;
//                }
//                if (count >= (start + Constant.PAGE_SIZE)) break;
//            }
//            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
//        } else {
//            return new ResponseVO<>(Constant.REQUEST_FAIL, "管理员没有报告");
//        }
    }

    //TODO 目前只能众包工人调用此方法
    @Override
    public ResponseVO<List<ReportViewVO>> viewWorkerAllReports(Integer userId, Integer pageId) {
        User user = userMapper.selectByPrimaryKey(userId);
        int start = (pageId - 1) * Constant.PAGE_SIZE;
        if (user.getType() == Constant.USER_TYPE_CONTRACTOR) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "发包方暂时不能预览所有收到的报告");
        } else if (user.getType() == Constant.USER_TYPE_WORKER) {
            List<ReportView> reportViews = reportMapper.selectByUserIdAndPage(userId, start, Constant.PAGE_SIZE);
            List<ReportViewVO> res = new LinkedList<>();
            // todo 其实这么写不是按照严格pageId返回固定数量
            for (ReportView reportView : reportViews) {
                List<Flaw> flaws = flawMapper.selectByReportId(reportView.getId());
                boolean isAllImproved = true;
                for (Flaw flaw : flaws) {
                    if (!flaw.getState()) {
                        isAllImproved = false;
                        break;
                    }
                }
                if (isAllImproved) {
                    Double score = calculateReportScore(reportView.getId());
                    Integer flawNum = flaws.size();
                    res.add(new ReportViewVO(reportView, score, flawNum));
                }
            }
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "管理员没有报告");
        }

    }

    @Override
    public ResponseVO<ReportViewVO> viewWorkerTaskReport(Integer userId, Integer taskId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (!user.getType().equals(Constant.USER_TYPE_WORKER)) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "发包方无此任务报告");
        }
        List<Integer> finishedTaskIds = getSubmitReportTasks(userId);
        if (!finishedTaskIds.contains(taskId)) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "该众包工人未提交过报告或缺陷未处理完", new ReportViewVO());
        }
        List<ReportView> reportViews = reportMapper.selectByTaskId(taskId);
        ReportViewVO reportViewVO = new ReportViewVO();
        for (ReportView reportView : reportViews) {
            if (reportView.getUserid().equals(userId)) {
                Double score = calculateReportScore(reportView.getId());
                Integer flawNum = flawMapper.selectByReportId(reportView.getId()).size();
                reportViewVO = new ReportViewVO(reportView, score, flawNum);
                break;
            }
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", reportViewVO);
    }

    @Override
    public ResponseVO<Object> addUserTag(Integer userId, Integer tag) {
        List<Integer> userTags = userTagMapper.selectByUserId(userId);
        if (userTags.contains(tag))
            return new ResponseVO<>(Constant.REQUEST_FAIL, "不能重复添加相同标签");
        UserTag userTag = new UserTag(userId, tag);
        userTagMapper.insert(userTag);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "标签添加成功");
    }

    @Override
    public ResponseVO<Object> deleteUserTag(Integer userId, Integer tag) {
        List<Integer> userTags = userTagMapper.selectByUserId(userId);
        if (userTags.contains(tag)) {
            userTagMapper.deleteByPrimaryKey(userId, tag);
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "标签删除成功");
        } else {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "不能删除没有添加的标签");
        }
    }

    @Override
    public ResponseVO<WorkerContextVO> getWorkerContext(Integer userId) {
        WorkerContext workerContext = workerContextMapper.selectByPrimaryKey(userId);
        WorkerContextVO workerContextVO = new WorkerContextVO(workerContext);
        if (workerContext == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "无此工人", workerContextVO);
//            workerContextVO.setId(userId);
//            workerContextVO.setDeviceType("");
//            workerContextVO.setOsInfo("");
//            workerContextVO.setRamSize("");
//            workerContextVO.setNetEnv("");
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", workerContextVO);
    }

    @Override
    public ResponseVO<WorkerContextVO> updateWorkerContext(Integer userId, WorkerContextDTO workerContextDTO) {
        WorkerContext workerContext = new WorkerContext(userId, workerContextDTO);
        workerContextMapper.updateByPrimaryKey(workerContext);
        return getWorkerContext(userId);
    }

    @Override
    public ResponseVO<WorkerRadarVO> getWorkerRadar(Integer userId) {
        List<Integer> abilities = FormatUtil.getAbility(userId);
        WorkerRadarVO workerRadarVO = new WorkerRadarVO(abilities);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", workerRadarVO);
    }

    @Override
    public ResponseVO<WorkerRadarVO> getWorkerAvgRadar() {
        List<Integer> avgAbility = FormatUtil.getAvgAbility();
        WorkerRadarVO workerRadarVO = new WorkerRadarVO(avgAbility);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", workerRadarVO);
    }

    @Override
    public ResponseVO<WorkerActivationVO> getWorkerActiveness(Integer userId) {
        WorkerActiveness workerActiveness = new WorkerActiveness();
        Double activeness = workerActiveness.getActivenessValue(userId);
        int score = FormatUtil.formatAbility(activeness);
        WorkerActivationVO workerActivationVO = new WorkerActivationVO(
                workerActiveness.getLastSubmitTime(),
                workerActiveness.getSubReportNumThreeDay(),
                workerActiveness.getSubReportNumTwoWeek(),
                workerActiveness.getSubReportNumOneMon(),
                workerActiveness.getSubReportNumHalfYear(),
                score
        );
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", workerActivationVO);
    }


    private Double calculateReportScore(Integer reportId) {
        List<Flaw> flaws = flawMapper.selectByReportId(reportId);
        Double score = 0.0;
        int count = 0;
        boolean hasScore = false;
        for (Flaw flaw : flaws) {
            if (flaw.getScore() >= 0) {
                score += flaw.getScore();
                count++;
                hasScore = true;
            }
        }
        if (!hasScore) {
            score = -1.0;
        } else {
            score /= count;
        }
        return score;
    }

    @Override
    public ResponseVO<List<WorkerCloudVO>> getWorkerWordCloud(@PathVariable Integer userId) {
        ResponseVO<Map<String, Double>> rawData;
        Integer pageId = 1;
        List<Integer> ids = new LinkedList<>();
        while (true) {
            ResponseVO<List<ReportViewVO>> tempResponse = viewWorkerAllReports(userId, pageId);
            if (tempResponse.code == Constant.REQUEST_FAIL) {
                break;
            }
            List<ReportViewVO> temp = tempResponse.data;
            if (temp == null || temp.size() == 0) {
                break;
            }
            ids.addAll(temp.stream().map(ReportViewVO::getId).collect(Collectors.toList()));
            pageId++;
        }
        List<String> res = new LinkedList<>();
        for (Integer idx : ids) {
            ReportVO report = reportService.viewReportDetails(idx).data;
            List<FlawVO> flaws = report.getFlaws();
            res.addAll(flaws.stream().map(FlawVO::getDescription).collect(Collectors.toList()));
            res.addAll(flaws.stream().map(FlawVO::getStepDes).collect(Collectors.toList()));
        }
        if (res.size() == 0) {
            return new ResponseVO<>(Constant.REQUEST_SUCCESS, "该用户无历史信息", new LinkedList<>());
        }
        rawData = pythonService.getTFIDF(res);
        List<WorkerCloudVO> workerCloudVOS = new LinkedList<>();
        for (String key : rawData.data.keySet()) {
            WorkerCloudVO workerCloudVO = new WorkerCloudVO(key, (int) (rawData.data.get(key) * 100));
            workerCloudVOS.add(workerCloudVO);
        }
        if (rawData.code == Constant.REQUEST_FAIL) {
            return new ResponseVO<>(rawData.code, rawData.msg);
        } else {
            return new ResponseVO<>(rawData.code, rawData.msg, workerCloudVOS);
        }
    }

    @Override
    public ResponseVO<Object> refreshWorkerAbility() {
        List<Integer> allUserIds = BugProbabilityHelper.getAllUserIds();
        for (Integer userId : allUserIds) {
            List<Double> allAbilityValue = comprehensiveAbility.calAllAbilityValue(userId);
            WorkerAbility workerAbility = new WorkerAbility(userId, allAbilityValue);
            // assert 数据库工人能力表中已有该userId
            workerAbilityMapper.updateByPrimaryKey(workerAbility);
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功");
    }

}
