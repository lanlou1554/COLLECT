package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor;

import com.seiii.collect.mapper.task.TaskTagMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.rulefactor.RuleFactor;
import com.seiii.collect.util.Constant;
import org.ejml.ops.NormOps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("任务相似度")
@Scope("prototype")
public class TaskSimilarityConcreteFactor extends RuleFactor {
    @Resource
    TaskUserMapper taskUserMapper;
    @Resource
    TaskTagMapper taskTagMapper;

    /**
     * @param targetUserId   目标用户
     * @param candidateTasks 候选任务列表
     * @return 优先数列表，优先数值为candidateTasks中任务与targetUser用户历史任务的平均相似度
     */
    @Override
    public List<Double> calculateRawFactorValues(Integer targetUserId, List<TaskViewVO> candidateTasks) {
        //求得候选任务矩阵candidateTasksMatrix
        int sz = candidateTasks.size();
        DenseMatrix64F candidateTasksMatrix = new DenseMatrix64F(sz, Constant.TASK_TAG_COUNT);
        for (int i = 0; i < sz; i++) {
            List<Integer> candidateTaskTagGroup = candidateTasks.get(i).getTagGroups();
            for (Integer candidateTaskTag : candidateTaskTagGroup) {
                candidateTasksMatrix.set(i, candidateTaskTag, 1);
            }
        }
        //求得历史任务矩阵pickedTasksMatrix
        List<Integer> userPickedTaskIds = taskUserMapper.selectByUserId(targetUserId);
        int userPickedTaskSz = userPickedTaskIds.size();
        int pickedTasksMatrixRowNum = Math.min(userPickedTaskSz, Constant.PICKED_TASK_CAL_NUM);
        //历史选择任务个数为0或禁用此项，均构建全零列表并返回
        if (pickedTasksMatrixRowNum == 0) {
            List<Double> specificAllZeroAns = new ArrayList<>();
            for (int i = 0; i < sz; i++) {
                specificAllZeroAns.add(0.0);
            }
            return specificAllZeroAns;
        }
        int cnt = 0;
        DenseMatrix64F pickedTasksMatrix = new DenseMatrix64F(pickedTasksMatrixRowNum, Constant.TASK_TAG_COUNT);
        for (int i = userPickedTaskSz - 1; i >= 0; i--, cnt++) {
            if (cnt >= 100)
                break;
            List<Integer> pickedTaskTagGroup = taskTagMapper.selectByTaskId(userPickedTaskIds.get(i));
            for (Integer pickedTaskTag : pickedTaskTagGroup) {
                pickedTasksMatrix.set(cnt, pickedTaskTag, 1);
            }
        }
        //对候选任务矩阵做归一化
        DenseMatrix64F[] candidateTasksVectors = new DenseMatrix64F[sz];
        CommonOps.rowsToVector(candidateTasksMatrix, candidateTasksVectors);
        for (int i = 0; i < sz; i++) {
            double vectorSquareRoot = NormOps.normF(candidateTasksVectors[i]);
            if (vectorSquareRoot != 0.0)
                CommonOps.divide(vectorSquareRoot, candidateTasksVectors[i]);
        }
        double[][] candidateTasksVectorsValues = new double[sz][Constant.TASK_TAG_COUNT];
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < Constant.TASK_TAG_COUNT; j++) {
                candidateTasksVectorsValues[i][j] = candidateTasksVectors[i].get(j, 0);
            }
        }
        candidateTasksMatrix = new DenseMatrix64F(candidateTasksVectorsValues);
        //对历史任务矩阵做归一化
        DenseMatrix64F[] pickedTasksVectors = new DenseMatrix64F[pickedTasksMatrixRowNum];
        CommonOps.rowsToVector(pickedTasksMatrix, pickedTasksVectors);
        for (int i = 0; i < pickedTasksMatrixRowNum; i++) {
            double vectorSquareRoot = NormOps.normF(pickedTasksVectors[i]);
            if (vectorSquareRoot != 0.0)
                CommonOps.divide(vectorSquareRoot, pickedTasksVectors[i]);
        }
        double[][] pickedTasksVectorsValues = new double[pickedTasksMatrixRowNum][Constant.TASK_TAG_COUNT];
        for (int i = 0; i < pickedTasksMatrixRowNum; i++) {
            for (int j = 0; j < Constant.TASK_TAG_COUNT; j++) {
                pickedTasksVectorsValues[i][j] = pickedTasksVectors[i].get(j, 0);
            }
        }
        pickedTasksMatrix = new DenseMatrix64F(pickedTasksVectorsValues);

        //候选任务矩阵与历史任务矩阵的转置做乘积，结果每一行是候选任务和所有历史任务的相似度
        //对上述结果矩阵每行求均值，得到单列向量，转置即为结果对应向量
        DenseMatrix64F taskSimilarityMatrix = new DenseMatrix64F(sz, pickedTasksMatrixRowNum);
        DenseMatrix64F pickedTasksMatrixTrans = new DenseMatrix64F(Constant.TASK_TAG_COUNT, pickedTasksMatrixRowNum);
        CommonOps.transpose(pickedTasksMatrix, pickedTasksMatrixTrans);
        CommonOps.mult(candidateTasksMatrix, pickedTasksMatrixTrans, taskSimilarityMatrix);

//        DenseMatrix64F[] taskSimilarityVectors = new DenseMatrix64F[sz];
//        CommonOps.rowsToVector(taskSimilarityMatrix,taskSimilarityVectors);
        DenseMatrix64F taskSimilarityMeanMatrix = new DenseMatrix64F(sz, 1);
//        for(DenseMatrix64F taskSimilarityVector:taskSimilarityVectors){
//            CommonOps.addEquals(taskSimilarityMeanMatrix,taskSimilarityVector);
//        }
        CommonOps.sumRows(taskSimilarityMatrix, taskSimilarityMeanMatrix);
        CommonOps.divide(pickedTasksMatrixRowNum, taskSimilarityMeanMatrix);
//        DenseMatrix64F taskSimilarityMeanMatrixTrans=new DenseMatrix64F(1,sz);
//        CommonOps.transpose(taskSimilarityMeanMatrix,taskSimilarityMeanMatrixTrans);
        List<Double> taskSimilarity = new ArrayList<>();
        for (int i = 0; i < sz; i++) {
            taskSimilarity.add(taskSimilarityMeanMatrix.get(i, 0));
        }
        return taskSimilarity;
    }
}
