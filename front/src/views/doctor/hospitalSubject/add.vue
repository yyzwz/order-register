<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">添加</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left">
            <FormItem label="科室名称" prop="subName">
                <Input v-model="form.subName" clearable style="width:570px" placeholder="请填写该科室的科室名称"  />
            </FormItem>
            <FormItem label="科室代码" prop="subCode">
                <Input v-model="form.subCode" clearable style="width:570px" placeholder="请填写该科室的科室代码" />
            </FormItem>
            <FormItem label="科室介绍" prop="about">
                <Input v-model="form.about" type="textarea" :rows="4" placeholder="请填写该科室的介绍信息" clearable style="width:570px" />
            </FormItem>
            <FormItem label="成立日期" prop="startDate">
                <DatePicker type="date" @on-change="changeStartDate" placeholder="请填写该科室的成立日期" format="yyyy-MM-dd" clearable style="width:570px"></DatePicker>
            </FormItem>
            <FormItem label="责任医生" prop="dutyDoctor">
                <Input v-model="form.dutyDoctor" placeholder="请填写该科室的责任医生" clearable style="width:570px" />
            </FormItem>
            <FormItem label="备注" prop="remark">
                <Input v-model="form.remark" type="textarea" :rows="4" placeholder="请选填该科室的备注信息" clearable style="width:570px" />
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
    addHospitalSubject
} from "./api.js";
export default {
    name: "add",
    components: {},
    data() {
        return {
            submitLoading: false, // 表单提交状态
            form: { // 添加或编辑表单对象初始化数据
                subName: "",
                subCode: "",
                about: "",
                startDate: "",
                dutyDoctor: "",
                remark: "",
            },
            // 表单验证规则
            formValidate: {}
        };
    },
    methods: {
        init() {},
        changeStartDate(e) {
            this.form.startDate = e;
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    addHospitalSubject(this.form).then(res => {
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
