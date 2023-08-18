package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities;

import com.seiii.collect.model.po.flaw.Flaw;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.WorkerAbilityInterface;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// 报告协作能力
public class CollaborationAbility implements WorkerAbilityInterface {

    // fork其他缺陷的概率
    private Double forkingFlawRate = 0.0;
    // 评分差距（当前评分和fork的节点的评分的差距）
    private Double forkRatingGap = 0.0;

    // 权重
    private final Double[] weight = {0.35, 0.65};

    @Override
    public Double calAbilityValue(Integer userId) {
        setAllFactors(userId);
        return forkingFlawRate * weight[0] + forkRatingGap * weight[1];
    }

    public void setAllFactors(Integer userId) {
        setForkingFlawRate(userId);
        setForkRatingGap(userId);
    }

    /*
     * 某众包工人提交的缺陷中fork别人的缺陷数量 / 该众包工人提交的所有缺陷数量
     * 算出的值范围为 0 - 1，值越大表示fork缺陷的概率越大，即协作能力越高
     * */
    private void setForkingFlawRate(Integer userId) {
        Integer forkingFlawNum = BugProbabilityHelper.getForkingFlawNum(userId);
        Integer submitFlawNum = BugProbabilityHelper.getSubmitFlawNum(userId);
        if (submitFlawNum == 0) {
            this.forkingFlawRate = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            this.forkingFlawRate = forkingFlawNum.doubleValue() / submitFlawNum.doubleValue();
        }
    }

    /*
     * 找到某众包工人fork其他缺陷的所有节点
     * 针对某个缺陷节点，当前节点的评分为 curScore，fork的节点的评分为 preScore（如果两者任一个没有评分，则放弃对该节点的评估）
     * 计算 diff = curScore - preScore，diff 范围为 -5 到 5
     * 将上述 diff 代入 $f(x) = 0.008x^2+0.1x+0.3$ 中的x，计算 gapRate
     * 对所有的 gapRate 求均值
     * 算出的值范围为 0 - 1，值越高表示比fork节点的效果越好，即协作能力越高
     * */
    private void setForkRatingGap(Integer userId) {
        List<Flaw> forkingFlawList = BugProbabilityHelper.getForkingFlawList(userId);
        int validFlawCnt = 0;
        double gapRateSum = 0.0;
        for (Flaw flaw : forkingFlawList) {
            String path = flaw.getPath();
            Double curScore = flaw.getScore();
            if (!curScore.equals(-1.0)) {
                String[] strings = path.split(",");
                Integer fatherId = Integer.parseInt(strings[strings.length - 2]);
                Double preScore = BugProbabilityHelper.getScoreFromFlawId(fatherId);
                if (!preScore.equals(-1.0)) {
                    double diff = curScore - preScore;
                    double gapRate = 0.008 * Math.pow(diff, 2.0) + 0.1 * diff + 0.3;
                    gapRateSum += gapRate;
                    validFlawCnt++;
                }
            }
        }
        if (validFlawCnt == 0) {
            this.forkRatingGap = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            this.forkRatingGap = gapRateSum / (double) validFlawCnt;
        }
    }

    public Double getForkingFlawRate() {
        return forkingFlawRate;
    }

    public Double getForkRatingGap() {
        return forkRatingGap;
    }

}
