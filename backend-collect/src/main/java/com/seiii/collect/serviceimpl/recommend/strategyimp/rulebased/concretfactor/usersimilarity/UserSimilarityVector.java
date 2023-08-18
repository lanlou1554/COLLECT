package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.usersimilarity;

import java.util.HashMap;
import java.util.Map;

public abstract class UserSimilarityVector {
    protected final int targetUserId;

    /**
     *
     * @param targetUserId 目标用户id，这个向量代表目标用户与其他所有用户的相似度
     */
    public UserSimilarityVector(int targetUserId){
        this.targetUserId = targetUserId;
    }

    /**
     *
     * @param otherUserId 与目标用户进行相似度比较的用户
     * @return targetUserId 和 otherUserId 所代表用户的相似度
     */
    public abstract double getSimilarityWithTargetUser(int otherUserId);

    public UserSimilarityVector plus(UserSimilarityVector otherVector) {
        if ((otherVector.targetUserId != this.targetUserId)) {
            throw new RuntimeException("不可以将两个对于不同目标用户的相似度向量相加");
        }

        return new UserSimilarityVector(this.targetUserId) {
            Map<Integer, Double> similarities = new HashMap<>();

            @Override
            public double getSimilarityWithTargetUser(int otherUserId) {
                Double result = similarities.get(otherUserId);
                if (result == null) {
                    result = UserSimilarityVector.this.getSimilarityWithTargetUser(otherUserId)
                            + otherVector.getSimilarityWithTargetUser(otherUserId);
                    similarities.put(otherUserId, result);
                }
                return result;
            }
        };
    }

    public UserSimilarityVector times(double factor) {
        return new UserSimilarityVector(this.targetUserId) {
            Map<Integer, Double> similarities = new HashMap<>();

            @Override
            public double getSimilarityWithTargetUser(int otherUserId) {
                Double result = similarities.get(otherUserId);
                if (result == null) {
                    result = UserSimilarityVector.this.getSimilarityWithTargetUser(otherUserId);
                }
                return result;
            }
        };
    }
}
