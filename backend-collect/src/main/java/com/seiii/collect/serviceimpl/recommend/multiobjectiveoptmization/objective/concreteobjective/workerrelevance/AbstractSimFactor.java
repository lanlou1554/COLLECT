package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance;

import lombok.Data;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Data
public abstract class AbstractSimFactor {
    protected Double weight;

    //TODO 默认此工人没有选过此task
    @Cacheable(cacheNames = "calSingleWorkerFactorValues", key = "#root.targetClass.toString() + '[' + #p0 + ',' + #p1 + ']'")
    public abstract Double calSingleWorkerFactorValues(Integer workerId, Integer taskId);

    // TODO 暂时全部工人的相似度衡量先取平均
/*    public Double calRawFactorValues(List<Integer> workerIds,Integer taskId){
        Double total = 0.0;
        for(Integer id:workerIds){
            total += calSingleWorkerFactorValues(id,taskId);
        }
        return total/workerIds.size();
    }*/
}
