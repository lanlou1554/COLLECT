import axios from "axios";
import {FLAW_MODULE} from "@/api/_prefix";


export const getFlawTree = flawId => {
        return axios
            .get(`${FLAW_MODULE}/tree/${flawId}`)
            .then(res=>{
                //console.log(`result of request '${FLAW_MODULE}/tree/${flawId}': \n ${JSON.stringify(res)}`)
                return res.data;
            })

    /*return Promise.resolve({
        "code": 1,
        "msg": "string",
        "data":
            {
                "children": [
                    {
                        "children": [
                        ],
                        "current": {
                            "id": flawId + 10,
                            "reportId": 0,
                            "pictureURLs": [
                                "string"
                            ],
                            "description": `flaw#${flawId + 10}`,
                            "stepDes": "string",
                            "deviceInfo": "string",
                            "score": 0,
                            "forkedId": 0,
                            "scoreNum": 3,
                            "state": true
                        }
                    }
                ],
                "current": {
                    "id": 93,
                    "reportId": 62,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": `flaw#${flawId}`,
                    "stepDes": "string",
                    "deviceInfo": "string",
                    "score": 0,
                    "forkedId": 0,
                    "scoreNum": 3,
                    "state": true
                }
            }

    })*/
}

export const getSimilarFlaws = flawId => {
    return axios
        .get(`${FLAW_MODULE}/similarFlaw/${flawId}`)
        .then(res => {
            return res.data
        })
    /*return Promise.resolve({
        "code": 1,
        "msg": "string",
        "data": [
            {
                "similarity": 1,
                "flaw": {
                    "id": 93,
                    "reportId": 62,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": "similarToFlaw#" + flawId ,
                    "stepDes": "string",
                    "deviceInfo": "string",
                    "score": 0,
                    "forkedId": 0
                }
            },
            {
                "similarity": 1,
                "flaw": {
                    "id": 1,
                    "reportId": 0,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": "similarToFlaw#" + flawId ,
                    "stepDes": "string",
                    "deviceInfo": "string",
                    "score": 0,
                    "forkedId": 0
                }
            },
            {
                "similarity": 1,
                "flaw": {
                    "id": 2,
                    "reportId": 0,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": "similarToFlaw#" + flawId ,
                    "stepDes": "string",
                    "deviceInfo": "string",
                    "score": 0,
                    "forkedId": 0
                }
            },
            {
                "similarity": 1,
                "flaw": {
                    "id": 3,
                    "reportId": 0,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": "similarToFlaw#" + flawId ,
                    "stepDes": "string",
                    "deviceInfo": "string",
                    "score": 0,
                    "forkedId": 0
                }
            },
            {
                "similarity": 1,
                "flaw": {
                    "id": 4,
                    "reportId": 0,
                    "pictureURLs": [
                        "string"
                    ],
                    "description": "similarToFlaw#" + flawId ,
                    "stepDes": "string",
                    "deviceInfo": "string",
                    "score": 0,
                    "forkedId": 0
                }
            }
        ]
    })*/

}

export const scoreFlaw = (score, flawId, userId) => {
        return axios
        .post(`${FLAW_MODULE}/score`, {
            userId,
            flawId,
            score
        })
        .then(res => {
            return res.data
        })
/*    return Promise.resolve({
        "code": 1,
        "msg": `flawId: ${flawId}; score: ${score}; userId: ${userId}`,
        "data": {}
    })*/
}

export const forkNoFlaw = flawId => {
    return axios.get(`${FLAW_MODULE}/nofork/${flawId}`).then(res=>{
        return res.data;
    });
    // return Promise.resolve({
    //     "code": 1,
    //     "msg": `flawId: ${flawId} no fork`,
    //     "data": {}
    // })
}

export const postRefineFlaw = (flawId,forkedFlawId,flawDTO) => {
    const{
        pictureURLs,
        description,
        stepDes,
        deviceInfo
    }=flawDTO;
    console.log({
        pictureURLs, description, stepDes, deviceInfo
    })
    return axios.post(`${FLAW_MODULE}/refine/${flawId}?forkedFlawId=${forkedFlawId}`, {
        pictureURLs,
        description,
        stepDes,
        deviceInfo
    }).then(res=>{
        return res.data;
    });
    // return Promise.resolve({
    //     "code": 1,
    //     "msg": `flawId: ${flawId} fork ${forkedFlawId} desc ${description}`,
    //     "data": {}
    // })
}

export const getFlawMap = taskId => {
    return axios
        .get(`${FLAW_MODULE}/flawMap/${taskId}`)
        .then(res => {
            return res.data
        })


/*    let matrix = []
    for(let i = 1; i < 10; i++){
        let vector = []
        for(let j = 1; j < 10; j++){
            vector.push((10 * i + j)/ 100)
        }
        matrix.push(vector)
    }
    return Promise.resolve({
        "code": 1,
        "msg": "taskId: " + taskId,
        "data":
            {
                "flawIds": [
                    1, 2, 3, 4, 5, 6, 7, 8, 9
                ],
                "similarityMatrix": matrix
            }

    })*/
}

export const getMyScore = (userId, flawId) => {
    return axios.get(`${FLAW_MODULE}/myScore/${flawId}?userId=${userId}`)
        .then(res => {
            return res.data
        })
/*  return Promise.resolve({
      "code": 1,
      "msg": `userId: ${userId}; flawId: ${flawId}`,
      "data": -1
  })*/
}

export const getFlawEvaluations = (flawId, userId) => {
    return axios.get(`${FLAW_MODULE}/evaluations/${flawId}?userId=${userId}`)
        .then(res => {
            return res.data
        })

/*    return Promise.resolve({
        "code": 1,
        "msg": "string",
        "data": [
            {
                "content": "" + flawId,
                "evaluationId": 0,
                "flawEvaLike": {
                    "dislikeNum": 0,
                    "likeNum": 0,
                    "status": 0
                },
                "user": {
                    "id": 0,
                    "type": 0,
                    "username": "" + userId
                }
            }
        ]
    })*/
}

export const evaluationLike = (evaluationId, userId) => {
    return axios.post(`${FLAW_MODULE}/evaluation/like/${evaluationId}?userId=${userId}`)
        .then(res => {
            return res.data;
        })
}

export const evaluationDislike = (evaluationId, userId) => {
    return axios.post(`${FLAW_MODULE}/evaluation/dislike/${evaluationId}?userId=${userId}`)
        .then(res => {
            return res.data;
        })
}

export const postFlawEvaluations = (flawEvaluationDTO, flawId) => {
    const {
        userId,
        content
    } = flawEvaluationDTO
    return axios.post(`${FLAW_MODULE}/add/evaluation/${flawId}`, {
        userId,
        content
    }).then(res => {
        return res.data
    })

    /*return Promise.resolve({
        "code": 1,
        "msg": "string",
        "data": [
            {
                "user": {
                    "id": userId,
                    "type": 0,
                    "username": `userId: ${userId}`
                },
                "content": content
            },
            {
                "user": {
                    "id": userId,
                    "type": 0,
                    "username": `userId: ${userId}`
                },
                "content": content
            },
            {
                "user": {
                    "id": userId,
                    "type": 0,
                    "username": `userId: ${userId}`
                },
                "content": content
            }
        ]
    })*/
}

export const addFlawAppend = (flawId, appendContent) => {
    return axios.post(`${FLAW_MODULE}/add/append`, {
        flawId,
        appendContent
    })
        .then(res => {
            return res.data
        })

/*    return Promise.resolve({
        "code": 1,
        "msg": `flawId: ${flawId}; content: ${content}`,
        "data": {}
    })*/
}
