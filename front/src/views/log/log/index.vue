<template>
<!-- 唯一div -->
<div class="search">
    <!-- 卡片盒子 -->
    <Card>
        <!-- 选项卡 -->
        <Tabs type="card" v-model="tabName" @on-click="changeTab">
            <!-- 系统登入的日志 -->
            <TabPane label="登录日志" icon="ios-cloud-upload-outline" name="1"></TabPane>
            <!-- 系统数据中心日志 -->
            <TabPane label="数据中心日志" icon="md-list" name="2"></TabPane>
            <!-- 系统扩展模块展示日志 -->
            <TabPane label="扩展模块展示日志" icon="ios-people" name="3"></TabPane>
            <!-- 系统图表日志 -->
            <TabPane label="图表日志" icon="md-laptop" name="4"></TabPane>
        </Tabs>
        <!-- 左上方按钮层 -->
        <Row :gutter="16">
            <!-- Form父级盒子 -->
            <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                <!-- Formitem子级盒子 -->
                <Form-item label="">
                    <!-- 搜索框 -->
                    <Input type="text" v-model="searchForm.name" clearable placeholder="日志名称" style="width: 120px;" />
                </Form-item>
                <Form-item label="">
                    <!-- 搜索框 -->
                    <Input type="text" v-model="searchForm.ip" clearable placeholder="IP地址" style="width: 120px;" />
                </Form-item>
                <Form-item label="">
                    <!-- 搜索框 -->
                    <Input type="text" v-model="searchForm.username" clearable placeholder="用户名" style="width: 120px;" />
                </Form-item>
                <Form-item label="">
                    <!-- 时间选择框 -->
                    <DatePicker type="daterange" v-model="selectDate" format="yyyy-MM-dd" clearable @on-change="selectDateRange" placeholder="选择起始时间" style="width: 200px"></DatePicker>
                </Form-item>
                <Form-item class="br">
                    <!-- Poptip鼠标移入提示语句 -->
                    <Poptip trigger="hover" content="输入搜索条件后,点击搜索完成查询" placement="bottom">
                        <!-- 搜索按钮 -->
                        <Button @click="handleSearch" type="primary" icon="ios-search" size="small" ghost shape="circle">搜索</Button>
                    </Poptip>
                    <Poptip trigger="hover" content="将所有搜索条件删除,搜索全部内容" placement="bottom">
                        <!-- 重置按钮 -->
                        <Button @click="handleReset" type="warning" size="small" ghost shape="circle" icon="md-refresh">重置</Button>
                    </Poptip>
                </Form-item>
                <!-- 右侧俩按钮 -->
                <Form-item style="position:fixed;right:50px;top:160px">
                    <Poptip trigger="hover" content="自定义表格的显示列,适应屏幕显示" placement="left">
                        <!-- 筛选按钮 -->
                        <Button type="info" @click="showFilterPanelFlag = !showFilterPanelFlag" class="showFilterPanelFlag" icon="ios-settings" size="small" ghost>列筛选
                        </Button>
                    </Poptip>
                    <Poptip trigger="hover" content="点我查看本模块的使用教程" placement="left">
                        <!-- 教程按钮 -->
                        <Button type="warning" @click="modal1 = true" class="showFilterPanelFlag" icon="md-help" size="small" ghost>使用教程
                        </Button>
                    </Poptip>
                    <!-- 教程内容的弹窗，显示日志档案教程 -->
                    <Modal v-model="modal1" title="操作日志档案教程">
                        <p>1.所有用户的操作日志均在此模块</p>
                        <p>2.日志根据系统类型，在不同页签展示</p>
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
            <!-- 表格组件，根据后台数据生成表格 -->
            <Table :loading="loading" border stripe size="small" :columns="columns" :height="tableHeight" :data="data" ref="table" sortable="custom" context-menu show-context-menu @on-contextmenu="handleContextMenu" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-click="rowClick" :row-class-name="rowClassNmae" @on-row-dblclick="lookContacts">
                <template slot="contextMenu">
                    <DropdownItem @click.native="openMessageModel" style="color: #ff9900">提示</DropdownItem>
                </template>
            </Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <!-- 表格右下方分页器，可以选择每页显示的条数 -->
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[5, 15, 25, 35, 45, 1000]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
</div>
</template>

