<template>
  <NavigatorTemplate>
    <template #dropdown-menu-items>
      <el-dropdown-item @click="logout">登出</el-dropdown-item>
    </template>
  </NavigatorTemplate>
</template>

<script>
import NavigatorTemplate from "@/navigator/NavigatorTemplate";
import {cleanLoginInfo} from "@/utils/util";
export default {
  name: "AdminNavigator",
  components: {NavigatorTemplate},
  methods: {
    logout(){
      cleanLoginInfo();
      this.$router.push({name: 'Login'});
    }
  }
}
</script>

<style scoped>

</style>
