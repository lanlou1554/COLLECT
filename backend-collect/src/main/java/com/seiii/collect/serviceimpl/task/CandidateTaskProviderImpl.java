package com.seiii.collect.serviceimpl.task;

import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.mapper.report.ReportMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.recommend.RecommendService;
import com.seiii.collect.serviceimpl.recommend.CandidateTasksProvider;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class CandidateTaskProviderImpl implements CandidateTasksProvider {
    @Resource
    TaskMapper taskMapper;
    @Resource
    TaskUserMapper taskUserMapper;
    @Resource
    TaskTagMapper taskTagMapper;
    @Override
    public List<TaskViewVO> provideCandidateTask(Integer userId) {
        List<TaskView> unfinishedTask = taskMapper.selectBeforeEndTimeAndAfterStartTime(new Date(), 0, Integer.MAX_VALUE);
        List<TaskViewVO> taskViewVOs = new ArrayList<>();
        for (TaskView taskView : unfinishedTask) {
            List<Integer> userIds = taskUserMapper.selectByTaskId(taskView.getId());
            if (userIds.size() < taskView.getWorkernum()&&!userIds.contains(userId)) { // 判断是否已招满
                List<Integer> users = taskUserMapper.selectByTaskId(taskView.getId());
                List<Integer> tags = taskTagMapper.selectByTaskId(taskView.getId());
                taskViewVOs.add(new TaskViewVO(taskView, users.size(), tags));
            }
        }
        return taskViewVOs;
    }
    @Override
    public List<TaskView> provideUnfinishedTask(){
        return taskMapper.selectBeforeEndTimeAndAfterStartTime(new Date(), 0, Integer.MAX_VALUE);
    }
}
