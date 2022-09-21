<template>
<div class="search">
    <add v-if="currView=='add'" @close="currView='index'" @submited="submited" />
    <edit v-if="currView=='edit'" @close="currView='index'" @submited="submited" :data="formData" />
    <Card v-show="currView=='index'">
        <Alert type="success" show-icon banner >温馨提示：您使用代码生成器生成后，无需任何改动就可以得到这个界面！</Alert>
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
            <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                <Form-item ref="searchForm" :model="searchForm" inline :label-width="0" style="display:flex;">
                    <Form-item label="" prop="name">
                        <Input type="text" v-model="searchForm.name" placeholder="姓名" clearable style="width: 120px" />
                    </Form-item>
                    <Form-item label="" prop="sex">
                        <dict dict="sex" v-model="searchForm.sex" transfer clearable placeholder="性别" style="width: 120px" />
                    </Form-item>
                    <Form-item label="" prop="number">
                        <Input type="text" v-model="searchForm.number" placeholder="学号" clearable style="width: 120px" />
                    </Form-item>
                    <Form-item label="" prop="school">
                        <Input type="text" v-model="searchForm.school" placeholder="学校" clearable style="width: 120px" />
                    </Form-item>
                    <Form-item style="margin-left:10px;" class="br">
                        <Button @click="handleSearch" type="primary" icon="ios-search" size="small" ghost>搜索</Button>
                        <Button @click="handleReset" type="warning" size="small" icon="md-refresh" ghost>重置</Button>
                        <Button @click="add" type="info" size="small" icon="md-add" ghost>添加</Button>
                        <Button @click="delAll" type="error" icon="md-trash" size="small" ghost>删除</Button>
                        <Button @click="excelData" type="success" icon="md-paper-plane" size="small" ghost>导出</Button>
                    </Form-item>
                    <Form-item style="position:fixed;right:50px;top:170px">
                        <Button type="info" @click="showFilterPanelFlag = !showFilterPanelFlag" class="showFilterPanelFlag" icon="md-settings" size="small" ghost>
                            列筛选</Button>
                        <Button type="warning" @click="modal1 = true" class="showFilterPanelFlag" icon="ios-help-circle-outline" size="small" ghost>
                            使用教程</Button>
                        <Modal v-model="modal1" title="使用教程">
                            <p>1.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                            <p>2.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                            <p>3.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                        </Modal>
                    </Form-item>
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
        <Row v-show="openTip"> </Row>
        <Row>
            <Table :loading="loading" :height="tableHeight" border stripe size="small" :columns="columns" :data="data" ref="table" sortable="custom" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-click="rowClick" :row-class-name="rowClassNmae"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[15,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
</div>
</template>

<script>
import {
    getStudentList,
    deleteStudent
} from "./api.js";
import add from "./add.vue";
import edit from "./edit.vue";
import dict from "@/views/template/dict";
export default {
    name: "single-window",
    components: {
        add,
        edit,
        dict
    },
    data() {
        return {
            tableHeight: 0,
            selected: [
                "选择",
                "序号",
                "姓名",
                "性别",
                "年龄",
                "学号",
                "学校",
                "创建时间",
                "创建者",
                "修改时间",
                "修改者",
                "操作",
            ],
            modal1: false,
            openSearch: true, // 显示搜索
            openTip: true, // 显示提示
            formData: {},
            currView: "index",
            loading: true, // 表单加载状态
            searchForm: { // 搜索框初始化对象
                pageNumber: 1, // 当前页数
                pageSize: 15, // 页面大小
                sort: "createTime", // 默认排序字段
                order: "desc", // 默认排序方式
            },
            selectList: [], // 多选数据
            selectCount: 0, // 多选计数
            selectRow: 0,
            columns: [
                // 表头
                {
                    type: "selection",
                    width: 60,
                    title: "选择",
                    align: "center",
                    fixed: "left",
                },
                {
                    title: "序号",
                    width: 85,
                    align: "center",
                    fixed: "left",
                    sortType: true,
                    render: (h, params) => {
                        return h(
                            "span",
                            params.index +
                            (this.searchForm.pageNumber - 1) * this.searchForm.pageSize +
                            1
                        );
                    },
                },
                {
                    title: "姓名",
                    key: "name",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "性别",
                    key: "sex",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "年龄",
                    key: "age",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "学号",
                    key: "number",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "学校",
                    key: "school",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "创建时间",
                    key: "createTime",
                    sortable: true,
                    sortType: "desc",
                    minWidth: 180,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "创建者",
                    key: "createBy",
                    sortable: true,
                    sortType: "desc",
                    minWidth: 100,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "修改时间",
                    key: "updateTime",
                    minWidth: 180,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "修改者",
                    key: "updateBy",
                    minWidth: 100,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    width: 200,
                    fixed: "right",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button", {
                                    props: {
                                        type: "primary",
                                        size: "small",
                                        icon: "ios-create-outline",
                                        ghost: true
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
                                        type: "error",
                                        size: "small",
                                        icon: "md-trash",
                                        ghost: true
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
            data: [], // 表单数据
            pageNumber: 1, // 当前页数
            pageSize: 10, // 页面大小
            total: 0, // 表单数据总数
            showFilterPanelFlag: false,
        };
    },
    methods: {
        init() {
            this.getDataList();
        },
        submited() {
            this.currView = "index";
            this.getDataList();
        },
        changePage(v) {
            this.searchForm.pageNumber = v;
            this.getDataList();
            this.clearSelectAll();
        },
        changePageSize(v) {
            this.searchForm.pageSize = v;
            this.getDataList();
        },
        rowClick(row, index) {
            this.selectRow = row;
        },
        rowClassNmae(row, index) {
            if (row.id == this.selectRow.id) {
                return "rowClassNmaeColor";
            }
            return "";
        },
        excelData() {
            this.$refs.table.exportCsv({
                filename: "导出结果",
            });
        },
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
            this.getDataList();
        },
        handleReset() {
            this.$refs.searchForm.resetFields();
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
            // 重新加载数据
            this.getDataList();
        },
        changeSort(e) {
            this.searchForm.sort = e.key;
            this.searchForm.order = e.order;
            if (e.order === "normal") {
                this.searchForm.order = "";
            }
            this.getDataList();
        },
        clearSelectAll() {
            this.$refs.table.selectAll(false);
        },
        changeSelect(e) {
            this.selectList = e;
            this.selectCount = e.length;
        },
        getDataList() {
            this.loading = true;
            getStudentList(this.searchForm).then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        },
        add() {
            this.currView = "add";
        },
        edit(v) {
            // 转换null为""
            for (let attr in v) {
                if (v[attr] == null) {
                    v[attr] = "";
                }
            }
            let str = JSON.stringify(v);
            let data = JSON.parse(str);
            this.formData = data;
            this.currView = "edit";
        },
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                // 记得确认修改此处
                content: "您确认要删除 " + v.name + " ?",
                loading: true,
                onOk: () => {
                    // 删除
                    deleteStudent({
                        ids: v.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.getDataList();
                        }
                    });
                }
            });
        },
        delAll() {
            if (this.selectCount <= 0) {
                this.$Message.warning("您还未选择要删除的数据");
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
                    // 批量删除
                    deleteStudent({
                        ids: ids
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.clearSelectAll();
                            this.getDataList();
                        }
                    });
                }
            });
        }
    },
    mounted() {
        this.init();
        this.tableHeight = Number(window.innerHeight - 273);
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
    },
};
</script>

<style lang="less">
// @import "../../../styles/table-common.less";
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

.ivu-table .rowClassNmaeColor td {
    background-color: #b0b3b6 !important;
    color: #ffffff !important;
    font-size: 12px;
}
</style>
