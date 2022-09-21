<template>
<div class="search">
    <Card>
        <Row type="flex" justify="space-between">
            <Col v-if="expand" span="4">
            <Row class="operation">
                <Button @click="refreshDict" type="success" ghost size="small">搜索</Button>
                <Button @click="addDcit" type="info" ghost size="small">添加</Button>
                <Button @click="editDcit" type="warning" ghost size="small" :disabled="!this.selectNode.id">编辑</Button>
                <Button @click="delDcit" type="error" ghost size="small">删除</Button>
            </Row>
            <Row :gutter="16">
                <Alert type="success" show-icon v-show="editTitle">
                    <span class="select-title">{{editTitle}}</span>
                    <a class="select-clear" @click="cancelEdit">取消</a>
                </Alert>
            </Row>
            <Row :gutter="16">
                <Input v-model="searchKey" suffix="ios-search" @on-change="search" placeholder="输入搜索字典" clearable style="width:100%" />
            </Row>
            <Row :gutter="16">
                <div class="tree-bar" :style="{maxHeight: maxHeight}">
                    <Tree ref="tree" :data="treeData" @on-select-change="selectTree" empty-text="没有数据字典"></Tree>
                    <Spin size="large" fix v-if="treeLoading"></Spin>
                </div>
            </Row>
            </Col>
            <div class="expand">
                <Icon :type="expandIcon" size="16" class="icon" @click="changeExpand" />
            </div>
            <Col :span="span">
            <Row :gutter="16">
                <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                    <Form-item label="">
                        <Input type="text" v-model="searchForm.title" placeholder="数据名称" clearable style="width: 120px" />
                    </Form-item>
                    <Form-item label="">
                        <Input type="text" v-model="searchForm.value" placeholder="数据值" clearable style="width: 120px" />
                    </Form-item>
                    <Form-item label="">
                        <Input type="text" v-model="searchForm.description" placeholder="数据描述" clearable style="width: 120px" />
                    </Form-item>
                    <Form-item label="">
                        <Select v-model="searchForm.status" placeholder="状态" clearable style="width: 120px">
                            <Option value="0">正常</Option>
                            <Option value="-1">禁用</Option>
                        </Select>
                    </Form-item>
                    <Form-item class="br">
                        <Button @click="handleSearch" ghost type="info" icon="ios-search" size="small">搜索数据</Button>
                        <Button @click="handleReset" ghost type="warning" icon="md-refresh" size="small">重置数据</Button>
                        <Button @click="add" ghost type="success" icon="md-add" size="small">添加数据</Button>
                        <Button @click="delAll" ghost type="error" icon="md-trash" size="small">删除数据</Button>
                    </Form-item>
                    <Alert type="success" show-icon v-show="selectCount > 0">
                        已选择
                        <span class="select-count">{{selectCount}}</span> 项
                        <a class="select-clear" @click="clearSelectAll">清空</a>
                    </Alert>
                </Form>
            </Row>
            <Row class="operation">
            </Row>
            <Row>
                <Table :loading="loading" stripe border :columns="columns" :data="data" sortable="custom" @on-sort-change="changeSort" @on-selection-change="showSelect" ref="table" @on-row-click="rowClick" :row-class-name="rowClassName"></Table>
            </Row>
            <Row type="flex" justify="end" class="page">
                <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[10,20,50]" size="small" show-total show-elevator show-sizer></Page>
            </Row>
            </Col>
        </Row>
    </Card>

    <Modal :title="dictModalTitle" v-model="dictModalVisible" :mask-closable="false" :width="500">
        <Form ref="dictForm" :model="dictForm" :label-width="85" :rules="dictFormValidate">
            <FormItem label="字典名称" prop="title">
                <Input v-model="dictForm.title" />
            </FormItem>
            <FormItem label="字典类型" prop="type" class="block-tool">
                <Tooltip placement="right" :max-width="220" transfer content="建议英文名且需唯一 非开发人员谨慎修改">
                    <Input v-model="dictForm.type" />
                </Tooltip>
            </FormItem>
            <FormItem label="备注" prop="description">
                <Input v-model="dictForm.description" />
            </FormItem>
            <FormItem label="排序值" prop="sortOrder">
                <Tooltip trigger="hover" placement="right" content="值越小越靠前，支持小数">
                    <InputNumber :max="1000" :min="0" v-model="dictForm.sortOrder"></InputNumber>
                </Tooltip>
            </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="dictModalVisible=false">取消</Button>
            <Button type="primary" :loading="submitLoading" @click="handelSubmitDict">提交</Button>
        </div>
    </Modal>

    <Modal :title="modalTitle" v-model="modalVisible" :mask-closable="false" :width="500">
        <Form ref="form" :model="form" :label-width="80" :rules="formValidate">
            <FormItem label="名称" prop="title">
                <Input v-model="form.title" />
            </FormItem>
            <FormItem label="数据值" prop="value">
                <Input v-model="form.value" />
            </FormItem>
            <FormItem label="备注" prop="description">
                <Input v-model="form.description" />
            </FormItem>
            <FormItem label="排序值" prop="sortOrder">
                <Tooltip trigger="hover" placement="right" content="值越小越靠前，支持小数">
                    <InputNumber :max="1000" :min="0" v-model="form.sortOrder"></InputNumber>
                </Tooltip>
            </FormItem>
            <FormItem label="是否启用" prop="status">
                <i-switch size="large" v-model="form.status" :true-value="0" :false-value="-1">
                    <span slot="open">启用</span>
                    <span slot="close">禁用</span>
                </i-switch>
            </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="modalVisible=false">取消</Button>
            <Button type="primary" :loading="submitLoading" @click="handelSubmit">提交</Button>
        </div>
    </Modal>
