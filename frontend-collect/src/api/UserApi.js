import axios from "axios";
import { USER_MODULE } from "./_prefix";

export const register = userDTO => {
    const { type, username, password, email, phone, tagGroups } = userDTO;
    return axios
        .post(`${USER_MODULE}/register`, {
            type,
            username,
            password,
            email,
            phone,
            tagGroups
        })
        .then(res => {
            return res.data;
        });

};

export const login = userFormDTO => {
    const { username, password } = userFormDTO;
    return axios
        .post(`${USER_MODULE}/login`, {
            username,
            password
        })
        .then(res => {
            return res.data;
        });
};

export const viewUserInfo = userId => {
    return axios
        .get(`${USER_MODULE}/view/${userId}`)
        .then(res => {
            return res.data;
        })

};

export const getUserReportViewVO = payload => {
    const{
        userId,
        pageId
    }=payload;
    return axios
        .get(`${USER_MODULE}/report/${userId}?pageId=${pageId}`)
        .then(res => {
            return res.data;
        })
};

export const getUserInfoList = idList => {
    const list = []
    list.push(...idList);
    return axios
        .post(`${USER_MODULE}/info/list`, list)
        .then(res => {
            return res.data;
        })

}



export const getTaskFinished = payload => {
    const{
        userId,
        pageId
    }=payload;
    return axios
           .get(`${USER_MODULE}/task/finished/${userId}?pageId=${pageId}`)
           .then(res=>{
               return res.data;
           })
};

export const getTaskUnfinished = payload => {
    const{
        userId,
        pageId
    }=payload;
    return axios
        .get(`${USER_MODULE}/task/unfinished/${userId}?pageId=${pageId}`)
        .then(res=>{
            return res.data;
        })
};

export const getTaskExpired = payload => {
    const{
        userId,
        pageId
    }=payload;
    return axios
        .get(`${USER_MODULE}/task/expired/${userId}?pageId=${pageId}`)
        .then(res=>{
            return res.data;
        })
};

export const getTaskReportView = payload =>{
    const{
        userId,
        taskId,
        pageId
    }=payload;
    return axios
           .get(`${USER_MODULE}/task/report/${userId}?taskId=${taskId}&&pageId=${pageId}`)
           .then(res=>{
               return res.data;
           })
}

export const userAddTag = (userId, tagId) => {
    return axios.post(`${USER_MODULE}/tag/add?userId=${userId}&tag=${tagId}`)
        .then(res => {
            return res.data;
        })
/*    return Promise.resolve({
        "code": 1,
        "msg": `userId: ${userId}; tagId: ${tagId}`,
        "data": {}
    })*/
}

export const userDeleteTag = (userId, tagId) => {
    return axios.delete(`${USER_MODULE}/tag/delete?userId=${userId}&tag=${tagId}`)
        .then(res => {
            return res.data
        })

/*    return Promise.resolve({
        "code": 1,
        "msg": `userId: ${userId}; tagId: ${tagId}`,
        "data": {}
    })*/
}

export const getWorkerReport = (userId, taskId) => {
    return axios.get(`${USER_MODULE}/workerreport/${userId}?taskId=${taskId}`)
        .then(res => {
            return res.data
        })
    /*return Promise.resolve({
        "code": 1,
        "msg": `userId: ${userId}; taskId: ${taskId}`,
        "data": {
            "id": 0,
            "taskId": 0,
            "userId": 0,
            "title": "string",
            "score": 0,
            "flawNum": 0
        }
    })*/

}

/*    return Promise.resolve(
        {
            "code": 0,
            "message": "加载失败",
            "data": userId
        }
    )*/


/*    return Promise.resolve(
        {
            "code": 1,
            "message": '',
            "data": [
                {
                    "id": 0,
                    "taskId": 0,
                    "userId": userId,
                    "title": "title1"
                },
                {
                    "id": 1,
                    "taskId": 1,
                    "userId": userId,
                    "title": "title1"
                }
            ]

        }
    )*/
export const viewUserRadar = userId => {
    return axios
        .get(`${USER_MODULE}/worker/radar/${userId}`)
        .then(res => {
            return res.data;
        })

    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": `userId: ${userId}`,
    //         "data":{
    //             "cooperateAbility": 50,
    //             "reviewAbility": 50,
    //             "creativeAbility": 50,
    //             "findBugAbility": 60,
    //             "languageAbility": 40,
    //             "avgAbility":50
    //         }
    //
    //     }
    // )
};
export const viewAvgRadar = () => {
    return axios
        .get(`${USER_MODULE}/worker/radar/avg`)
        .then(res => {
            return res.data;
        })

    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": "请求成功",
    //         "data":{
    //             "cooperateAbility": 40,
    //             "reviewAbility": 40,
    //             "creativeAbility": 40,
    //             "findBugAbility": 40,
    //             "languageAbility": 40,
    //             "avgAbility":40
    //         }
    //
    //     }
    // )
};
export const viewUserActivation = userId => {
    return axios
        .get(`${USER_MODULE}/worker/activation/${userId}`)
        .then(res => {
            return res.data;
        })

    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": `userId: ${userId}`,
    //         "data":{
    //             "lastSubmitTime": "2022-05-24T08:26:19.451Z",
    //             "lastThreeDaySubmitNum": 0,
    //             "lastTwoWeekSubmitNum": 10,
    //             "lastOneMonthSubmitNum": 20,
    //             "lastHalfYearSubmitNum": 100,
    //             "score": 30
    //         }
    //
    //     }
    // )
};
export const viewUserWordCloud = userId => {
    return axios
        .get(`${USER_MODULE}/worker/wordcloud/${userId}`)
        .then(res => {
            return res.data;
        })

    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": `userId: ${userId}`,
    //         "data":[
    //             {
    //                 name: "点击",
    //                 value: 100
    //             },
    //             {
    //                 name: "程序",
    //                 value: 20
    //             },{
    //                 name: "崩溃",
    //                 value: 67
    //             },
    //             {
    //                 name: "测试",
    //                 value: 45
    //             },
    //             {
    //                 name: "页面",
    //                 value: 34
    //             },
    //             {
    //                 name: "刷新",
    //                 value: 23
    //             },
    //             {
    //                 name: "文件夹",
    //                 value: 23
    //             },
    //             {
    //                 name: "安卓",
    //                 value: 22
    //             },
    //             {
    //                 name: "返回",
    //                 value: 20
    //             },
    //             {
    //                 name: "桌面",
    //                 value: 12
    //             }
    //         ]
    //
    //     }
    // )
};


export const updateWorkerTestContext = (workerContextDTO, userId) => {
    const {
        deviceType,
        netEnv,
        osInfo,
        ramSize
    } = workerContextDTO
    return axios.post(`${USER_MODULE}/worker/contextUpdate/${userId}`, {
        deviceType, netEnv, osInfo, ramSize
    })
        .then(res => {
            return res.data
        })
}


export const getWorkerTestContext =  (userId) => {
    return axios.get(`${USER_MODULE}/worker/context/${userId}`)
        .then(res => {
            return res.data
        })
}
