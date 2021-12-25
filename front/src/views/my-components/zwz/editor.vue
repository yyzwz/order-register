<template>
  <div>
    <div style="position:relative">
      <div :id="id" style="min-width:800px"></div>
      <div v-if="showExpand">
        <div class="e-menu e-code" @click="editHTML">
          <Icon type="md-code-working" size="22" />
        </div>
        <div class="e-menu e-preview" @click="fullscreenModal=true">
          <Icon type="ios-eye" size="24" />
        </div>
        <div class="e-menu e-trash" @click="clear">
          <Icon type="md-trash" size="18" />
        </div>
      </div>
    </div>

    <Modal
      title="编辑html代码"
      v-model="showHTMLModal"
      :mask-closable="false"
      :width="900"
      :fullscreen="full"
    >
      <Input
        v-if="!full"
        v-model="dataEdit"
        :rows="15"
        type="textarea"
        style="max-height:60vh;overflow:auto;"
      />
      <Input v-if="full" v-model="dataEdit" :rows="32" type="textarea" />
      <div slot="footer">
        <Button @click="full=!full" icon="md-expand">全屏开/关</Button>
        <Button @click="editHTMLOk" type="primary" icon="md-checkmark-circle-outline">确定保存</Button>
      </div>
    </Modal>
    <Modal title="预览" v-model="fullscreenModal" fullscreen>
      <div v-html="data">{{data}}</div>
      <div slot="footer">
        <Button @click="fullscreenModal=false">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import { uploadFile } from "@/api/index";
import E from "wangeditor";
import xss from "xss";
// 表情包配置 自定义表情可在该js文件中统一修改
import { sina } from "@/libs/emoji";
var editor = null;
export default {
  name: "editor",
  props: {
    id: {
      type: String,
      default: "editor"
    },
    value: String,
    base64: {
      type: Boolean,
      default: false
    },
    showExpand: {
      type: Boolean,
      default: true
    },
    openXss: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      editor: null,
      data: this.value, // 富文本数据
      dataEdit: "", // 编辑数据
      showHTMLModal: false, // 显示html
      full: false, // html全屏开关
      fullscreenModal: false // 显示全屏预览
    };
  },
  methods: {
    initEditor() {
      let that = this;
      // 详见wangeditor3官网文档 https://www.kancloud.cn/wangfupeng/wangeditor3/332599
      editor = new E(`#${this.id}`);
      // 编辑内容绑定数据
      editor.customConfig.onchange = html => {
        if (this.openXss) {
          this.data = xss(html);
        } else {
          this.data = html;
        }
        this.$emit("input", this.data);
        this.$emit("on-change", this.data);
      };
      // z-index
      editor.customConfig.zIndex = 100;
      if (this.base64) {
        // 使用 base64 保存图片
        editor.customConfig.uploadImgShowBase64 = true;
      } else {
        // 配置上传图片服务器端地址
        editor.customConfig.uploadImgServer = uploadFile;
        editor.customConfig.uploadImgHeaders = {
          accessToken: that.getStore("accessToken")
        };
        editor.customConfig.uploadFileName = "file";
        editor.customConfig.uploadImgHooks = {
          before: function(xhr, editor, files) {
            // 图片上传之前触发
          },
          success: function(xhr, editor, result) {
            // 图片上传并返回结果，图片插入成功之后触发
          },
          fail: function(xhr, editor, result) {
            // 图片上传并返回结果，但图片插入错误时触发
            that.$Message.error("上传图片失败");
          },
          error: function(xhr, editor) {
            // 图片上传出错时触发
            that.$Message.error("上传图片出错");
          },
          timeout: function(xhr, editor) {
            // 图片上传超时时触发
            that.$Message.error("上传图片超时");
          },
          // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
          customInsert: function(insertImg, result, editor) {
            if (result.success == true) {
              let url = result.result;
              insertImg(url);
              that.$Message.success("上传图片成功");
            } else {
              that.$Message.error(result.message);
            }
          }
        };
      }
      editor.customConfig.customAlert = function(info) {
        // info 是需要提示的内容
        // that.$Message.info(info);
      };
      // 字体
      editor.customConfig.fontNames = ["微软雅黑", "宋体", "黑体", "Arial"];
      // 表情面板可以有多个 tab ，因此要配置成一个数组。数组每个元素代表一个 tab 的配置
      editor.customConfig.emotions = [
        {
          // tab 的标题
          title: "新浪",
          // type -> 'emoji' / 'image'
          type: "image",
          // content -> 数组
          content: sina
        }
      ];
      editor.create();
      if (this.value) {
        if (this.openXss) {
          editor.txt.html(xss(this.value));
        } else {
          editor.txt.html(this.value);
        }
      }
    },
    editHTML() {
      this.dataEdit = this.data;
      this.showHTMLModal = true;
    },
    editHTMLOk() {
      editor.txt.html(this.dataEdit);
      this.$emit("input", this.dataEdit);
      this.$emit("on-change", this.dataEdit);
      this.showHTMLModal = false;
    },
    clear() {
      this.$Modal.confirm({
        title: "确认清空",
        content: "确认要清空编辑器内容？清空后不能撤回",
        onOk: () => {
          this.data = "";
          editor.txt.html(this.data);
          this.$emit("input", this.data);
          this.$emit("on-change", this.data);
        }
      });
    },
    setData(value) {
      if (!editor) {
        this.initEditor();
      }
      if (value && value != this.data) {
        this.data = value;
        editor.txt.html(this.data);
        this.$emit("input", this.data);
        this.$emit("on-change", this.data);
      }
    }
  },
  watch: {
    value(val) {
      this.setData(val);
    }
  },
  mounted() {
    this.initEditor();
  }
};
</script>

<style lang="less">
.e-menu {
  z-index: 101;
  position: absolute;
  cursor: pointer;
  color: #999;
  :hover {
    color: #333;
  }
}
.e-code {
  top: 4px;
  left: 692px;
}
.e-preview {
  top: 4px;
  left: 726px;
}
.e-trash {
  top: 6px;
  left: 760px;
}
</style>

