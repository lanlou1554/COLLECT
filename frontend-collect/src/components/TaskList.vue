<template>

    <task-card style="margin: 30px 10px" v-for="taskView of taskList" :task-view="taskView" :key="taskView.id" @click="examineTaskDetail(taskView.id)"></task-card>
  <el-empty v-if="empty" description="没有任务"/>
  <el-container v-loading="loading" style="height: 50px"
                  element-loading-background="rgba(0,0,0,0)"/>
</template>

<script>
import TaskCard from "@/components/TaskCard";
import {reactive} from "vue";
import {storageGet} from "@/utils/util";
import {ADMIN, EMPLOYER, WORKER} from "@/const/UserRole";
export default {
  name: "TaskList",
  setup(){
    const pageId = reactive(1);

    return { pageId }
  },
  components: {TaskCard},
  props: {
    loadTasks: {},
    scroll: {},
    visable: {}
  },
  data: () => {
    return{
      loading: false,
      taskList: [],
      id: `lll${Math.floor(Math.random() * 10000000) }${new Date().getDate()}`
    }
  },
  watch: {
    scroll(){
      if(this.visable && !this.loading)this.load()
    },
    visable(visable){
      //console.log(`visiable ${this.visable}`)
      if(visable)this.reload();
    }
  },
  methods: {
    load(){
      console.log('loading')
      this.loading = true;
/*      let loadingLayer = this.$loading({
        target: `#${this.id}`,
        background: 'rgba(0,0,0,0)'
      })*/
      this.loadTasks(this.pageId).then(taskList => {
        if(taskList.length !== 0){
          this.taskList.push(...taskList);
          console.log(taskList)
          this.pageId++;
        }
      }).finally(()=>{
        this.loading = false;
        //loadingLayer.close();
      })
    },
    reload(){
      this.taskList = []
      this.pageId = 1;
      this.load()
    },
    examineTaskDetail(taskId){
      if(Number.parseInt(storageGet("type"))===WORKER){
        this.$router.push({path:'/worker/taskDetail',query:{taskId}});
      }
      else if(Number.parseInt(storageGet("type"))===ADMIN){
        this.$router.push({path:'/admin/taskDetail',query:{taskId}});
      }
      else if(Number.parseInt(storageGet("type"))===EMPLOYER){
        this.$router.push({path:'/employer/taskDetail',query:{taskId}})
      }
    }
  },
  mounted() {
    if(this.visable)this.load()
  },
  computed:{
    empty(){
      return (!this.taskList || this.taskList.length === 0) && !this.loading
    }
  }
}
</script>

<style scoped>

</style>
