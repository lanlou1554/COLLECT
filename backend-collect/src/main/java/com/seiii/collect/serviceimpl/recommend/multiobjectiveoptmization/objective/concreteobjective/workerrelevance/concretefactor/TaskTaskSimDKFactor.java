package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.Util;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.RelevanceDefaultWeight;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;
import java.util.List;
import javax.annotation.Resource;

@Component
public class TaskTaskSimDKFactor extends AbstractSimFactor {
    Util util;
    TaskUserMapper taskUserMapper;

    public TaskTaskSimDKFactor(Util util,TaskUserMapper taskUserMapper){
        this.util = util;
        this.taskUserMapper = taskUserMapper;
        this.weight = RelevanceDefaultWeight.TASK_TASK_DK_DEFAULT_WEIGHT;
    }

    // 目标任务的DK和工人过去任务的DK，逐个算相似度，然后求平均
    @Override
    public Double calSingleWorkerFactorValues(Integer workerId, Integer taskId) {
        List<Integer> userPickedTaskIds = taskUserMapper.selectByUserId(workerId);
        if (userPickedTaskIds.size() == 0) return 0.0;
        //TODO 如果服务器有问题，则直接返回0
        ResponseVO<List<String>> response = util.getDomainKnowledge(false,taskId);
        if (response.code == Constant.REQUEST_FAIL){
            return 0.0;
        }
        List<String> targetTaskDK = response.data;
        Double total = 0.0;
        Integer count = 0;
        for (Integer historyTaskId: userPickedTaskIds){
            ResponseVO<List<String>> tempResponse = util.getDomainKnowledge(false,historyTaskId);
            if (tempResponse.code == Constant.REQUEST_FAIL){
                continue;
            }
            List<String> historyTaskDK = tempResponse.data;
            total += util.cosineSimilarity(targetTaskDK,historyTaskDK);
            count++;
        }
        if(count == 0)count++;
        return total/count;
    }
}
