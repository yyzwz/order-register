import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 读取实体类
export const generateTable = (name, rowNum, params) => {
    return postBodyRequest(`/generate/table/${name}/${rowNum}`, params)
}
// 生成代码
export const getEntityData = (path, params) => {
    return getRequest(`/generate/getEntityData/${path}`, params)
}
