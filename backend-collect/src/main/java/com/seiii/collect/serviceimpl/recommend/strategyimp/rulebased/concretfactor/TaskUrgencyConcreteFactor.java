package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor;

import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.rulefactor.RuleFactor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("任务紧迫度")
@Scope("prototype")
public class TaskUrgencyConcreteFactor extends RuleFactor {

    /**
     * 第i个任务没有归一化的紧迫程度(记为Ui)：(测试还需要招募的人数 / 测试剩余时间)
     * 所有任务中最大的未归一化紧迫程度Umax
     *
     * @param targetUserId   目标用户
     * @param candidateTasks 候选任务列表
     * @return 优先数列表，优先数值为candidateTasks中任务的紧迫程度，既Ui/Umax
     */
    @Override
    public List<Double> calculateRawFactorValues(Integer targetUserId, List<TaskViewVO> candidateTasks) {
        List<Double> taskUrgencies = new ArrayList<>();
        double taskMaxUrgency = 0.0;
        for (TaskViewVO taskVO : candidateTasks) {
            int numToEmploy = taskVO.getWorkerNum() - taskVO.getPickedWorkerNum();
            double dayToEnd = ((double) (taskVO.getEndTime().getTime() - taskVO.getStartTime().getTime())) / (24 * 60 * 60 * 1000);
            double taskUrgency = numToEmploy / dayToEnd;
            taskMaxUrgency = Math.max(taskMaxUrgency, taskUrgency);
            taskUrgencies.add(taskUrgency);
        }
        if (taskMaxUrgency == 0.0)
            taskMaxUrgency = 1.0;
        for (int i = 0; i < taskUrgencies.size(); i++) {
            taskUrgencies.set(i, taskUrgencies.get(i) / taskMaxUrgency);
        }
        return taskUrgencies;
    }
}
