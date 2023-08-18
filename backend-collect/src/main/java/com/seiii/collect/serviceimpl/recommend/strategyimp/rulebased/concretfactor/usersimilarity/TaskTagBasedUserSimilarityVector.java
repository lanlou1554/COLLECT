package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.usersimilarity;

import com.seiii.collect.model.po.recommend.UserVectorComponent;
import com.seiii.collect.util.Constant;
import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskTagBasedUserSimilarityVector extends UserSimilarityVector {
    private Map<Integer, Integer> userIdxMap;
    private SimpleMatrix userMatrix;
    private SimpleMatrix similarityVector;

    public TaskTagBasedUserSimilarityVector(int targetUserId, List<UserVectorComponent> userVectorComponents) {
        super(targetUserId);
        initUserSimilarityMatrix(userVectorComponents);
        initSimilarityVector(targetUserId);
    }

    private void initUserSimilarityMatrix(List<UserVectorComponent> userVectorComponents) {
        Map<Integer, List<UserVectorComponent>> groupedVectorComponents = userVectorComponents.stream()
                .collect(Collectors.groupingBy(UserVectorComponent::getUserId));
        userIdxMap = new HashMap<>();
        double[][] matrixValues = new double[groupedVectorComponents.size()][];
        int userIdx = 0;
        for (Integer userId : groupedVectorComponents.keySet()) {
            userIdxMap.put(userId, userIdx);
            matrixValues[userIdx] = reduceToUserVector(groupedVectorComponents.get(userId));
            userIdx++;
        }
        if (matrixValues.length == 0) return;
        //矩阵的每一行代表一个用户的向量
        userMatrix = new SimpleMatrix(matrixValues);
    }

    private double[] reduceToUserVector(List<UserVectorComponent> userVectorComponents) {
        double[] vector = new double[Constant.USER_VECTOR_DIMENSION];
        for (UserVectorComponent userVectorComponent : userVectorComponents) {
            Integer tag = userVectorComponent.getTag();
            int idx = tag == null ? Constant.USER_VECTOR_DIMENSION - 1 : tag;
            vector[idx] += userVectorComponent.getScore();
        }
        SimpleMatrix userVector = new SimpleMatrix(1, vector.length, true, vector);
        double scale = userVector.normF();
        if (scale != 0) {
            userVector = userVector.divide(scale);
        }
        return userVector.getMatrix().getData();
    }

    private void initSimilarityVector(int targetUserId) {
        Integer targetUserIdx = userIdxMap.get(targetUserId);
        if (targetUserIdx == null) return;
        SimpleMatrix targetUserVector = userMatrix.extractVector(true, targetUserIdx);
        similarityVector = targetUserVector.mult(userMatrix.transpose());
    }

    @Override
    public double getSimilarityWithTargetUser(int otherUserId) {
        if (similarityVector == null) return 0f;
        Integer otherUserIdx = userIdxMap.get(otherUserId);
        if (otherUserIdx == null) return 0f;
        return similarityVector.get(otherUserIdx);
    }


}
