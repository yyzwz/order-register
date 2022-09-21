<template>
<div class="search">
    <Card>
        <Row class="operation">
            <Button @click="add" type="primary" icon="md-add" ghost shape="circle" size="small">添加</Button>
            <Button @click="delAll" icon="md-trash" type="error" ghost shape="circle" size="small">删除</Button>
            <Button @click="getParentList" icon="md-refresh" type="info" ghost shape="circle" size="small">刷新</Button>
            <Button @click="excelData" type="success" icon="md-paper-plane" ghost shape="circle" size="small">导出用户</Button>
            <i-switch v-model="strict" size="large" style="margin-left:5px">
                <span slot="open">级联</span>
                <span slot="close">单选</span>
            </i-switch>
        </Row>
        <Row :gutter="16">
            <Col span="8">
            <Alert show-icon type="success" v-show="form.id">
                当前选择
                <span class="select-title">{{editTitle}}</span>
                <a class="select-clear" @click="cancelEdit"> 取消选择</a>
            </Alert>
            <Input v-model="searchKey" suffix="ios-search" @on-change="search" placeholder="输入部门名搜索" clearable />
            <div class="tree-bar" :style="{maxHeight: maxHeight}">
                <Tree ref="tree" :data="data" :load-data="loadData" show-checkbox @on-check-change="changeSelect" @on-select-change="selectTree" :check-strictly="!strict"></Tree>
                <Spin size="large" fix v-if="loading"></Spin>
            </div>
            </Col>
            <Col span="16">
            <Form ref="form" :model="form" :label-width="100" :rules="formValidate">
                <Row :gutter="16">
                    <Col span="12">
                    <FormItem label="上级部门" prop="parentTitle">
                        <div style="display:flex;">
                            <Input v-model="form.parentTitle" readonly style="margin-right:10px;" />
                            <Poptip transfer trigger="click" placement="right-start" title="选择上级部门" width="250">
                                <Button icon="md-list">选择部门</Button>
                                <div slot="content" style="position:relative;min-height:5vh">
                                    <Tree :data="dataEdit" :load-data="loadData" @on-select-change="selectTreeEdit"></Tree>
                                    <Spin size="large" fix v-if="loadingEdit"></Spin>
                                </div>
                            </Poptip>
                        </div>
                    </FormItem>
                    </Col>
                    <Col span="12">
                    <FormItem label="部门名称" prop="title">
                        <Input v-model="form.title" />
                    </FormItem>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="12">
                    <FormItem label="部门领导" prop="mainHeader">
                        <Select :loading="userLoading" not-found-text="该部门暂无用户数据" v-model="form.mainHeader" multiple filterable placeholder="选择部门领导">
                            <Option v-for="item in users" :value="item.id" :key="item.id" :label="item.nickname">
                                <span style="margin-right:10px;">{{ item.nickname }}</span>
                                <span style="color:#ccc;">{{ item.username }}</span>
                            </Option>
                        </Select>
                    </FormItem>
                    </Col>
                    <Col span="12">
                    <FormItem label="部门组长" prop="viceHeader">
                        <Select :loading="userLoading" not-found-text="该部门暂无用户数据" v-model="form.viceHeader" multiple filterable placeholder="选择部门组长">
                            <Option v-for="item in users" :value="item.id" :key="item.id" :label="item.nickname">
                                <span style="margin-right:10px;">{{ item.nickname }}</span>
                                <span style="color:#ccc;">{{ item.username }}</span>
                            </Option>
                        </Select>
                    </FormItem>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                    <FormItem label="排序值" prop="sortOrder">
                        <Tooltip trigger="hover" placement="right" content="值越小越靠前，支持小数">
                            <InputNumber :max="1000" :min="0" v-model="form.sortOrder"></InputNumber>
                        </Tooltip>
                    </FormItem>
                    </Col>
                    <Col span="8">
                    <FormItem label="是否启用" prop="status">
                        <i-switch size="large" v-model="form.status" :true-value="0" :false-value="-1">
                            <span slot="open">启用</span>
                            <span slot="close">禁用</span>
                        </i-switch>
                    </FormItem>
                    </Col>
                    <Col span="8">
                    <Form-item class="br">
                        <Button @click="submitEdit" :loading="submitLoading" type="success" ghost shape="circle" icon="ios-create-outline" size="small">保存</Button>
                        <Button @click="handleReset" type="warning" ghost shape="circle" size="small" icon="md-refresh">重置</Button>
                    </Form-item>
                    </Col>
                </Row>
            </Form>
            </Col>
        </Row>
    </Card>
    <Divider dashed>部门下用户列表</Divider>
    <Card>
        <Row>
            <Table :loading="userLoading" border :columns="userColumns" :data="userData" ref="table" sortable="custom" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-click="rowClick" :row-class-name="rowClassName"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="userTotal" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[10,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>

    <Modal :title="modalTitle" v-model="addOrEditDepartmentModal" :mask-closable="false" :width="500" on-text="提交" @on-ok="submitAdd">
        <Form ref="formAdd" :model="formAdd" :label-width="85" :rules="formValidate">

            <Row :gutter="16" v-show="showParent">
                <Col span="24">
                <FormItem label="上级部门：">{{form.title}}</FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="24">
                <FormItem label="部门名称" prop="title">
                    <Input v-model="formAdd.title" />
                </FormItem>
                </Col>
            </Row>
            <Row :gutter="16">
                <Col span="12">
                <FormItem label="排序值" prop="sortOrder">
                    <Tooltip trigger="hover" placement="right" content="值越小越靠前，支持小数">
                        <InputNumber :max="1000" :min="0" v-model="formAdd.sortOrder"></InputNumber>
                    </Tooltip>
                </FormItem>
                </Col>
                <Col span="12">
                <FormItem label="是否启用" prop="status">
                    <i-switch size="large" v-model="formAdd.status" :true-value="0" :false-value="-1">
                        <span slot="open">启用</span>
                        <span slot="close">禁用</span>
                    </i-switch>
                </FormItem>
                </Col>
            </Row>
        </Form>
    </Modal>
</div>
</template>

<script>
import {
    initDepartment,
    addDepartment,
    editDepartment,
    deleteDepartment,
    searchDepartment,
    getUserByDepartmentId,
    getMyUserListData
} from "./api.js";
export default {
    name: "department-manage",
    data() {
        return {
            userLoading: true,
            searchForm: {
                pageNumber: 1,
                pageSize: 10,
                sort: "createTime",
                order: "desc",
                departmentId: ""
            },
            userData: [],
            userTotal: 0,
            selectList: [],
            selectCount: 0,
            selectRow: {},
            loading: true,
            maxHeight: "500px",
            strict: true,
            userLoading: false,
            loadingEdit: true,
            addOrEditDepartmentModal: false,
            selectList: [],
            selectCount: 0,
            showParent: false,
            modalTitle: "",
            editTitle: "",
            searchKey: "",
            form: {
                id: "",
                title: "",
                parentId: "",
                parentTitle: "",
                sortOrder: 0,
                status: 0
            },
            formAdd: {},
            formValidate: {
                title: [{
                    required: true,
                    message: "名称不能为空",
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
            dataEdit: [],
            users: [],
            userColumns: [{
                    type: "selection",
                    width: 60,
                    align: "center",
                    fixed: "left"
                },
                {
                    type: "index",
                    width: 60,
                    align: "center",
                    fixed: "left"
                },
                {
                    title: "用户名",
                    key: "nickname",
                    minWidth: 125,
                    sortable: true,
                    fixed: "left"
                },
                {
                    title: "登录账号",
                    key: "username",
                    minWidth: 125,
                    sortable: true
                },
                {
                    title: "头像",
                    key: "avatar",
                    width: 80,
                    align: "center",
                    render: (h, params) => {
                        return h("Avatar", {
                            props: {
                                src: params.row.avatar
                            }
                        });
                    }
                },
                {
                    title: "所属部门",
                    key: "departmentTitle",
                    minWidth: 120
                },
                {
                    title: "手机",
                    key: "mobile",
                    minWidth: 125,
                    sortable: true
                },
                {
                    title: "邮箱",
                    key: "email",
                    minWidth: 180,
                    sortable: true
                },
                {
                    title: "性别",
                    key: "sex",
                    width: 70,
                    align: "center"
                },
                {
                    title: "类型",
                    key: "type",
                    align: "center",
                    width: 100,
                    render: (h, params) => {
                        if (params.row.type == 1) {
                            return h("div", [
                                h(
                                    "Tag", {
                                        props: {
                                            color: "magenta",
                                            size: "medium"
                                        }
                                    },
                                    "管理员"
                                ),
                            ]);
                        } else {
                            return h("div", [
                                h(
                                    "Tag", {
                                        props: {
                                            color: "blue",
                                            size: "default"
                                        }
                                    },
                                    "用户"
                                ),
                            ]);
                        }
                    },
                },
                {
                    title: "状态",
                    key: "status",
                    align: "center",
                    width: 110,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Tag", {
                                    props: {
                                        color: (params.row.status == 0 ? "green" : "red"),
                                        size: "medium"
                                    }
                                },
                                (params.row.status == 0 ? "启用" : "禁用")
                            ),
                        ]);
                    }
                }
            ]
        };
    },
    methods: {
        init() {
            this.getParentList();
            this.getParentListEdit();
            this.getUserDataList();
        },
        getUserDataList() {
            var that = this;
            that.userLoading = true;
            getMyUserListData(that.searchForm).then(res => {
                that.userLoading = false;
                if (res.success) {
                    that.userData = res.result.records;
                    that.userTotal = res.result.total;
                }
            });
        },
        getParentList() {
            this.loading = true;
            initDepartment().then(res => {
                this.loading = false;
                if (res.success) {
                    res.result.forEach(function (e) {
                        if (e.isParent) {
                            e.loading = false;
                            e.children = [];
                            e._loading = false;
                        }
                    });
                    this.data = res.result;
                }
            });
        },
        getParentListEdit() {
            this.loadingEdit = true;
            initDepartment().then(res => {
                this.loadingEdit = false;
                if (res.success) {
                    res.result.forEach(function (e) {
                        if (e.isParent) {
                            e.loading = false;
                            e.children = [];
                        }
                    });
                    let first = {
                        id: "0",
                        title: "一级部门"
                    };
                    res.result.unshift(first);
                    this.dataEdit = res.result;
                }
            });
        },
        loadData(item, callback) {
            initDepartment({
                parentId: item.id
            }).then(res => {
                if (res.success) {
                    res.result.forEach(function (e) {
                        if (e.isParent) {
                            e.loading = false;
                            e.children = [];
                            e._loading = false;
                        }
                    });
                    callback(res.result);
                }
            });
        },
        search() {
            if (this.searchKey) {
                this.loading = true;
                searchDepartment({
                    title: this.searchKey
                }).then(res => {
                    this.loading = false;
                    if (res.success) {
                        this.data = res.result;
                    }
                });
            } else {
                this.getParentList();
            }
        },
        excelData() {
            this.$refs.table.exportCsv({
                filename: "部门用户导出结果",
            });
        },
        selectTree(v) {
            if (v.length > 0) {
                for (let attr in v[0]) {
                    if (v[0][attr] == null) {
                        v[0][attr] = "";
                    }
                }
                let str = JSON.stringify(v[0]);
                let data = JSON.parse(str);
                this.editTitle = data.title;
                this.userLoading = true;
                getUserByDepartmentId({
                    departmentId: data.id
                }).then(res => {
                    this.userLoading = false;
                    if (res.success) {
                        this.users = res.result;
                        this.form = data;
                    }
                });
                this.searchForm.departmentId = v[0].id;
                this.getUserDataList();
            } else {
                this.cancelEdit();
                this.searchForm.departmentId = "";
                this.getUserDataList();
            }
        },
        cancelEdit() {
            let data = this.$refs.tree.getSelectedNodes()[0];
            if (data) {
                data.selected = false;
            }
            this.$refs.form.resetFields();
            delete this.form.id;
            this.editTitle = "";
        },
        selectTreeEdit(v) {
            if (v.length > 0) {
                for (let attr in v[0]) {
                    if (v[0][attr] == null) {
                        v[0][attr] = "";
                    }
                }
                let str = JSON.stringify(v[0]);
                let data = JSON.parse(str);
                this.form.parentId = data.id;
                this.form.parentTitle = data.title;
            }
        },
        handleReset() {
            this.$refs.form.resetFields();
            this.form.status = 0;
        },
        showSelect(e) {
            this.selectList = e;
            this.selectCount = e.length;
        },
        clearSelectAll() {
            this.$refs.table.selectAll(false);
        },
        submitEdit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    if (!this.form.id) {
                        this.$Message.warning("请先点击选择要修改的部门");
                        return;
                    }
                    this.submitLoading = true;
                    editDepartment(this.form).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("编辑成功");
                            this.init();
                            this.addOrEditDepartmentModal = false;
                        }
                    });
                }
            });
        },
        submitAdd() {
            this.$refs.formAdd.validate(valid => {
                if (valid) {
                    this.submitLoading = true;
                    addDepartment(this.formAdd).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("添加成功");
                            this.init();
                            this.addOrEditDepartmentModal = false;
                        }
                    });
                }
            });
        },
        tableAdd(v) {
            this.form = v;
            this.add();
        },
        add() {
            if (this.form.id == "" || this.form.id == null) {
                this.addRoot();
                return;
            }
            this.modalTitle = "添加子部门";
            this.showParent = true;
            this.formAdd = {
                parentId: this.form.id,
                sortOrder: 0,
                status: 0
            };
            this.addOrEditDepartmentModal = true;
        },
        addRoot() {
            this.modalTitle = "添加一级部门";
            this.showParent = false;
            this.formAdd = {
                parentId: 0,
                sortOrder: 0,
                status: 0
            };
            this.addOrEditDepartmentModal = true;
        },
        changeSelect(v) {
            this.selectCount = v.length;
            this.selectList = v;
        },
        remove(v) {
            this.selectCount = 1;
            this.selectList.push(v);
            this.delAll();
        },
        delAll() {
            if (this.selectCount <= 0) {
                this.$Message.warning("您还未勾选要删除的数据");
                return;
            }
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除所选的部门?",
                loading: true,
                onOk: () => {
                    let ids = "";
                    this.selectList.forEach(function (e) {
                        ids += e.id + ",";
                    });
                    ids = ids.substring(0, ids.length - 1);
                    deleteDepartment({
                        ids: ids
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("删除成功");
                            this.selectList = [];
                            this.selectCount = 0;
                            this.cancelEdit();
                            this.init();
                        }
                    });
                }
            });
        },
        rowClick(row, index) {
            this.selectRow = row;
        },
        rowClassName(row, index) {
            if (row.id == this.selectRow.id) {
                return "rowClassNameColor";
            }
            return "";
        },
    },
    mounted() {
        let height = document.documentElement.clientHeight;
        this.maxHeight = Number(height - 287) + "px";
        this.init();
    }
};
</script>

<style lang="less">
.ivu-table td {
    height: 38px !important;
}

.ivu-table-cell-with-expand {
    height: 38px !important;
    line-height: 38px !important;
}

.ivu-table .rowClassNameColor td {
    background-color: #b0b3b6 !important;
    color: #ffffff !important;
}
</style>
