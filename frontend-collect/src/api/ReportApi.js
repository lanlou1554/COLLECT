import axios from "axios";
import { REPORT_MODULE } from "./_prefix";

export const getReportDetail = payload => {
   const { reportId } = payload;
     return axios
        .get(`${REPORT_MODULE}/detail/${reportId}`)
        .then(res => {
            return res.data;
        });
    /*return Promise.resolve({
        "code": 1,
        "message": "string",
        "data": {
            "id": 0,
            "taskId": 0,
            "userId": 3,
            "title": "string",
            "flaws": [
                {
                    "id": 0,
                    "reportId": reportId,
                    "pictureURLs": [
                        'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
                        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
                    ],
                    "description": "很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述很长的缺陷描述",
                    "stepDes": "string",
                    "deviceInfo": "string"
                },
                {
                    id: 1,
                    reportId:reportId,
                    pictureURLs:[
                        'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
                    ],
                    description: 'd',
                    stepDes: '<script>alert(1)</script>',
                    deviceInfo: 'device information'
                }
            ]
        }
    })*/
};

export const postReportDetail = ReportDTO =>{
    const{
        taskId,
        userId,
        title,
        flaws
    }=ReportDTO;
/*    console.log({
        taskId, userId, title, flaws
    })*/

/*    return Promise.resolve({
        "code": 1,
        "msg": "string",
        "data": {
            "id": 1,
            "taskId": 0,
            "userId": 0,
            "title": "string",
            "createTime": "2022-03-03T16:12:29.546Z",
            "flaws": [
                {
                    "id": 0,
                    "reportId": 0,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": "string",
                    "stepDes": "string",
                    "deviceInfo": "string"
                }
            ]
        }
    })*/
    return axios.post(`${REPORT_MODULE}/issue`, {
            taskId,
            userId,
            title,
            flaws
        }).then(res=>{
            return res.data;
    });
};

export const getTbaFlaws = reportId =>{
    return axios
        .get(`${REPORT_MODULE}/toBeRefined?reportId=${reportId}`)
        .then(res => {
            return res.data
        })
    // return Promise.resolve({
    //     "code":1,
    //     "msg":"string",
    //     "data":[
    //         {
    //             "tbaFlaw": {
    //                 "id": 93,
    //                 "reportId": 62,
    //                 "pictureURLs": [
    //                     "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4tz26A8nX8.png"
    //                 ],
    //                 "description": "belongToReport#" + reportId ,
    //                 "stepDes": "strin1g",
    //                 "deviceInfo": "string",
    //                 "score": 0,
    //                 "forkedId": 0
    //             },
    //             "similarFlaws": [
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 1,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4tz26A8nX8.png"
    //                         ],
    //                         "description": "similarToFlaw#93" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 2,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#93" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 3,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#93" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 4,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#93",
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 }
    //             ]
    //         },
    //         {
    //             "tbaFlaw": {
    //                 "id": 92,
    //                 "reportId": 62,
    //                 "pictureURLs": [
    //                     "string"
    //                 ],
    //                 "description": "belongToReport#" + reportId ,
    //                 "stepDes": "string",
    //                 "deviceInfo": "string",
    //                 "score": 0,
    //                 "forkedId": 0
    //             },
    //             "similarFlaws": [
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 1,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#92" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 2,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#92" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 3,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#92" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 4,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#92",
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 }
    //             ]
    //         },
    //         {
    //             "tbaFlaw": {
    //                 "id": 91,
    //                 "reportId": 62,
    //                 "pictureURLs": [
    //                     "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4tz26A8nX8.png"
    //                 ],
    //                 "description": "belongToReport#" + reportId ,
    //                 "stepDes": "strin1g",
    //                 "deviceInfo": "string",
    //                 "score": 0,
    //                 "forkedId": 0
    //             },
    //             "similarFlaws": [
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 1,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4tz26A8nX8.png"
    //                         ],
    //                         "description": "similarToFlaw#93" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 2,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#93" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 3,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#93" ,
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 },
    //                 {
    //                     "similarity": 1,
    //                     "flaw": {
    //                         "id": 4,
    //                         "reportId": 0,
    //                         "pictureURLs": [
    //                             "string"
    //                         ],
    //                         "description": "similarToFlaw#93",
    //                         "stepDes": "string",
    //                         "deviceInfo": "string",
    //                         "score": 0,
    //                         "forkedId": 0
    //                     }
    //                 }
    //             ]
    //         }
    //     ]
    // })
}
