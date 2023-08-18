package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor;

import com.seiii.collect.mapper.recommend.UserSimilarityMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.usersimilarity.JaccardUserSimilarityVector;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.usersimilarity.TaskTagBasedUserSimilarityVector;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.usersimilarity.UserSimilarityVector;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.rulefactor.RuleFactor;
import com.seiii.collect.util.Constant;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("用户相似度")
@Scope("prototype")
public class UserSimilarityBasedCollaborativeConcreteFactor extends RuleFactor {
    private final UserSimilarityMapper userSimilarityMapper;
    private final TaskUserMapper taskUserMapper;

    public UserSimilarityBasedCollaborativeConcreteFactor(UserSimilarityMapper userSimilarityMapper, TaskUserMapper taskUserMapper) {
        this.userSimilarityMapper = userSimilarityMapper;
        this.taskUserMapper = taskUserMapper;
    }


    /**
     * @param targetUserId   目标用户Id
     * @param candidateTasks 候选任务列表
     * @return 优先数列表，优先数值为选择candidateTasks中任务的用户与targetUser用户的平均相似度
     */
    @Override
    public List<Double> calculateRawFactorValues(Integer targetUserId, List<TaskViewVO> candidateTasks) {
        UserSimilarityVector jaccardSimilarityVector = new JaccardUserSimilarityVector(targetUserId,
                userSimilarityMapper.selectJaccardSimilarity(targetUserId, Constant.SIMILAR_USER_LIMIT));
        UserSimilarityVector taskTagBasedSimilarityVector = new TaskTagBasedUserSimilarityVector(targetUserId,
                userSimilarityMapper.selectSimilarUserVectorInfo(targetUserId, Constant.SIMILAR_USER_LIMIT));
        UserSimilarityVector weightedSimilarityVector = jaccardSimilarityVector.times(Constant.JACCARD_USER_SIMILARITY_WEIGHT)
                .plus(taskTagBasedSimilarityVector.times(Constant.TASK_TAG_USER_SIMILARITY_WEIGHT));

        Map<Integer, List<TaskUser>> taskUserGroupByTaskId = taskUserMapper
                .selectByTaskIds(candidateTasks.stream().map(TaskViewVO::getId).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(TaskUser::getTaskid));

        return candidateTasks.stream().map(task -> {
                    List<TaskUser> taskUserList = taskUserGroupByTaskId.get(task.getId());
                    if (taskUserList == null) return 0.0; //如果该任务没有任何人选择，则直接返回0
                    return taskUserGroupByTaskId.get(task.getId()).stream()
                            .filter(taskUser -> !taskUser.getUserid().equals(targetUserId)) //程序可能无法保证待推荐任务均未被当前targetUser所选择，为了不影响推荐的准确性，这里需要排除调targetUser本身。
                            .mapToDouble(taskUser -> weightedSimilarityVector.getSimilarityWithTargetUser(taskUser.getUserid()))
                            .average().orElse(0.0);
                }

        ).collect(Collectors.toList());
    }


}
