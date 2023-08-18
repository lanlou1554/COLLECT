package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.service.python.PythonService;
import com.seiii.collect.service.report.ReportService;
import com.seiii.collect.service.task.TaskService;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import com.seiii.collect.util.Constant;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Util {
    private PythonService pythonService;
    private UserService userService;
    private ReportService reportService;
    private TaskService taskService;

    public Util(PythonService pythonService, UserService userService, ReportService reportService, TaskService taskService) {
        this.pythonService = pythonService;
        this.userService = userService;
        this.reportService = reportService;
        this.taskService = taskService;
    }

    // TODO 存在通过阈值的过滤机制
    private List<String> extractDomainKnowledge(Map<String, Double> data) {
        List<String> res = new ArrayList<>();
        for (String key : data.keySet()) {
            if (data.get(key) >= Constant.DOMAIN_KNOWLEDGE_THRESHOLD) {
                res.add(key);
            }
        }
        return res;
    }

    //TODO 目前并没有存储领域知识，每次实时计算。且默认id存在
    // id为workerid或taskId
    @Cacheable(cacheNames = "getDomainKnowledge")
    public ResponseVO<List<String>> getDomainKnowledge(Boolean isWorker, Integer id) {
        ResponseVO<Map<String, Double>> rawData;
        if (isWorker) {
            Integer pageId = 1;
            List<Integer> ids = new LinkedList<>();
            while (true) {
                ResponseVO<List<ReportViewVO>> tempResponse = userService.viewWorkerAllReports(id, pageId);
                if (tempResponse.code == Constant.REQUEST_FAIL) {
                    break;
                }
                List<ReportViewVO> temp = tempResponse.data;
                if (temp == null || temp.size() == 0) {
                    break;
                }
                ids.addAll(temp.stream().map(ReportViewVO::getId).collect(Collectors.toList()));
                pageId++;
            }
            List<String> res = new LinkedList<>();
            for (Integer idx : ids) {
                ReportVO report = reportService.viewReportDetails(idx).data;
                List<FlawVO> flaws = report.getFlaws();
                res.addAll(flaws.stream().map(FlawVO::getDescription).collect(Collectors.toList()));
                res.addAll(flaws.stream().map(FlawVO::getStepDes).collect(Collectors.toList()));
            }
            if (res.size() == 0) {
                return new ResponseVO<>(Constant.REQUEST_SUCCESS, "该用户无历史信息", res);
            }
            rawData = pythonService.getTFIDF(res);
        } else {
            // id为taskId
            ResponseVO<TaskVO> tempResponse = taskService.viewTaskDetails(id);
            if (tempResponse.code == Constant.REQUEST_FAIL) {
                return new ResponseVO<>(Constant.REQUEST_FAIL, tempResponse.msg);
            }
            TaskVO task = tempResponse.data;
            List<String> res = new LinkedList<>();
            if (task.getTitle() != null) res.add(task.getTitle());
            if (task.getDescription() != null) res.add(task.getDescription());
            if (res.size() != 0) rawData = pythonService.getTFIDF(res);
            else return new ResponseVO<>(Constant.REQUEST_FAIL, "该任务没有描述和标题！");
        }
        if (rawData.code == Constant.REQUEST_FAIL) {
            return new ResponseVO<>(rawData.code, rawData.msg);
        } else {
            return new ResponseVO<>(rawData.code, rawData.msg, extractDomainKnowledge(rawData.data));
        }
    }

    public Double cosineSimilarity(List<String> vector1, List<String> vector2) {
        if (vector1.size() == 0 || vector2.size() == 0) {
            return 0.0;
        }

        HashSet<String> totalTermList = new HashSet<String>();

        for (int i = 0; i < vector1.size(); i++) {
            totalTermList.add(vector1.get(i));
        }
        for (int i = 0; i < vector2.size(); i++) {
            totalTermList.add(vector2.get(i));
        }

        int v1sum = 0, v2sum = 0, multiply = 0;
        for (String term : totalTermList) {
            int v1 = 0, v2 = 0;
            if (vector1.contains(term))
                v1 = 1;
            if (vector2.contains(term))
                v2 = 1;

            v1sum += v1 * v1;
            v2sum += v2 * v2;
            multiply += v1 * v2;
        }

        double sim = (1.0 * multiply) / (Math.sqrt(1.0 * v1sum) * Math.sqrt(1.0 * v2sum));
        return sim;
    }
}
