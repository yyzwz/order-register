<template>
<div>
    <div id="container"></div>
</div>
</template>

<script>
import {
    Pie,
    measureTextWidth
} from '@antv/g2plot';
export default {
    name: "test-page",
    components: {},
    props: {},
    data() {
        return {
            depCountData: [{
                    title: '零食类',
                    value: 27
                },
                {
                    title: '冷冻类',
                    value: 25
                },
                {
                    title: '烟酒类',
                    value: 18
                },
                {
                    title: '生鲜类',
                    value: 15
                },
                {
                    title: '散装类',
                    value: 10
                },
                {
                    title: '水果类',
                    value: 5
                }
            ],
            piePlot: {},
        }
    },
    methods: {
        init() {
            this.initAntvFx();
        },
        renderStatistic(containerWidth, text, style) {
            const {
                width: textWidth,
                height: textHeight
            } = measureTextWidth(text, style);
            const R = containerWidth / 2;
            let scale = 1;
            if (containerWidth < textWidth) {
                scale = Math.min(Math.sqrt(Math.abs(Math.pow(R, 2) / (Math.pow(textWidth / 2, 2) + Math.pow(textHeight, 2)))), 1);
            }
            const textStyleStr = `width:${containerWidth}px;`;
            return `<div style="${textStyleStr};font-size:${scale}em;line-height:${scale < 1 ? 1 : 'inherit'};">${text}</div>`;
        },
        initAntvFx() {
            var that = this;
            var data = this.depCountData;
            this.piePlot = new Pie('container', {
                appendPadding: 10,
                data,
                angleField: 'value',
                colorField: 'title',
                radius: 1,
                innerRadius: 0.64,
                meta: {
                    value: {
                        formatter: (v) => `${v} `,
                    },
                },
                label: {
                    type: 'inner',
                    offset: '-50%',
                    style: {
                        textAlign: 'center',
                    },
                    autoRotate: false,
                    content: '{value}',
                },
                statistic: {
                    title: {
                        offsetY: -4,
                        customHtml: (container, view, datum) => {
                            const {
                                width,
                                height
                            } = container.getBoundingClientRect();
                            const d = Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
                            const text = datum ? datum.title : '系统访问分析';
                            return that.renderStatistic(d, text, {
                                fontSize: 28
                            });
                        },
                    },
                    content: {
                        offsetY: 4,
                        style: {
                            fontSize: '32px',
                        },
                        customHtml: (container, view, datum, data) => {
                            const {
                                width
                            } = container.getBoundingClientRect();
                            const text = datum ? ` ${datum.value}人` : ` ${data.reduce((r, d) => r + d.value, 0)}人`;
                            return that.renderStatistic(width, text, {
                                fontSize: 32
                            });
                        },
                    },
                },
                // 添加 中心统计文本 交互
                interactions: [{
                    type: 'element-selected'
                }, {
                    type: 'element-active'
                }, {
                    type: 'pie-statistic-active'
                }],
            });
            this.piePlot.render();
            this.piePlot.on('element:click', (args) => {
                that.getPieUserItemFx(args.data.data.title);
            });
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
    font-size: 20px;
    text-align: center;
    justify-content: center;
    display: flex;

    .antvSecondTitle {
        color: #ff9900;
        font-size: 22px;
    }
}
</style>
