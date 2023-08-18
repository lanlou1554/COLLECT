<template>
  <el-container class="main-container">
    <el-header class="navigation-bar shadow-below">
        <el-button class="home-page-btn" type="text" @click="returnMainPage">COLLECT</el-button>
        <el-dropdown>
          <el-icon :size="30"><ic-more></ic-more></el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              <slot name="dropdown-menu-items"></slot>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
    </el-header>
    <el-main  class="main-content" @scroll="onScroll($event)">
      <div class="col-container" style="justify-content: center;">
        <div class="f1" :style="{maxWidth: `${maxContentWidth}px`}">
          <router-view
              :scroll="scroll"
              @collect:alert="showAlert($event)"
              @collect:set-max-width="setMaxWidth($event)"
          ></router-view>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
export default {
  name: "NavigatorTemplate",
  data: ()=>{
    return {
      scroll: 0,
      alert: false,
      alertType: 'info',
      alertMsg: '',
      isAtBottom: false,
      maxContentWidth: 850
    }
  },
  methods: {
    returnMainPage(){
      this.$router.push("/")
    },
    onScroll(e){
      let self = this
      let Scroll = e.target
      // 网页可见区域高：document.body.clientHeight
      // 网页正文全文高：document.body.scrollHeight
      let scrollHeight = Scroll.scrollHeight - Scroll.clientHeight
      self.scrollTop = Scroll.scrollTop
      if (scrollHeight - Scroll.scrollTop < 100) {
        if(!this.isAtBottom){
          this.isAtBottom = true;
          console.log("bottom")
          this.scroll++;
          setTimeout(() => {
            this.isAtBottom = false;
          }, 1000)
        }


      }
    },
    showAlert( event ){
      console.log('alert')
      let duration = 1000;
      if(event){
        if(event.alertDuration)duration = event.alertDuration;
        if(event.msg)this.alertMsg = event.msg;
        else if(event.message)this.alertMsg = event.message;
        if(event.type)this.alertType = event.type;
        else if(event.code !== undefined && event.code !== null)this.alertType = STATUS_2_TYPE[event.code];
        console.log(event)
        ElMessage({
          message: this.alertMsg,
          type: this.alertType,
          duration: duration
        })
      }

      this.alert = true;
      setTimeout(() => {
        console.log('-alert')
        this.alert = false;
      }, duration)
    },
    setMaxWidth( width ){
      this.maxContentWidth = width
    }

  }
}
</script>

<style scoped>
@media (max-width: 400px) {
  .main-content{
    padding-left: 5px;
    padding-right: 5px;
  }
}

.main-container{
  width: 100%;
  height: 100vh;
  background-color: #f5f5f5;
}
.navigation-bar{
background-color: white;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

}
.home-page-btn{
  font-size: x-large;
}
</style>
