<style lang="less">
@import "./settingManage.less";
</style>
<template>
<div>
    <Card>
        <Layout>
            <Sider hide-trigger style="background: #fff;max-width: 220px;flex: 0 0 220px;">
                <Menu :active-name="currName" theme="light" width="auto" @on-select="currName=$event">
                    <MenuItem name="oss">文件存储配置</MenuItem>
                </Menu>
            </Sider>
            <Content :style="{padding: '8px 40px', minHeight: '550px', background: '#fff'}">
                <div v-show="currName=='oss'">
                    <div class="setting-title">文件对象存储配置</div>
                    <div style="display:flex;position:relative">
                        <Form ref="ossForm" :model="oss" :label-width="110" label-position="left" :rules="ossValidate">
                            <FormItem label="服务提供商" prop="serviceName">
                                <Select v-model="oss.serviceName" @on-change="changeOss" placeholder="请选择" style="width: 230px">
                                    <Option value="LOCAL_OSS">本地服务器</Option>
                                </Select>
                            </FormItem>
                            <FormItem label="存储磁盘路径" prop="filePath" :error="errorMsg2">
                                <Input type="text" v-model="oss.filePath" placeholder="例如D:/upload" style="width: 380px" :disabled="changeLoading" />
                            </FormItem>
                            <FormItem label="文件预览接口" prop="endpoint" :error="errorMsg">
                                <Input type="text" v-model="oss.endpoint" placeholder="请输入文件预览接口" style="width: 380px">
                                <Select v-model="oss.http" slot="prepend" style="width: 80px" prop="http" :disabled="changeLoading">
                                    <Option value="http://">http://</Option>
                                    <Option value="https://">https://</Option>
                                </Select>
                                </Input>
                            </FormItem>
                            <FormItem>
                                <Button type="primary" style="width: 100px;" :disabled="changeLoading" :loading="saveLoading" @click="saveEditOss">保存并启用</Button>
                            </FormItem>
                        </Form>
                        <Spin fix v-if="loading"></Spin>
                    </div>
                </div>
            </Content>
        </Layout>
    </Card>
</div>
</template>

<script>
import {
    checkOssSet,
    getOssSet,
    editOssSet
} from "@/api/index";
export default {
    name: "setting-manage",
    data() {
        return {
            currName: "oss",
            loading: false, // 表单加载状态
            changeLoading: false,
            saveLoading: false,
            oss: {
                serviceName: "",
                accessKey: "",
                secretKey: "",
                endpoint: "",
                bucket: ""
            },
            changedOssSK: false, // 是否修改oss的secrectKey
            notice: {
                duration: 10
            },
            ossValidate: {
                // 表单验证规则
                serviceName: [{
                    required: true,
                    message: "请选择",
                    trigger: "blur"
                }],
                accessKey: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                secretKey: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                endpoint: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                bucket: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }]
            },
        };
    },
    methods: {
        init() {
            this.initOssSet();
        },
        initOssSet() {
            this.loading = true;
            checkOssSet().then(res => {
                if (res.success && res.result) {
                    let oss = res.result;
                    getOssSet(oss).then(res => {
                        this.loading = false;
                        if (res.result) {
                            // 转换null为""
                            for (let attr in res.result) {
                                if (res.result[attr] == null) {
                                    res.result[attr] = "";
                                }
                            }
                            this.oss = res.result;
                        } else {
                            this.changedOssSK = true;
                        }
                    });
                } else {
                    this.loading = false;
                    this.changedOssSK = true;
                }
            });
        },
        changeOss(v) {
            this.oss.http = "http://";
            this.oss.endpoint = "127.0.0.1:9999/zwz/file/view";
        },
        saveEditOss() {
            if (!this.oss.http) {
                this.errorMsg = "请选择http协议";
                return;
            } else {
                this.errorMsg = "";
            }
            if (!this.oss.endpoint) {
                this.errorMsg = "请输入文件预览接口";
                return;
            } else {
                this.errorMsg = "";
            }
            if (!this.oss.filePath) {
                this.errorMsg2 = "请输入存储磁盘路径";
                return;
            } else {
                this.errorMsg2 = "";
            }
            editOssSet(this.oss).then(res => {
                this.saveLoading = false;
                if (res.success) {
                    this.$Message.success("保存成功");
                }
            });
        },
    },
    mounted() {
        let name = this.$route.query.name;
        if (name) {
            this.currName = name;
        }
        this.init();
    }
};
</script>
