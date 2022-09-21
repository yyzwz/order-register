import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 添加权限
export const addPermission = (params) => {
    return postRequest('/permission/add', params)
}
// 编辑权限
export const editPermission = (params) => {
    return postRequest('/permission/edit', params)
}
// 删除权限
export const deletePermission = (params) => {
    return postRequest('/permission/delByIds', params)
}
// 搜索权限
export const searchPermission = (params) => {
    return getRequest('/permission/search', params)
}
export const getDictDataByType = (type, params) => {
    return getRequest(`/dictData/getByType/${type}`, params)
}
export const getUserByPermission = (params) => {
    return getRequest('/permission/getUserByPermission', params)
}