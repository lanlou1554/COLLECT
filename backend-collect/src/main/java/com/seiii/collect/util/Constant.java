package com.seiii.collect.util;

import io.swagger.models.auth.In;

import java.util.Arrays;
import java.util.List;

/**
 * 定义一些常量
 */
public class Constant {
    // 请求操作成功返回码
    public static final Integer REQUEST_SUCCESS = 1;
    // 请求操作失败返回码
    public static final Integer REQUEST_FAIL = 0;
    // 返回的最高相似度缺陷的个数
    public static final Integer SIMILAR_FLAW_COUNT = 5;
    // 判断是否相似的阈值
    public static final Double SIMILARITY_BOUND = 0.9;
    // 相似度保留几位小数
    public static final Integer RESERVED_DECIMAL_NUM = 2;
    //每页页数
    public static final Integer PAGE_SIZE = 50;
    //userType 用户类型，0对应发包方，1对应众包工人，2对应管理员
    public static final Integer USER_TYPE_CONTRACTOR = 0;
    public static final Integer USER_TYPE_WORKER = 1;
    public static final Integer USER_TYPE_ADMIN = 2;
    // task的state属性
    // 0表示未领取或者请求该任务详情的用户不是众包工人，1表示进行中，2表示已完成，3表示逾期未交，4表示待完善（缺陷没有全部处理完）,5表示招募已停止
    public static final Integer TASK_UNCLAIMED = 0;
    public static final Integer TASK_ONGOING = 1;
    public static final Integer TASK_FINISHED = 2;
    public static final Integer TASK_EXPIRED = 3;
    public static final Integer TASK_TO_BE_IMPROVED = 4;
    public static final Integer TASK_RECRUIT_STOP=5;
    //推荐规则影响因子
    public static final List<String> RECOMMEND_FACTORS = Arrays.asList("任务紧迫度", "任务相似度", "用户相似度");

    //任务标签的个数
    public static final Integer TASK_TAG_COUNT = 9;

    //等于任务标签的总数加1
    public static final Integer USER_VECTOR_DIMENSION = TASK_TAG_COUNT + 1;
    //使用杰卡德距离选择出的相似用户的个数上限
    public static final Integer SIMILAR_USER_LIMIT = 100;
    //用户相似度权值
    public static final Double JACCARD_USER_SIMILARITY_WEIGHT = 0.5;
    public static final Double TASK_TAG_USER_SIMILARITY_WEIGHT = 0.5;
    //用历史任务推荐任务时考虑历史任务的个数
    public static final Integer PICKED_TASK_CAL_NUM = 10;

    // 工人能力可视化取值范围（即最大值）
    public static final Integer ABILITY_MAX_VALUE = 5;
    // 出现异常情况时，工人能力评价指标的默认值（范围0-1的情况下）
    public static final Double ABILITY_FACTOR_DEFAULT_VALUE = 0.3;
    // 出现异常情况时，工人能力的默认值
    public static final Double ABILITY_DEFAULT_VALUE = ABILITY_MAX_VALUE.doubleValue() * ABILITY_FACTOR_DEFAULT_VALUE;
    // 语言表达能力的默认值
    public static final Double EXPRESSION_ABILITY_DEFAULT_VALUE = 0.5;

    // 威尔逊得分中正态分布的分位数，0.5对应70%，2对应95%
    public static final Double QUANTILE_OF_NORMAL_DISTRIBUTION = 0.5;

    //选取的领域知识个数
    public static final Integer DOMAIN_KNOWLEDGE_NUM = 5;
    //选取的领域知识阈值
    public static final Double DOMAIN_KNOWLEDGE_THRESHOLD = 0.3;

    //发包方可以设置的任务推荐元素
    public static final List<String> RECOMMEND_ELEMENTS = Arrays.asList("工人能力", "工人活跃度", "工人相关性","工人多样性","开销");

    //发包方可以设置的任务推荐元素默认值
    public static final List<Double> RECOMMEND_ELEMENTS_WEIGHTS = Arrays.asList(0.4, 0.1, 0.2, 0.2, 0.1);


}
