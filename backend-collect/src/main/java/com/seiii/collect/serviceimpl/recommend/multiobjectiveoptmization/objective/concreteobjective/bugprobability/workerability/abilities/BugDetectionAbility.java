package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities;

import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.WorkerAbilityInterface;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// 寻找bug的能力
public class BugDetectionAbility implements WorkerAbilityInterface {

    // 参与的任务数
    private Double partiTaskNum = 0.0;
    // 提交的报告数
    private Double subReportNum = 0.0;
    // 提交的缺陷数
    private Double subFlawNum = 0.0;
    // 发现缺陷占比
    private Double detectFlawRate = 0.0;

    // 权重
    private final Double[] weight = {0.1, 0.1, 0.4, 0.4};

    @Override
    public Double calAbilityValue(Integer userId) {
        setAllFactors(userId);
        return partiTaskNum * weight[0] + subReportNum * weight[1]
                + subFlawNum * weight[2] + detectFlawRate * weight[3];
    }

    public void setAllFactors(Integer userId) {
        setPartiTaskNum(userId);
        setSubReportNum(userId);
        setSubFlawNum(userId);
        setDetectFlawRate(userId);
    }

    // 映射函数，暂定 1-1/(x+1)
    private double f(double x) {
        return 1.0 - 1.0 / (x + 1);
    }

    /*
     * 设参与的任务数为x，代入 1 - 1/x+1 求值
     * 算出的值在0-1之间，值越大表示参与的任务数越多，即找bug能力越强
     * */
    private void setPartiTaskNum(Integer userId) {
        Integer participateTaskNum = BugProbabilityHelper.getParticipateTaskNum(userId);
        this.partiTaskNum = f(participateTaskNum.doubleValue());
    }

    /*
     * 设提交的报告数为x，代入 1 - 1/x+1 求值
     * 算出的值在0-1之间，值越大表示提交的报告数越多，即找bug能力越强
     * */
    private void setSubReportNum(Integer userId) {
        Integer submitReportNum = BugProbabilityHelper.getSubmitReportNum(userId);
        this.subReportNum = f(submitReportNum.doubleValue());
    }

    /*
     * 设提交的缺陷数为x，代入 1 - 1/x+1 求值
     * 算出的值在0-1之间，值越大表示提交的缺陷数越多，即找bug能力越强
     * */
    private void setSubFlawNum(Integer userId) {
        Integer submitFlawNum = BugProbabilityHelper.getSubmitFlawNum(userId);
        this.subFlawNum = f(submitFlawNum.doubleValue());
    }

    /*
     * 对于某个任务，rate = 众包工人发现的flaw数量 / 按照缺陷图得到的该任务下的总flaw数
     * 对于众包工人参与的所有任务（需要是已经提交完善缺陷的任务），求出 rate 均值
     * 算出的值在 0 - 1 之间，值越大表示发现的缺陷占比越高，即寻找bug能力越强
     * */
    private void setDetectFlawRate(Integer userId) {
        List<Integer> submitTaskIdList = BugProbabilityHelper.getSubmitTaskIdList(userId);
        if (submitTaskIdList.size() == 0) {
            this.detectFlawRate = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            double rateSum = 0.0;
            for (Integer taskId : submitTaskIdList) {
                Integer subNum = BugProbabilityHelper.getSubmitFlawNumFromTask(userId, taskId);
                Integer allNum = BugProbabilityHelper.getAllFlawNumFromTask(taskId);
                rateSum += (subNum.doubleValue() / allNum.doubleValue());
            }
            this.detectFlawRate = rateSum / (double) submitTaskIdList.size();
        }
    }

    public Double getPartiTaskNum() {
        return partiTaskNum;
    }

    public Double getSubReportNum() {
        return subReportNum;
    }

    public Double getSubFlawNum() {
        return subFlawNum;
    }

    public Double getDetectFlawRate() {
        return detectFlawRate;
    }

}
