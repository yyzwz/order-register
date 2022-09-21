import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getStudentOne = (params) => {
    return getRequest('/student/getOne', params)
}
export const getStudentList = (params) => {
    return getRequest('/student/getByPage', params)
}
export const getStudentCount = (params) => {
    return getRequest('/student/count', params)
}
export const addStudent = (params) => {
    return postRequest('/student/insert', params)
}
export const editStudent = (params) => {
    return postRequest('/student/update', params)
}
export const addOrEditStudent = (params) => {
    return postRequest('/student/insertOrUpdate', params)
}
export const deleteStudent = (params) => {
    return postRequest('/student/delByIds', params)
}