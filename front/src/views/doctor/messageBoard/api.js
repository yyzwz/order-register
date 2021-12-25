import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getMessageBoardList = (params) => {
    return getRequest('/messageBoard/getByPage', params)
}
// 添加
export const addMessageBoard = (params) => {
    return postRequest('/messageBoard/insert', params)
}
export const addReplyMessageBoard = (params) => {
    return postRequest('/messageBoard/insertReply', params)
}
// 编辑
export const editMessageBoard = (params) => {
    return postRequest('/messageBoard/update', params)
}
// 删除
export const deleteMessageBoard = (params) => {
    return postRequest('/messageBoard/delByIds', params)
}