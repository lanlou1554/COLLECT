package com.seiii.collect.serviceimpl.admin;

import com.seiii.collect.mapper.recommend.RecommendRuleFactorMapper;
import com.seiii.collect.mapper.recommend.RecommendRuleMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import com.seiii.collect.model.po.recommend.RecommendRule;
import com.seiii.collect.model.po.recommend.RecommendRuleFactor;
import com.seiii.collect.model.po.task.Task;
import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.admin.AdminService;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    TaskMapper taskMapper;
    @Resource
    TaskUserMapper taskUserMapper;
    @Resource
    TaskTagMapper taskTagMapper;
    @Resource
    RecommendRuleMapper recommendRuleMapper;
    @Resource
    RecommendRuleFactorMapper recommendRuleFactorMapper;

    @Override
    public ResponseVO<List<TaskViewVO>> getAllTasks(Integer pageId) {
        int start = (pageId - 1) * Constant.PAGE_SIZE;
        List<TaskView> taskViews = taskMapper.selectAllByPageId(start, Constant.PAGE_SIZE);
        List<TaskViewVO> res = new LinkedList<>();
        for (TaskView taskView : taskViews) {
            List<Integer> users = taskUserMapper.selectByTaskId(taskView.getId());
            List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
            res.add(new TaskViewVO(taskView, users.size(), tags));
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", res);
    }

    @Override
    public ResponseVO<TaskVO> viewTaskDetails(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if (task == null || task.getId() == null) {
            return new ResponseVO<>(Constant.REQUEST_FAIL, "任务查找失败！");
        }
        List<Integer> users = taskUserMapper.selectByTaskId(taskId);
        List<Integer> tags = taskTagMapper.selectByTaskId(taskId);
        int state=task.getRecruitstop()?Constant.TASK_RECRUIT_STOP:Constant.TASK_UNCLAIMED;
        TaskVO taskVO = new TaskVO(task, state, users.size(), tags);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", taskVO);
    }

    @Override
    public ResponseVO<List<RecommendRuleVO>> getRecommendRules() {
        List<RecommendRule> recommendRules = recommendRuleMapper.selectAll();
        List<RecommendRuleVO> recommendRuleVOs = new ArrayList<>();
        int usingIndex = 0;
        int index = 0;
        for (RecommendRule recommendRule : recommendRules) {
            List<RecommendRuleFactor> recommendRuleFactors = recommendRuleFactorMapper.selectByRuleId(recommendRule.getId());
            List<RecommendRuleFactorVO> recommendRuleFactorVOs = new ArrayList<>();
            for (RecommendRuleFactor recommendRuleFactor : recommendRuleFactors) {
                recommendRuleFactorVOs.add(new RecommendRuleFactorVO(recommendRuleFactor));
            }
            RecommendRuleVO recommendRuleVO = new RecommendRuleVO(recommendRule, recommendRuleFactorVOs);
            recommendRuleVOs.add(recommendRuleVO);
            if (recommendRuleVO.isUsing()) {
                usingIndex = index;
            }
            index++;
        }
        Collections.swap(recommendRuleVOs, 0, usingIndex);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", recommendRuleVOs);
    }

    @Override
    public ResponseVO<List<RecommendRuleFactorVO>> getRecommendRuleFactors() {
        List<String> factorNames = Constant.RECOMMEND_FACTORS;
        List<RecommendRuleFactorVO> recommendRuleFactorVOs = new ArrayList<>();
        for (String factorName : factorNames) {
            RecommendRuleFactorVO recommendRuleFactorVO = new RecommendRuleFactorVO(factorName, 0.0);
            recommendRuleFactorVOs.add(recommendRuleFactorVO);
        }
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "请求成功", recommendRuleFactorVOs);
    }

    @Override
    public ResponseVO<Object> deleteRecommendRule(Integer ruleId) {
        recommendRuleFactorMapper.deleteByRuleId(ruleId);
        recommendRuleMapper.deleteByPrimaryKey(ruleId);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "删除成功");
    }

    @Override
    public ResponseVO<List<RecommendRuleVO>> addRecommendRule(RecommendRuleDTO recommendRuleDTO) {
        String name = recommendRuleDTO.getName();
        if (StringUtils.hasText(name)) {
            if (recommendRuleMapper.selectByName(name) == null) {
                RecommendRule recommendRule = new RecommendRule(recommendRuleDTO);
                recommendRuleMapper.insert(recommendRule);
                Integer ruleId = recommendRule.getId();
                List<RecommendRuleFactorVO> factors = recommendRuleDTO.getFactors();
                for (RecommendRuleFactorVO factor : factors) {
                    RecommendRuleFactor recommendRuleFactor = new RecommendRuleFactor(factor, ruleId);
                    recommendRuleFactorMapper.insert(recommendRuleFactor);
                }
                List<RecommendRuleVO> recommendRuleVOs = this.getRecommendRules().data;
                int addIndex = 0;
                for (RecommendRuleVO recommendRuleVO : recommendRuleVOs) {
                    if (recommendRuleVO.getId().equals(ruleId)) {
                        break;
                    }
                    addIndex++;
                }
                Collections.swap(recommendRuleVOs, 1, addIndex);
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "添加成功！", recommendRuleVOs);
            } else {
                List<RecommendRuleVO> recommendRuleVOs = this.getRecommendRules().data;
                return new ResponseVO<>(Constant.REQUEST_FAIL, "规则已存在！", recommendRuleVOs);
            }
        } else {
            List<RecommendRuleVO> recommendRuleVOs = this.getRecommendRules().data;
            return new ResponseVO<>(Constant.REQUEST_FAIL, "规则名不能为空！", recommendRuleVOs);
        }
    }

    @Override
    public ResponseVO<Object> selectRecommendRule(Integer ruleId) {
        recommendRuleMapper.updateAllOff();
        recommendRuleMapper.updateOneOn(ruleId);
        return new ResponseVO<>(Constant.REQUEST_SUCCESS, "选择成功！");
    }


}
