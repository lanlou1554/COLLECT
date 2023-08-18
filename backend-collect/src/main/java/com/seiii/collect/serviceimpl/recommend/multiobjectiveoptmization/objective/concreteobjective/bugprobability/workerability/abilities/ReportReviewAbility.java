package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities;

import com.seiii.collect.model.po.flaw.Score;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.WorkerAbilityInterface;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// 报告审查能力
public class ReportReviewAbility implements WorkerAbilityInterface {

    // 评分差距
    private Double ratingGap = 0.0;
    // 威尔逊得分（根据点赞、点踩数计算）
    private Double wilsonScore = 0.0;

    // 权重
    private final Double[] weight = {0.4, 0.6};

    @Override
    public Double calAbilityValue(Integer userId) {
        setAllFactors(userId);
        return ratingGap * weight[0] + wilsonScore * weight[1];
    }

    public void setAllFactors(Integer userId) {
        setRatingGap(userId);
        setWilsonScore(userId);
    }

    /*
     * 找到某众包工人对于其他缺陷的所有评分
     * 对于每条评分数据，计算 diff = abs( 该工人对他的评分 - 该缺陷的均分)
     * 对于每条评分数据，计算 gapRate = diff / 5（评分范围为0-5）
     * 对所有 gapRate 求均值
     * 1 - 上述值
     * 算出的值在 0 - 1 之间，值越大表示评分差距越小，即审查能力越高
     * */
    private void setRatingGap(Integer userId) {
        List<Score> allScoreList = BugProbabilityHelper.getAllScoreList(userId);
        int scoreNum = allScoreList.size();
        if (scoreNum == 0) {
            this.ratingGap = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            double gateRateSum = 0.0;
            for (Score score : allScoreList) {
                Integer flawId = score.getFlawid();
                Double curScore = score.getSocre().doubleValue();
                Double avgScore = BugProbabilityHelper.getScoreFromFlawId(flawId);
                double diff = Math.abs(curScore - avgScore);
                double gapRate = diff / 5.0;
                gateRateSum += gapRate;
            }
            this.ratingGap = 1 - gateRateSum / (double) scoreNum;
        }
    }

    /*
     * 采用威尔逊算法，计算威尔逊得分
     * u 表示点赞数，v 表示点踩数，n 表示评价总数，p 表示好评率，z 表示正态分布的分位数（参数）。
     * 由于我们数据量普遍比较小，所以 z 取 0.5，约等于 70% 的置信度。
     * */
    private void setWilsonScore(Integer userId) {
        List<List<Integer>> allEvaInfo = BugProbabilityHelper.getAllEvaInfo(userId);
        int evaNum = allEvaInfo.size();
        if (evaNum == 0) {
            this.wilsonScore = Constant.ABILITY_FACTOR_DEFAULT_VALUE;
        } else {
            double wilsonSum = 0.0;
//            int hasValCnt = 0;
            for (List<Integer> eva : allEvaInfo) {
                // 计算单条评价的威尔逊得分
                double u = eva.get(0).doubleValue(); // 点赞数
                double v = eva.get(1).doubleValue(); // 点踩数
                double n = u + v; // 总评价数
//                if (n == 0) // 暂时跳过点赞点踩数为0的评价
//                    continue;
//                hasValCnt++;
                double p = u / n; // 好评率
                // 威尔逊得分
                double s = (p + (Math.pow(Constant.QUANTILE_OF_NORMAL_DISTRIBUTION, 2) / (2. * n))
                        - ((Constant.QUANTILE_OF_NORMAL_DISTRIBUTION / (2. * n))
                        * Math.sqrt(4. * n * (1. - p) * p + Math.pow(Constant.QUANTILE_OF_NORMAL_DISTRIBUTION, 2))))
                        / (1. + Math.pow(Constant.QUANTILE_OF_NORMAL_DISTRIBUTION, 2) / n);
                wilsonSum += s;
            }
            this.wilsonScore = wilsonSum / (double) evaNum;
        }
    }

    public Double getRatingGap() {
        return ratingGap;
    }

    public Double getWilsonScore() {
        return wilsonScore;
    }
}
