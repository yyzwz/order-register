<template>
<div class="login">
    <Row justify="center" align="middle" @keydown.enter.native="submitRegist" style="height:100%">
        <!-- 头部 -->
        <div class="loginUp">
            <div class="loginLeft">
                <img src="../assets/login/logo.png" alt="" srcset="">
                <span class="line"></span>
                <span class="title">基于Vue的医院门诊预约挂号管理系统</span>
            </div>
        </div>
        <div class="loginMiddle">
            <div class="login-background">
                <div class="loginBg"></div>
                <div class="loginRight">
                    <Row v-if="!socialLogining" class="loginRow">
                        <Form ref="usernameLoginForm" :model="form" :rules="rules" class="form" style="width:100%">
                            <FormItem prop="username" class="loginInput">
                                <Row>
                                    <Input v-model="form.username" size="large" clearable placeholder="登陆账号" autocomplete="off">
                                    <Icon class="iconfont icon-yonghu" slot="prefix" style="line-height:50px" />
                                    </Input>
                                </Row>
                            </FormItem>
                            <FormItem prop="nickname" class="loginInput">
                                <Row>
                                    <Input v-model="form.nickname" size="large" clearable placeholder="昵称" autocomplete="off">
                                    <Icon class="iconfont icon-yonghu" slot="prefix" style="line-height:50px" />
                                    </Input>
                                </Row>
                            </FormItem>
                            <FormItem prop="password">
                                <Input style="height:50px;line-height:50px" type="password" v-model="form.password" size="large" placeholder="请输入密码" password autocomplete="off">
                                <Icon class="iconfont icon-mima1" slot="prefix" style="line-height:50px" />
                                </Input>
                            </FormItem>
                            <FormItem prop="code">
                                <Row type="flex" justify="space-between" style="align-items: center;overflow: hidden;">
                                    <Col span="16">
                                    <Input v-model="form.code" size="large" clearable placeholder="请输入图片验证码" style="width:90%" :maxlength="10" class="input-verify" />
                                    </Col>
                                    <Col span="8">
                                    <div class="code-image" style="position:relative;font-size:12px;width:100%">
                                        <Spin v-if="loadingCaptcha" fix></Spin>
                                        <img :src="captchaImg" @click="getCaptchaImg" alt="加载验证码失败" style="width:110px;cursor:pointer;display:block" />
                                    </div>
                                    </Col>
                                </Row>
                            </FormItem>
                        </Form>
                        <Row>
                            <Button class="login-btn" type="primary" size="large" :loading="loading" @click="submitRegist" long>
                                <span v-if="!loading" style="letter-spacing:20px; font-weight:bold">注册</span>
                                <span v-else>正在注册</span>
                            </Button>
                        </Row>
                        <Row>
                            <router-link to="/login">
                                <Button class="login-btn" type="primary" size="large" long>返回登录</Button>
                            </router-link>
                        </Row>
                    </Row>
                    <p class="loginBottom">
                        ART YOUR DREAM
                    </p>
                    <div v-if="socialLogining">
                        <RectLoading />
                    </div>
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
import {
    regist,
    drawCodeImage,
    initCaptcha
} from "@/api/index";
import {
    validateUsername,
    validateMobile,
    validatePassword
} from "@/libs/validate";
import Header from "@/views/main-components/header";
import LangSwitch from "@/views/main-components/lang-switch";
import CountDownButton from "@/views/my-components/zwz/count-down-button";
import SetPassword from "@/views/my-components/zwz/set-password";
var vaptchaObject;
export default {
    components: {
        CountDownButton,
        LangSwitch,
        SetPassword,
        Header
    },
    data() {
        return {
            captchaId: "",
            captchaImg: "",
            getSms: "获取验证码",
            error: false,
            loading: false,
            sending: false,
            select: "86",
            errorCode: "",
            form: {
                username: "",
                password: "",
                mobile: "",
                code: "",
                captchaId: ""
            },
            rules: {
                username: [{
                        required: true,
                        message: "请输入手机号",
                        trigger: "blur"
                    },
                    {
                        validator: validateMobile,
                        trigger: "blur"
                    }
                ],
                nickname: [{
                    required: true,
                    message: "请输入用户名（昵称）",
                    trigger: "blur"
                }],
                password: [{
                        required: true,
                        message: "请输入密码",
                        trigger: "blur"
                    },
                    {
                        validator: validatePassword,
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    methods: {
        getCaptchaImg() {
            this.loadingCaptcha = true;
            initCaptcha().then(res => {
                this.loadingCaptcha = false;
                if (res.success) {
                    this.captchaId = res.result;
                    this.captchaImg = drawCodeImage + this.captchaId;
                }
            });
        },
        submitRegist() {
            this.form.captchaId = this.captchaId;
            this.form.mobile = this.form.username;
            this.$refs.usernameLoginForm.validate(valid => {
                if (valid) {
                    if (!this.form.code) {
                        this.errorCode = "验证码不能为空";
                        return;
                    } else {
                        this.errorCode = "";
                    }
                    this.loading = true;
                    regist(this.form).then(res => {
                        this.loading = false;
                        if (res.success) {
                            let query = {
                                username: this.form.username
                            };
                            this.$router.push({
                                name: "regist-result",
                                query: query
                            });
                        } else {
                            vaptchaObject.reset();
                        }
                    });
                }
            });
        }
    },
    mounted() {
        this.getCaptchaImg();
    }
};
</script>

<style lang="less">
@import "./regist.less";
</style>
