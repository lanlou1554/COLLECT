package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness;

public enum ScopeType {

    LAST_THREE_DAYS(3),
    LAST_TWO_WEEKS(14),
    LAST_ONE_MONTH(30),
    LAST_HALF_YEAR(366 / 2);

    private final int days;

    private ScopeType(int value) {
        days = value;
    }

    public int getDays() {
        return days;
    }

}