<script>
import {
    getLogListData
} from "./api.js";
export default {
    name: "logLook",
    data() {
        return {
            // 日志表的列
            columns: [{
                    title: "序号",
                    type: "index",
                    minWidth: 80,
                    align: "center",
                    fixed: "left",
                },
                {
                    title: "操作名称",
                    key: "name",
                    minWidth: 120,
                    sortable: true,
                    fixed: "left",
                    tooltip: true,
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Tag", {
                                    props: {
                                        color: "purple",
                                        size: "medium"
                                    }
                                },
                                params.row.name
                            ),
                        ]);
                    }
                },
                {
                    title: "操作者",
                    key: "username",
                    minWidth: 100,
                    sortable: true,
                    fixed: "left",
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "秒耗时",
                    key: "costTime",
                    minWidth: 150,
                    sortable: true,
                    align: "center",
                    tooltip: true,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Tag", {
                                    props: {
                                        color: (params.row.costTime > 500 ? "red" : "green"),
                                        size: "medium"
                                    }
                                },
                                params.row.costTime / 1000
                            ),
                        ]);
                    }
                },
                {
                    title: "请求类型",
                    key: "requestType",
                    minWidth: 130,
                    align: "center",
                    sortable: true,
                    tooltip: true,
                    render: (h, params) => {
                        if (params.row.requestType === "GET") {
                            return h("div", [
                                h(
                                    "Tag", {
                                        props: {
                                            color: "magenta",
                                            size: "medium"
                                        }
                                    },
                                    params.row.requestType
                                ),
                            ]);
                        } else if (params.row.requestType === "POST") {
                            return h("div", [
                                h(
                                    "Tag", {
                                        props: {
                                            color: "orange",
                                            size: "medium"
                                        }
                                    },
                                    params.row.requestType
                                ),
                            ]);
                        } else {
                            return h("div", [
                                params.row.requestType
                            ]);
                        }

                    }
                },
                {
                    title: "请求路径",
                    minWidth: 120,
                    key: "requestUrl",
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "请求参数",
                    minWidth: 120,
                    key: "requestParam",
                    tooltip: true,
                    align: "center",
                },
                {
                    title: "操作者IP",
                    key: "ip",
                    minWidth: 150,
                    sortable: true,
                    align: "center",
                    tooltip: true,
                },
                {
                    title: "IP定位",
                    key: "ipInfo",
                    minWidth: 150,
                    align: "center",
                    sortable: true,
                    tooltip: true,
                },
                {
                    title: "操作设备",
                    key: "device",
                    minWidth: 150,
                    align: "center",
                    sortable: true,
                    tooltip: true,
                },
                {
                    title: "接口编码",
                    key: "doType",
                    minWidth: 120,
                    sortable: true,
                    align: "center",
                    tooltip: true
                },
                {
                    title: "操作时间",
                    key: "createTime",
                    sortable: true,
                    minWidth: 220,
                    align: "center",
                    sortType: "desc",
                    fixed: "right",
                    tooltip: true,
                }
            ],
            // 列筛选
            selected: [
                "序号",
                "操作名称",
                "操作者",
                "秒耗时",
                "请求类型",
                "请求路径",
                "请求参数",
                "操作者IP",
                "IP定位",
                "操作设备",
                "接口编码",
                "操作时间",
            ],
            tableHeight: 0,
            modal1: false,
            tabName: "",
            loading: true,
            selectList: [],
            selectRow: 0,
            selectDate: null,
            searchKey: "",
            searchForm: {
                logType: "1",
                pageNumber: 1,
                pageSize: 15,
                startDate: "",
                endDate: "",
                sort: "createTime",
                order: "desc",
                ipInfo: ""
            },
            data: [],
            total: 0,
            showFilterPanelFlag: false,
        };
    },
    methods: {
        openMessageModel() {
            this.$Message.info("这是一条日志!");
        },
        handleContextMenu(row) {
            this.selectRow = row;
        },
        // 顶部标签切换
        changeTab(v) {
            this.searchForm.logType = v;
            this.getLogList();
        },
        // 切换分页页码
        changePage(v) {
            this.searchForm.pageNumber = v;
            this.getLogList();
        },
        // 切换每页个数
        changePageSize(v) {
            this.searchForm.pageSize = v;
            this.getLogList();
        },
        // 搜索日志起始时间
        selectDateRange(v) {
            if (v) {
                this.searchForm.startDate = v[0];
                this.searchForm.endDate = v[1];
            }
        },
        // 表格单击事件
        rowClick(row, index) {
            this.selectRow = row;
        },
        // 表格样式变化
        rowClassNmae(row, index) {
            if (row.id == this.selectRow.id) {
                return "rowClassNmaeColor";
            }
            return "";
        },
        // 搜索
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 20;
            this.getLogList();
        },
        // 查询日志数据
        getLogList() {
            this.loading = true;
            this.searchForm.key = this.searchKey;
            getLogListData(this.searchForm).then((res) => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        },
        // 重置搜索条件
        handleReset() {
            this.searchKey = "";
            this.selectDate = null;
            this.searchForm.startDate = "";
            this.searchForm.endDate = "";
            this.getLogList();
        },
        // 切换查询排序规则
        changeSort(e) {
            this.searchForm.sort = e.key;
            this.searchForm.order = e.order;
            if (e.order == "normal") {
                this.searchForm.order = "";
            }
            this.getLogList();
        }
    },
    mounted() {
        this.getLogList();
        // 自适配屏幕高度
        this.height = window.innerHeight - this.$refs.table.$el.offsetTop - 240;
        this.tableHeight = Number(window.innerHeight - 320);
        // 列筛选
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
        // 列筛选
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
@import "../../../styles/table-common.less";

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
    right: 10px;
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
