<template>
<div class="login">
    <Row justify="center" align="middle" @keydown.enter="submitRegist" style="height:100%;background:#fff">
        <!-- 头部 -->
        <div class="loginUp">
            <div class="loginLeft">
                <img src="../assets/login/logo.png" alt="" srcset="">
                <span class="line"></span>
                <span class="title">医院门诊预约挂号管理系统</span>
            </div>
        </div>
        <div class="loginMiddle">
            <div class="login-background">
                <div class="loginBg"></div>
                <div class="loginRight">
                    <Row :gutter="-20" style="margin-top:15%;">
                        <Icon type="md-checkmark-circle" color="#52c41a" size="64"></Icon>
                    </Row>
                    <Row :gutter="-20">
                        <p class="success-words">{{username}} 注册成功</p>
                    </Row>
                    <Row :gutter="-20" style="margin-top:30px;">
                        <Col span="12">
                        <router-link to="/login">
                            <Button type="primary" size="large" style="margin-right:10px;">立即登录</Button>
                        </router-link>
                        </Col>
                        <Col span="12">
                        <router-link to="/regist">
                            <Button size="large">返回注册</Button>
                        </router-link>
                        </Col>
                    </Row>
                </div>
            </div>
        </div>
        <div class="loginDown">
            <p style="margin-top:10px">帮助 | 隐私 | 条款</p>
            <p>
                <span>Copyright © 2020 - 至今 XXX 版权所有</span>
                <span style="display:inline-block; width:4px;height:6px"></span>
                <a target="_blank" href="https://beian.miit.gov.cn" style="color:#848585">ICP备案 浙ICP备XXXXXXXX号</a>
                <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=XXXXXXXXXXXXXX号">
                    <img src="../assets/login/gonganlogo.png" style="margin-left:6px" />
                    <p style="display:inline-block;color:#848585">浙公网安备 XXXXXXXXXXXXXX号</p>
                </a>
            </p>
        </div>
    </Row>
</div>
</template>

<script>
import Cookies from "js-cookie";
import Header from "@/views/main-components/header";
import LangSwitch from "@/views/main-components/lang-switch";
export default {
    components: {
        Header,
        LangSwitch
    },
    data() {
        return {
            username: ""
        };
    },
    methods: {},
    mounted() {
        this.username = this.$route.query.username;
    }
};
</script>

<style lang="less">
@import "./regist-result.less";
</style>
