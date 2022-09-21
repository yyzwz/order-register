<template>
<div :style="{ height: clientHeight }" class="topDiv">
    <Row :gutter="20">
        <!-- 主体部分 -->
        <div class="body">
            <!--logo标题图片 -->
            <img class="title" src="../../assets/logo.png" alt="" />
            <!--第二排内容 -->
            <div class="awayMenu">
                <!--左侧 -->
                <div class="awayLeft">
                    <span class="manage2">
                        欢迎
                        <!--用户名称 -->
                        <span>{{ name }}</span>
                    </span>
                    <!--登入地址 -->
                    <div class="manage">登入地址：{{ location }}</div>
                </div>
                <!--时间，上下布局 -->
                <div class="bottom">
                    <!--年月日 -->
                    <span class="showtime">{{ showtime }}</span>
                    <!--时分 -->
                    <span class="showtime2">{{ showtime2 }}</span>
                </div>
            </div>
            <!--三层标题 -->
            <div class="bigTips">
                <span style="color:rgba(255,255,255,0.8)">常用模块</span>
                <span style="67.5%"> </span>
            </div>
            <!--常用按钮层 -->
            <div class="buttonMenu">
                <!--常用按钮盒子 -->
                <div class="addMenuBox">
                    <!--循环遍历按钮 -->
                    <div class="addMenu" v-for="(item, index) in addMenuTempList" :key="index" @click="selectItem(item)">
                        {{ item.title }}
                    </div>
                </div>
                <!--分隔线 -->
                <div class="shu"></div>
                <!--右侧3按钮 -->
                <div class="threeButton">
                    <!--按钮1 -->
                    <div class="button" @click="toDaiBanPage">
                        <div class="left">
                            <!--图片1 -->
                            <img class="homeThreeIcon" src="../../assets/homeIcon1.png" />
                            <!--内容1 -->
                            <span class="text">我的待办</span>
                        </div>
                    </div>
                    <!--按钮2 -->
                    <div class="button" @click="toFaQiPage">
                        <div class="left">
                            <!--图片2 -->
                            <img class="homeThreeIcon" src="../../assets/homeIcon2.png" />
                            <!--内容2 -->
                            <span class="text">我的未办</span>
                        </div>
                    </div>
                    <!--按钮3 -->
                    <div class="button" @click="toJingBanPage">
                        <div class="left">
                            <!--图片3 -->
                            <img class="homeThreeIcon" src="../../assets/homeIcon3.png" />
                            <!--内容3 -->
                            <span class="text">我的经办</span>
                        </div>
                    </div>
                </div>
            </div>
            <!--左下角提升语句，点击跳转盒子-->
            <div class="bottomText" @click="toOwnMenu">
                添加"常用模块"? 点我 进入个人门户设置
            </div>
        </div>
    </Row>
</div>
</template>

