<template>
<div class="search">
    <Card>
        <Form ref="searchForm" :model="searchForm" inline :label-width="100">
            <Row class="operation">
                <Form-item label="API接口前缀">
                    <Input type="text" v-model="apiPreStr" clearable placeholder="如 test" style="width: 300px" />
                </Form-item>
                <Button @click="findBackFieldModal = true" type="warning" size="small" ghost shape="circle" icon="ios-construct">读取</Button>
                <Button @click="generate" :loading="createCodeLoading" size="small" ghost shape="circle" type="success" icon="md-send">生成</Button>
                <Button @click="clear" size="small" type="error" ghost shape="circle" icon="md-trash">清空</Button>
            </Row>
        </Form>
        <Row>
            <Col span="24">
            <Table border stripe size="large" :columns="columns" :data="data" ref="table">
                <template slot-scope="{ row }" slot="field">
                    <Input v-model="row.field" @on-blur="changeField(row, $event)" />
                </template>
                <template slot-scope="{ row }" slot="name">
                    <Input v-model="row.name" @on-blur="changeName(row, $event)" />
                </template>
                <template slot-scope="{ row }" slot="tableShow">
                    <i-switch v-model="row.tableShow" @on-change="changeTableShow(row, $event)">
                        <span slot="open">开</span>
                        <span slot="close">关</span>
                    </i-switch>
                </template>
                <template slot-scope="{ row }" slot="editable">
                    <i-switch v-model="row.editable" @on-change="changeEditable(row, $event)">
                        <span slot="open">开</span>
                        <span slot="close">关</span>
                    </i-switch>
                </template>
                <template slot-scope="{ row }" slot="type">
                    <Select v-model="row.type" :disabled="!row.editable" @on-change="changeType(row, $event)" transfer>
                        <Option value="text">文本输入框</Option>
                        <Option value="select">选择下拉框</Option>
                        <Option value="number">数字输入框</Option>
                        <Option value="date">日期选择器</Option>
                        <Option value="daterange">日期范围选择器</Option>
                        <Option value="upload">图片上传输入组件</Option>
                        <Option value="uploadThumb">图片上传缩略图组件</Option>
                        <Option value="switch">开关</Option>
                        <Option value="radio">单选框</Option>
                        <Option value="time">时间选择器</Option>
                        <Option value="area">省市县级联</Option>
                    </Select>
                </template>
                <template slot-scope="{ row }" slot="validate">
                    <i-switch v-model="row.validate" @on-change="changeValidate(row, $event)" :disabled="!row.editable">
                        <span slot="open">开</span>
                        <span slot="close">关</span>
                    </i-switch>
                </template>
                <template slot-scope="{ row }" slot="searchable">
                    <i-switch v-model="row.searchable" @on-change="changeSearchable(row, $event)">
                        <span slot="open">开</span>
                        <span slot="close">关</span>
                    </i-switch>
                </template>
                <template slot-scope="{ row }" slot="searchType">
                    <Select v-model="row.searchType" @on-change="changeSearchType(row, $event)" :disabled="!row.searchable" transfer>
                        <Option value="text">文本输入框</Option>
                        <Option value="select">选择下拉框</Option>
                        <Option value="daterange" :disabled="daterangeSearch">日期范围选择器</Option>
                        <Option value="date">日期选择器</Option>
                        <Option value="area">省市县级联</Option>
                    </Select>
                </template>
                <template slot-scope="{ row }" slot="sortable">
                    <i-switch v-model="row.sortable" @on-change="changeSortable(row, $event)">
                        <span slot="open">开</span>
                        <span slot="close">关</span>
                    </i-switch>
                </template>
                <template slot-scope="{ row }" slot="defaultSort">
                    <i-switch v-model="row.defaultSort" @on-change="changeDefaultSort(row, $event)" :disabled="(!row.sortable||defaultSort)&&!row.defaultSort||!row.sortable">
                        <span slot="open">开</span>
                        <span slot="close">关</span>
                    </i-switch>
                </template>
                <template slot-scope="{ row }" slot="defaultSortType">
                    <Select v-model="row.defaultSortType" @on-change="changeDefaultSortType(row, $event)" :disabled="!row.defaultSort||!row.sortable" transfer>
                        <Option value="desc">倒序</Option>
                        <Option value="asc">正序</Option>
                    </Select>
                </template>
                <template slot-scope="{ row }" slot="action">
                    <Button type="error" ghost size="small" @click="remove(row)">删除</Button>
                </template>
            </Table>
            </Col>
        </Row>
    </Card>

    <!-- 读取后端字段窗口 -->
    <Modal title="读取后端字段" v-model="findBackFieldModal" :width="800" @on-ok="submitEntityData">
        <Form ref="entityForm" :model="entityForm" :label-width="130" :rules="entityFormValidate">
            <FormItem label="实体类引用路径" prop="path">
                <Row type="flex" justify="space-between">
                    <Input v-model="entityForm.path" placeholder="例如：cn.zwz.modules.base.entity.User" clearable style="width:410px" />
                    <Button type="warning" icon="md-play" @click="generateEntityData">读取字段</Button>
                </Row>
            </FormItem>
        </Form>
        <Input v-model="findBackFieldContent" type="textarea" autosize style="max-height:50vh;overflow:auto;" placeholder="生成结果" />
    </Modal>

    <!-- 生成前端代码 -->
    <Modal title="生成代码" v-model="codeModal" :width="1000" styles="top:30px">
        <Tabs v-model="tabName" type="card" @on-click="changeCode">
            <TabPane name="index.vue" label="index.vue"></TabPane>
            <TabPane name="add.vue" label="add.vue"></TabPane>
            <TabPane name="edit.vue" label="edit.vue"></TabPane>
            <TabPane name="api.js" label="api.js"></TabPane>
        </Tabs>
        <Input v-model="createCodeContent" type="textarea" autosize style="max-height:60vh;overflow:auto;" />
        <div slot="footer">
            <Button v-clipboard:copy="createCodeContent" v-clipboard:success="onCopy" type="primary" icon="md-copy">复制代码</Button>
        </div>
    </Modal>
