import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getLogListData = (params) => {
    return getRequest('/log/getAllByPage', params)
}