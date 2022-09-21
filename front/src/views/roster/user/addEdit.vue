<template>
<div class="user-edit">
    <Modal :title="title" v-model="visible" width="90" draggable :mask-closable="type=='0'" ok-text="确认提交" @on-ok="submit">

        <Row :gutter="16" justify="center">
            <div class="info-title"><span v-show="type!='2'">登陆账号{{form.username}}（唯一标识{{form.id}}） </span>
                <Icon v-show="form.status==-1" type="md-lock" size="18" style="margin-left:10px;cursor:pointer" />
            </div>
        </Row>
        <Form ref="form" :model="form" :rules="formValidate" label-position="top">
            <Row :gutter="16" v-if="type=='2'">
                <Col span="12">
                <FormItem label="登录账号" prop="username" style="width:100%">
                    <Input v-model="form.username" autocomplete="off" :maxlength="16" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="密码" prop="password" style="width:100%">
                    <SetPassword v-model="form.password" @on-change="changePass" />
                </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="用户名" prop="nickname" style="width:100%">
                    <Input v-model="form.nickname" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="邮箱" prop="email" style="width:100%">
                    <Input v-model="form.email" />
                </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="手机号" prop="mobile" style="width:100%">
                    <Input v-model="form.mobile" />
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="性别" style="width:100%">
                    <dict dict="sex" v-model="form.sex" transfer clearable placeholder="选择性别" />
                </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="所属部门" style="width:100%">
                    <department-tree-choose @on-change="handleSelectDepTree" ref="depTree"></department-tree-choose>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="用户类型" style="width:100%">
                    <Select v-model="form.type" placeholder="请选择用户类型">
                        <Option :value="0">普通用户</Option>
                        <Option :value="1">管理员</Option>
                    </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="头像" style="width:100%">
                    <upload-pic-input v-model="form.avatar"></upload-pic-input>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="角色" prop="roleIds" style="width:100%">
                    <Select v-model="form.roleIds" multiple>
                        <Option v-for="item in roleList" :value="item.id" :key="item.id" :label="item.name">
                            <span style="margin-right:10px;">{{ item.name }}</span>
                            <span style="color:#ccc;">{{ item.description }}</span>
                        </Option>
                    </Select>
                </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="区县" prop="addressArray" style="width:100%">
                    <al-cascader v-model="form.addressArray" @on-change="changeAddress" data-type="code" level="2" />
                </FormItem>
                </Col>
            </Row>
        </Form>
    </Modal>
</div>
</template>

<script>
import {
    getAllRoleList,
    addUser,
    editUser
} from "./api.js";
import {
    validateUsername,
    validateMobile,
    validatePassword
} from "@/libs/validate";
import departmentTreeChoose from "@/views/template/department-tree-choose";
import uploadPicInput from "@/views/template/upload-pic-input";
import SetPassword from "@/views/template/set-password";
import dict from "@/views/template/dict";
export default {
    name: "user",
    components: {
        departmentTreeChoose,
        uploadPicInput,
        SetPassword,
        dict
    },
    props: {
        value: {
            type: Boolean,
            default: false
        },
        data: {
            type: Object
        },
        type: {
            type: String,
            default: "0"
        }
    },
    data() {
        return {
            visible: this.value,
            title: "",
            passColor: "",
            submitLoading: false,
            form: {
                addressArray: []
            },
            formValidate: {
                username: [{
                        required: true,
                        message: "请输入登录账号",
                        trigger: "blur"
                    },
                    {
                        validator: validateUsername,
                        trigger: "blur"
                    }
                ],
                nickname: [{
                    required: true,
                    message: "请输入用户名",
                    trigger: "blur"
                }],
                mobile: [{
                        required: true,
                        message: "请输入手机号",
                        trigger: "blur"
                    },
                    {
                        validator: validateMobile,
                        trigger: "blur"
                    }
                ],
                password: [{
                        required: true,
                        message: "请输入密码",
                        trigger: "blur"
                    },
                    {
                        validator: validatePassword,
                        trigger: "blur"
                    }
                ],
                email: [{
                        required: true,
                        message: "请输入邮箱地址"
                    },
                    {
                        type: "email",
                        message: "邮箱格式不正确"
                    }
                ]
            },
            roleList: []
        };
    },
    methods: {
        init() {
            this.getRoleList();
        },
        getRoleList() {
            getAllRoleList().then(res => {
                if (res.success) {
                    this.roleList = res.result;
                }
            });
        },
        handleSelectDepTree(v) {
            this.form.departmentId = v;
        },
        changeAddress(v) {
            this.form.address = JSON.stringify(this.form.addressArray);
        },
        changePass(v, grade, strength) {
            this.form.passStrength = strength;
        },
        submit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    if (this.type == "1") {
                        this.submitLoading = true;
                        editUser(this.form).then(res => {
                            this.submitLoading = false;
                            if (res.success) {
                                this.$Message.success("操作成功");
                                this.$emit("on-submit", true);
                                this.visible = false;
                            }
                        });
                    } else {
                        this.submitLoading = true;
                        addUser(this.form).then(res => {
                            this.submitLoading = false;
                            if (res.success) {
                                this.$Message.success("操作成功");
                                this.$emit("on-submit", true);
                                this.visible = false;
                            }
                        });
                    }
                }
            });
        },
        setCurrentValue(value) {
            if (value === this.visible) {
                return;
            }
            if (this.type == "1") {
                this.title = "编辑";
            } else if (this.type == "2") {
                this.title = "添加";
            } else {
                this.title = "详情";
            }
            this.$refs.form.resetFields();
            if (this.type == "0" || this.type == "1") {
                // 回显数据
                let data = this.data;
                // 地址
                if (data.address) {
                    data.addressArray = JSON.parse(data.address);
                } else {
                    data.addressArray = [];
                }
                // 部门
                this.$refs.depTree.setData(data.departmentId, data.departmentTitle);
                // 角色
                let selectRolesId = [];
                data.roles.forEach(function (e) {
                    selectRolesId.push(e.id);
                });
                data.roleIds = selectRolesId;
                delete data.roles;
                delete data.permissions;
                // 密码强度
                if (data.passStrength == "弱") {
                    this.passColor = "#ed3f14";
                } else if (data.passStrength == "中") {
                    this.passColor = "#faad14";
                } else if (data.passStrength == "强") {
                    this.passColor = "#52c41a";
                }
                // 回显
                this.form = data;
            } else {
                // 添加
                this.$refs.depTree.setData("", "");
                this.form = {
                    type: 0,
                    sex: "",
                    addressArray: []
                };
            }
            this.visible = value;
        }
    },
    watch: {
        value(val) {
            this.setCurrentValue(val);
        },
        visible(value) {
            this.$emit("input", value);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">

</style>
