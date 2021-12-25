import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getHospitalSubjectList = (params) => {
    return getRequest('/hospitalSubject/getByPage', params)
}
// 添加
export const addHospitalSubject = (params) => {
    return postRequest('/hospitalSubject/insertOrUpdate', params)
}
// 编辑
export const editHospitalSubject = (params) => {
    return postRequest('/hospitalSubject/insertOrUpdate', params)
}
// 删除
export const deleteHospitalSubject = (params) => {
    return postRequest('/hospitalSubject/delByIds', params)
}