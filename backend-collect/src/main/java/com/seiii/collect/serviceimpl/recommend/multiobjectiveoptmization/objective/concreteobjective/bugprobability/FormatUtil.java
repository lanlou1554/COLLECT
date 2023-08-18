package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability;

import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.ComprehensiveAbility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 用于格式化/可视化
public class FormatUtil {

    // 将浮点数四舍五入为整数
    public static int double2int(double num) {
        BigDecimal bd = new BigDecimal(num).setScale(0, RoundingMode.HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    // 将0-1的能力值格式化为0-100并保留整数
    public static int formatAbility(double abilityVal) {
        double newVal = abilityVal * 100;
        return double2int(newVal);
    }

    // 计算某个工人的每种能力，以数组返回
    // 顺序：协作、审查、创新、bug、语言、综合
    public static List<Integer> getAbility(Integer userId) {
        ComprehensiveAbility comprehensiveAbility = new ComprehensiveAbility();
        List<Integer> abilities = new ArrayList<>();
        List<Double> allAbilityValue = comprehensiveAbility.getAllAbilityValue(userId);
        for (Double val : allAbilityValue) {
            abilities.add(formatAbility(val));
        }
        return abilities;
    }

    // 计算所有工人的每种能力的平均值，以数组返回
    // 顺序：协作、审查、创新、bug、语言、综合
    public static List<Integer> getAvgAbility() {
        List<Integer> allUserIds = BugProbabilityHelper.getAllUserIds();
        List<Integer> res = new ArrayList<>(Collections.nCopies(6, 0));
        for (Integer userId : allUserIds) {
            List<Integer> ability = getAbility(userId);
            System.err.println(ability);
            for (int i = 0; i < ability.size(); i++) {
                res.set(i, res.get(i) + ability.get(i));
            }
        }
        int userNum = allUserIds.size();
        for (int i = 0; i < res.size(); i++) {
            res.set(i, double2int((double) res.get(i) / (double) userNum));
        }
        return res;
    }

}
