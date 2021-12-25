<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">编辑</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left">
            <FormItem label="医生姓名" prop="doctorName">
                <Input v-model="form.doctorName" clearable style="width:570px" />
            </FormItem>
            <FormItem label="医生年龄" prop="age">
                <Input v-model="form.age" clearable style="width:570px" />
            </FormItem>
            <FormItem label="学历学位" prop="education">
                <dict dict="education" v-model="form.education" transfer clearable style="width: 570px" placeholder="学历学位" />
            </FormItem>
            <FormItem label="开始工作时间" prop="workDate">
                <DatePicker type="date" format="yyyy-MM-dd" @on-change="changeWorkDate" v-model="tempWorkDate" clearable style="width:570px"></DatePicker>
            </FormItem>
            <FormItem label="毕业学校" prop="university">
                <Input v-model="form.university" clearable style="width:570px" />
            </FormItem>
            <FormItem label="职称" prop="postLevel">
                <dict dict="postLevel" v-model="form.postLevel" transfer clearable style="width: 570px" placeholder="职称" />
            </FormItem>
            <FormItem label="专业" prop="major">
                <Input v-model="form.major" clearable style="width:570px" />
            </FormItem>
            <FormItem label="所属科室" prop="subjectName">
                <Input v-model="form.subjectName" readonly style="width:570px" />
                <Button @click="subjectModel=true" type="success">导入</Button>
            </FormItem>
            <FormItem label="医生介绍" prop="about">
                <Input v-model="form.about" clearable style="width:570px" />
            </FormItem>
            <FormItem label="照片" prop="photo">
                <uploadThumb v-model="form.photo" multiple style="width:570px"></uploadThumb>
            </FormItem>
            <Form-item class="br">
                <Button @click="handleSubmit" :loading="submitLoading" type="primary">提交并保存</Button>
                <Button @click="handleReset">重置</Button>
                <Button type="dashed" @click="close">关闭</Button>
            </Form-item>
        </Form>
    </Card>
</div>
</template>

<script>
import {
    editDoctor,
    getAllHospitalSubjectList
} from "./api.js";
import dict from "@/views/my-components/zwz/dict";
import uploadThumb from "@/views/my-components/zwz/upload-pic-input";
export default {
    name: "edit",
    components: {
        dict,
        uploadThumb
    },
    props: {
        data: Object
    },
    data() {
        return {
            tempWorkDate: "",
            subjectModel: false,
            subData: [],
            subLoading: false,
            subColumns: [{
                    title: "科室名称",
                    key: "subName",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "科室代码",
                    key: "subCode",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "科室介绍",
                    key: "about",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "成立日期",
                    key: "startDate",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "责任医生",
                    key: "dutyDoctor",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "备注",
                    key: "remark",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
            ],
            submitLoading: false, // 表单提交状态
            form: { // 添加或编辑表单对象初始化数据
                doctorName: "",
                age: "",
                education: "",
                workDate: "",
                university: "",
                postLevel: "",
                major: "",
                subjectName: "",
                about: "",
            },
            // 表单验证规则
            formValidate: {}
        };
    },
    methods: {
        init() {
            this.getAllHospitalSubjectListFx();
            this.handleReset();
            this.form = this.data;
            this.tempWorkDate = this.form.workDate;
        },
        changeWorkDate(e) {
            this.form.workDate = e;
        },
        selectSubRow(e) {
            this.form.subjectId = e.id;
            this.form.subjectName = e.subName;
            this.subjectModel = false;
        },
        getAllHospitalSubjectListFx() {
            var that = this;
            that.subLoading = true;
            getAllHospitalSubjectList().then(res => {
                that.subLoading = false;
                if (res.success) {
                    that.subData = res.result;
                }
            })
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    editDoctor(this.form).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.submited();
                        }
                    });
                }
            });
        },
        close() {
            this.$emit("close", true);
        },
        submited() {
            this.$emit("submited", true);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">
// 建议引入通用样式 具体路径自行修改 可删除下面样式代码
// @import "../../../styles/single-common.less";
.edit-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;

    .back-title {
        color: #515a6e;
        display: flex;
        align-items: center;
    }

    .head-name {
        display: inline-block;
        height: 20px;
        line-height: 20px;
        font-size: 16px;
        color: #17233d;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .window-close {
        z-index: 1;
        font-size: 12px;
        position: absolute;
        right: 0px;
        top: -5px;
        overflow: hidden;
        cursor: pointer;

        .ivu-icon-ios-close {
            color: #999;
            transition: color .2s ease;
        }
    }

    .window-close .ivu-icon-ios-close:hover {
        color: #444;
    }
}
</style>
