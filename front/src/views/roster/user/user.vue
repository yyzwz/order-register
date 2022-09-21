<template>
<div class="search">
    <Card>
        <Row>
            <Form ref="searchForm" :model="searchForm" inline :label-width="40">
                <Form-item label="姓名" prop="nickname">
                    <Input type="text" v-model="searchForm.nickname" clearable placeholder="搜索姓名" style="width: 160px" />
                </Form-item>
                <Form-item label="部门" prop="department">
                    <department-choose @on-change="handleSelectDep" placeholder="用户部门" style="width: 160px" ref="dep"></department-choose>
                </Form-item>
                <Form-item style="margin-left:10px;" class="br">
                    <Button @click="handleSearch" type="primary" icon="ios-search" ghost shape="circle" size="small">搜索</Button>
                    <Button @click="handleReset" type="warning" ghost shape="circle" icon="md-refresh" size="small">重置</Button>
                    <Button @click="add" type="info" icon="md-add" ghost shape="circle" size="small">添加</Button>
                    <Button @click="importModalVisible=true" type="success" icon="md-paper-plane" ghost shape="circle" size="small">导入</Button>
                    <Button @click="excelData" type="success" icon="md-paper-plane" ghost shape="circle" size="small">导出</Button>
                </Form-item>
                    <Form-item style="position:fixed;right:50px;top:130px">
                        <Button type="info" @click="showFilterPanelFlag = !showFilterPanelFlag" class="showFilterPanelFlag" icon="md-settings" size="small" ghost>
                            列筛选</Button>
                        <Button type="warning" @click="usingTutorialsModal = true" class="showFilterPanelFlag" icon="ios-help-circle-outline" size="small" ghost>
                            使用教程</Button>
                        <Modal v-model="usingTutorialsModal" title="使用教程">
                            <p>1.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                            <p>2.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                            <p>3.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                        </Modal>
                    </Form-item>
            </Form>
        </Row>
        <Row class="operation" style="position:relative;">
            <transition>
                <div v-show="showFilterPanelFlag" class="filter-panel">
                    <CheckboxGroup v-model="selected">
                        <div v-for="item in mycolumns" :key="item.key">
                            <Checkbox :label="item.title" style="margin: 2px 5px"></Checkbox>
                        </div>
                    </CheckboxGroup>
                </div>
            </transition>
        </Row>
        <Row>
            <Table :loading="loading" border stripe :height="height" :columns="columns" :data="data" sortable="custom" @on-sort-change="changeSort" @on-selection-change="showSelect" ref="table" @on-row-click="rowClick" :row-class-name="rowClassName"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[20,30,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
    <Drawer title="导入数据" closable v-model="importModalVisible" width="1000">
        <div style="display:flex;justify-content: space-between;align-items: center;">
            <Upload action :before-upload="beforeUploadImport" accept=".xls, .xlsx">
                <Button :loading="reading" icon="ios-cloud-upload-outline" style="margin-right:10px">上传Excel文件</Button>
                <span v-if="uploadfile.name">当前选择文件：{{ uploadfile.name }}</span>
            </Upload>
            <Button @click="clearImportData" icon="md-trash">清空数据</Button>
        </div>
        <Table :columns="importColumns" border :height="height" :data="importTableData" ref="importTable"></Table>
        <div class="drawer-footer">
            <div style="position:absolute;right:15px;display: inline-block">
                <Button @click="importModalVisible=false">关闭</Button>
                <Button :loading="importLoading" :disabled="importTableData.length<=0" @click="importData" style="margin-left:8px" type="primary">
                    确认导入
                    <span v-if="importTableData.length>0">{{importTableData.length}} 条数据</span>
                </Button>
            </div>
        </div>
    </Drawer>
    <addEdit :data="form" :type="showType" v-model="showUser" @on-submit="getUserList" />
</div>
</template>

<script>
import {
    getUserList,
    enableUser,
    disableUser,
    deleteUser,
    importUserData,
    resetUserPass
} from "./api.js";
import departmentChoose from "@/views/template/department-choose";
import excel from "@/libs/excel";
import addEdit from "./addEdit.vue";
import dict from "@/views/template/dict";
export default {
    name: "user-manage",
    components: {
        departmentChoose,
        addEdit,
        dict
    },
    data() {
        return {
            selected: [
                "编号",
                "用户名",
                "登录账号",
                "头像",
                "所属部门",
                "手机",
                "邮箱",
                "性别",
                "类型",
                "状态",
                "创建时间",
                "操作",
            ],
            showFilterPanelFlag: false,
            usingTutorialsModal: false,
            height: 510,
            selectRow: {},
            showUser: false,
            showType: "0",
            loading: true,
            reading: false,
            importLoading: false,
            loadingExport: true,
            exportModalVisible: false,
            importModalVisible: false,
            drop: false,
            selectCount: 0,
            selectList: [],
            searchForm: {
                nickname: "",
                departmentId: "",
                pageNumber: 1,
                pageSize: 20,
                sort: "createTime",
                order: "desc"
            },
            form: {},
            columns: [{
                    title: "编号",
                    type: "index",
                    width: 80,
                    align: "center",
                    fixed: "left"
                },
                {
                    title: "用户名",
                    key: "nickname",
                    minWidth: 150,
                    sortable: true,
                    fixed: "left"
                },
                {
                    title: "登录账号",
                    key: "username",
                    minWidth: 150,
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
                                    "span", {
                                        style: {
                                            color: "#ff9900",
                                        },
                                    },
                                    "管理员"
                                ),
                            ]);
                        } else {
                            return h("div", [
                                "普通用户"
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
                        if (params.row.status == 0) {
                            return h("div", [
                                h(
                                    "span", {
                                        style: {
                                            color: "#3CB371",
                                        },
                                    },
                                    "正常启用"
                                ),
                            ]);
                        } else {
                            return h("div", [
                                h(
                                    "span", {
                                        style: {
                                            color: "#ff9900",
                                        },
                                    },
                                    "禁用"
                                ),
                            ]);
                        }
                    },
                },
                {
                    title: "创建时间",
                    key: "createTime",
                    sortable: true,
                    sortType: "desc",
                    width: 180
                },
                {
                    title: "操作",
                    key: "action",
                    width: 300,
                    align: "center",
                    fixed: "right",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button", {
                                    props: {
                                        type: "primary",
                                        size: "small",
                                        ghost: true,
                                        shape: "circle"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row);
                                        }
                                    }
                                },
                                "编辑"
                            ),
                            h(
                                "Button", {
                                    props: {
                                        type: "warning",
                                        size: "small",
                                        ghost: true,
                                        shape: "circle"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.resetPass(params.row);
                                        }
                                    }
                                },
                                "重置密码"
                            ),
                            h(
                                "Button", {
                                    props: {
                                        size: "small",
                                        ghost: true,
                                        type: (params.row.status == 0 ? "error" : "success"),
                                        shape: "circle"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            if (params.row.status == 0) {
                                                this.disable(params.row);
                                            } else {
                                                this.enable(params.row);
                                            }
                                        }
                                    }
                                },
                                (params.row.status == 0 ? "禁用" : "启用")
                            ),
                            h(
                                "Button", {
                                    props: {
                                        type: "error",
                                        size: "small",
                                        ghost: true,
                                        shape: "circle"
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.row);
                                        }
                                    }
                                },
                                "删除"
                            )
                        ]);
                    }
                }
            ],
            filename: "用户数据",
            importTableData: [],
            importColumns: [],
            uploadfile: {
                name: ""
            },
            data: [],
            total: 0
        };
    },
    methods: {
        init() {
            this.getUserList();
        },
        excelData() {
            this.$refs.table.exportCsv({
                filename: "导出结果",
            });
        },
        handleSelectDep(v) {
            this.searchForm.departmentId = v;
        },
        changePage(v) {
            this.searchForm.pageNumber = v;
            this.getUserList();
            this.clearSelectAll();
        },
        changePageSize(v) {
            this.searchForm.pageSize = v;
            this.getUserList();
        },
        getUserList() {
            this.loading = true;
            getUserList(this.searchForm).then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        },
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 20;
            this.getUserList();
        },
        handleReset() {
            this.$refs.searchForm.resetFields();
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 10;
            this.$refs.dep.clearSelect();
            this.searchForm.departmentId = "";
            this.getUserList();
        },
        changeSort(e) {
            this.searchForm.sort = e.key;
            this.searchForm.order = e.order;
            if (e.order == "normal") {
                this.searchForm.order = "";
            }
            this.getUserList();
        },
        resetPass(e) {
            this.$Modal.confirm({
                title: "确认重置",
                content: "重置后密码为123456",
                loading: true,
                onOk: () => {
                    resetUserPass({
                        ids: e.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.clearSelectAll();
                            this.getUserList();
                        }
                    });
                }
            });
        },
        beforeUploadImport(file) {
            this.uploadfile = file;
            const fileExt = file.name
                .split(".")
                .pop()
                .toLocaleLowerCase();
            if (fileExt == "xlsx" || fileExt == "xls") {
                this.readFile(file);
                this.file = file;
            } else {
                this.$Message.error("不是Excel文件");
            }
            return false;
        },
        readFile(file) {
            this.reading = true;
            const reader = new FileReader();
            reader.readAsArrayBuffer(file);
            reader.onerror = e => {
                this.reading = false;
                this.$Message.error("文件读取出错");
            };
            reader.onload = e => {
                const data = e.target.result;
                const {
                    header,
                    results
                } = excel.read(data, "array");
                const tableTitle = header.map(item => {
                    return {
                        title: item,
                        key: item,
                        minWidth: 100,
                        align: "center"
                    };
                });
                this.importTableData = results;
                this.importColumns = tableTitle;
                this.reading = false;
                this.$Message.success("读取数据成功");
            };
        },
        clearImportData() {
            this.importTableData = [];
            this.importColumns = [];
            this.uploadfile = {};
        },
        importData() {
            this.importLoading = true;
            importUserData(this.importTableData).then(res => {
                this.importLoading = false;
                if (res.success) {
                    this.importModalVisible = false;
                    this.getUserList();
                    this.$Modal.info({
                        title: "导入结果",
                        content: res.message
                    });
                }
            });
        },
        add() {
            this.showType = "2";
            this.showUser = true;
        },
        edit(v) {
            for (let i in v) {
                if (v[i] == null) {
                    v[i] = "";
                }
            }
            let str = JSON.stringify(v);
            let data = JSON.parse(str);
            this.form = data;
            this.showType = "1";
            this.showUser = true;
        },
        enable(v) {
            this.$Modal.confirm({
                title: "确认启用",
                content: "启用用户 " + v.username + " ?",
                loading: true,
                onOk: () => {
                    enableUser({
                        id: v.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("启用成功");
                            this.getUserList();
                        }
                    });
                }
            });
        },
        disable(v) {
            this.$Modal.confirm({
                title: "确认禁用",
                content: "禁用用户 " + v.username + " ?",
                loading: true,
                onOk: () => {
                    disableUser({
                        id: v.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("禁用成功");
                            this.getUserList();
                        }
                    });
                }
            });
        },
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "删除用户 " + v.username + " ?",
                loading: true,
                onOk: () => {
                    deleteUser({
                        ids: v.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("删除成功");
                            this.getUserList();
                        }
                    });
                }
            });
        },
        showSelect(e) {
            this.selectList = e;
            this.selectCount = e.length;
        },
        clearSelectAll() {
            this.$refs.table.selectAll(false);
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
        this.height = window.innerHeight - this.$refs.table.$el.offsetTop - 160;
        this.init();
        this.mycolumns = this.columns;
        let showcolumns = [];
        for (var i = 0; i < this.selected.length; i++) {
            var item = this.selected[i];
            for (var j = 0; j < this.columns.length; j++) {
                if (this.columns[j].title == item) {
                    showcolumns.push(this.columns[j]);
                }
            }
        }
        this.columns = showcolumns;
    },
    watch: {
        selected: function (newcolumns) {
            let showcolumns = [];
            for (var i = 0; i < this.mycolumns.length; i++) {
                var item = this.mycolumns[i];
                if (item.title == undefined) showcolumns.push(item);
                else if (newcolumns.includes(item.title)) showcolumns.push(item);
            }
            this.columns = showcolumns;
        },
    }
};
</script>

<style lang="less">

.search {
    .operation {
        margin-bottom: 2vh;
    }

    .select-count {
        font-weight: 600;
        color: #40a9ff;
    }

    .select-clear {
        margin-left: 10px;
    }

    .page {
        margin-top: 2vh;
    }

    .drop-down {
        margin-left: 5px;
    }
}

.filter-panel {
    width: 166px;
    min-height: 120px;
    height: 200px;
    position: absolute;
    background-color: white;
    z-index: 9999;
    margin-left: 1px;
    overflow-y: scroll;
    border: 1px solid blue;
    top: 35px;
    right: 10px;
}

.openSearch {
    position: absolute;
    right: 240px;
}

.openTip {
    position: absolute;
    right: 130px;
}
.showFilterPanelFlag {
    position: static !important;
    right: 10px;
    margin-right: 10px;
}
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
