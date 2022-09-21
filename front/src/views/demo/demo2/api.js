import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getTeacherOne = (params) => {
    return getRequest('/teacher/getOne', params)
}
export const getTeacherList = (params) => {
    return getRequest('/teacher/getByPage', params)
}
export const getTeacherCount = (params) => {
    return getRequest('/teacher/count', params)
}
export const addTeacher = (params) => {
    return postRequest('/teacher/insert', params)
}
export const editTeacher = (params) => {
    return postRequest('/teacher/update', params)
}
export const addOrEditTeacher = (params) => {
    return postRequest('/teacher/insertOrUpdate', params)
}
export const deleteTeacher = (params) => {
    return postRequest('/teacher/delByIds', params)
}