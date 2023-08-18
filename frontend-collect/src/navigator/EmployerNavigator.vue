<template>
  <navigator-template>
    <template #dropdown-menu-items>
      <el-dropdown-item @click="directUserInfoPage">
        <el-row style="align-items: center">
          <el-avatar
              style="margin-right: 3px"
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          <div>{{ userName }}</div>
        </el-row>
      </el-dropdown-item>
      <el-dropdown-item @click="directTaskPost">发布测试</el-dropdown-item>
      <el-dropdown-item @click="logout">登出</el-dropdown-item>
    </template>
  </navigator-template>
</template>

<script>
import NavigatorTemplate from "@/navigator/NavigatorTemplate";
import {cleanLoginInfo, storageGet} from "@/utils/util";
export default {
  name: "EmployerNavigator",
  components: {NavigatorTemplate},
  methods:{
    directUserInfoPage(){
      this.$router.push('/employer/userinfo');
    },
    directTaskPost(){
      this.$router.push('/employer/postTask')
    },
    logout(){
      cleanLoginInfo();
      this.$router.push({name: 'Login'});
    }
  },
  computed: {
    userName(){
      return storageGet('username')
    }
  }
}
</script>

<style scoped>
.menu-item{
  text-align: center;
}
</style>
