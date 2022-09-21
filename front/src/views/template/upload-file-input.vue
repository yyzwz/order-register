<template>
  <div>
    <div style="display:flex;">
      <Input
        v-model="currentValue"
        @on-change="handleChange"
        :placeholder="placeholder"
        :size="size"
        :disabled="disabled"
        :readonly="readonly"
        :maxlength="maxlength"
        style="margin-right:10px;"
      >
      </Input>
      <Upload
        :action="uploadFileUrl"
        :headers="accessToken"
        :on-success="handleSuccess"
        :on-error="handleError"
        :format="format"
        :accept="accept"
        :max-size="maxSize*1024"
        :on-format-error="handleFormatError"
        :on-exceeded-size="handleMaxSize"
        :before-upload="beforeUpload"
        :show-upload-list="false"
      >
        <Button type="warning" ghost :loading="loading" :size="size" :disabled="disabled" :icon="icon">{{buttonTitle}}</Button>
      </Upload>
    </div>
  </div>
</template>

<script>
import "viewerjs/dist/viewer.css";
import Viewer from "viewerjs";
import { uploadFile } from "@/api/index";
export default {
  name: "uploadPicInput",
  props: {
    value: String,
    size: String,
    placeholder: {
      type: String,
      default: "可输入文件链接"
    },
    buttonTitle: {
      type: String,
      default: "上传文件"
    },
    disabled: {
      type: Boolean,
      default: false
    },
    readonly: {
      type: Boolean,
      default: false
    },
    maxlength: Number,
    icon: {
      type: String,
      default: "ios-cloud-upload-outline"
    },
    maxSize: {
      type: Number,
      default: 100
    },
    accept: {
      type: String,
      default: ".pdf"
    }
  },
  computed: {
    format() {
      if (this.accept) {
        let format = [];
        this.accept.split(",").forEach(e => {
          format.push(e.replace(".", "").replace(" ", ""));
        });
        return format;
      } else {
        return [];
      }
    }
  },
  data() {
    return {
      accessToken: {},
      currentValue: this.value,
      loading: false,
      uploadFileUrl: uploadFile
    };
  },
  methods: {
    init() {
      this.accessToken = {
        accessToken: this.getStore("accessToken")
      };
    },
    handleFormatError(file) {
      this.loading = false;
      this.$Notice.warning({
        title: "不支持的文件格式",
        desc:
          "所选文件‘ " +
          file.name +
          " ’格式不正确, 请选择 "+this.accept+" 格式文件"
      });
    },
    handleMaxSize(file) {
      this.loading = false;
      this.$Notice.warning({
        title: "文件大小过大",
        desc:
          "所选文件‘ " +
          file.name +
          " ’大小过大, 不得超过 " +
          this.maxSize +
          "M."
      });
    },
    beforeUpload() {
      this.loading = true;
      return true;
    },
    handleSuccess(res, file) {
      this.loading = false;
      if (res.success) {
        this.currentValue = res.result;
        this.$emit("input", this.currentValue);
        this.$emit("on-change", this.currentValue);
      } else {
        this.$Message.error(res.message);
      }
    },
    handleError(error, file, fileList) {
      this.loading = false;
      this.$Message.error(error.toString());
    },
    handleChange(v) {
      this.$emit("input", this.currentValue);
      this.$emit("on-change", this.currentValue);
    },
    setCurrentValue(value) {
      if (value === this.currentValue) {
        return;
      }
      this.currentValue = value;
      this.$emit("on-change", this.currentValue);
    }
  },
  watch: {
    value(val) {
      this.setCurrentValue(val);
    }
  },
  mounted() {
    this.init();
  }
};
</script>

