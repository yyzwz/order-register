<style lang="less">
@import "../styles/menu.less";
</style>
<template>
  <Menu
    ref="sideMenu"
    accordion
    :active-name="$route.name"
    :open-names="singleOpenName"
    :theme="menuTheme"
    width="auto"
    @on-select="changeMenu"
  >
    <template v-for="item in menuList">
      <template>
        <Submenu :name="item.name" :key="item.name">
          <template slot="title">
            <Icon :type="item.icon" :size="iconSize"></Icon>
            <span class="layout-text">{{ itemTitle(item) }}</span>
          </template>
          <template v-for="child in item.children">
            <MenuItem :name="child.name" :key="'menuitem' + child.name">
              <Icon :type="child.icon" :size="iconSize" :key="'icon' + child.name"></Icon>
              <span class="layout-text" :key="'title' + child.name">{{ itemTitle(child) }}</span>
            </MenuItem>
          </template>
        </Submenu>
      </template>
    </template>
  </Menu>
</template>

<script>
export default {
  name: "sidebarMenu",
  data() {
    return {
      singleOpenName: []
    };
  },
  props: {
    menuList: Array,
    iconSize: Number,
    menuTheme: {
      type: String,
      default: "dark"
    },
    openNames: {
      type: Array
    }
  },
  methods: {
    changeMenu(active) {
      this.$emit("on-change", active);
    },
    handleSelect(name) {
      this.$emit("on-select", name);
    },
    itemTitle(item) {
      return item.title;
    },
    getOpenedNamesByActiveName(name) {
      return this.$route.matched
        .map(item => item.name)
        .filter(item => item !== name);
    }
  },
  updated() {
    this.$nextTick(() => {
      if (this.$refs.sideMenu) {
        this.$refs.sideMenu.updateOpened();
      }
    });
  },
  watch: {
    // 监听路由变化
    $route(to, from) {
      this.singleOpenName = [this.$route.matched[0].name];
    }
  },
  mounted() {
    this.singleOpenName = [this.$route.matched[0].name];
  }
};
</script>
