package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities;

import com.seiii.collect.model.po.flaw.Flaw;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.WorkerAbilityInterface;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// 创新能力
public class CreativityAbility implements WorkerAbilityInterface {

    // flaw节点作为根节点的概率
    private Double rootProbability = 0.0;
    // 缺陷被fork的概率
    private Double forkedFlawNum = 0.0;
    // 提交的报告的重复度
    private Double duplicateDegree = 0.0;
    // 缺陷相似度
    private Double flawSimilarity = 0.0;

    // 权重
    private final Double[] weight = {0.2, 0.1, 0.35, 0.25};

    @Override
    public Double calAbilityValue(Integer userId) {
        setAllFactors(userId);
        return rootProbability * weight[0] + forkedFlawNum * weight[1]
                + duplicateDegree * weight[2] + flawSimilarity * weight[3];
    }

    public void setAllFactors(Integer userId) {
        setRootProbability(userId);
        setForkedFlawNum(userId);
        setDuplicateDegree(userId);
        setFlawSimilarity(userId);
    }

    /*
     * 对于某众包工人提交的所有缺陷，作为根节点的缺陷数量 / 提交的所有缺陷
     * 算出的值在0-1之间，值越大表示概率越大，即创新能力越高
     * */
    private void setRootProbability(Integer userId) {
        Integer rootFlawNum = BugProbabilityHelper.getRootFlawNum(userId);
        Integer submitFlawNum = BugProbabilityHelper.getSubmitFlawNum(userId);
        if (submitFlawNum == 0) {
            this.rootProbability = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            this.rootProbability = rootFlawNum.doubleValue() / submitFlawNum.doubleValue();
        }
    }

    /*
     * 计算某众包工人提交的所有缺陷，缺陷直接或间接被fork的次数，求和
     * 为了简单归一化，将上述结果除以所有flaw的数量
     * 算出的值在0-1之间，值越大表示被fork的次数越多，即创新能力越高
     * */
    private void setForkedFlawNum(Integer userId) {
        Integer forkedFlawNum = BugProbabilityHelper.getForkedFlawNum(userId);
        Integer allFlawNum = BugProbabilityHelper.getAllFlawNum();
        if (allFlawNum == 0) {
            this.forkedFlawNum = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            this.forkedFlawNum = forkedFlawNum.doubleValue() / allFlawNum.doubleValue();
        }
    }

    /*
     * 对于某个缺陷，duplicates为该缺陷所属的缺陷树的节点个数（重复个数）
     * 定义该缺陷的 duplicate index = 1 / duplicates
     * 对于某众包工人，计算它提交的所有缺陷的duplicate index之和，并除以他提交的所有缺陷的个数
     * 算出的值在0-1之间，值越大表示重复程度越小，即创新能力越高
     * */
    private void setDuplicateDegree(Integer userId) {
        Integer submitFlawNum = BugProbabilityHelper.getSubmitFlawNum(userId);
        if (submitFlawNum == 0) {
            this.duplicateDegree = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            List<Flaw> submitFlawList = BugProbabilityHelper.getSubmitFlawList(userId);
            double dupSum = 0.0;
            for (Flaw flaw : submitFlawList) {
                Integer flawId = flaw.getId();
                Integer treeNodesNum = BugProbabilityHelper.getAllFlawNumFromTree(flawId);
                int duplicates = treeNodesNum - 1;
                double duplicateIdx;
                if (duplicates == 0) {
                    duplicateIdx = 1.0;
                } else {
                    duplicateIdx = 1.0 / (double) duplicates;
                }
                dupSum += duplicateIdx;
            }
            this.duplicateDegree = dupSum / submitFlawNum.doubleValue();
        }
    }

    /*
     * 对于某个缺陷，计算它和所属任务下的所有他人提交的缺陷的相似度均值
     * 对于某众包工人，将其所有提交的缺陷计算上述均值，求出最终均值
     * 1 - 上述值
     * 算出的值在0-1之间，值越大表示相似度越低，即创新能力越高
     * */
    private void setFlawSimilarity(Integer userId) {
        Integer submitFlawNum = BugProbabilityHelper.getSubmitFlawNum(userId);
        if (submitFlawNum == 0) {
            this.flawSimilarity = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            List<Flaw> submitFlawList = BugProbabilityHelper.getSubmitFlawList(userId);
            double allFlawSimSum = 0.0;
            for (Flaw flaw : submitFlawList) {
                Integer flawId = flaw.getId();
                List<Double> similarityList = BugProbabilityHelper.getSimilarityList(flawId);
                int simSize = similarityList.size();
                if (simSize == 0)
                    continue;
                double oneFlawSimSum = 0.0;
                for (Double sim : similarityList) {
                    oneFlawSimSum += sim;
                }
                allFlawSimSum += (oneFlawSimSum / (double) simSize);
            }
            this.flawSimilarity = 1 - allFlawSimSum / submitFlawNum.doubleValue();
        }
    }

    public Double getRootProbability() {
        return rootProbability;
    }

    public Double getForkedFlawNum() {
        return forkedFlawNum;
    }

    public Double getDuplicateDegree() {
        return duplicateDegree;
    }

    public Double getFlawSimilarity() {
        return flawSimilarity;
    }
}
