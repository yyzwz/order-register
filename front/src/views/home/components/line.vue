<template>
  <div class="card-line">
    <Card>
      <p slot="title" class="card-title">加盟店销量</p>
      <div slot="extra">
        <DatePicker type="year" placeholder="选择查看的年份" style="width: 220px"></DatePicker>
      </div>
      <div class="wrap">
        <div class="chart">
          <apexchart type="line" height="380" :options="chartOptions" :series="series" />
        </div>
        <Row class="row">
          <Col span="8" class="text">
            <h6 class>距离目标</h6>
            <h3>
              <Icon type="ios-arrow-round-down" size="28" color="#f0643b" />￥10,345
            </h3>
          </Col>
          <Col span="8" class="text">
            <h6 class>同比上周</h6>
            <h3>
              <Icon type="ios-arrow-round-up" size="28" color="#23b397" />￥7,589
            </h3>
          </Col>
          <Col span="8" class="text">
            <h6 class>同比上月</h6>
            <h3>
              <Icon type="ios-arrow-round-down" size="28" color="#f0643b" />￥1,476
            </h3>
          </Col>
        </Row>
      </div>
    </Card>
  </div>
</template>

<script>
var zh = require("../../../../src/libs/zh.json");
export default {
  name: "line-chart",
  components: {},
  props: {},
  data() {
    return {
      series: [
        {
          name: "",
          data: []
        }
      ],
      chartOptions: {
        chart: {
          stacked: false,
          toolbar: {
            show: false
          },
          locales: [zh],
          defaultLocale: "zh"
        },
        stroke: {
          width: [0, 2, 4],
          curve: "smooth"
        },
        plotOptions: {
          bar: {
            columnWidth: "50%"
          }
        },
        fill: {
          opacity: [0.85, 0.25, 1],
          gradient: {
            inverseColors: false,
            shade: "light",
            type: "vertical",
            opacityFrom: 0.85,
            opacityTo: 0.55,
            stops: [0, 100, 100, 100]
          }
        },
        markers: {
          size: 0
        },
        xaxis: {
          labels: {
            formatter: function(value) {
              return value + "月";
            }
          },
          categories: [
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
          ]
        },
        yaxis: {
          min: 0
        },
        tooltip: {
          shared: true,
          intersect: false
        },
        dataLabels: {
          enabled: false
        },
        legend: {
          offsetY: 5
        }
      }
    };
  },
  methods: {
    init() {
      this.series = [
        {
          name: "科室A",
          type: "column",
          data: [23, 11, 22, 27, 13, 22, 37, 21, 44, 22, 30, 35]
        },
        {
          name: "科室B",
          type: "area",
          data: [44, 55, 41, 67, 22, 43, 21, 41, 56, 27, 43, 47]
        },
        {
          name: "科室C",
          type: "line",
          data: [30, 25, 36, 30, 45, 35, 64, 52, 59, 36, 39, 45]
        }
      ];
    }
  },
  mounted() {
    this.init();
  }
};
</script>
<style lang="less">
.card-line {
  .ivu-card-head {
    border-bottom: none !important;
  }
  .card-title {
    color: #67757c;
  }
  .wrap {
    height: 470px;
    .text {
      padding: 1rem;
      text-align: center;
      color: #67757c;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      h6 {
        font-size: 0.875rem;
        margin-bottom: 0.5rem;
        line-height: 1.2;
      }
      h3 {
        font-size: 1.3125rem;
        margin-bottom: 0.5rem;
        font-weight: 400;
        line-height: 1.2;
      }
    }
  }
}
</style>