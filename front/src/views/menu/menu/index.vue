<template>
<div class="search">
    <Card>
        <Row :gutter="16">
            <Col span="7">
            <Row :gutter="16">
                <Col span="23">
                <div class="tree-bar" style="overflow-y: scroll;padding-right: 50px;" :style="{maxHeight: maxHeight + 'px'}">
                    <Tree ref="tree" :data="data" show-checkbox :render="renderContent" @on-select-change="selectTree" @on-check-change="changeSelect" :check-strictly="!strict"></Tree>
                    <Spin size="large" fix v-if="loading"></Spin>
                </div>
                </Col>
            </Row>
            </Col>
            <Col span="15">
            <Row class="operation">
                <Col span="12">
                <Button @click="addMenu" type="primary" shape="circle" ghost icon="md-add">添加菜单</Button>
                <Button @click="deletePermissionFx" type="error" shape="circle" ghost icon="md-trash">删除菜单</Button>
                <Button @click="getPermissionList" type="success" shape="circle" ghost icon="md-trash">刷新菜单</Button>
                <i-switch v-model="strict" size="large" style="margin-left:5px">
                    <span slot="open">级联</span>
                    <span slot="close">单选</span>
                </i-switch>
                </Col>
                <Col span="12">
                <Input v-model="searchKey" suffix="ios-search" @on-change="search" placeholder="模拟搜索菜单" clearable />
                </Col>
            </Row>
            <Divider dashed />
            <Row :gutter="16" v-show="form.id">
                <Col span="24">
                <Alert show-icon type="success">
                    当前选择的菜单：
                    <span class="select-title">{{editTitle}}</span>
                    <Divider type="vertical" />
                    <a class="select-clear" @click="cancelEdit">取消选择</a>
                </Alert>
                </Col>
                <Divider dashed />
            </Row>
            <Form ref="form" :model="form" :label-width="110" :rules="formValidate">
                <Row :gutter="16">
                    <Col span="12">
                    <FormItem label="类型" prop="type">
                        <div v-show="form.type==-1">
                            <Icon type="ios-navigate-outline" size="16" style="margin-right:5px;"></Icon>
                            <span>一级菜单</span>
                        </div>
                        <div v-show="form.type==0">
                            <Icon type="ios-list-box-outline" size="16" style="margin-right:5px;"></Icon>
                            <span>普通菜单</span>
                        </div>
                        <div v-show="form.type==1">
                            <Icon type="md-radio-button-on" size="16" style="margin-right:5px;"></Icon>
                            <span>按钮级权限</span>
                        </div>
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==-1||form.type==0">
                    <FormItem label="名称" prop="title">
                        <Input v-model="form.title" style="width:100%" />
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==1">
                    <FormItem label="名称" prop="title">
                        <Input v-model="form.title" placeholder="操作按钮名称不得重复" style="width:100%" />
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==0">
                    <FormItem label="路径" prop="path">
                        <Input v-model="form.path" style="width:100%" />
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==1">
                    <FormItem label="请求路径" prop="path">
                        <Input v-model="form.path" placeholder="若无可填写'无'" style="width:100%" />
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==1">
                    <FormItem label="按钮权限类型" prop="buttonType">
                        <Select v-model="form.buttonType" placeholder="请选择或输入搜索" filterable clearable style="width:100%">
                            <Option v-for="(item, i) in dictPermissions" :key="i" :value="item.value">{{item.title}}</Option>
                        </Select>
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==-1">
                    <FormItem :label="form.type==-1? '英文名' : '路由英文名'" prop="name">
                        <Input v-model="form.name" placeholder="需唯一" style="width:100%" />
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==-1||form.type==0">
                    <FormItem label="图标" prop="icon">
                        <icon-choose v-model="form.icon"></icon-choose>
                    </FormItem>
                    </Col>
                    <Col span="12" v-show="form.type==0">
                    <FormItem label="前端代码" prop="component">
                        <Input v-model="form.component" style="width:100%" />
                    </FormItem>
                    </Col>
                    <Col span="12">
                    <FormItem label="排序值" prop="sortOrder">
                        <InputNumber :max="1000" :min="0" v-model="form.sortOrder" style="width:100%"></InputNumber>
                    </FormItem>
                    </Col>
                    <Col span="12">
                    <FormItem label="是否启用" prop="status">
                        <i-switch size="large" v-model="form.status" :true-value="0" :false-value="-1">
                            <span slot="open">启用</span>
                            <span slot="close">禁用</span>
                        </i-switch>
                    </FormItem>
                    </Col>
                    <Col span="12">
                    <Form-item>
                        <Button style="margin-right:5px" @click="submitEdit" ghost shape="circle" :loading="submitLoading" type="primary" icon="ios-create-outline">保存菜单</Button>
                        <Divider type="vertical" />
                        <Button icon="ios-undo" type="error" ghost shape="circle" @click="handleReset">重置菜单</Button>
                    </Form-item>
                    </Col>
                </Row>
            </Form>
            <Divider>有 {{form.title}} 权限的用户</Divider>
            <Row :gutter="16">
                <Table :loading="userByPermissionLoading" border stripe size="small" :columns="userByPermissionColumns" :data="userByPermissionList"></Table>
            </Row>
            </Col>
        </Row>
    </Card>

    <Modal draggable :title="modalTitle" v-model="menuModalVisible" :mask="false" :width="500" :styles="{top: '30px'}">
        <Form ref="formAdd" :model="formAdd" :label-width="100" :rules="formValidate">
            <div v-if="showParent">
                <FormItem label="上级节点：">{{parentTitle}}</FormItem>
            </div>
            <FormItem label="类型" prop="type">
                <div v-show="formAdd.type==-1">
                    <Icon type="ios-navigate-outline" size="16" style="margin-right:5px;"></Icon>
                    <span>顶部菜单</span>
                </div>
                <div v-show="formAdd.type==0">
                    <Icon type="ios-list-box-outline" size="16" style="margin-right:5px;"></Icon>
                    <span>页面菜单</span>
                </div>
                <div v-show="formAdd.type==1">
                    <Icon type="md-radio-button-on" size="16" style="margin-right:5px;"></Icon>
                    <span>操作按钮</span>
                </div>
            </FormItem>
            <FormItem label="名称" prop="title" v-if="formAdd.type==-1||formAdd.type==0">
                <Input v-model="formAdd.title" />
            </FormItem>
            <FormItem label="名称" prop="title" v-if="formAdd.type==1" class="block-tool">
                <Tooltip placement="right" content="操作按钮名称不得重复">
                    <Input v-model="formAdd.title" />
                </Tooltip>
            </FormItem>
            <FormItem label="路径" prop="path" v-if="formAdd.type==0">
                <Input v-model="formAdd.path" />
            </FormItem>
            <FormItem label="请求路径" prop="path" v-if="formAdd.type==1" class="block-tool">
                <Tooltip placement="right" :max-width="230" transfer content="填写后端请求URL，后端将作权限拦截，若无可填写'无'或其他">
                    <Input v-model="formAdd.path" />
                </Tooltip>
            </FormItem>
            <FormItem label="按钮权限类型" prop="buttonType" v-if="formAdd.type==1">
                <Select v-model="formAdd.buttonType" placeholder="请选择或输入搜索" filterable clearable>
                    <Option v-for="(item, i) in dictPermissions" :key="i" :value="item.value">{{item.title}}</Option>
                </Select>
            </FormItem>
            <FormItem label="英文名" prop="name" v-if="formAdd.type==-1" class="block-tool">
                <Tooltip placement="right" content="需唯一">
                    <Input v-model="formAdd.name" />
                </Tooltip>
            </FormItem>
            <FormItem label="路由英文名" prop="name" v-if="formAdd.type==0" class="block-tool">
                <Tooltip placement="right" content="需唯一">
                    <Input v-model="formAdd.name" />
                </Tooltip>
            </FormItem>
            <FormItem label="图标" prop="icon" v-if="formAdd.type==-1||formAdd.type==0">
                <icon-choose v-model="formAdd.icon"></icon-choose>
            </FormItem>
            <FormItem label="前端代码" prop="component" v-if="formAdd.type==0">
                <Input v-model="formAdd.component" />
            </FormItem>
            <FormItem label="排序值" prop="sortOrder">
                <InputNumber :max="1000" :min="0" v-model="formAdd.sortOrder"></InputNumber>
            </FormItem>
            <FormItem label="是否启用" prop="status">
                <i-switch size="large" v-model="formAdd.status" :true-value="0" :false-value="-1">
                    <span slot="open">启用</span>
                    <span slot="close">禁用</span>
                </i-switch>
            </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="menuModalVisible = false">取消</Button>
            <Button type="primary" :loading="submitLoading" @click="submitAdd">提交</Button>
        </div>
    </Modal>
