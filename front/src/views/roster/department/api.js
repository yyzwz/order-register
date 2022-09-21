import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const initDepartment = (params) => {
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
export const getUserByDepartmentId = (params) => {
    return getRequest('/user/getByDepartmentId', params)
}
export const getMyUserListData = (params) => {
    return getRequest('/myUser/getByPage', params)
}