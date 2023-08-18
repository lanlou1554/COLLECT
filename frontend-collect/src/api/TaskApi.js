import axios from "axios";
import { TASK_MODULE } from "./_prefix";

export const postTaskDetail = TaskDTO =>{
    const{
        userId,
        type,
        description,
        startTime,
        endTime,
        workerNum,
        exeFileName,
        reqDocName,
        title,
        tagGroups,
        recommendFactor
    }=TaskDTO;
    console.log(TaskDTO)
    return axios.post(`${TASK_MODULE}/issue`,{
        userId,
        type,
        description,
        startTime,
        endTime,
        workerNum,
        exeFileName,
        reqDocName,
        title,
        tagGroups,
        recommendFactor
    }).then(res=>{
        return res.data;
    });
    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "msg": "请求成功",
    //         "data": {
    //             "id": 8,
    //             "userId": 2,
    //             "type": 0,
    //             "title": "要测试一个应用，桌面版和手机版都要",
    //             "description": "同标题，不急，时间充裕，但是希望充分测试",
    //             "startTime": "2022-04-01T16:00:00.000+00:00",
    //             "endTime": "2022-08-10T16:00:00.000+00:00",
    //             "createTime": "2022-04-02T07:35:45.000+00:00",
    //             "workerNum": 20,
    //             "pickedWorkerNum": 6,
    //             "exeFileName": "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/JJe64TyYaK.zip",
    //             "reqDocName": "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/zKazFyzXRX.pdf",
    //             "state": 2,
    //             "tagGroups": [
    //                 0,
    //                 2
    //             ]
    //         }
    //     }
    // )
};

export const pickTask = payload =>{
    const{
        taskId,
        userId,
    }=payload;
    return axios
        .post(`${TASK_MODULE}/pick/${taskId}?userId=${userId}`)
        .then(res=>{
            return res.data;
        })
};

export const getTaskRecruiting = payload => {
    const{
        pageId,
        userId
    }=payload;
    return axios.get(`${TASK_MODULE}/unfinished?pageId=${pageId}&userId=${userId}`)
           .then(res=>{
               return res.data;
           })
   /* return Promise.resolve(
        {
            "code": 1,
            "message": '',
            "data": [
                {
                    id: pageId,
                    userId: 0,
                    type: 1,
                    title: "<script>alert(1)</script>",
                    description: "使用COLLECT网页端测试注册、登录、发布测试、浏览测试、提交测试报告等功能。使用COLLECT网页端测试注册、登录、发布测试、浏览测试、提交测试报告等功能。使用COLLECT网页端测试注册、登录、发布测试、浏览测试、提交测试报告等功能。使用COLLECT网页端测试注册、登录、发布测试、浏览测试、提交测试报告等功能。",
                    startTime: "2022-02-23T16:08:57.745Z",
                    endTime: "2022-02-23T16:08:57.745Z",
                    workerNum: 10
                }
            ]

        }
    )*/
};

export const getTaskDetail = payload =>{
    const{
        userId,
        taskId,
    }=payload;
    return axios
           .get(`${TASK_MODULE}/detail/${taskId}?userId=${userId}`)
           .then(res=>{
                return res.data;
            })
    /*return Promise.resolve({
        "code": 1,
        "msg": "string",
        "data": {
            "id": 0,
            "userId": 0,
            "type": 0,
            "title": "string",
            "description": JSON.stringify(payload),
            "startTime": "2022-03-04T17:48:58.966Z",
            "endTime": "2022-03-04T17:48:58.966Z",
            "workerNum": 0,
            "exeFileName": "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/3dw8MK7c7F.png",
            "reqDocName": "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/AY6tF4dh3H.png",
            "state": 2,
            "createTime": "2022-03-04T17:48:58.966Z"
        }});*/
}

export const getReportId = payload =>{
    const{
        userId,
        taskId,
    }=payload;
    return axios
        .get(`${TASK_MODULE}/detail/getreport/${taskId}?userId=${userId}`)
        .then(res => {
            return res.data
        })
}


export const stopRecruiting = taskId => {
    return axios.post(`${TASK_MODULE}/stop/${taskId}`).then(res => {
        return res.data
    })
    // return Promise.resolve({
    //
    //     "code": 1,
    //     "msg": "成功： " + taskId,
    //     "data": {}
    //
    // })
}

export const getRecommendFactor = taskId => {
    return axios
        .get(`${TASK_MODULE}/taskRecommendFactor/get/${taskId}`)
        .then(res => {
            return res.data
        })
}

export const setRecommendFactor = (RecommendFactorDTO,taskId) => {
    return axios
        .post(`${TASK_MODULE}/taskRecommendFactor/set/${taskId}`,RecommendFactorDTO)
        .then(res => {
            return res.data
        })
    // console.log(RecommendFactorDTO,taskId)
    // return Promise.resolve({
    //     "code": 1,
    //     "msg": "请求成功",
    //     "data": [
    //         {
    //             "name": "工人能力",
    //             "weight": 0.3
    //         },
    //         {
    //             "name": "工人活跃度",
    //             "weight": 0.1
    //         },
    //         {
    //             "name": "工人相关性",
    //             "weight": 0.2
    //         },
    //         {
    //             "name": "工人多样性",
    //             "weight": 0.2
    //         },
    //         {
    //             "name": "开销",
    //             "weight": 0.2
    //         }
    //     ]
    // });
}

export const getDefaultRecommendFactor = () => {
    return axios
        .get(`${TASK_MODULE}/taskRecommendFactor/list`)
        .then(res => {
            return res.data
        })
}

export const getCurrentTaskRadar = taskId => {
    return axios
        .get(`${TASK_MODULE}/taskRadar/current/${taskId}`)
        .then(res => {
            return res.data
        })
    /*console.log(taskId)
    return Promise.resolve(
        {
            "code": 1,
            "msg": "string",
            "data":
                {
                    "ability": 40,
                    "activation": 30,
                    "revelance": 20,
                    "diversity": 10,
                    "avgTarget": 50
                }

        }
    )*/
}

export const getTargetTaskRadar = taskId => {
    return axios
        .get(`${TASK_MODULE}/taskRadar/target/${taskId}`)
        .then(res => {
            return res.data
        })
    /*console.log(taskId)
    return Promise.resolve(
        {
            "code": 1,
            "msg": "string",
            "data":
                {
                    "ability": 20,
                    "activation": 20,
                    "revelance": 20,
                    "diversity": 20,
                    "avgTarget": 20
                }

        }
    )*/
}

export const getBugArrivalCurve = taskId => {
    return axios.get(`${TASK_MODULE}/taskFlawDetection/${taskId}`).then(res => {return res.data})
    /*return Promise.resolve({
        "code": 1,
        "msg": "成功：" + taskId,
        "data": {
            "numPredicted": 6,
            "curves": [
                {
                    "time": "2022-05-26T17:05:25.482Z",
                    "num": 0
                },
                {
                    "time": "2022-05-27T17:05:25.482Z",
                    "num": 1
                },
                {
                    "time": "2022-05-28T17:05:25.482Z",
                    "num": 1
                },
                {
                    "time": "2022-06-36T17:05:25.482Z",
                    "num": 2
                },
                {
                    "time": "2022-07-30T17:05:25.482Z",
                    "num": 3
                },
                {
                    "time": "2022-08-01T17:05:25.482Z",
                    "num": 4
                }
            ]
        }
    })*/
}