</div>
</template>

<script>
import {
    getAllDictList,
    addDict,
    editDict,
    deleteDict,
    searchDict,
    getAllDictDataList,
    addDictData,
    editDictData,
    deleteData
} from "./api.js";
export default {
    name: "dic-manage",
    data() {
        return {
          selected: [
                "选择",
                "编号",
                "所属字典",
                "键",
                "值",
                "描述",
                "排序",
                "是否禁用",
                "操作"
          ],
            treeLoading: false, // 树加载状态
            maxHeight: "500px",
            loading: false, // 表格加载状态
            editTitle: "", // 编辑节点名称
            searchKey: "", // 搜索树
            expand: true,
            span: 18,
            expandIcon: "md-arrow-round-back",
            selectNode: {},
            treeData: [], // 树数据
            selectCount: 0, // 多选计数
            selectList: [], // 多选数据
            searchForm: {
                // 搜索框对应data对象
                name: "",
                status: "",
                pageNumber: 1, // 当前页数
                pageSize: 10, // 页面大小
                sort: "sortOrder", // 默认排序字段
                order: "asc" // 默认排序方式
            },
            modalType: 0, // 添加或编辑标识
            modalVisible: false, // 添加或编辑显示
            dictModalVisible: false,
            dictModalTitle: "",
            modalTitle: "", // 添加或编辑标题
            dictForm: {
                title: "",
                type: "",
                description: "",
                sortOrder: 0
            },
            form: {
                // 添加或编辑表单对象初始化数据
                title: "",
                value: "",
                status: 0,
                description: "",
                sortOrder: 0
            },
            dictFormValidate: {
                // 表单验证规则
                title: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                type: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                sortOrder: [{
                    required: true,
                    type: "number",
                    message: "排序值不能为空",
                    trigger: "blur"
                }]
            },
            formValidate: {
                // 表单验证规则
                title: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                value: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                sortOrder: [{
                    required: true,
                    type: "number",
                    message: "排序值不能为空",
                    trigger: "blur"
                }]
            },
            columns: [{
                    title: "选择",
                    type: "selection",
                    minWidth: 60,
                    align: "center"
                },
                {
                    title: "编号",
                    type: "index",
                    minWidth: 90,
                    align: "center"
                },
                {
                    title: "所属字典",
                    key: "dictName",
                    minWidth: 120,
                    sortable: true
                },
                {
                    title: "键",
                    key: "title",
                    minWidth: 120,
                    sortable: true
                },
                {
                    title: "值",
                    key: "value",
                    minWidth: 120,
                    sortable: true
                },
                {
                    title: "描述",
                    key: "description",
                    minWidth: 120,
                    sortable: true
                },
                {
                    title: "排序",
                    key: "sortOrder",
                    minWidth: 90,
                    sortable: true,
                    sortType: "asc"
                },
                {
                    title: "是否禁用",
                    key: "status",
                    align: "center",
                    minWidth: 120,
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
                },
                {
                    title: "创建时间",
                    key: "createTime",
                    minWidth: 180,
                    sortable: true
                },
                {
                    title: "操作",
                    key: "action",
                    width: 180,
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
                                        icon: "ios-brush"
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
                                        ghost: true,
                                        icon: "md-close"
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
            submitLoading: false, // 添加或编辑提交状态
            data: [], //表单数据
            total: 0, // 表单数据总数
            selectRow: {}
        };
    },
    methods: {
        init() {
            // 获取树数据
            this.getAllDict();
            // 获取表单数据
            this.getDataList();
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
        getAllDict() {
            this.treeLoading = true;
            getAllDictList().then(res => {
                this.treeLoading = false;
                if (res.success) {
                    this.treeData = res.result;
                }
            });
        },
        search() {
            // 搜索树
            if (this.searchKey) {
                this.treeLoading = true;
                searchDict({
                    key: this.searchKey
                }).then(res => {
                    this.treeLoading = false;
                    if (res.success) {
                        this.treeData = res.result;
                    }
                });
            } else {
                // 为空重新加载
                this.getAllDict();
            }
        },
        selectTree(v) {
            if (v.length > 0) {
                this.$refs.dictForm.resetFields();
                // 转换null为""
                for (let attr in v[0]) {
                    if (v[0][attr] == null) {
                        v[0][attr] = "";
                    }
                }
                let str = JSON.stringify(v[0]);
                let data = JSON.parse(str);
                this.selectNode = data;
                this.dictForm = data;
                this.editTitle = data.title + "(" + data.type + ")";
                // 重新加载表
                this.searchForm.pageNumber = 1;
                this.searchForm.pageSize = 10;
                this.getDataList();
            } else {
                this.cancelEdit();
            }
        },
        cancelEdit() {
            let data = this.$refs.tree.getSelectedNodes()[0];
            if (data) {
                data.selected = false;
            }
            // 取消选择后获取全部数据
            this.selectNode = {};
            this.editTitle = "";
            this.getDataList();
        },
        changeSelect(v) {
            this.selectCount = v.length;
            this.selectList = v;
        },
        changeExpand() {
            this.expand = !this.expand;
            if (this.expand) {
                this.expandIcon = "md-arrow-round-back";
                this.span = 19;
            } else {
                this.expandIcon = "md-arrow-round-forward";
                this.span = 23;
            }
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
        getDataList() {
            this.loading = true;
            if (this.selectNode.id) {
                this.searchForm.dictId = this.selectNode.id;
            } else {
                delete this.searchForm.dictId;
            }
            if (!this.searchForm.status) {
                this.searchForm.status = "";
            }
            getAllDictDataList(this.searchForm).then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        },
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 10;
            this.getDataList();
        },
        handleReset() {
            this.$refs.searchForm.resetFields();
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 10;
            // 重新加载数据
            this.getDataList();
        },
        changeSort(e) {
            this.searchForm.sort = e.key;
            this.searchForm.order = e.order;
            if (e.order == "normal") {
                this.searchForm.order = "";
            }
            this.getDataList();
        },
        showSelect(e) {
            this.selectList = e;
            this.selectCount = e.length;
        },
        clearSelectAll() {
            this.$refs.table.selectAll(false);
        },
        refreshDict() {
            this.getAllDict();
            this.selectNode = {};
            this.editTitle = "";
            this.getDataList();
        },
        addDcit() {
            this.modalType = 0;
            this.dictModalTitle = "添加字典";
            this.$refs.dictForm.resetFields();
            this.dictModalVisible = true;
        },
        editDcit() {
            this.modalType = 1;
            this.dictModalTitle = "编辑字典";
            this.dictModalVisible = true;
        },
        delDcit() {
            if (!this.selectNode.id) {
                this.$Message.warning("您还未选择要删除的字典");
                return;
            }
            this.$Modal.confirm({
                title: "确认删除",
                loading: true,
                content: "您确认要删除 " + this.selectNode.title + " ?",
                onOk: () => {
                    // 删除
                    deleteDict({
                        ids: this.selectNode.id
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.refreshDict();
                        }
                    });
                }
            });
        },
        add() {
            if (!this.selectNode.id) {
                this.$Message.warning("请先选择一个字典类别");
                return;
            }
            this.modalType = 0;
            this.modalTitle = "添加字典 " + this.editTitle + " 的数据";
            this.$refs.form.resetFields();
            this.modalVisible = true;
        },
        edit(v) {
            this.modalType = 1;
            this.modalTitle = "编辑字典 " + this.editTitle + " 的数据";
            this.$refs.form.resetFields();
            // 转换null为""
            for (let attr in v) {
                if (v[attr] == null) {
                    v[attr] = "";
                }
            }
            let str = JSON.stringify(v);
            let data = JSON.parse(str);
            this.form = data;
            this.modalVisible = true;
        },
        handelSubmitDict() {
            this.$refs.dictForm.validate(valid => {
                if (valid) {
                    this.submitLoading = true;
                    if (this.modalType == 0) {
                        // 添加 避免编辑后传入id等数据 记得删除
                        delete this.dictForm.id;
                        addDict(this.dictForm).then(res => {
                            this.submitLoading = false;
                            if (res.success) {
                                this.$Message.success("操作成功");
                                this.getAllDict();
                                this.dictModalVisible = false;
                            }
                        });
                    } else if (this.modalType == 1) {
                        // 编辑
                        editDict(this.dictForm).then(res => {
                            this.submitLoading = false;
                            if (res.success) {
                                this.$Message.success("操作成功");
                                this.getAllDict();
                                this.dictModalVisible = false;
                            }
                        });
                    }
                }
            });
        },
        handelSubmit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    this.submitLoading = true;
                    if (this.modalType == 0) {
                        // 添加 避免编辑后传入id等数据 记得删除
                        delete this.form.id;
                        this.form.dictId = this.selectNode.id;
                        addDictData(this.form).then(res => {
                            this.submitLoading = false;
                            if (res.success) {
                                this.$Message.success("操作成功");
                                this.getDataList();
                                this.modalVisible = false;
                            }
                        });
                    } else if (this.modalType == 1) {
                        // 编辑
                        editDictData(this.form).then(res => {
                            this.submitLoading = false;
                            if (res.success) {
                                this.$Message.success("操作成功");
                                this.getDataList();
                                this.modalVisible = false;
                            }
                        });
                    }
                }
            });
        },
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除 " + v.title + " ?",
                loading: true,
                onOk: () => {
                    // 删除
                    deleteData({
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
                    deleteData({
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
        // 计算高度
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