<script>
import Cookies from "js-cookie";
import {
    ipInfo,
    getMyDoorList6
} from "./api.js";
export default {
    name: "home",
    data() {
        return {
            name: "",
            showtime: "",
            showtime2: "",
            location: "公司内网",
            addMenuTempList: [],
            number1: 0,
            number2: 0,
            number3: 0,
            number1List: [],
            number2List: [],
            number3List: []
        };
    },

    methods: {
        init() {
            this.getMyDoorListFx();
            let user = JSON.parse(Cookies.get("userInfo"));
            this.name = user.nickname;
            this.getNowTime();
            ipInfo().then((res) => {
                if (res.success) {
                    this.location = res.result;
                }
            });
            this.timer = setInterval(this.getNowTime, 1000);
        },
        selectItem(e) {
            if (e.name != undefined && e.name != "null") {
                this.$router.push({
                    name: e.name,
                });
            }
        },
        toDaiBanPage() {
            this.$Message.success("正在开发，敬请期待！");
        },
        toFaQiPage() {
            this.$Message.success("正在开发，敬请期待！");
        },
        toJingBanPage() {
            this.$Message.success("正在开发，敬请期待！");
        },
        toOwnMenu() {
            this.$router.push("/myHome");
        },
        getMyDoorListFx() {
            var that = this;
            getMyDoorList6().then((res) => {
                that.addMenuTempList = res.result;
            });
        },
        getNowTime() {
            this.showtime = this.format(new Date(), "yyyy年MM月dd日");
            this.showtime2 = this.format(new Date(), "HH:mm:dd");
        },
    },
    mounted() {
        this.init();
        this.clientHeight = `${document.documentElement.clientHeight}`;
        let that = this;
        window.onresize = function () {
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
@import "./home.less";

.ivu-tooltip {
    width: 100% !important;
}

.margin {
    margin-bottom: 20px;
}

.awayLeft {
    width: 60%;
    display: flex;
    align-items: center;
}

.bottom {
    width: 30%;
    margin-left: 10%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.title {
    width: 15%;
    margin: 5vh 0;
    max-height: 80px;
}

.awayMenu {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.manage {
    width: 40%;
    height: 100%;
    font-family: Microsoft YaHei;
    font-size: 20px;
    display: flex;
    align-items: center;
    color: rgb(255, 255, 255, 0.7);
}

.manage2 {
    width: 60%;
    height: 100%;
    font-size: 28px;
    display: flex;
    align-items: center;
    color: rgb(255, 255, 255, 0.7);
}

.buttonMenu {
    width: 100%;
    display: flex;
}

.bigTips {
    width: 100%;
    display: flex;
    justify-content: left;
    align-items: center;
    margin-top: 3vh;
    margin-bottom: 3vh;
    font-size: 20px;
}

.addMenuBox {
    width: 50%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    flex-wrap: wrap;

    .addMenu {
        width: 45%;
        // min-width: 180px;
        height: 22%;
        margin: 2% 2.5%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(255, 255, 255, 0.4);
        border-radius: 20px;
        color: #fff;
        font-size: 20px;
    }
}

.addMenu :nth-child(5) {
    margin-bottom: 0;
}

.addMenu :nth-child(6) {
    margin-bottom: 0;
}

.shu {
    width: 0.2%;
    margin: 0 9.9%;
    background-color: #4c4c4c;
}

.threeButton {
    width: 30%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
}

.showtime {
    font-family: Microsoft YaHei;
    font-size: 22px;
    letter-spacing: 1px;
    color: rgb(255, 255, 255);
    font-weight: 100;
    text-align: center;
}

.showtime2 {
    font-family: Microsoft YaHei;
    font-size: 26px;
    font-weight: 500;
    letter-spacing: 1px;
    color: rgb(255, 255, 255);
    text-align: center;
}

.homeThreeIcon {
    opacity: 1;
    height: 25px;
    width: 25px;
    margin-right: 10%;
}

.button {
    width: 60%;
    height: 28%;
    min-width: 310px;
    max-width: 860px;
    max-height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 10px;
    padding: 20px;
    margin: 0 auto;
    z-index: 99;

    .left {
        width: 60%;
        min-width: 150;
        display: flex;
        align-items: center;
    }

    .text {
        color: #fff;
        font-size: 20px;
        position: relative;
    }

    .shu {
        width: 1px !important;
        height: 100%;
        max-height: 35px;
        background-color: #fff;
    }

    .number {
        font-size: 22px;
        color: #fff;
    }
}

.bottomText {
    opacity: 0.7;
    width: 100%;
    margin-top: 7vh;
    text-align: left;
    font-size: 16px;
    color: #e6e6e6;
    text-decoration: underline;
}

.textBox {
    width: 800px;
    display: flex;
    flex-direction: column;
    align-items: left;
    padding: 0 10%;

    .text {
        font-size: 16px;
        margin: 3px 0;
        border-bottom: 1px solid #777;

        .one {
            display: flex;
            align-items: center;

            .xuhao {
                width: 20%;
                min-width: 50px;
                background-color: #ff9900;
                border: 1px solid #ff9900;
                display: flex;
                justify-content: center;
                align-items: center;
                border-radius: 5px;
                padding: 3px;
                margin-right: 8px;
            }

            .floar {
                width: 85%;
                display: flex;
                justify-content: center;
                font-size: 16px;
                font-family: "Fantasy";
                font-weight: 500;
                margin-right: 30px;
            }
        }

        .two {
            display: flex;
            margin: 5px 0;
            align-items: center;
            justify-content: space-between;

            .twoItem {
                width: 35%;
                font-size: 12px;
            }

            .twoItem2 {
                width: 65%;
                display: flex;
                justify-content: center;
                font-size: 12px;

            }
        }
    }
}
</style>
