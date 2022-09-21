<template>
<div>
    <div id="container"></div>
</div>
</template>

<script>
import {
    Bar
} from '@antv/g2plot';
export default {
    name: "test-page",
    components: {},
    props: {},
    data() {
        return {
            stackedBarPlot: {},
            userData: [
                {
                    title: '零食类',
                    type: '进货量',
                    value: 6,
                },
                {
                    title: '零食类',
                    type: '交易量',
                    value: 5,
                },
                {
                    title: '零食类',
                    type: '询问数',
                    value: 2,
                },
                {
                    title: '零食类',
                    type: '售后数',
                    value: 10,
                },
                {
                    title: '冷冻类',
                    type: '进货量',
                    value: 3,
                },
                {
                    title: '冷冻类',
                    type: '交易量',
                    value: 3,
                },
                {
                    title: '冷冻类',
                    type: '询问数',
                    value: 1,
                },
                {
                    title: '冷冻类',
                    type: '售后数',
                    value: 5,
                },
                {
                    title: '烟酒类',
                    type: '进货量',
                    value: 10,
                },
                {
                    title: '烟酒类',
                    type: '交易量',
                    value: 11,
                },
                {
                    title: '烟酒类',
                    type: '询问数',
                    value: 7,
                },
                {
                    title: '烟酒类',
                    type: '售后数',
                    value: 15,
            }]
        }
    },
    methods: {
        init() {
            this.initAntvFx();
        },
        initAntvFx() {
            var data = this.userData;
            this.stackedBarPlot = new Bar('container', {
                data,
                isGroup: true,
                xField: 'value',
                yField: 'title',
                seriesField: 'type',
                dodgePadding: 4,
                label: {
                    position: 'middle', // 'left', 'middle', 'right'
                    layout: [
                        {
                            type: 'interval-adjust-position'
                        },
                        {
                            type: 'interval-hide-overlap'
                        },
                        {
                            type: 'adjust-color'
                        },
                    ],
                },
            });
            this.stackedBarPlot.render();
        },
    },
    mounted() {
        this.init();
    },
};
</script>

<style lang="less" scoped>
#container {
    width: 100%;
    height: 700px;
    margin-top: 20px;
}

.antvTitle {
    font-size: 26px;
    text-align: center;
    justify-content: center;
    display: flex;
}
</style>
