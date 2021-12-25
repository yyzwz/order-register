<template>
  <div class="relate">
    <Row
      type="flex"
      justify="center"
      align="middle"
      @keydown.enter.native="checkVaptcha"
      style="height:100%"
    >
      <Col class="layout">
        <div>
          <Header />
          <Row v-if="!relateLoading">
            <Tabs value="1">
              <TabPane label="绑定账号" name="1" icon="md-person-add">
                <Form ref="relateLoginForm" :model="form" :rules="rules" class="form">
                  <FormItem prop="username">
                    <Input
                      v-model="form.username"
                      prefix="ios-contact"
                      size="large"
                      clearable
                      placeholder="请输入用户名"
                      autocomplete="off"
                    />
                  </FormItem>
                  <FormItem prop="password">
                    <Input
                      type="password"
                      v-model="form.password"
                      prefix="ios-lock"
                      size="large"
                      clearable
                      placeholder="请输入密码"
                      autocomplete="off"
                    />
                  </FormItem>
                </Form>
              </TabPane>
            </Tabs>
            <Row>
              <Button type="primary" size="large" :loading="loading" @click="checkVaptcha" long>
                <span v-if="!loading">立即绑定</span>
                <span v-else>绑定中...</span>
              </Button>
            </Row>
            <Row type="flex" justify="space-between" class="other-thing">
              <a @click="$router.go(-1)" class="back">
                <Icon type="md-arrow-round-back" style="margin-right:3px;" />返回
              </a>
              <router-link to="/regist">
                <a class="back">还没有账号？立即注册</a>
              </router-link>
            </Row>
          </Row>
          <div v-if="relateLoading">
            <RectLoading />
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import { vaptchaID, relate, userInfo, getJWT, getOtherSet } from "@/api/index";
import util from "@/libs/util.js";
import Header from "@/views/main-components/header";
import RectLoading from "@/views/my-components/zwz/rect-loading";
var vaptchaObject;
export default {
  components: {
    Header,
    RectLoading
  },
  data() {
    return {
      relateLoading: true,
      loadingVaptcha: true,
      loading: false,
      verified: false,
      form: {
        isLogin: false,
        username: "",
        password: "",
        socialType: null,
        id: "",
        token: ""
      },
      rules: {
        username: [
          {
            required: true,
            message: "账号不能为空",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submitRelate() {
      this.$refs.relateLoginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.form.isLogin = false;
          relate(this.form).then(res => {
            if (res.success) {
              // 获取JWT
              getJWT({ JWTKey: res.result }).then(res => {
                if (res.success) {
                  this.$Message.success("绑定成功");
                  let accessToken = res.result;
                  this.setStore("accessToken", accessToken);
                  getOtherSet().then(res => {
                    if (res.result) {
                      let domain = res.result.ssoDomain;
                      Cookies.set("accessToken", accessToken, {
                        domain: domain,
                        expires: 7
                      });
                    }
                  });
                  // 获取用户信息
                  userInfo().then(res => {
                    if (res.success) {
                      // 避免超过大小限制
                      delete res.result.permissions;
                      let roles = [];
                      res.result.roles.forEach(e => {
                        roles.push(e.name);
                      });
                      this.setStore("roles", roles);
                      // 保存7天
                      Cookies.set("userInfo", JSON.stringify(res.result), {
                        expires: 7
                      });
                      this.setStore("userInfo", res.result);
                      this.$store.commit("setAvatarPath", res.result.avatar);
                      // 加载菜单
                      util.initRouter(this);
                      this.$router.push({
                        name: "home_index"
                      });
                    } else {
                      this.loading = false;
                    }
                  });
                } else {
                  this.loading = false;
                }
              });
            } else {
              this.loading = false;
              vaptchaObject.reset();
            }
          });
        }
      });
    },
    relateDirect() {
      // 已登录 直接绑定
      this.form.isLogin = true;
      relate(this.form).then(res => {
        if (res.success) {
          this.$Message.success("绑定成功");
        }
        // 返回个人中心
        this.$router.push({
          name: "ownspace_index",
          query: {
            type: "social"
          }
        });
      });
    },
    initVaptcha() {
      let that = this;
      vaptcha({
        //配置参数
        vid: vaptchaID, // 验证单元id
        type: "invisible", // 展现类型 隐藏式
        offline_server: "你的离线验证接口地址 可选但此处不能为空"
      }).then(function(vaptchaObj) {
        vaptchaObject = vaptchaObj;
        let userInfo = Cookies.get("userInfo");
        if (userInfo) {
          vaptchaObject.validate(); // 开始验证
        }
        vaptchaObj.listen("pass", function() {
          that.form.token = vaptchaObj.getToken();
          // 验证成功 发送绑定请求
          if (userInfo) {
            // 直接绑定
            that.relateDirect();
          } else {
            // 未登录填写表单绑定
            that.submitRelate();
          }
        });
      });
    },
    checkVaptcha() {
      this.$refs.relateLoginForm.validate(valid => {
        if (valid) {
          vaptchaObject.validate(); // 若没验证验证码 开始验证
        }
      });
    }
  },
  mounted() {
    let q = this.$route.query;
    this.form.socialType = q.socialType;
    this.form.id = q.id;
    // 加载验证码
    this.initVaptcha();
    let userInfo = Cookies.get("userInfo");
    if (!userInfo) {
      this.relateLoading = false;
    }
  }
};
</script>

<style lang="less">
@import "./relate.less";
</style>
