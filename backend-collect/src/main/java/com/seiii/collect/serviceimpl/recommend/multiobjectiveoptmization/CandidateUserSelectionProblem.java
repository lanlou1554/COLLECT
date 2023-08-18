package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization;

import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CandidateUserSelectionProblem extends AbstractBinaryProblem {
    private final List<AbstractUserEvaluateObjective> objectives;
    private final List<Integer> candidateUserIds;
    CandidateUserSelectionProblem(final List<Integer> candidateUserIds, final List<AbstractUserEvaluateObjective> objectives){
        this.objectives = objectives;
        this.candidateUserIds = candidateUserIds;
        setNumberOfObjectives(objectives.size());
        setNumberOfVariables(candidateUserIds.size());
    }

    private List<Integer> parseSolution(BinarySolution solution){
        ArrayList<Integer> selectedUsers = new ArrayList<>();

        for(int i = 0; i < solution.getNumberOfVariables(); i++){
            if(solution.getVariableValue(i).get(0)){
                selectedUsers.add(candidateUserIds.get(i));
            }
        }

        return selectedUsers;
    }

    public List<Integer> selectFinalSolution(List<BinarySolution> front, TaskRecruitStopRecommendFactor resultReceiver){

        BinarySolution solution = front.stream().min(Comparator.comparingDouble(s -> Arrays.stream(s.getObjectives()).sum())).orElseThrow(() -> new RuntimeException("can not selectFromCandidateUsersAboutTask one solution from all: " + front.toString()));
        if(resultReceiver == null)return parseSolution(solution);
        double sum = 0;
        for( int i = 0; i < solution.getNumberOfObjectives(); i++){
            double val = solution.getObjective(i);
            if(!Double.isFinite(val))val = 0;
            sum += val;
            objectives.get(i).fillExpected(resultReceiver, val);
        }
        resultReceiver.setAvgtargetexpected(sum/solution.getNumberOfObjectives());
        return parseSolution(solution);
    }




    @Override
    public void evaluate(BinarySolution binarySolution) {
        List<Integer> selectedUsers = parseSolution(binarySolution);
        for(int i = 0; i < objectives.size(); i++){
            binarySolution.setObjective(i, objectives.get(i).evaluate(selectedUsers));
        }
    }

    @Override
    protected int getBitsPerVariable(int i) {
        return 1;
    }
}
