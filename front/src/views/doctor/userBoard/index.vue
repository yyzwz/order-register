<template>
<div class="search">
    <add v-if="currView=='add'" @close="currView='index'" @submited="submited" />
    <edit v-if="currView=='edit'" @close="currView='index'" @submited="submited" :data="formData" />
    <Card v-show="currView=='index'">
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
            <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                <Form-item ref="searchForm" :model="searchForm" inline :label-width="0" style="display:flex;">
                    <Form-item label="" prop="date">
                        <DatePicker type="date" v-model="searchForm.date" placeholder="请选择" clearable style="width: 200px"></DatePicker>
                    </Form-item>
                    <Form-item label="" prop="content">
                        <Input type="text" v-model="searchForm.content" placeholder="请输入留言内容" clearable style="width: 200px" />
                    </Form-item>
                    <Form-item style="margin-left:10px;" class="br">
                        <Button @click="handleSearch" type="primary" icon="ios-search" size="small" ghost>搜索</Button>
                        <Button @click="handleReset" type="warning" size="small" icon="md-refresh" ghost>重置</Button>
                        <Button @click="addMessageModal=true" type="info" size="small" icon="md-add" ghost>添加</Button>
                    </Form-item>
                    <Form-item style="position:fixed;right:50px;top:130px">
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
            <Table :loading="loading" :height="tableHeight" border stripe size="small" :columns="columns" :data="data" ref="table" sortable="custom" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-dblclick="dbClickZwzRow" @on-row-click="rowClick" :row-class-name="rowClassNmae"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[15,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
    <Modal v-model="addMessageModal" title="添加留言" ok-text="确认留言" @on-ok="addMessageFx">
        <Input v-model="addMessageStr" :rows="8" type="textarea" placeholder="请在这里填写您的留言信息..." />
    </Modal>
    <Modal v-model="addReMessageModal" title="添加回复" ok-text="确认回复" @on-ok="addReMessageFx">
        <Input v-model="addReMessageStr" :rows="8" type="textarea" placeholder="请在这里填写您的留言信息..." />
    </Modal>
    <Modal v-model="lookReMessageModal" width="85" title="留言回复" footer-hide>
        <Row>
            <Table :loading="reLoading" :height="tableHeight" border stripe size="small" :columns="reColumns" :data="reData"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="researchForm.pageNumber" :total="retotal" :page-size="researchForm.pageSize" @on-change="changeRePage" @on-page-size-change="changeRePageSize" :page-size-opts="[15,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Modal>
</div>
</template>

<script>
import {
    getMessageBoardList,
    deleteMessageBoard,
    addMessageBoard,
    addReplyMessageBoard
} from "./api.js";
import add from "./add.vue";
import edit from "./edit.vue";
export default {
    name: "single-window",
    components: {
        add,
        edit
    },
    data() {
        return {
            lookReMessageModal: false,
            addReMessageStr: "",
            addReMessageModal: false,
            addMessageStr: "",
            addMessageModal: false,
            tableHeight: 0,
            selected: [
                "选择",
                "序号",
                "留言人",
                "留言时间",
                "留言内容",
                "创建时间",
                "创建者",
                "回复",
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
                    title: "留言人",
                    key: "userName",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "留言时间",
                    key: "date",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "留言内容",
                    key: "content",
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
                    title: "回复",
                    key: "reply",
                    align: "center",
                    width: 200,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button", {
                                    props: {
                                        type: "warning",
                                        size: "small",
                                        icon: "ios-add",
                                        ghost: true
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.replyFx(params.row);
                                        }
                                    }
                                },
                                "回复"
                            )
                        ]);
                    }
                },
            ],
            data: [], // 表单数据
            pageNumber: 1, // 当前页数
            pageSize: 10, // 页面大小
            total: 0, // 表单数据总数
            showFilterPanelFlag: false,
            zwzSelectRow: {},
            reLoading: false,
            reColumns: [
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
                            (this.researchForm.pageNumber - 1) * this.researchForm.pageSize +
                            1
                        );
                    },
                },
                {
                    title: "留言人",
                    key: "userName",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "留言时间",
                    key: "date",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "留言内容",
                    key: "content",
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
            ],
            reData: [],
            researchForm: { // 搜索框初始化对象
                pageNumber: 1, // 当前页数
                pageSize: 15, // 页面大小
                sort: "createTime", // 默认排序字段
                order: "desc", // 默认排序方式
                replyId: ""
            },
            retotal: 0
        };
    },
    methods: {
        init() {
            this.getDataList();
        },
        replyFx(e) {
            this.zwzSelectRow = e;
            this.addReMessageModal = true;
        },
        dbClickZwzRow(e) {
            this.zwzSelectRow = e;
            this.researchForm.replyId = e.id;
            this.getReDataList();
            this.lookReMessageModal = true;
        },
        addMessageFx() {
            var that = this;
            addMessageBoard({
                content: that.addMessageStr
            }).then(res => {
                if (res.success) {
                    this.$Message.success("留言成功");
                }
            })
        },
        addReMessageFx() {
            var that = this;
            addReplyMessageBoard({
                content: that.addReMessageStr,
                replyId: that.zwzSelectRow.id
            }).then(res => {
                if (res.success) {
                    this.$Message.success("回复成功");
                }
            })
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
            getMessageBoardList(this.searchForm).then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        },
        getReDataList() {
            this.reLoading = true;
            getMessageBoardList(this.researchForm).then(res => {
                this.reLoading = false;
                if (res.success) {
                    this.reData = res.result.records;
                    this.retotal = res.result.total;
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
                    deleteMessageBoard({
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
                    deleteMessageBoard({
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
