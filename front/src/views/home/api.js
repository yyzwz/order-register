import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getMyDoorList = (params) => {
    return postRequest('/myDoor/getMyDoorList', params)
}
export const getMyDoorList6 = (params) => {
    return postRequest('/myDoor/getMyDoorList6', params)
}
export const setMyDoorList = (params) => {
    return postRequest('/myDoor/setMyDoorList', params)
}
export const ipInfo = (params) => {
    return getRequest('/common/ip/info', params)
}
// 个人中心编辑
export const userInfoEdit = (params) => {
    return postRequest('/user/edit', params)
}