<style lang="less">
@import "../../../styles/table-common.less";
@import "./ossManage.less";
</style>
<template>
  <div class="search">
    <Card>
      <div class="operation">
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
          <Form ref="searchForm" :model="searchForm" inline :label-width="85">
            <Form-item label="原文件名" prop="name">
              <Input
                type="text"
                v-model="searchForm.name"
                placeholder="请输入文件名"
                clearable
                style="width: 200px"
              />
            </Form-item>
            <Form-item label="上传者账号" prop="createBy">
              <Input
                type="text"
                v-model="searchForm.createBy"
                placeholder="请输入上传者登录账号"
                clearable
                style="width: 200px"
              />
            </Form-item>
            <span v-if="drop">
              <Form-item label="存储文件名" prop="name">
                <Input
                  type="text"
                  v-model="searchForm.fkey"
                  placeholder="请输入存储文件名"
                  clearable
                  style="width: 200px"
                />
              </Form-item>
              <Form-item label="创建时间">
                <DatePicker
                  v-model="selectDate"
                  type="daterange"
                  format="yyyy-MM-dd"
                  clearable
                  @on-change="selectDateRange"
                  placeholder="选择起始时间"
                  style="width: 200px"
                ></DatePicker>
              </Form-item>
            </span>
            <Form-item style="margin-left:-35px;" class="br">
              <Button @click="handleSearch" type="primary" icon="ios-search">搜索</Button>
              <Button @click="handleReset">重置</Button>
              <a class="drop-down" @click="dropDown">
                {{dropDownContent}}
                <Icon :type="dropDownIcon"></Icon>
              </a>
            </Form-item>
          </Form>
        </Row>
        <div class="oss-operation">
          <div class="br">
            <Button @click="uploadVisible=true" type="primary" icon="md-cloud-upload">上传文件</Button>
            <Dropdown @on-click="handleDropdown">
              <Button>
                更多操作
                <Icon type="md-arrow-dropdown" />
              </Button>
              <DropdownMenu slot="list">
                <DropdownItem name="refresh">刷新</DropdownItem>
                <DropdownItem v-show="showType=='list'" name="removeAll">批量删除</DropdownItem>
                <DropdownItem v-show="showType=='thumb'" name="name">原名称排序</DropdownItem>
                <DropdownItem v-show="showType=='thumb'" name="size">文件大小排序</DropdownItem>
                <DropdownItem v-show="showType=='thumb'" name="type">文件类型排序</DropdownItem>
                <DropdownItem v-show="showType=='thumb'" name="time">创建时间排序</DropdownItem>
                <DropdownItem name="config">存储配置</DropdownItem>
              </DropdownMenu>
            </Dropdown>
            <Button type="dashed" @click="openSearch=!openSearch">{{openSearch ? "关闭搜索" : "开启搜索"}}</Button>
            <Button type="dashed" @click="openTip=!openTip">{{openTip ? "关闭提示" : "开启提示"}}</Button>
          </div>

          <div>
            <RadioGroup
              v-model="fileType"
              @on-change="changeFileType"
              type="button"
              style="margin-right:25px"
            >
              <Radio label="all">所有文件</Radio>
              <Radio label="pic">图片</Radio>
              <Radio label="video">视频</Radio>
            </RadioGroup>
            <RadioGroup v-model="showType" type="button" @on-change="changeShowType">
              <Radio title="列表" label="list">
                <Icon type="md-list"></Icon>
              </Radio>
              <Radio title="缩略图" label="thumb">
                <Icon type="ios-apps"></Icon>
              </Radio>
            </RadioGroup>
          </div>
        </div>
      </div>

      <div v-show="showType=='list'">
        <Row v-show="openTip">
          <Alert show-icon>
            已选择
            <span class="select-count">{{selectCount}}</span> 项
            <a class="select-clear" @click="clearSelectAll">清空</a>
            <span v-if="selectCount>0" style="margin-left:15px">共计 {{totalSize}} 存储量</span>
          </Alert>
        </Row>
        <Row>
          <Table
            :loading="loading"
            border
            :columns="columns"
            :data="data"
            ref="table"
            sortable="custom"
            @on-sort-change="changeSort"
            @on-selection-change="changeSelect"
          ></Table>
        </Row>
      </div>
      <div v-show="showType=='thumb'">
        <div class="oss-wrapper">
          <Card v-for="(item, i) in data" :key="i" class="oss-card">
            <div class="content">
              <img
                @click="showPic(item)"
                v-if="item.type.indexOf('image')>=0"
                class="img"
                :src="item.url"
              />
              <div v-else-if="item.type.indexOf('video')>=0" class="video" @click="showVideo(item)">
                <!-- 文件小于5MB显示video -->
                <video class="cover" v-if="item.size<1024 * 1024 * 5">
                  <source :src="item.url" />
                </video>
                <img class="play" src="@/assets/play.png" />
              </div>
              <div v-else class="other">
                <div class="name">{{item.name}}</div>
                <div class="key">{{item.fkey}}</div>
                <div
                  class="info"
                >文件类型：{{item.type}} 文件大小：{{((item.size * 1.0) / (1024 * 1024)).toFixed(2)}} MB 创建时间：{{item.createTime}}</div>
              </div>
              <div class="actions">
                <div class="btn">
                  <Tooltip content="下载" placement="top">
                    <Icon @click="download(item)" type="md-download" size="16" />
                  </Tooltip>
                </div>
                <div class="btn">
                  <Tooltip content="重命名" placement="top">
                    <Icon @click="rename(item)" type="md-create" size="16" />
                  </Tooltip>
                </div>
                <div class="btn">
                  <Tooltip content="复制" placement="top">
                    <Icon @click="copy(item)" type="md-copy" size="16" />
                  </Tooltip>
                </div>
                <div class="btn-no">
                  <Tooltip content="删除" placement="top">
                    <Icon @click="remove(item)" type="md-trash" size="16" />
                  </Tooltip>
                </div>
              </div>
            </div>
          </Card>
        </div>
      </div>
      <Row type="flex" justify="end" class="page">
        <Page
          :current="searchForm.pageNumber"
          :total="total"
          :page-size="searchForm.pageSize"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          :page-size-opts="pageSizeOpts"
          size="small"
          show-total
          show-elevator
          show-sizer
        ></Page>
      </Row>
    </Card>

    <Drawer title="文件上传" closable v-model="uploadVisible" width="500">
      <Upload
        action="/zwz/upload/file"
        :headers="accessToken"
        :on-success="handleSuccess"
        :on-error="handleError"
        :max-size="5120"
        :on-exceeded-size="handleMaxSize"
        :before-upload="beforeUpload"
        multiple
        type="drag"
        ref="up"
      >
        <div style="padding: 20px 0">
          <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
          <p>点击这里或将文件拖拽到这里上传</p>
        </div>
      </Upload>
      <div class="drawer-footer">
        <Button @click="clearFiles">清空上传列表</Button>
      </div>
    </Drawer>

    <Modal :title="modalTitle" v-model="modalVisible" :mask-closable="false" :width="500">
      <Form ref="form" :model="form" :label-width="95" :rules="formValidate">
        <FormItem label="原文件名" prop="name">
          <Input v-model="form.name" />
        </FormItem>
        <FormItem label="存储文件名" prop="fkey">
          <Input v-model="form.fkey" />
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="handleCancel">取消</Button>
        <Button type="primary" :loading="submitLoading" @click="handleSubmit">提交</Button>
      </div>
    </Modal>

    <Modal
      v-model="videoVisible"
      :title="videoTitle"
      :width="800"
      @on-cancel="closeVideo"
      draggable
    >
      <div id="dplayer"></div>
      <div slot="footer">
        <span>文件类型：{{file.type}} 文件大小：{{file.msize}} 创建时间：{{file.createTime}}</span>
      </div>
    </Modal>
  </div>