</div>
</template>

<script>
import {
    generateTable,
    getEntityData
} from "./api.js";
export default {
    name: "vue-code",
    data() {
        return {
            tabName: "index.vue",
            // 控制是否显示读取后端字段窗口
            findBackFieldModal: false,
            // 后端字段读取结果
            findBackFieldContent: "",
            // 后端实体类完整路径
            entityForm: {
                path: ""
            },
            // 生成代码加载标识
            createCodeLoading: false,
            // 生成的代码
            createCodeContent: "",
            // 接口前缀
            apiPreStr: "",
            // 生成前端代码窗口是否显示
            codeModal: false,
            form: {
                sortOrder: 0,
                field: "",
                name: "",
                level: "2",
                tableShow: true,
                editable: true,
                type: "text",
                searchType: "text",
                searchLevel: "2",
                validate: true,
                searchable: false,
                sortable: false,
                defaultSort: false,
                defaultSortType: "desc"
            },
            // 代码生成器表单规则
            formValidate: {
                field: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }],
                name: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }]
            },
            // 读取后端字段表单规则
            entityFormValidate: {
                path: [{
                    required: true,
                    message: "不能为空",
                    trigger: "blur"
                }]
            },
            // 代码生成器表格列
            columns: [{
                    title: "编号",
                    key: "sortOrder",
                    sortType: "asc",
                    sortable: true,
                    align: "center",
                    minWidth: 90
                },
                {
                    title: "后端字段名",
                    slot: "field",
                    minWidth: 150
                },
                {
                    title: "备注",
                    slot: "name",
                    minWidth: 150
                },
                {
                    title: "表格显示",
                    slot: "tableShow",
                    minWidth: 110
                },
                {
                    title: "可添加编辑",
                    slot: "editable",
                    minWidth: 110
                },
                {
                    title: "输入组件",
                    slot: "type",
                    minWidth: 200
                },
                {
                    title: "非空",
                    slot: "validate",
                    minWidth: 125
                },
                {
                    title: "搜索",
                    slot: "searchable",
                    minWidth: 100
                },
                {
                    title: "搜索组件",
                    slot: "searchType",
                    minWidth: 160
                },
                {
                    title: "排序",
                    slot: "sortable",
                    minWidth: 80
                },
                {
                    title: "默认排序",
                    slot: "defaultSort",
                    minWidth: 100
                },
                {
                    title: "默认排序方式",
                    slot: "defaultSortType",
                    minWidth: 150
                },
                {
                    title: "操作",
                    slot: "action",
                    align: "center",
                    fixed: "right",
                    width: 90
                }
            ],
            // 代码生成器表格值
            data: [],
            defaultSort: false,
            daterangeSearch: false,
            result: {}
        };
    },
    methods: {
        init() {
            let data = this.getStore("tableData");
            let apiPreStr = this.getStore("createCodeApiPre");
            let defaultSort = this.getStore("tableDefaultSort");
            let daterangeSearch = this.getStore("tableDaterangeSearch");
            if (data && data != "undefined") {
                this.data = JSON.parse(data);
            }
            if (apiPreStr && apiPreStr != "undefined") {
                this.apiPreStr = apiPreStr;
            }
            if (defaultSort && defaultSort != "undefined") {
                if (defaultSort == "true") {
                    this.defaultSort = true;
                } else {
                    this.defaultSort = false;
                }
            }
            if (daterangeSearch && daterangeSearch != "undefined") {
                if (daterangeSearch == "true") {
                    this.daterangeSearch = true;
                } else {
                    this.daterangeSearch = false;
                }
            }
        },
        // 读取字段
        generateEntityData() {
            this.$refs.entityForm.validate(valid => {
                if (valid) {
                    getEntityData(this.entityForm.path).then(res => {
                        if (res.success) {
                            this.findBackFieldContent = res.result;
                        }
                    });
                }
            });
        },
        // 将读取到的后端字段导入到表格
        submitEntityData() {
            if (!this.findBackFieldContent) {
                this.$Message.warning("后端实体类没有读到，请重试");
                return;
            }
            try {
                let data = JSON.parse(this.findBackFieldContent);
                this.data = data.data;
                this.findBackFieldModal = false;
            } catch (e) {
                this.$Message.error("后端实体类解析异常: " + e);
            }
        },
        changeField(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.field = v.target.value;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeName(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.name = v.target.value;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeTableShow(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.tableShow = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeType(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.type = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeEditable(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.editable = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeValidate(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.validate = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeSearchable(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.searchable = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeSearchType(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.searchType = v;
            this.data.splice(row._index, 1, data);
            if (this.daterangeSearch && v != "daterange") {
                this.daterangeSearch = false;
            } else if (v == "daterange") {
                this.daterangeSearch = true;
            }
            this.save();
        },
        changeSortable(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.sortable = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeDefaultSort(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            // 判断默认排序
            if (this.defaultSort && v) {
                this.$Message.warning("已设定了默认排序的字段，请勿设置多个");
                // 恢复禁用
                data.defaultSort = false;
            } else if (!this.defaultSort && v) {
                // 没有记录过
                data.defaultSort = true;
                this.defaultSort = true;
            } else if (this.defaultSort && !v) {
                // 取消唯一的
                data.defaultSort = false;
                this.defaultSort = false;
            }
            this.data.splice(row._index, 1, data);
            this.save();
        },
        changeDefaultSortType(row, v) {
            for (let attr in row) {
                if (row[attr] == null) {
                    row[attr] = "";
                }
            }
            let str = JSON.stringify(row);
            let data = JSON.parse(str);
            data.defaultSortType = v;
            this.data.splice(row._index, 1, data);
            this.save();
        },
        remove(v) {
            this.data.splice(v._index, 1);
            this.save();
            this.$Message.success("删除成功");
        },
        clear() {
            this.$Modal.confirm({
                title: "确认清空",
                content: "您确认要清空 ?",
                onOk: () => {
                    this.data = [];
                    this.apiPreStr = "";
                    this.defaultSort = false;
                    this.daterangeSearch = false;
                    this.save();
                    this.$Message.success("清空成功");
                }
            });
        },
        generate() {
            if (!this.apiPreStr) {
                this.$Message.warning("接口前缀不能为空");
                return;
            }
            this.createCodeLoading = true;
            generateTable(this.apiPreStr, "1", this.data).then(res => {
                this.createCodeLoading = false;
                if (res.success) {
                    this.result = res.result;
                    this.changeCode();
                    this.codeModal = true;
                    this.save();
                }
            });
        },
        changeCode() {
            if (this.tabName == "index.vue") {
                this.createCodeContent = this.result.componentApi;
            }
            if (this.tabName == "add.vue") {
                this.createCodeContent = this.result.addApi;
            }
            if (this.tabName == "edit.vue") {
                this.createCodeContent = this.result.editApi;
            }
            if (this.tabName == "api.js") {
                this.createCodeContent = this.result.api;
            }
        },
        onCopy() {
            this.$Message.success("复制成功");
        },
        save() {
            this.setStore("tableData", JSON.stringify(this.data));
            this.setStore("createCodeApiPre", this.apiPreStr);
            this.setStore("tableDefaultSort", this.defaultSort);
            this.setStore("tableDaterangeSearch", this.daterangeSearch);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">
.search {
    .operation {
        margin-bottom: 2vh;
    }
}
</style>
