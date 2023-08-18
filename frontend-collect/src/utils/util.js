import {ROLE_PATH} from "@/const/UserRole";


export const storageSet = (key, value) => {
    window.sessionStorage.setItem(key, value);
}

export const storageGet = (key) => {
    return window.sessionStorage.getItem(key);
}

export const isLogin = () => {
    return storageGet('isLogin');
}

export const cleanLoginInfo = () => {
    storageSet('id', null);
    storageSet('type', null);
    storageSet('username', null);
    storageSet('isLogin', false);
}

export const setLoginInfo = userViewVO => {
    storageSet("id", userViewVO.id);
    storageSet("type", userViewVO.type);
    storageSet("username", userViewVO.username);
    storageSet('isLogin', true);
}

export const getPathType = path => {
    //console.log(path)
    for(let role = 0; role < ROLE_PATH.length; role++){
        if(path.startsWith(ROLE_PATH[role]))return role
    }
    return null;
}

export const checkPermission = (role, callback) => {
    callback(isLogin() && role === Number.parseInt(storageGet('type')))
}

export const autoLogin = (callback) => {
    let role = Number.parseInt(storageGet('type'));
    if(isLogin())callback(isNaN(role) ? null : role);
    else callback(null)
}


const random_string = (len) => {
    len = len || 32;
    const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    const maxPos = chars.length;
    let pwd = '';
    for (let i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

const get_suffix = (filename) => {
    let pos = filename.lastIndexOf('.')
    let suffix = ''
    if (pos !== -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}


export const getRandomName = (filename) => {
    let suffix = get_suffix(filename);
    return random_string(10) + suffix;
}

export const getRandomId = () => {
    return `${Math.floor(Math.random() * 10000000) }${new Date().getTime()}`
}

export const tagFilter = tag => {
    return !tag.hidden;
}

export const formatDate = date => {
    return `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日`
}

export const download = (url) => {
    const ele = document.createElement('a');
    ele.setAttribute('href', url);
    //this.$options.filters['filterUrl']是调用全局过滤器,filterUrl是你自己项目main.js里面定义的过滤器
    //ele.setAttribute('download',name);
    ele.style.display = 'none';
    document.body.appendChild(ele);
    ele.click();
    document.body.removeChild(ele);
}
