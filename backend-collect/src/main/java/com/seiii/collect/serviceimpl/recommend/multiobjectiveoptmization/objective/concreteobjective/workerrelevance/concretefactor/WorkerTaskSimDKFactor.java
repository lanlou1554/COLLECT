package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.Util;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.RelevanceDefaultWeight;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WorkerTaskSimDKFactor extends AbstractSimFactor {
    Util util;

    public WorkerTaskSimDKFactor(Util util){
        this.util = util;
        this.weight = RelevanceDefaultWeight.WORKER_TASK_DK_DEFAULT_WEIGHT;
    }

    // worker的DK与task的DK相似度
    @Override
    public Double calSingleWorkerFactorValues(Integer workerId, Integer taskId) {
        ResponseVO<List<String>> response1 = util.getDomainKnowledge(true,workerId);
        ResponseVO<List<String>> response2 = util.getDomainKnowledge(false,taskId);
        if (response1.code == Constant.REQUEST_FAIL || response2.code == Constant.REQUEST_FAIL){
            return 0.0;
        }
        return util.cosineSimilarity(response1.data,response2.data);

    }
}
