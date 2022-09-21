import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const uploadFile = "/zwz/upload/file"
export const drawCodeImage = "/zwz/common/captcha/draw/"
export const getMenuList = "/zwz/permission/getMenuList"


export const login = (params) => {
    return postNoAuthRequest('/login', params)
}
export const userInfo = (params) => {
    return getRequest('/user/info', params)
}
export const regist = (params) => {
    return postNoAuthRequest('/user/regist', params)
}
export const initCaptcha = (params) => {
    return getNoAuthRequest('/common/captcha/init', params)
}
export const ipInfo = (params) => {
    return getRequest('/common/ip/info', params)
}
export const userInfoEdit = (params) => {
    return postRequest('/user/edit', params)
}
export const changePass = (params) => {
    return postRequest('/user/modifyPass', params)
}
export const unlock = (params) => {
    return postRequest('/user/unlock', params)
}
export const getUserListData = (params) => {
    return getRequest('/user/getByCondition', params)
}
export const getMyUserListData = (params) => {
    return getRequest('/myUser/getByPage', params)
}
export const getUserByDepartmentId = (id, params) => {
    return getRequest(`/user/getByDepartmentId/${id}`, params)
}
export const initDepartment = (params) => {
    return getRequest('/department/getByParentId', params)
}
export const loadDepartment = (params) => {
    return getRequest('/department/getByParentId', params)
}
export const addDepartment = (params) => {
    return postRequest('/department/add', params)
}
export const editDepartment = (params) => {
    return postRequest('/department/edit', params)
}
export const deleteDepartment = (params) => {
    return postRequest('/department/delByIds', params)
}
export const searchDepartment = (params) => {
    return getRequest('/department/search', params)
}
export const getDictDataByType = (type, params) => {
    return getRequest(`/dictData/getByType/${type}`, params)
}