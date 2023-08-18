<template>
  <el-card>

      <el-skeleton :rows="2" animated v-if="loading && animation"/>
      <el-row v-else style="align-items:stretch">
        <el-avatar :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
        <div style="margin: 10px 20px;">
          <div style="display: flex; align-items: center;">
            <div style="margin-right: 20px">{{ userVO.username }}</div>
            <el-tag>{{ userRole }}</el-tag>
          </div>
          <el-row class="info-row">
            <div class="info-title">
              <p>电子邮件</p>
            </div>
            <el-divider direction="vertical" class="divider"></el-divider>
            <span>{{ userVO.email }}</span>
          </el-row>
          <el-row class="info-row">
            <div class="info-title">
              <p>电话</p>
            </div>
            <el-divider direction="vertical" class="divider"></el-divider>
            <span>{{ userVO.phone }}</span>
          </el-row>
        </div>
      </el-row>

    <router-view :userVO="userVO" @collect:reload-userinfo="getUserInfo(true)" @collect:alert="showAlert($event)"></router-view>
<!--    <worker-ability></worker-ability>-->
  </el-card>
</template>

<script>
import {ROLES_CH} from "@/const/UserRole";
import {viewUserInfo} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {storageGet} from "@/utils/util";

export default {
  name: "UserInfoPage",
  data: () => {
    return {
      userVO: {
        id: 0,
        type: 2,
        username: '用户名',
        password: '',
        email: '233333333@qq.com',
        phone: '23333333333',
        tagGroups: []
      },
      loading: false,
      animation: true
    }
  },
  computed: {
    userRole(){
      return ROLES_CH[this.userVO.type]
    }
  },
  methods: {
    getUserInfo(noAnimation){
      if(!this.loading){
        this.loading = true
        const oldAnimation = this.animation
        if(noAnimation){
          this.animation = false
        }

        viewUserInfo(Number.parseInt(storageGet('id')))
            .then((res) => {
              if(res.code === SUCCESS){
                this.userVO = res.data;
              }else{
                this.$emit(SHOW_ALERT, res);
              }
              this.loading = false
              this.animation = oldAnimation;
            })
      }
    },
    showAlert(e){
      this.$emit(SHOW_ALERT, e)
    }

  },
  mounted() {
    this.getUserInfo();
  }
}
</script>

<style scoped>
.info-title{
  width: 70px;

}
.info-row{
  display: flex;
  align-items: center;
  height: 25px;
  font-size: small;
  color: darkgrey;
}
.divider{
}
</style>
