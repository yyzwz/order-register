<template>
  <div>
    <Row :gutter="20">
      <Col :sm="24" :md="12" :lg="6">
        <div class="text">
          <span class="manage2">
            欢迎<span style="color:#f00; marginLeft:10px">{{ name }}</span>
          </span>
          <div class="">
            <span class="manage">工号:{{ jonNumber }}</span>
            <span class="manage">{{ location }}</span>
            <span class="manage3">{{ showtime }}</span>
          </div>
        </div>
      </Col>
      <Col :sm="24" :md="12" :lg="6">
        <cardArea class="margin" />
      </Col>
      <Col :sm="24" :md="12" :lg="6">
        <cardLine />
      </Col>
      <Col :sm="24" :md="12" :lg="6">
        <cardProgress class="margin" />
      </Col>
    </Row>
    <Row :gutter="20">
      <Col :sm="24" :lg="24" :xl="12">
        <LineChart class="margin" />
      </Col>
      <Col :sm="24" :lg="24" :xl="12">
        <bubble class="margin" />
      </Col>
    </Row>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import cardArea from "./components/cardArea.vue";
import cardAreaFill from "./components/cardAreaFill.vue";
import cardLine from "./components/cardLine.vue";
import cardProgress from "./components/cardProgress.vue";
import LineChart from "./components/line.vue";
import bubble from "./components/bubble.vue";
import { ipInfo } from "@/api/index";
export default {
  name: "dashboard-2",
  components: {
    cardArea,
    cardAreaFill,
    LineChart,
    cardLine,
    cardProgress,
    bubble,
  },
  data() {
    return {
      name: "",
      showtime: "",
      ip: "",
      location: "",
      jonNumber: "",
    };
  },

  methods: {
    init() {
      let user = JSON.parse(Cookies.get("userInfo"));
      this.name = user.nickname;
      this.jonNumber = user.username;
      this.getNowTime();
      ipInfo().then((res) => {
        if (res.success) {
          this.location = res.result;
        }
      });
      this.timer = setInterval(this.getNowTime, 1000);
    },
    getNowTime() {
      this.showtime = this.format(new Date(), "yyyy-MM-dd hh:mm:ss");
    },
  },
  mounted() {
    this.init();
    this.clientHeight = `${document.documentElement.clientHeight}`; //获取浏览器可视区域高度
    let that = this;
    window.onresize = function() {
      this.clientHeight = `${document.documentElement.clientHeight}`;
      if (that.$refs.page) {
        that.$refs.page.style.minHeight = clientHeight - 100 + "px";
      }
    };
  },
  watch: {
    clientHeight() {
      this.changeFixed(this.clientHeight);
    },
  },
};
</script>

<style lang="less" scoped>
.margin {
  margin-bottom: 20px;
}
.text {
  height: 182px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 4px;
  background-color: #fff;
  padding: 16px;
}
.manage {
  width: 100%;
  height: 15px;
  font-size: 16px;
  font-weight: 500;
  display: flex;
  justify-content: center;
  line-height: 15px;
  margin: 10px auto;
}
.manage2 {
  width: 100%;
  font-size: 25px;
  font-weight: 500;
  display: flex;
  justify-content: center;
  margin: 10px auto 0;
}
.manage3 {
  width: 100%;
  height: 15px;
  font-size: 15px;
  font-weight: 500;
  display: flex;
  justify-content: center;
  line-height: 15px;
  margin: 10px auto;
}
</style>
