package com.seiii.collect.mapper.recommend;


import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.mapper.user.UserTagMapper;
import com.seiii.collect.model.po.recommend.UserVectorComponent;
import com.seiii.collect.model.po.user.JaccardUserSimilarity;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.po.user.UserTag;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RecommendUserVectorMapperTest {
    @Resource
    UserSimilarityMapper recommendUserVectorMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    UserTagMapper userTagMapper;

    @Transactional
    @Rollback
    @Test
    void testSelectSimilarUserVectorInfo() {
        List<UserVectorComponent> vectorComponents = recommendUserVectorMapper.selectSimilarUserVectorInfo(6, 2);
        Assertions.assertTrue(vectorComponents.stream().map(UserVectorComponent::getUserId).collect(Collectors.toSet()).size() <= 2);
        Assertions.assertTrue(vectorComponents.stream().allMatch(component -> component.getScore() >= 0));
    }

    @Transactional
    @Rollback
    @Test
    void testSelectJaccardSimilarity() {
        List<User> allUsers = userMapper.selectAll();
        Map<Integer, List<User>> userGroupById =
                allUsers.stream().collect(Collectors.groupingBy(User::getId));
        List<JaccardUserSimilarity> jaccardUserSimilarities =
                recommendUserVectorMapper.selectJaccardSimilarity(3, allUsers.size());

        for(JaccardUserSimilarity jaccardUserSimilarity : jaccardUserSimilarities){
            Assertions.assertEquals(jaccardUserSimilarity.getUserid1(), 3);
            Assertions.assertTrue(userGroupById.containsKey(jaccardUserSimilarity.getUserid2()));
            Assertions.assertEquals(userGroupById.get(jaccardUserSimilarity.getUserid2()).size(), 1);
            Assertions.assertEquals(userGroupById.get(jaccardUserSimilarity.getUserid2()).get(0).getType(), Constant.USER_TYPE_WORKER);
        }
    }

    @Transactional
    @Rollback
    @Test
    void testSelectJaccardSimilarityWithNoLabeledUserInDataBase(){
        List<User> users = userMapper.selectAll();
        Set<Integer> allWorkersId = users.stream()
                .filter(user -> user.getType().equals(Constant.USER_TYPE_WORKER))
                .map(User::getId).collect(Collectors.toSet());
        List<JaccardUserSimilarity> jaccardUserSimilarities =
                recommendUserVectorMapper.selectJaccardSimilarity(3, users.size());
        Set<Integer> userIdInJaccardSimilarityResult = jaccardUserSimilarities.stream()
                .map(JaccardUserSimilarity::getUserid2)
                .collect(Collectors.toSet());
        Set<Integer> workerWithTags =  userTagMapper.selectAll().stream().map(UserTag::getUserid).collect(Collectors.toSet());
        Set<Integer> workerWithOutTags = new HashSet<>(allWorkersId);
        workerWithOutTags.removeAll(workerWithTags);
        System.out.println(workerWithOutTags.isEmpty());
        Assertions.assertTrue(userIdInJaccardSimilarityResult.containsAll(workerWithOutTags));
    }

}
