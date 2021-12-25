// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';
// 获取登录者细信息
export const getLoginInfo = (params) => {
    return getRequest('/user/info', params)
}

// 获取服务信息
export const getServiceByIds = (params) => {
    return getRequest('/dynamic_new/get_service_by_ids', params)
}
// 分页获取数据
export const getDynamic_newList = (params) => {
    return getRequest('/hospitalNews/getByPage', params)
}
// 添加
export const addDynamic_new = (params) => {
    return postRequest('/hospitalNews/insertOrUpdate', params)
}
// 编辑
export const editDynamic_new = (params) => {
    return postRequest('/hospitalNews/insertOrUpdate', params)
}
// 删除
export const deleteDynamic_new = (params) => {
    return postRequest('/hospitalNews/delByIds', params)
}

// 设置置顶
export const setTopById = (params) => {
    return postRequest('/hospitalNews/set_top_by_id', params)
}

// 设置公开
export const setPublicById = (params) => {
    return postRequest('/hospitalNews/set_public_by_id', params)
}