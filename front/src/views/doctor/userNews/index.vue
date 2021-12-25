<template>
<div class="search">
    <Card>
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
            <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                <Form-item ref="searchForm" :model="searchForm" inline :label-width="0" style="display: flex">
                    <Form-item label="" prop="newTitle">
                        <Input type="text" v-model="searchForm.newTitle" placeholder="请输入新闻标题" clearable style="width: 200px" @on-clear="onClear" />
                    </Form-item>
                    <Form-item style="margin-left: 10px" class="br">
                        <Button @click="handleSearch" type="primary" icon="ios-search" size="small">搜索</Button>
                        <Button @click="handleReset" type="warning" size="small" icon="md-refresh">重置</Button>
                    </Form-item>
                    <Form-item style="position: fixed; right: 50px; top: 130px">
                        <Button type="info" @click="showFilterPanelFlag = !showFilterPanelFlag" class="showFilterPanelFlag" icon="md-settings" size="small">
                        </Button>
                        <Button type="warning" @click="modal1 = true" class="showFilterPanelFlag" icon="ios-help-circle-outline" size="small">
                        </Button>
                        <Modal v-model="modal1" title="使用教程">
                            <p>使用教程</p>
                        </Modal>
                    </Form-item>
                </Form-item>
            </Form>
        </Row>
        <Row class="operation" style="position: relative">
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
            <Table :loading="loading" border :columns="columns" :data="data" ref="table" sortable="custom" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-click="rowClick" :row-class-name="rowClassNmae"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[10, 20, 50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
</div>
</template>

<script>
import {
    getDynamic_newList,
    deleteDynamic_new,
    setTopById,
    setPublicById,
} from "./api.js";
export default {
    name: "single-window",
    components: {
    },
    data() {
        return {
            selected: [
                "选择",
                "序号",
                "新闻标题",
                "新闻描述",
                "图片",
                "新闻时效",
                "关联服务",
                "发布机构",
                "是否公开",
                "是否置顶",
            ],
            modal1: false,
            openSearch: true, // 显示搜索
            openTip: true, // 显示提示
            formData: {},
            loading: true, // 表单加载状态
            searchForm: {
                // 搜索框初始化对象
                newTitle: "",
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
                    title: "新闻标题",
                    key: "newTitle",
                    minWidth: 120,
                    sortable: false,
                    fixed: "left",
                },
                {
                    title: "新闻描述",
                    key: "newDescribe",
                    minWidth: 160,
                    sortable: false,
                    tooltip: true,
                    render: (h, params) => {
                        return h('pre', {
                            domProps: {
                                innerHTML: params.row.newDescribe
                            }
                        })
                    }
                },
                {
                    title: "图片",
                    key: "photo",
                    minWidth: 100,
                    sortable: false,
                    render: (h, params) => {
                        if (params.row.photo != "") {
                            return h("viewer", [
                                h("img", {
                                    attrs: {
                                        src: params.row.photo,
                                        height: 80,
                                        width: 100,
                                    },
                                }),
                            ]);
                        } else {
                            let re = "暂无照片",
                                color = "red";
                            return h("div", [
                                h(
                                    "Tag", {
                                        props: {
                                            color: color,
                                        },
                                    },
                                    re
                                ),
                            ]);
                        }
                    },
                },
                {
                    title: "新闻时效",
                    key: "periodTime",
                    minWidth: 120,
                    sortable: false,
                },
                {
                    title: "是否置顶",
                    key: "isTop",
                    minWidth: 120,
                    sortable: false,
                    render: (h, params) => {
                        if (this.$route.meta.permTypes.includes("setDynamicNewTop")) {
                            return h("i-switch", {
                                props: {
                                    size: "large",
                                    value: params.row.isTop === "yes",
                                },
                                scopedSlots: {
                                    open: () => h("span", "开启"),
                                    close: () => h("span", "关闭"),
                                },
                                on: {
                                    "on-change": (value) => {
                                        setTopById({
                                            id: params.row.id
                                        }).then((res) => {
                                            if (res.success) {
                                                this.$Message.success("操作成功");
                                                this.getDataList();
                                            }
                                        });
                                    },
                                },
                            });
                        } else {
                            let re = "已置顶",
                                color = "";
                            if (params.row.isTop == "yes") color = "blue";
                            else {
                                color = "red";
                                re = "非置顶";
                            }
                            return h("div", [
                                h(
                                    "Tag", {
                                        props: {
                                            color: color,
                                        },
                                    },
                                    re
                                ),
                            ]);
                        }
                    },
                },
                {
                    title: "是否公开",
                    key: "isPublic",
                    minWidth: 120,
                    sortable: false,
                    render: (h, params) => {
                        return h("i-switch", {
                            props: {
                                size: "large",
                                value: params.row.isPublic === "yes",

                            },
                            scopedSlots: {
                                open: () => h("span", "开启"),
                                close: () => h("span", "关闭"),
                            },
                            on: {
                                "on-change": (value) => {
                                    setPublicById({
                                        id: params.row.id
                                    }).then((res) => {
                                        if (res.success) {
                                            this.$Message.success("操作成功");
                                            this.getDataList();
                                        }
                                    });
                                },
                            },
                        });
                    },
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
        onClear() {
            this.searchForm.newTitle = "";
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
            getDynamic_newList(this.searchForm).then((res) => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                    for (var i = 0; i < res.result.records.length; i++) {
                        if (
                            !(
                                JSON.parse(this.getStore("userInfo")).departmentId ==
                                res.result.records[i].nursingOrganizationId ||
                                JSON.parse(this.getStore("userInfo")).organizationId ==
                                res.result.records[i].nursingOrganizationId
                            )
                        )
                            res.result.records[i]._disabled = true;
                    }
                }
            });
        },
    },
    mounted() {
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
                else if (newcolumns.contains(item.title)) showcolumns.push(item);
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
    background-color: #2db7f5 !important;
    color: #fff !important;
    font-size: 18px;
}
</style>
