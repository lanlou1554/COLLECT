import axios from "axios";
import {ADMIN_MODULE} from "@/api/_prefix";

export const getAllTasksByAdmin = pageId =>{
    return axios
           .get(`${ADMIN_MODULE}/task/all?pageId=${pageId}`)
           .then(res=>{
               return res.data;
           })
};

export const getTaskDetailByAdmin = taskId =>{
    return axios
           .get(`${ADMIN_MODULE}/task/detail/${taskId}`)
           .then(res=>{
               return res.data;
           })
}

export const getFactors = () =>{
    return axios
        .get(`${ADMIN_MODULE}/priorityFactor/list`)
        .then(res=>{
            return res.data;
        })
    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": 'success',
    //         "data": [
    //             {
    //                 "name":"推荐规则1",
    //                 "weight":1
    //             },
    //             {
    //                 "name":"推荐规则2",
    //                 "weight":1
    //             },
    //             {
    //                 "name":"推荐规则3",
    //                 "weight":1
    //             },
    //             {
    //                 "name":"推荐规则4",
    //                 "weight":1
    //             },
    //             {
    //                 "name":"推荐规则5",
    //                 "weight":1
    //             }
    //         ]
    //     }
    // );
}

export const getRecommendRule = () =>{
    return axios
        .get(`${ADMIN_MODULE}/recommendRule/list`)
        .then(res=>{
            return res.data;
        })
    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": 'success',
    //         "data":[
    //             {
    //                 "id": 1,
    //                 "name": "rule1",
    //                 "using": true,
    //                 "factors":[
    //                     {
    //                         "name":"推荐规则1",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则2",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则3",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则4",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则5",
    //                         "weight":1
    //                     }
    //                 ]
    //             },
    //             {
    //                 "id": 2,
    //                 "name": "rule2",
    //                 "using": false,
    //                 "factors":[
    //                     {
    //                         "name":"推荐规则1",
    //                         "weight":0.5
    //                     },
    //                     {
    //                         "name":"推荐规则2",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则3",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则4",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则5",
    //                         "weight":1
    //                     }
    //                 ]
    //             },
    //             {
    //                 "id": 3,
    //                 "name": "rule3",
    //                 "using": false,
    //                 "factors":[
    //                     {
    //                         "name":"推荐规则1",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则2",
    //                         "weight":0.5
    //                     },
    //                     {
    //                         "name":"推荐规则3",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则4",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则5",
    //                         "weight":1
    //                     }
    //                 ]
    //             },
    //             {
    //                 "id": 4,
    //                 "name": "rule4",
    //                 "using": false,
    //                 "factors":[
    //                     {
    //                         "name":"推荐规则1",
    //                         "weight":0.7
    //                     },
    //                     {
    //                         "name":"推荐规则2",
    //                         "weight":0.5
    //                     },
    //                     {
    //                         "name":"推荐规则3",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则4",
    //                         "weight":1
    //                     },
    //                     {
    //                         "name":"推荐规则5",
    //                         "weight":1
    //                     }
    //                 ]
    //             }
    //         ]
    //     }
    // );
}

export const deleteRule = ruleId => {
    // alert(ruleId);
    return axios.delete(`${ADMIN_MODULE}/recommendRule/delete/${ruleId}`).then(res=>{
        return res.data;
    });
    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": '删除成功',
    //         "data": {}
    //     }
    // );
}

export const addRule = recommendRuleDTO => {
    const{
        name,
        factors
    }=recommendRuleDTO;
    console.log({
        name,factors
    })
    return axios.post(`${ADMIN_MODULE}/recommendRule/add`,{
        name,
        factors
    }).then(res=>{
        return res.data;
    });
    // return Promise.resolve({
    //     "code": 1,
    //     "msg": "添加成功",
    //     "data": [
    //         {
    //             "id": 1,
    //             "name": "rule1",
    //             "using": true,
    //             "factors":[
    //                 {
    //                     "name":"推荐规则1",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则2",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则3",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则4",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则5",
    //                     "weight":1
    //                 }
    //             ]
    //         },
    //         {
    //             "id": 7,
    //             "name": "ruleAdd",
    //             "using": false,
    //             "factors":[
    //                 {
    //                     "name":"推荐规则1",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则2",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则3",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则4",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则5",
    //                     "weight":1
    //                 }
    //             ]
    //         },
    //         {
    //             "id": 2,
    //             "name": "rule2",
    //             "using": false,
    //             "factors":[
    //                 {
    //                     "name":"推荐规则1",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则2",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则3",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则4",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则5",
    //                     "weight":1
    //                 }
    //             ]
    //         },
    //         {
    //             "id": 3,
    //             "name": "rule3",
    //             "using": false,
    //             "factors":[
    //                 {
    //                     "name":"推荐规则1",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则2",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则3",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则4",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则5",
    //                     "weight":1
    //                 }
    //             ]
    //         },
    //         {
    //             "id": 4,
    //             "name": "rule4",
    //             "using": false,
    //             "factors":[
    //                 {
    //                     "name":"推荐规则1",
    //                     "weight":0.7
    //                 },
    //                 {
    //                     "name":"推荐规则2",
    //                     "weight":0.5
    //                 },
    //                 {
    //                     "name":"推荐规则3",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则4",
    //                     "weight":1
    //                 },
    //                 {
    //                     "name":"推荐规则5",
    //                     "weight":1
    //                 }
    //             ]
    //         }
    //     ]
    // })
}

export const pickRule = ruleId => {
    // alert(ruleId);
    return axios.post(`${ADMIN_MODULE}/recommendRule/select/${ruleId}`).then(res=>{
        return res.data;
    });
    // return Promise.resolve(
    //     {
    //         "code": 1,
    //         "message": '选择成功',
    //         "data": {}
    //     }
    // );
}