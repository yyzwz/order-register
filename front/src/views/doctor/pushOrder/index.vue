<template>
<div class="search">
    <Card>
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
            <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                <Form-item ref="searchForm" :model="searchForm" inline :label-width="0" style="display:flex;">
                    <Form-item label="" prop="doctorName">
                        <Input type="text" v-model="searchForm.doctorName" placeholder="医生姓名" clearable style="width: 200px" />
                    </Form-item>
                    <Form-item label="" prop="postLevel">
                        <Input type="text" v-model="searchForm.postLevel" placeholder="职称" clearable style="width: 200px" />
                    </Form-item>
                    <Form-item label="">
                        <Select v-model="searchForm.subjectId" placeholder="所属科室" style="width:200px">
                            <Option v-for="(item,index) in subList" :value="item.id" :key="index">{{ item.subName }}</Option>
                        </Select>
                    </Form-item>
                    <Form-item style="margin-left:10px;" class="br">
                        <Button @click="handleSearch" type="primary" icon="ios-search" size="small" ghost>搜索</Button>
                        <Button @click="handleReset" type="warning" size="small" icon="md-refresh" ghost>重置</Button>
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
            <Table :loading="loading" :height="tableHeight" border stripe size="small" :columns="columns" :data="data" ref="table" sortable="custom" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-click="rowClick" :row-class-name="rowClassNmae"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[15,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
    <Modal v-model="orderModal" width="750" title="医生放号" ok-text="确认放号" @on-ok="addNumberFxFx">
        <Form :label-width="100" label-position="left">
            <Row>
                <FormItem label="医生姓名">
                    <Input v-model="zwzSelecrDoctor.doctorName" readonly style="width:570px" />
                </FormItem>
            </Row>
            <Row>
                <FormItem label="放号日期">
                    <DatePicker :placeholder="zwzOrderDate" @on-change="changeAddNumberDate" clearable style="width: 570px"></DatePicker>
                </FormItem>
            </Row>
            <Row>
                <FormItem label="放号时段">
                    <Select v-model="zwzOrderStep" style="width:570px" placeholder="请选择放号时段">
                        <Option value="0">上午</Option>
                        <Option value="1">下午</Option>
                    </Select>
                </FormItem>
            </Row>
            <Row>
                <FormItem label="放号个数">
                    <InputNumber :max="100" :min="1" v-model="zwzOrderNumber" placeholder="请输入放号个数" style="width:570px"></InputNumber>
                </FormItem>
            </Row>
        </Form>
    </Modal>
    <Modal v-model="orderFindModal" width="950" title="查询医生放号情况" footer-hide>
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
            <Form ref="orderSearchForm" :model="orderSearchForm" inline :label-width="0">
                <Form-item ref="searchForm" :model="searchForm" inline :label-width="0" style="display:flex;">
                    <Form-item label="">
                        <Select v-model="orderSearchForm.step" style="width:120px" placeholder="放号时段">
                            <Option value="0">上午</Option>
                            <Option value="1">下午</Option>
                        </Select>
                    </Form-item>
                    <Form-item label="">
                        <DatePicker :placeholder="orderSearchForm.date" @on-change="changeAddNumberDate2" clearable style="width: 120px"></DatePicker>
                    </Form-item>
                    <Form-item style="margin-left:10px;" class="br">
                        <Button @click="getDoctorSchedulingListFx" type="primary" icon="ios-search" size="small" ghost>搜索</Button>
                    </Form-item>
                </Form-item>
            </Form>
        </Row>
        <Row>
            <Table :loading="loading" :height="tableHeight" border stripe size="small" :columns="orderColumns" :data="orderData"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="orderSearchForm.pageNumber" :total="orderTotal" :page-size="orderSearchForm.pageSize" @on-change="changeOrderPage" @on-page-size-change="changeOrderPageSize" :page-size-opts="[15,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Modal>
</div>
</template>

<script>
import {
    getDoctorList,
    addNumber,
    getDoctorSchedulingList,
    getAllHospitalSubjectList
} from "./api.js";
export default {
    name: "single-window",
    components: {},
    data() {
        return {
            orderData: [],
            orderFindModal: false,
            orderModal: false,
            tableHeight: 0,
            selected: [
                "选择",
                "序号",
                "医生姓名",
                "医生年龄",
                "学历学位",
                "开始工作时间",
                "毕业学校",
                "职称",
                "专业",
                "所属科室",
                "医生介绍",
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
            zwzSelecrDoctor: {
                id: ""
            },
            orderColumns: [{
                    title: "序号",
                    width: 85,
                    align: "center",
                    fixed: "left",
                    sortType: true,
                    render: (h, params) => {
                        return h(
                            "span",
                            params.index +
                            (this.orderSearchForm.pageNumber - 1) * this.orderSearchForm.pageSize +
                            1
                        );
                    },
                },
                {
                    title: "医生姓名",
                    key: "doctorName",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "就诊日期",
                    key: "date",
                    minWidth: 120,
                    tooltip: true,
                },
                {
                    title: "就诊时段",
                    key: "step",
                    minWidth: 120,
                    tooltip: true,
                },
                {
                    title: "排号",
                    key: "number",
                    minWidth: 120,
                    tooltip: true,
                },
                {
                    title: "预约情况",
                    key: "orderFlag",
                    minWidth: 120,
                    tooltip: true,
                    render: (h, params) => {
                        if (params.row.orderFlag == 0) {
                            return h("div", [
                                h(
                                    "span", {
                                        style: {
                                            color: "#3CB371",
                                        },
                                    },
                                    "未预约"
                                ),
                            ]);
                        } else {
                            return h("div", [
                                h(
                                    "span", {
                                        style: {
                                            color: "#ff0000",
                                        },
                                    },
                                    "已预约"
                                ),
                            ]);
                        }
                    },
                },
            ],
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
                    title: "医生姓名",
                    key: "doctorName",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "医生年龄",
                    key: "age",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "学历学位",
                    key: "education",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "开始工作时间",
                    key: "workDate",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "毕业学校",
                    key: "university",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "职称",
                    key: "postLevel",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "专业",
                    key: "major",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "所属科室",
                    key: "subjectName",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "医生介绍",
                    key: "about",
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
                                            this.addNumberFx(params.row);
                                        }
                                    }
                                },
                                "放号"
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
                                            this.getDoctorSchedulingListFxFx(params.row);
                                        }
                                    }
                                },
                                "查询"
                            )
                        ]);
                    }
                }
            ],
            data: [], // 表单数据
            pageNumber: 1, // 当前页数
            pageSize: 10, // 页面大小
            total: 0, // 表单数据总数
            orderTotal: 0,
            showFilterPanelFlag: false,
            zwzOrderDate: "",
            zwzOrderStep: "0",
            zwzOrderNumber: 1,
            orderSearchForm: { // 搜索框初始化对象
                pageNumber: 1, // 当前页数
                pageSize: 15, // 页面大小
                sort: "createTime", // 默认排序字段
                order: "asc", // 默认排序方式
                doctorId: "",
                date: "",
                step: ""
            },
            subList: []
        };
    },
    methods: {
        init() {
            this.getDataList();
            this.getAllHospitalSubjectListFx();
        },
        getAllHospitalSubjectListFx() {
            var that = this;
            getAllHospitalSubjectList().then(res => {
                if (res.success) {
                    that.subList = res.result;
                }
            })
        },
        changeAddNumberDate(e) {
            this.zwzOrderDate = e;
        },
        changeAddNumberDate2(e) {
            this.orderSearchForm.date = e;
        },
        getDoctorSchedulingListFxFx(e) {
            this.orderSearchForm.doctorId = e.id;
            this.getDoctorSchedulingListFx();
            this.orderFindModal = true;
        },
        getDoctorSchedulingListFx() {
            var that = this;
            getDoctorSchedulingList(that.orderSearchForm).then(res => {
                if (res.success) {
                    that.orderData = res.result.records;
                    that.orderTotal = res.result.total;
                }
            })
        },
        addNumberFxFx() {
            var that = this;
            addNumber({
                doctorId: that.zwzSelecrDoctor.id,
                date: that.zwzOrderDate,
                step: that.zwzOrderStep,
                number: that.zwzOrderNumber
            }).then(res => {
                if (res.success) {
                    this.$Message.success("放号成功");
                }
            })
        },
        addNumberFx(e) {
            this.zwzSelecrDoctor = e;
            this.zwzOrderDate = new Date().getFullYear() + "-" + ((new Date().getMonth() > 8) ? (new Date().getMonth() + 1) : "0" + (new Date().getMonth() + 1)) + "-" + (new Date().getDate() > 9 ? new Date().getDate() : "0" + new Date().getDate());
            this.orderModal = true;
        },
        submited() {
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
        changeOrderPage(v) {
            this.orderSearchForm.pageNumber = v;
            this.getDoctorSchedulingListFx();
        },
        changeOrderPageSize(v) {
            this.orderSearchForm.pageSize = v;
            this.getDoctorSchedulingListFx();
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
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
            this.getDataList();
        },
        handleReset() {
            this.searchForm = { // 搜索框初始化对象
                pageNumber: 1, // 当前页数
                pageSize: 15, // 页面大小
                sort: "createTime", // 默认排序字段
                order: "desc", // 默认排序方式
            },
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
            getDoctorList(this.searchForm).then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        },
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
    background-color: #b0b3b6 !important;
    color: #ffffff !important;
    font-size: 12px;
}
</style>
