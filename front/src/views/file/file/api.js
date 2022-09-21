import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 查询文件
export const getFileListData = (params) => {
    return getRequest('/file/getByCondition', params)
}
// 复制文件
export const copyFile = (params) => {
    return postRequest('/file/copy', params)
}
// 重命名文件
export const renameFile = (params) => {
    return postRequest('/file/rename', params)
}
// 删除文件
export const deleteFile = (params) => {
    return postRequest('/file/delete', params)
}
export const getOneSetting = (params) => {
    return getRequest('/setting/getOne', params)
}
export const setOneSetting = (params) => {
    return getRequest('/setting/setOne', params)
}