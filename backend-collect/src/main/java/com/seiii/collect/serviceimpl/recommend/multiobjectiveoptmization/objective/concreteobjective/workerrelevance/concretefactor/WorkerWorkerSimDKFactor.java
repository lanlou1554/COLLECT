package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.Util;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.RelevanceDefaultWeight;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WorkerWorkerSimDKFactor extends AbstractSimFactor {
    Util util;
    TaskUserMapper taskUserMapper;

    public WorkerWorkerSimDKFactor(Util util,TaskUserMapper taskUserMapper){
        this.util = util;
        this.taskUserMapper = taskUserMapper;
        this.weight = RelevanceDefaultWeight.WORKER_WORKER_DK_DEFAULT_WEIGHT;
    }

    @Override
    public Double calSingleWorkerFactorValues(Integer workerId, Integer taskId) {
        //TODO 如果服务器有问题，则直接返回0
        ResponseVO<List<String>> response = util.getDomainKnowledge(true,workerId);
        if (response.code == Constant.REQUEST_FAIL){
            return 0.0;
        }
        List<String> targetWorkerDK = response.data;
        Double total = 0.0;
        Integer count = 0;
        List<Integer> taskPickedUserIds = taskUserMapper.selectByTaskId(taskId);
        if (taskPickedUserIds.size() == 0) return 0.0;
        for (Integer historyUserId: taskPickedUserIds){
            ResponseVO<List<String>> tempResponse = util.getDomainKnowledge(true,historyUserId);
            if (tempResponse.code == Constant.REQUEST_FAIL){
                continue;
            }
            List<String> historyUserDK = tempResponse.data;
            total += util.cosineSimilarity(targetWorkerDK,historyUserDK);
            count++;
        }
        return total/count;
    }
}
