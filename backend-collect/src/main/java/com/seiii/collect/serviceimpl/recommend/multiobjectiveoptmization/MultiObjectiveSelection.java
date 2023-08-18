package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization;


import com.seiii.collect.mapper.user.worker.WorkerContextMapper;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.*;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;


import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Component
@NoArgsConstructor
public class MultiObjectiveSelection{
    @Resource
    ApplicationContext applicationContext;

    @Resource
    Util util;
    @Resource
    WorkerContextMapper workerContextMapper;

    public MultiObjectiveSelection(int popSize, int maxGeneration) {
        this.popSize = popSize;
        this.maxGeneration = maxGeneration;
    }

    private int popSize = 100, maxGeneration = 50;

    public List<Integer> selectFromCandidateUsersAboutTask(List<Integer> candidateUserIds, Integer taskId, MultiObjectiveRecommendFactor factor){
        return selectFromCandidateUsersAboutTask(candidateUserIds, taskId, factor, null, null);
    }

    public List<Integer> selectFromCandidateUsersAboutTask(List<Integer> candidateUserIds, Integer taskId, MultiObjectiveRecommendFactor factor, TaskRecruitStopRecommendFactor resultReceiver, List<Integer> usersWhoSelectedThisTask){
        //System.out.println("start multi-objective selection for task<" + taskId + ">, candidate user number is: " + candidateUserIds.size());
        //LocalDateTime start = LocalDateTime.now();


        Set<Integer> allUsers = new HashSet<>(candidateUserIds);
        if(usersWhoSelectedThisTask != null){
            allUsers.addAll(usersWhoSelectedThisTask);
        }
        

        List<AbstractUserEvaluateObjective> objectives = generateObjectives(taskId, new ArrayList<>(allUsers), factor);
        CandidateUserSelectionProblem problem = new CandidateUserSelectionProblem(candidateUserIds, objectives);
        CrossoverOperator<BinarySolution> crossoverOperator = new SinglePointCrossover(0.8);
        MutationOperator<BinarySolution> mutationOperator = new BitFlipMutation(0.8);
        Algorithm<List<BinarySolution>> alg = new NSGAIIBuilder<BinarySolution>(problem, crossoverOperator, mutationOperator, popSize)
                .setSelectionOperator(new BinaryTournamentSelection<>())
                .setMaxEvaluations(popSize * maxGeneration)
                .build();
        AlgorithmRunner runner = new AlgorithmRunner.Executor(alg).execute();
        List<BinarySolution> solutions = alg.getResult();
            if(usersWhoSelectedThisTask != null && resultReceiver != null){
                double average = objectives.stream().mapToDouble(objective -> objective.evaluateAndFillActual(usersWhoSelectedThisTask, resultReceiver)).average().orElse(0);
                resultReceiver.setAvgtargetactual(average);

            }


        //LocalDateTime end = LocalDateTime.now();
        //System.out.println("end multi-objective selection for task<" + taskId + ">, time: " + Duration.between(start, end).getSeconds() + "seconds");
        return problem.selectFinalSolution(solutions, resultReceiver);
    }




    //note: 如果目标的计算需要使用到mapper，请修改这个方法的实现，将mapper自动注入到该类中，并将mapper从这里传入到不同Objectives的构造方法中。
    protected List<AbstractUserEvaluateObjective> generateObjectives(
            Integer taskId,
            List<Integer> candidateUserIds,
            MultiObjectiveRecommendFactor factor){
        List<AbstractUserEvaluateObjective> objectives = Arrays.asList(new WorkerAbilityObjective(), new WorkerActivenessObjective(), new WorkerCost(candidateUserIds.size()), new TaskRelevance(taskId,applicationContext), new WorkerDiversity(util,workerContextMapper));
        objectives.forEach(obj -> {
            obj.setWeight(factor);
            obj.prepareForAllCandidateUsers(candidateUserIds);
        });
        return  objectives;
    }
}
