package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MultiObjectiveSelectionTest {

    @Test
    void selectFromCandidateUsersAboutTask() {
        List<AbstractUserEvaluateObjective> objectives  = Stream.of(1, 2, 3, 4).map(id -> new AbstractUserEvaluateObjective() {
            @Override
            protected double calculateValue(List<Integer> userIds) {
                return userIds.contains(id) ? 1 : 0;
            }

            @Override
            protected ObjectiveType getObjectType() {
                return ObjectiveType.MAXIMIZE;
            }


        }).collect(Collectors.toList());

        MultiObjectiveSelection selection = new MultiObjectiveSelection(100, 200){
            @Override
            protected List<AbstractUserEvaluateObjective> generateObjectives(Integer taskId, List<Integer> candidateUserIds,
                                                                             MultiObjectiveRecommendFactor factor) {
                return objectives;
            }
        };

        List<Integer> userIds = selection.selectFromCandidateUsersAboutTask(Arrays.asList(1, 2, 3, 4), 1, null);
        Assertions.assertTrue(userIds.containsAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    void selectFromCandidateUsersAboutTask2() {
        List<AbstractUserEvaluateObjective> objectives  = Stream.of(1, 2, 3, 4).map(id -> new AbstractUserEvaluateObjective() {
            @Override
            protected double calculateValue(List<Integer> userIds) {
                return userIds.contains(id) ? 0 : 1;
            }

            @Override
            protected ObjectiveType getObjectType() {
                return ObjectiveType.MINIMIZE;
            }


        }).collect(Collectors.toList());

        MultiObjectiveSelection selection = new MultiObjectiveSelection(100, 200){
            @Override
            protected List<AbstractUserEvaluateObjective> generateObjectives(Integer taskId, List<Integer> candidateUserIds,
                                                                             MultiObjectiveRecommendFactor factor) {
                return objectives;
            }
        };

        List<Integer> userIds = selection.selectFromCandidateUsersAboutTask(Arrays.asList(1, 2, 3, 4), 1, null);
        Assertions.assertTrue(userIds.containsAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    void selectFromCandidateUsersAboutTask3() {
        List<AbstractUserEvaluateObjective> objectives  = Stream.of(1, 2, 3, 4).map(id -> new AbstractUserEvaluateObjective() {
            @Override
            protected double calculateValue(List<Integer> userIds) {
                return userIds.contains(id) ? 1 : 0;
            }

            @Override
            protected ObjectiveType getObjectType() {
                return ObjectiveType.MAXIMIZE;
            }

        }).collect(Collectors.toList());

        objectives.addAll(Stream.of(5, 6, 7, 8).map(id -> new AbstractUserEvaluateObjective() {
            @Override
            protected double calculateValue(List<Integer> userIds) {
                return userIds.contains(id) ? -1 : 0;
            }

            @Override
            protected ObjectiveType getObjectType() {
                return ObjectiveType.MAXIMIZE;
            }


        }).collect(Collectors.toList()));

        MultiObjectiveSelection selection = new MultiObjectiveSelection(100, 200){
            @Override
            protected List<AbstractUserEvaluateObjective> generateObjectives(Integer taskId,List<Integer> candidateUserIds,
                                                                             MultiObjectiveRecommendFactor factor) {
                return objectives;
            }
        };

        List<Integer> userIds = selection.selectFromCandidateUsersAboutTask(Arrays.asList(1, 2, 3, 4), 1, null);
        Assertions.assertTrue(userIds.containsAll(Arrays.asList(1, 2, 3, 4)));
        for(Integer id : Arrays.asList(5, 6, 7, 8)){
            Assertions.assertFalse(userIds.contains(id));
        }
    }
}