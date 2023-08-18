package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.usersimilarity;

import com.seiii.collect.model.po.user.JaccardUserSimilarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JaccardUserSimilarityVector extends UserSimilarityVector {
    Map<Integer, Double> similarityMap;

    public JaccardUserSimilarityVector(int targetUserId, List<JaccardUserSimilarity> similarities) {
        super(targetUserId);
        similarityMap = new HashMap<>();
        for (JaccardUserSimilarity similarity : similarities) {
            assert similarity.getUserid1() == targetUserId || similarity.getUserid2() == targetUserId;
            int otherUserId = similarity.getUserid1() == targetUserId ? similarity.getUserid2() : similarity.getUserid1();
            similarityMap.put(otherUserId, similarity.getJaccardsim());
        }
    }

    @Override
    public double getSimilarityWithTargetUser(int otherUserId) {
        Double result = similarityMap.get(otherUserId);
        if (result == null) return 0;
        return result;

    }
}
