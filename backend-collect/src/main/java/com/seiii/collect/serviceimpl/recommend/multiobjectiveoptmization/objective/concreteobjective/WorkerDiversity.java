package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.model.po.user.worker.WorkerContext;
import com.seiii.collect.mapper.user.worker.WorkerContextMapper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;


import java.util.HashSet;
import java.util.List;

public class WorkerDiversity extends AbstractUserEvaluateObjective {
    //不要使用spring自动注入mapper，请将mapper放在从构造方法传入
    Util util;
    WorkerContextMapper workerContextMapper;
    public WorkerDiversity(Util util,WorkerContextMapper workerContextMapper){
        this.util=util;
        this.workerContextMapper=workerContextMapper;
    }

    @Override
    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor) {
        this.weight = factor.getWorkerDiversity();
        return this;
    }

    @Override
    protected double calculateValue(List<Integer> userIds) {
        //todo 最大化工人的多样性
        double divScore=0.0;
        double domainTotal=0.0;
        double contextTotal=((double)userIds.size())*4;
        HashSet<String> domainList=new HashSet<String>();
        HashSet<String> contextList=new HashSet<String>();
        for(Integer userId:userIds){
            List<String> domain=util.getDomainKnowledge(true,userId).getData();
            for(int i=0;i<domain.size();i++){
                domainList.add(domain.get(i));
            }
            domainTotal+=domain.size();
            WorkerContext workerContext=workerContextMapper.selectByPrimaryKey(userId);
            if(workerContext==null){
                continue;
            }
            contextList.add(workerContext.getDevicetype());
            contextList.add(workerContext.getRamsize());
            contextList.add(workerContext.getOsinfo());
            contextList.add(workerContext.getNetenv());
        }
        //注意对divScore做了归一化
        divScore=((double)domainList.size())/domainTotal+((double)contextList.size())/contextTotal;

        return divScore;
    }

    @Override
    protected ObjectiveType getObjectType() {
        return ObjectiveType.MAXIMIZE;
    }

    @Override
    protected void fillActual(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setDiversityactual(resultValue);
    }

    @Override
    public void fillExpected(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setDiversityexpected(resultValue);
    }

}