</div>
</template>

<script>
import {
    addPermission,
    editPermission,
    deletePermission,
    searchPermission,
    getDictDataByType,
    getUserByPermission
} from "./api.js";
import IconChoose from "@/views/template/icon-choose";
import util from "@/libs/util.js";
export default {
    name: "menu",
    components: {
        IconChoose
    },
    data() {
        return {
            userByPermissionLoading: false,
            loading: true,
            strict: true,
            maxHeight: 500,
            menuModalVisible: false,
            iconModalVisible: false,
            selectList: [],
            selectCount: 0,
            showParent: false,
            searchKey: "",
            parentTitle: "",
            editTitle: "",
            modalTitle: "",
            form: {
                id: "",
                title: "",
                name: "",
                icon: "",
                path: "",
                component: "",
                parentId: "",
                buttonType: "",
                type: 0,
                sortOrder: 0,
                level: 0,
                status: 0,
                url: ""
            },
            formAdd: {
                buttonType: ""
            },
            formValidate: {
                title: [{
                    required: true,
                    message: "菜单名称不能为空",
                    trigger: "blur"
                }],
                name: [{
                    required: true,
                    message: "路由英文名不能为空",
                    trigger: "blur"
                }],
                icon: [{
                    required: true,
                    message: "图标不能为空",
                    trigger: "click"
                }],
                path: [{
                    required: true,
                    message: "路径不能为空",
                    trigger: "blur"
                }],
                component: [{
                    required: true,
                    message: "前端代码不能为空",
                    trigger: "blur"
                }],
                sortOrder: [{
                    required: true,
                    type: "number",
                    message: "排序值不能为空",
                    trigger: "blur"
                }]
            },
            submitLoading: false,
            data: [],
            dictPermissions: [],
            userByPermissionList: [],
            userByPermissionColumns: [{
                title: "用户名",
                key: "userName",
                minWidth: 120,
                tooltip: true,
                sortable: false,
            }, {
                title: "对应角色",
                key: "roleStr",
                minWidth: 200,
                tooltip: true,
                sortable: false,
            }, {
                title: "登陆账号",
                key: "code",
                minWidth: 120,
                tooltip: true,
                sortable: false,
            }, {
                title: "用户手机",
                key: "mobile",
                minWidth: 120,
                tooltip: true,
                sortable: false,
            }, {
                title: "用户标识",
                key: "userId",
                minWidth: 120,
                tooltip: true,
                sortable: false,
            }]
        };
    },
    methods: {
        init() {
            this.getPermissionList();
            this.getDictPermissions();
        },
        getDictPermissions() {
            getDictDataByType("permission_type").then(res => {
                if (res.success) {
                    this.dictPermissions = res.result;
                }
            });
        },
        renderContent(h, {
            root,
            node,
            data
        }) {
            let icon = "";
            if (data.level == 0) {
                icon = "ios-navigate";
            } else if (data.level == 1) {
                icon = "md-list-box";
            } else if (data.level == 2) {
                icon = "md-list";
            } else if (data.level == 3) {
                icon = "md-radio-button-on";
            } else {
                icon = "md-radio-button-off";
            }
            return h("span", [
                h("span", [
                    h("Icon", {
                        props: {
                            type: icon,
                            size: "16"
                        },
                        style: {
                            "margin-right": "8px",
                            "margin-bottom": "3px"
                        }
                    }),
                    h("span", data.title)
                ])
            ]);
        },
        getPermissionList() {
            this.loading = true;
            this.getRequest("/permission/getAllList").then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result;
                }
            });
        },
        search() {
            if (this.searchKey) {
                this.loading = true;
                searchPermission({
                    title: this.searchKey
                }).then(res => {
                    this.loading = false;
                    if (res.success) {
                        this.data = res.result;
                    }
                });
            } else {
                this.getPermissionList();
            }
        },
        selectTree(v) {
            if (v.length > 0) {
                // 转换null为""
                for (let attr in v[0]) {
                    if (v[0][attr] == null) {
                        v[0][attr] = "";
                    }
                }
                let str = JSON.stringify(v[0]);
                let menu = JSON.parse(str);
                this.form = menu;
                this.editTitle = menu.title;
                this.getUserByPermission();
            } else {
                this.cancelEdit();
                this.userByPermissionList = [];
            }
        },
        getUserByPermission() {
            var that = this;
            that.userByPermissionLoading = true;
            getUserByPermission({
                permissionId: that.form.id
            }).then(res => {
                that.userByPermissionLoading = false;
                if (res.success) {
                    that.userByPermissionList = res.result;
                }
            })
        },
        cancelEdit() {
            let data = this.$refs.tree.getSelectedNodes()[0];
            if (data) {
                data.selected = false;
            }
            this.$refs.form.resetFields();
            this.form.id = "";
            this.editTitle = "";
        },
        handleReset() {
            let type = this.form.type;
            this.$refs.form.resetFields();
            this.form.icon = "";
            this.form.component = "";
            this.form.type = type;
        },
        submitEdit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    if (!this.form.id) {
                        this.$Message.warning("请先点击选择要修改的菜单节点");
                        return;
                    }
                    this.submitLoading = true;
                    if (this.form.sortOrder == null) {
                        this.form.sortOrder = "";
                    }
                    if (this.form.buttonType == null) {
                        this.form.buttonType = "";
                    }
                    if (this.form.type == 1) {
                        this.form.name = "";
                        this.form.icon = "";
                        this.form.component = "";
                    }
                    delete this.form.updateTime;
                    delete this.form.children;
                    editPermission(this.form).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("编辑成功");
                            // 标记重新获取菜单数据
                            this.$store.commit("setAdded", false);
                            util.initRouter(this);
                            this.init();
                            this.menuModalVisible = false;
                        }
                    });
                }
            });
        },
        submitAdd() {
            this.$refs.formAdd.validate(valid => {
                if (valid) {
                    this.submitLoading = true;
                    if (this.formAdd.type == 1) {
                        this.formAdd.name = "";
                        this.formAdd.icon = "";
                        this.formAdd.component = "";
                    }
                    addPermission(this.formAdd).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("添加成功");
                            this.$store.commit("setAdded", false);
                            util.initRouter(this);
                            this.init();
                            this.menuModalVisible = false;
                        }
                    });
                }
            });
        },
        addMenu() {
            if (!this.form.id) {
                this.modalTitle = "添加顶部菜单(可拖动)";
                this.showParent = false;
                this.formAdd = {
                    type: -1,
                    level: 0,
                    sortOrder: 0,
                    status: 0
                };
                this.menuModalVisible = true;
                return;
            }
            this.parentTitle = this.form.title;
            this.modalTitle = "添加子节点";
            this.showParent = true;
            let type = 0;
            if (this.form.level == 1) {
                type = 0;
            } else if (this.form.level == 2) {
                type = 1;
            } else if (this.form.level == 3) {
                this.$Modal.warning({
                    title: "抱歉，不能添加啦",
                    content: "仅支持2级菜单目录"
                });
                return;
            } else {
                type = 0;
            }
            let component = "";
            this.formAdd = {
                icon: "",
                type: type,
                parentId: this.form.id,
                level: Number(this.form.level) + 1,
                sortOrder: 0,
                buttonType: "",
                status: 0
            };
            if (this.form.level == 0) {
                this.formAdd.path = "/";
                this.formAdd.component = "Main";
            }
            this.menuModalVisible = true;
        },
        changeSelect(v) {
            this.selectCount = v.length;
            this.selectList = v;
        },
        deletePermissionFx() {
            if (this.selectCount <= 0) {
                this.$Message.warning("请选择要删除的菜单");
                return;
            }
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除所选的 " + this.selectCount + " 条数据?",
                loading: true,
                onOk: () => {
                    let ids = "";
                    this.selectList.forEach(function (e) {
                        ids += e.id + ",";
                    });
                    ids = ids.substring(0, ids.length - 1);
                    deletePermission({
                        ids: ids
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("删除成功");
                            this.$store.commit("setAdded", false);
                            util.initRouter(this);
                            this.selectList = [];
                            this.selectCount = 0;
                            this.cancelEdit();
                            this.init();
                        }
                    });
                }
            });
        }
    },
    mounted() {
        let height = document.documentElement.clientHeight;
        this.maxHeight = Number(height - 260);
        this.init();
    }
};
</script>
