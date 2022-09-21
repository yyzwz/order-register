import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const changePass = (params) => {
    return postRequest('/user/modifyPass', params)
}