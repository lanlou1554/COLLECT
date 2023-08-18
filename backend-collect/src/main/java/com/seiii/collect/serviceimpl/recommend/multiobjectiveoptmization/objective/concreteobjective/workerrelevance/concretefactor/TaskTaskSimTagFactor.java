package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.RelevanceDefaultWeight;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.TaskSimilarityConcreteFactor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskTaskSimTagFactor extends AbstractSimFactor {
    TaskSimilarityConcreteFactor taskSimilarityConcreteFactor;
    TaskMapper taskMapper;
    TaskTagMapper taskTagMapper;

    public TaskTaskSimTagFactor(TaskSimilarityConcreteFactor taskSimilarityConcreteFactor,TaskMapper taskMapper,TaskTagMapper taskTagMapper){
        this.taskSimilarityConcreteFactor = taskSimilarityConcreteFactor;
        this.taskMapper = taskMapper;
        this.taskTagMapper = taskTagMapper;
        this.weight = RelevanceDefaultWeight.TASK_TASK_TAG_DEFAULT_WEIGHT;
    }

    @Override
    public Double calSingleWorkerFactorValues(Integer workerId, Integer taskId) {
        List<Integer> taskTagGroup = taskTagMapper.selectByTaskId(taskId);
        TaskViewVO taskViewVO = new TaskViewVO(taskMapper.selectByIds(Arrays.asList(taskId)).get(0),taskTagGroup);
        return taskSimilarityConcreteFactor.calculateRawFactorValues(workerId,Arrays.asList(taskViewVO)).get(0);
    }
}
