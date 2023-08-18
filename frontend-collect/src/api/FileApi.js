import axios from "axios";
import {FILE_UPLOAD_API} from "./_prefix";

export const getSignature = () => {
    //console.log('axios get signature')
    return axios
        .get(`${FILE_UPLOAD_API}/`)
        .then(res => {
            return res.data;
        }).catch(res => {
            console.log('error happen: ')
            console.log(res)
        });
};
