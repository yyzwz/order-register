<template>
  <div>
    <Row class="header">
      <img src="../../assets/logo-white.png" width="220px">
      <div class="description">基于Vue的医院门诊预约挂号管理系统</div>
    </Row>
  </div>
</template>

<script>
export default {
  name: "header"
};
</script>

<style lang="less">
.header {
  margin-top: 8vh;
  margin-bottom: 5vh;
  text-align: center;

  .description {
    font-size: 14px;
    color: rgba(0, 0, 0, 0.45);
    margin-top: 1vh;
  }
}
</style>