</template>

<script>
import {
  checkOssSet,
  getFileListData,
  copyFile,
  renameFile,
  deleteFile,
  aliDownloadFile
} from "@/api/index";
import DPlayer from "dplayer";
import "viewerjs/dist/viewer.css";
import Viewer from "viewerjs";
var dp;
export default {
  name: "oss-manage",
  data() {
    return {
      openSearch: true,
      openTip: true,
      accessToken: {}, // 上传token鉴权
      loading: false, // 表单加载状态
      drop: false,
      dropDownContent: "展开",
      dropDownIcon: "ios-arrow-down",
      fileType: "all",
      showType: "list",
      modalVisible: false, // 添加或编辑显示
      uploadVisible: false,
      videoVisible: false,
      picVisible: false,
      picTitle: "",
      videoTitle: "",
      modalTitle: "", // 添加或编辑标题
      searchForm: {
        // 搜索框对应data对象
        name: "",
        fkey: "",
        type: "",
        pageNumber: 1, // 当前页数
        pageSize: 5, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
        startDate: "", // 起始时间
        endDate: "" // 终止时间
      },
      selectDate: null, // 选择日期绑定modal
      form: {
        name: "",
        fkey: ""
      },
      file: {},
      // 表单验证规则
      formValidate: {
        name: [{ required: true, message: "不能为空", trigger: "blur" }],
        fkey: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      submitLoading: false, // 添加或编辑提交状态
      selectList: [], // 多选数据
      selectCount: 0, // 多选计数
      totalSize: "", // 文件大小统计
      columns: [
        // 表头
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        {
          type: "index",
          width: 60,
          align: "center"
        },
        {
          title: "原文件名",
          key: "name",
          minWidth: 130,
          sortable: true
        },
        {
          title: "存储文件名",
          key: "fkey",
          width: 165,
          sortable: true
        },
        {
          title: "缩略图(点击预览)",
          key: "url",
          width: 150,
          align: "center",
          render: (h, params) => {
            if (params.row.type.includes("image") > 0) {
              return h("img", {
                attrs: {
                  src: params.row.url
                },
                style: {
                  cursor: "zoom-in",
                  width: "80px",
                  height: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.showPic(params.row);
                  }
                }
              });
            } else if (params.row.type.includes("video") > 0) {
              // 如果视频文件大小超过5MB不予加载video
              if (params.row.size < 1024 * 1024 * 5) {
                return h(
                  "video",
                  {
                    style: {
                      cursor: "pointer",
                      width: "80px",
                      height: "60px",
                      margin: "10px 0",
                      "object-fit": "contain"
                    },
                    on: {
                      click: () => {
                        this.showVideo(params.row);
                      }
                    }
                  },
                  [
                    h("source", {
                      attrs: {
                        src: params.row.url
                      }
                    })
                  ]
                );
              } else {
                return h("img", {
                  attrs: {
                    src: require("@/assets/play.png")
                  },
                  style: {
                    cursor: "pointer",
                    width: "80px",
                    height: "60px",
                    margin: "10px 0",
                    "object-fit": "contain"
                  },
                  on: {
                    click: () => {
                      this.showVideo(params.row);
                    }
                  }
                });
              }
            } else if (
              params.row.type.includes("wordprocessingml") > 0 ||
              params.row.type.includes("msword") > 0
            ) {
              return h("img", {
                attrs: {
                  src: require("@/assets/word.png")
                },
                style: {
                  width: "55px",
                  height: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.$Message.warning("暂不支持预览");
                  }
                }
              });
            } else if (params.row.type.includes("octet-stream") > 0) {
              return h("img", {
                attrs: {
                  src: require("@/assets/xls.png")
                },
                style: {
                  width: "80px",
                  height: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.$Message.warning("暂不支持预览");
                  }
                }
              });
            } else if (params.row.type.includes("pdf") > 0) {
              return h("img", {
                attrs: {
                  src: require("@/assets/pdf.png")
                },
                style: {
                  cursor: "zoom-in",
                  width: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.showFile(params.row);
                  }
                }
              });
            } else if (params.row.type.includes("audio") > 0) {
              return h("img", {
                attrs: {
                  src: require("@/assets/mp3.png")
                },
                style: {
                  cursor: "zoom-in",
                  width: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.$Message.warning("暂不支持预览");
                  }
                }
              });
            } else if (
              params.row.type.includes("zip") > 0 ||
              params.row.type.includes("rar") > 0 ||
              params.row.type.includes("7z") > 0
            ) {
              return h("img", {
                attrs: {
                  src: require("@/assets/zip.png")
                },
                style: {
                  width: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.$Message.warning("暂不支持预览");
                  }
                }
              });
            } else {
              return h("img", {
                attrs: {
                  src: require("@/assets/file.png")
                },
                style: {
                  width: "55px",
                  height: "60px",
                  margin: "10px 0",
                  "object-fit": "contain"
                },
                on: {
                  click: () => {
                    this.$Message.warning("暂不支持预览");
                  }
                }
              });
            }
          }
        },
        {
          title: "文件类型",
          key: "type",
          width: 115,
          sortable: true
        },
        {
          title: "文件大小",
          key: "size",
          width: 115,
          sortable: true,
          render: (h, params) => {
            let m =
              ((params.row.size * 1.0) / (1024 * 1024)).toFixed(2) + " MB";
            return h("span", m);
          }
        },
        {
          title: "上传者",
          key: "createBy",
          width: 120,
          sortable: true,
          align: "center",
          render: (h, params) => {
            return h(
              "Tooltip",
              {
                props: {
                  placement: "top",
                  content: params.row.createBy
                }
              },
              [
                h(
                  "Tag",
                  {
                    style: {
                      "margin-right": "8px"
                    },
                    props: {
                      type: "border"
                    }
                  },
                  params.row.nickname
                )
              ]
            );
          }
        },
        {
          title: "存储位置",
          key: "location",
          align: "center",
          width: 110,
          render: (h, params) => {
            let location = "",
              color = "";
            if (params.row.location == 0) {
              location = "本地服务器";
              color = "green";
            } else if (params.row.location == 1) {
              location = "七牛云";
              color = "blue";
            } else if (params.row.location == 2) {
              location = "阿里云";
              color = "orange";
            } else if (params.row.location == 3) {
              location = "腾讯云";
              color = "geekblue";
            } else if (params.row.location == 4) {
              location = "MinIO";
              color = "magenta";
            }
            return h("div", [
              h(
                "Tag",
                {
                  props: {
                    color: color
                  }
                },
                location
              )
            ]);
          }
        },
        {
          title: "创建时间",
          key: "createTime",
          width: 180,
          sortable: true,
          sortType: "desc"
        },
        {
          title: "操作",
          key: "action",
          align: "center",
          fixed: "right",
          width: 180,
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.download(params.row);
                    }
                  }
                },
                "下载"
              ),
              h(
                "Dropdown",
                {
                  props: { transfer: true },
                  on: {
                    "on-click": v => {
                      this.changeDropDown(params.row, v);
                    }
                  }
                },
                [
                  h(
                    "Button",
                    {
                      props: { size: "small" },
                      style: {
                        height: "23.5px"
                      }
                    },
                    [
                      "更多操作",
                      h("Icon", {
                        props: {
                          type: "ios-arrow-down"
                        }
                      })
                    ]
                  ),
                  h("DropdownMenu", { slot: "list" }, [
                    h("DropdownItem", { props: { name: "rename" } }, "重命名"),
                    h("DropdownItem", { props: { name: "copy" } }, "复制"),
                    h("DropdownItem", { props: { name: "remove" } }, "删除")
                  ])
                ]
              )
            ]);
          }
        }
      ],
      data: [], // 表单数据
      total: 0, // 表单数据总数
      pageSizeOpts: [5, 10, 20]
    };
  },
  methods: {
    handleDropdown(name) {
      if (name == "refresh") {
        this.getDataList();
      } else if (name == "removeAll") {
        this.removeAll();
      } else if (name == "name") {
        this.searchForm.sort = "name";
        this.getDataList();
      } else if (name == "size") {
        this.searchForm.sort = "size";
        this.getDataList();
      } else if (name == "type") {
        this.searchForm.sort = "type";
        this.getDataList();
      } else if (name == "time") {
        this.searchForm.sort = "createTime";
        this.getDataList();
      } else if (name == "config") {
        this.$router.push({
          name: "setting",
          query: { name: "oss" }
        });
      }
    },
    init() {
      this.accessToken = {
        accessToken: this.getStore("accessToken")
      };
      checkOssSet().then(res => {
        if (!res.success) {
          this.$Modal.confirm({
            title: "您还未配置OSS",
            content: "您还未配置第三方OSS服务，是否现在立即去配置?",
            onOk: () => {
              this.$router.push({
                name: "setting",
                query: { name: "oss" }
              });
            }
          });
        } else {
          this.getDataList();
        }
      });
    },
    showPic(v) {
      let image = new Image();
      image.src = v.url;
      let viewer = new Viewer(image, {
        hidden: function() {
          viewer.destroy();
        }
      });
      viewer.show();
    },
    showVideo(v) {
      dp = new DPlayer({
        container: document.getElementById("dplayer"),
        screenshot: true,
        video: {
          url: v.url
        },
        danmaku: {
          id: v.fkey,
          api: "https://api.prprpr.me/dplayer/"
        }
      });
      this.file = v;
      this.file.msize = ((v.size * 1.0) / (1024 * 1024)).toFixed(2) + " MB";
      this.videoTitle = v.name + "(" + v.fkey + ")";
      this.videoVisible = true;
    },
    closeVideo() {
      dp.destroy();
    },
    dropDown() {
      if (this.drop) {
        this.dropDownContent = "展开";
        this.dropDownIcon = "ios-arrow-down";
      } else {
        this.dropDownContent = "收起";
        this.dropDownIcon = "ios-arrow-up";
      }
      this.drop = !this.drop;
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
    changeSort(e) {
      this.searchForm.sort = e.key;
      this.searchForm.order = e.order;
      if (e.order == "normal") {
        this.searchForm.order = "";
      }
      this.getDataList();
    },
    selectDateRange(v) {
      if (v) {
        this.searchForm.startDate = v[0];
        this.searchForm.endDate = v[1];
      }
    },
    changeShowType() {
      this.searchForm.pageNumber = 1;
      if (this.showType == "list") {
        this.searchForm.pageSize = 5;
      } else {
        this.searchForm.pageSize = 12;
      }
      this.getDataList();
    },
    getDataList() {
      if (this.showType == "list") {
        this.pageSizeOpts = [5, 10, 20];
      } else {
        this.pageSizeOpts = [12, 24];
      }
      this.loading = true;
      getFileListData(this.searchForm).then(res => {
        this.loading = false;
        if (res.success) {
          this.data = res.result.content;
          this.total = res.result.totalElements;
        }
      });
    },
    handleSearch() {
      this.searchForm.pageNumber = 1;
      if (this.showType == "list") {
        this.searchForm.pageSize = 5;
      } else {
        this.searchForm.pageSize = 12;
      }
      this.getDataList();
    },
    changeFileType() {
      let name = this.fileType;
      if (name == "all") {
        this.searchForm.type = "";
      } else if (name == "pic") {
        this.searchForm.type = "image";
      } else if (name == "video") {
        this.searchForm.type = "video";
      }
      this.handleSearch();
    },
    handleReset() {
      this.$refs.searchForm.resetFields();
      this.searchForm.pageNumber = 1;
      if (this.showType == "list") {
        this.searchForm.pageSize = 5;
      } else {
        this.searchForm.pageSize = 12;
      }
      this.selectDate = null;
      this.searchForm.startDate = "";
      this.searchForm.endDate = "";
      // 重新加载数据
      this.getDataList();
    },
    beforeUpload() {
      if (
        this.$route.meta.permTypes &&
        !this.$route.meta.permTypes.includes("upload")
      ) {
        this.$Message.error("此处您没有上传权限(为演示功能，该按钮未配置隐藏)");
        return false;
      }
      return true;
    },
    handleMaxSize(file) {
      this.$Notice.warning({
        title: "文件大小过大",
        desc: "所选文件‘ " + file.name + " ’大小过大, 不得超过 5M."
      });
    },
    handleSuccess(res, file) {
      if (res.success) {
        this.$Message.success("上传文件 " + file.name + " 成功");
        this.getDataList();
      } else {
        this.$Message.error(res.message);
      }
    },
    handleError(error, file, fileList) {
      this.$Message.error(error.toString());
    },
    clearFiles() {
      this.$refs.up.clearFiles();
    },
    handleCancel() {
      this.modalVisible = false;
    },
    download(v) {
      window.open(
        v.url +
          "?attname=&response-content-type=application/octet-stream&filename=" +
          v.name
      );
    },
    showFile(v) {
      window.open(v.url + "?preview=true");
    },
    changeDropDown(row, v) {
      if (v == "rename") {
        this.rename(row);
      } else if (v == "copy") {
        this.copy(row);
      } else if (v == "remove") {
        this.remove(row);
      }
    },
    copy(v) {
      this.$Modal.confirm({
        title: "确认复制",
        content: "您确认要复制文件 " + v.name + " ?",
        loading: true,
        onOk: () => {
          copyFile({ id: v.id, key: v.fkey }).then(res => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success(
                "复制文件成功，新文件名为 " + v.name + "_副本"
              );
              this.getDataList();
            }
          });
        }
      });
    },
    removeAll() {
      if (this.selectCount <= 0) {
        this.$Message.warning("您还未选择要删除的数据");
        return;
      }
      this.$Modal.confirm({
        title: "确认删除",
        content: "您确认要删除所选的 " + this.selectCount + " 个文件?",
        loading: true,
        onOk: () => {
          let ids = "";
          this.selectList.forEach(function(e) {
            ids += e.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          deleteFile({ ids: ids }).then(res => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("批量删除文件成功");
              this.clearSelectAll();
              this.getDataList();
            }
          });
        }
      });
    },
    remove(v) {
      this.$Modal.confirm({
        title: "确认删除",
        content: "您确认要删除文件 " + v.name + " ?",
        loading: true,
        onOk: () => {
          deleteFile({ ids: v.id }).then(res => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("删除文件 " + v.name + " 成功");
              this.getDataList();
            }
          });
        }
      });
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true;
          let params = {
            id: this.form.id,
            newKey: this.form.fkey,
            newName: this.form.name
          };
          renameFile(params).then(res => {
            this.submitLoading = false;
            if (res.success) {
              this.$Message.success("操作成功");
              this.getDataList();
              this.modalVisible = false;
            }
          });
        }
      });
    },
    rename(v) {
      this.modalTitle = "编辑文件名";
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
    clearSelectAll() {
      this.$refs.table.selectAll(false);
      this.totalSize = "";
    },
    changeSelect(e) {
      this.selectList = e;
      this.selectCount = e.length;
      let size = 0;
      e.forEach(item => {
        size += item.size * 1.0;
      });
      this.totalSize = ((size * 1.0) / (1024 * 1024)).toFixed(2) + " MB";
    }
  },
  mounted() {
    this.init();
  }
};
</script>