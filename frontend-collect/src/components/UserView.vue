<template>
  <div class="user-info">
    <el-avatar
        style="margin-right: 10px"
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    ></el-avatar>
    <span style="color: gray">{{ userViewVo.username ? userViewVo.username : '' }}</span>
  </div>
</template>

<script>
export default {
  name: "UserView",
  props: ['userViewVo']
}
</script>

<style scoped>
.user-info{
  display: flex;
  align-items: center;
}
</style>
