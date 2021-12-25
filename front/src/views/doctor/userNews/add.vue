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
      <Form
        ref="form"
        :model="form"
        :label-width="120"
        :rules="formValidate"
        label-position="right"
      >
        <FormItem label="新闻标题" prop="newTitle">
          <Input v-model="form.newTitle" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="新闻时效" prop="periodTime">
          <DatePicker
            :editable="false"
            type="datetimerange"
            format="yyyy-MM-dd HH:mm:ss"
            @on-change="changeServiceDate"
            style="width: 570px"
          ></DatePicker>
          <!-- <Input v-model="form.periodTime" clearable style="width:570px"/> -->
        </FormItem>
        <FormItem label="是否公开" prop="isPublic">
          <Select v-model="form.isPublic" clearable style="width: 570px">
            <Option value="yes">是</Option>
            <Option value="no">否</Option>
          </Select>
        </FormItem>
        <FormItem label="图片" prop="photo">
          <upload-pic-input
            v-model="form.photo"
            style="width: 570px"
          ></upload-pic-input>
        </FormItem>
        <Form-item class="br">
          <Button @click="handleSubmit" :loading="submitLoading" type="primary"
            >提交并保存</Button
          >
          <Button @click="handleReset">重置</Button>
          <Button type="dashed" @click="close">关闭</Button>
        </Form-item>
        <Divider dashed />
        <FormItem label="新闻描述" prop="newDescribe">
          <VueUeditorWrap v-model="form.newDescribe" :config="editorConfig" editor-id="editor-demo-01"/>
          <!-- <Input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 9 }"
            v-model="form.newDescribe"
            clearable
            style="width: 570px"
          /> -->
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script>
import VueUeditorWrap from 'vue-ueditor-wrap';
import { addDynamic_new, getServiceByIds } from "./api.js";
import uploadPicInput from "@/views/my-components/zwz/upload-pic-input";
export default {
  name: "add",
  components: {
    uploadPicInput,
    VueUeditorWrap
  },
  data() {
    return {
      currentPublishOrganizationId: "-1",
      submitLoading: false, // 表单提交状态
      form: {
        // 添加或编辑表单对象初始化数据
        newTitle: "",
        newDescribe: "",
        periodTime: "",
        isPublic: "yes",
        photo: "",
        relateServiceId: "",
        relateServiceName: "",
      },
      // 表单验证规则
      formValidate: {
        newTitle: [
          { required: true, message: "不能为空", trigger: "blur" },
          {
            type: "string",
            max: 100,
            message: "不能超过100个字符",
            trigger: "blur",
          },
        ],
        newDescribe: [
          { required: true, message: "不能为空", trigger: "blur" },
          {
            type: "string",
            max: 200,
            message: "不能超过200个字符",
            trigger: "blur",
          },
        ],
        periodTime: [{ required: true, message: "不能为空", trigger: "blur" }],
      },
    };
  },
  methods: {
    init() {},
    getServiceIdsFormSon(data) {
      this.form.relateServiceId = data;
      this.relateServiceDrawer = false;
      var params = {
        ids: data,
      };
      getServiceByIds(params).then((res) => {
        this.submitLoading = false;
        var serviceNames = "";
        if (res.success) {
          for (var i = 0; i < res.result.length; i++) {
            if (serviceNames != "") serviceNames = serviceNames + "\n";
            serviceNames = serviceNames + (i+1)+"."+ res.result[i].serviceName;
          }
          this.form.relateServiceName = serviceNames;
        }
      });
    },
    handleReset() {
      this.$refs.form.resetFields();
    },
    changeServiceDate(e) {
      this.form.periodTime = e[0] + " - " + e[1];
    },
    handleSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          addDynamic_new(this.form).then((res) => {
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
    },
  },
  created() {
    this.editorConfig = {
      UEDITOR_HOME_URL: '/UEditor/',
      // 编辑器不自动被内容撑高
      autoHeightEnabled: false,
      // 初始容器高度
      initialFrameHeight: 400,
      // 初始容器宽度
      initialFrameWidth: '100%',
      serverUrl: 'https://zwz99.top:16888/ueditor/jsp/controller.jsp',
      // serverUrl: '//ueditor.szcloudplus.com/cos',
    };
  },
  mounted() {
    this.init();
  },
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
      transition: color 0.2s ease;
    }
  }

  .window-close .ivu-icon-ios-close:hover {
    color: #444;
  }
}
</style>