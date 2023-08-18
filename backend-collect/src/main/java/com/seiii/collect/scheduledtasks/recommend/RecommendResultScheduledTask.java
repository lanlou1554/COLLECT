package com.seiii.collect.scheduledtasks.recommend;

import com.seiii.collect.model.po.user.User;
import com.seiii.collect.service.recommend.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.seiii.collect.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class RecommendResultScheduledTask {
    private final static Logger LOGGER = LoggerFactory.getLogger(RecommendResultScheduledTask.class);

    private final RecommendService recommendService;
    private final UserService userService;

    public RecommendResultScheduledTask(RecommendService recommendService, UserService userService){
        this.recommendService = recommendService;
        this.userService = userService;
    }

    @Scheduled(cron = "${scheduledTaskTime.refreshRecommendationResult}")
    private void refreshRecommendationResult(){
        LOGGER.info("schedule task begin");
        recommendService.refreshRecommendationResult();
        LOGGER.info("schedule task finished!");
    }

    @Scheduled(cron = "${scheduledTaskTime.refreshWorkerAbility}")
    private void refreshWorkerAbility(){
        LOGGER.info("calculating worker ability......");
        userService.refreshWorkerAbility();
        LOGGER.info("worker ability completed!");
    }
}
