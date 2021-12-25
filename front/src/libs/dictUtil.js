import axios from 'axios';
import { getDictData } from '@/api/index';

let dictUtil = {

};

// 获取常用的数据字典保存至vuex
dictUtil.initDictData = function (vm) {
    axios.get(getDictData + "sex").then(res => {
        if(res.success){
            vm.$store.commit("setSex", res.result);
        }
    });
    axios.get(getDictData + "message_type").then(res => {
        if(res.success){
            vm.$store.commit("setMessageType", res.result);
        }
    });
    axios.get(getDictData + "priority").then(res => {
        if(res.success){
            vm.$store.commit("setPriority", res.result);
        }
    });
};

export default dictUtil;
