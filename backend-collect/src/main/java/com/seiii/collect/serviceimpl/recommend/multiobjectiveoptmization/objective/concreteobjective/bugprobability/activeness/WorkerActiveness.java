package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness;

import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WorkerActiveness {

    // 最后一次提交时间
    private Date lastSubmitTime;
    // 近三天提交的报告数
    private Integer subReportNumThreeDay = 0;
    // 近两周提交的报告数
    private Integer subReportNumTwoWeek = 0;
    // 近一个月提交的报告数
    private Integer subReportNumOneMon = 0;
    // 近半年提交的报告数
    private Integer subReportNumHalfYear = 0;

    // 后四者权重
    private final Double[] weight = {0.4, 0.3, 0.2, 0.1};

    public Double getActivenessValue(Integer userId) {
        setAllFactors(userId);
        return f(subReportNumThreeDay) * weight[0] + f(subReportNumTwoWeek) * weight[1]
                + f(subReportNumOneMon) * weight[2] + f(subReportNumHalfYear) * weight[3];
    }

    // 映射函数 f(x) = 1 - 1/(x+1)
    public Double f(Integer num) {
        return 1.0 - 1.0 / (num + 1);
    }

    public void setAllFactors(Integer userId) {
        setLastSubmitTime(userId);
        setSubReportNumThreeDay(userId);
        setSubReportNumTwoWeek(userId);
        setSubReportNumOneMon(userId);
        setSubReportNumHalfYear(userId);
    }

    private void setLastSubmitTime(Integer userId) {
        this.lastSubmitTime = BugProbabilityHelper.getLastSubmitTime(userId);
    }

    private void setSubReportNumThreeDay(Integer userId) {
        this.subReportNumThreeDay = BugProbabilityHelper.getSubmitReportNumByTime(userId, ScopeType.LAST_THREE_DAYS);
    }

    private void setSubReportNumTwoWeek(Integer userId) {
        this.subReportNumTwoWeek = BugProbabilityHelper.getSubmitReportNumByTime(userId, ScopeType.LAST_TWO_WEEKS);
    }

    private void setSubReportNumOneMon(Integer userId) {
        this.subReportNumOneMon = BugProbabilityHelper.getSubmitReportNumByTime(userId, ScopeType.LAST_ONE_MONTH);
    }

    private void setSubReportNumHalfYear(Integer userId) {
        this.subReportNumHalfYear = BugProbabilityHelper.getSubmitReportNumByTime(userId, ScopeType.LAST_HALF_YEAR);
    }

    public Date getLastSubmitTime() {
        return lastSubmitTime;
    }

    public Integer getSubReportNumThreeDay() {
        return subReportNumThreeDay;
    }

    public Integer getSubReportNumTwoWeek() {
        return subReportNumTwoWeek;
    }

    public Integer getSubReportNumOneMon() {
        return subReportNumOneMon;
    }

    public Integer getSubReportNumHalfYear() {
        return subReportNumHalfYear;
    }

}
