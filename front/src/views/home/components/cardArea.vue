<template>
  <div>
    <Card class="card-area">
      <div class="card-header">
        <span class="title">在职员工数</span>
        <Dropdown trigger="click">
          <Icon type="ios-more" size="22" style="cursor: pointer" />
          <DropdownMenu slot="list">
            <DropdownItem>查看详情</DropdownItem>
            <DropdownItem>更多操作</DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </div>
      <div class="value">
        ￥
        <span id="countup"></span>
      </div>
      <apexchart
        type="area"
        height="45"
        :options="chartOptions"
        :series="series"
      />
      <div class="today">
        <span class="t">在职员工数</span>
        <span class="v"><Icon type="md-walk" />68668</span>
      </div>
    </Card>
  </div>
</template>

<script>
import { CountUp } from "countup.js";
var zh = require("../../../../src/libs/zh.json");
export default {
  name: "cardArea",
  components: {},
  props: {},
  data() {
    return {
      series: [
        {
          name: "",
          data: [],
        },
      ],
      chartOptions: {
        colors: ["#ff455f"],
        chart: {
          toolbar: {
            show: false,
          },
          sparkline: {
            enabled: true,
          },
          locales: [zh],
          defaultLocale: "zh",
        },
        dataLabels: {
          enabled: false,
        },
        stroke: {
          width: 2,
          curve: "straight",
        },
        labels: [...Array(12).keys()].map((n) => `2019-11-${n + 1}`),
        yaxis: {
          min: 0,
        },
        xaxis: {
          type: "datetime",
        },
        tooltip: {
          enabled: true,
          x: {
            show: true,
            format: "MMM dd日",
          },
        },
      },
    };
  },
  methods: {
    init() {
      this.series = [
        {
          name: "员工数",
          data: [47000, 45000, 54000, 38000, 56000, 24000, 65000, 31000, 37000, 30009, 62000, 51000],
        },
      ];
      new CountUp("countup", 68668).start();
    },
  },
  mounted() {
    this.init();
  },
};
</script>
<style lang="less" scoped>
.card-area {
  height: 182px;
  .card-header {
    display: flex;
    height: 22px;
    align-items: center;
    justify-content: space-between;
    color: rgba(0, 0, 0, 0.45);
  }
  .value {
    height: 38px;
    margin-top: 4px;
    margin-bottom: 6px;
    overflow: hidden;
    color: rgba(0, 0, 0, 0.85);
    font-size: 30px;
    line-height: 38px;
    white-space: nowrap;
    text-overflow: ellipsis;
    word-break: break-all;
  }
  .today {
    margin-top: 8px;
    padding-top: 9px;
    border-top: 1px solid #e8e8e8;
    .v {
      margin-left: 8px;
      color: rgba(0, 0, 0, 0.85);
    }
  }
}
</style>
