<template>
  <navigator-template>
    <template #dropdown-menu-items>
      <el-dropdown-item @click="directRegister">注册</el-dropdown-item>
      <el-dropdown-item @click="directLogin">登录</el-dropdown-item>
    </template>
  </navigator-template>
</template>

<script>
import NavigatorTemplate from "@/navigator/NavigatorTemplate";
export default {
  name: "VisitorNavigator",
  components: {NavigatorTemplate},
  methods:{
    directLogin(){
      this.$router.push("/")
    },
    directRegister(){
      this.$router.push({path:'/register'})
    }
  }
}
</script>

<style scoped>

</style>
