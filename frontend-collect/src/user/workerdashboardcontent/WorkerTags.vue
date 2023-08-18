<template>
  <el-space>
    <el-tag
        v-for="tag in tags"
        :key="tag.id"
        type="info"
        size="large"
        :closable="true"
        @close="onTagDeleted(tag)"
    >
      {{ tag.name }}
    </el-tag>
    <el-button @click="dialogVisible = true">
      + 标签
    </el-button>
  </el-space>
  <el-dialog
      v-model="dialogVisible"
      title="请选择标签"
  >
    <el-space wrap>
      <el-tag v-for="tag in leftTags" :key="tag.id" @click="onNewTagSelected(tag)">
        {{ tag.name }}
      </el-tag>
    </el-space>
  </el-dialog>
</template>

<script>
import {userAddTag, userDeleteTag} from "@/api/UserApi";
import {SHOW_ALERT} from "@/const/Events";
import {UserTags} from "@/const/Tags";
import {storageGet, tagFilter} from "@/utils/util";

export default {
  name: "WorkerTags",
  props: ['userVO'],
  emits: ['collect:reload-userinfo'],
  data(){
    return {
      dialogVisible: false,
    }
  },
  computed: {
    tags(){
      if(this.userVO.tagGroups){
        return this.userVO.tagGroups.map(id => {
          return UserTags[id]
        }).filter(tagFilter)
      }else{
        return []
      }

    },
    leftTags(){
      let currentTags = new Set(this.userVO.tagGroups)
      console.log(currentTags)
      return UserTags.filter(tag => {
        return !currentTags.has(tag.id)
      }).filter(tagFilter)
    },
    userId(){
      return Number.parseInt(storageGet('id'))
    }
  },
  methods: {
    onNewTagSelected(newTag){
      this.dialogVisible = false;
      userAddTag(this.userId, newTag.id)
          .then(res => {
            this.$emit(SHOW_ALERT, res);
            this.getUserInfo();
          })
    },
    onTagDeleted(tag){
      userDeleteTag(this.userId, tag.id)
          .then(res => {
            this.$emit(SHOW_ALERT, res);
            this.getUserInfo();
          })
    },
    getUserInfo(){
      this.$emit('collect:reload-userinfo')
    },
  }
}
</script>

<style scoped>

</style>
