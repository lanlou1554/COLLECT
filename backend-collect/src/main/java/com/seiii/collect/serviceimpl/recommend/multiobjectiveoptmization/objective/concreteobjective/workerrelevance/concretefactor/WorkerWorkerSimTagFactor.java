package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.RelevanceDefaultWeight;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.UserSimilarityBasedCollaborativeConcreteFactor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;

@Component
public class WorkerWorkerSimTagFactor extends AbstractSimFactor {
    UserSimilarityBasedCollaborativeConcreteFactor userSimilarityBasedCollaborativeConcreteFactor;
    TaskMapper taskMapper;

    public WorkerWorkerSimTagFactor(UserSimilarityBasedCollaborativeConcreteFactor userSimilarityBasedCollaborativeConcreteFactor,TaskMapper taskMapper){
        this.userSimilarityBasedCollaborativeConcreteFactor = userSimilarityBasedCollaborativeConcreteFactor;
        this.taskMapper = taskMapper;
        this.weight = RelevanceDefaultWeight.WORKER_WORKER_TAG_DEFAULT_WEIGHT;
    }

    @Override
    public Double calSingleWorkerFactorValues(Integer workerId, Integer taskId) {
        TaskViewVO taskViewVO = new TaskViewVO(taskMapper.selectByIds(Arrays.asList(taskId)).get(0),new LinkedList<>());
        return userSimilarityBasedCollaborativeConcreteFactor.calculateRawFactorValues(workerId,Arrays.asList(taskViewVO)).get(0);
    }
}
