<template>
  <el-row style="justify-content: space-between">
    <span v-if="showTitle">缺陷补充：</span>
    <el-button v-if="showAddButton" type="text" @click="editMode = true">
      + 添加补充
    </el-button>
    <el-button v-if="showEditButton" circle type="primary" size="small" @click="editMode = true">
      <el-icon>
        <ic-edit></ic-edit>
      </el-icon>
    </el-button>
    <div v-if="editMode">
      <el-button circle size="small" @click="save">
        <el-icon>
          <ic-check></ic-check>
        </el-icon>
      </el-button>
      <el-button circle size="small" type="danger" @click="close">
        <el-icon>
          <ic-close></ic-close>
        </el-icon>
      </el-button>
    </div>
  </el-row>
  <el-form :model="append" ref="ruleFormRef">
    <el-form-item
        :prop="`content`"
        :rules="[{
          required: editMode,
          message: '缺陷补充不能为空',
          trigger: 'change'
      }]">
      <el-input v-model="append.content" style="margin-top: 10px" v-if="showTextArea" type="textarea" :readonly="!editMode"></el-input>
    </el-form-item>
  </el-form>


</template>

<script>
import {ref} from "vue";

export default {
  name: "FlawAppendComponent",
  props: ['modelValue', 'readOnly'],
  emits:['collect:save-append'],
  setup(){
    const ruleFormRef = ref()
    return { ruleFormRef }
  },
  data(){
    return {
      append: {
        content: ''
      },
      editMode: false
    }
  },
  computed: {
    showTitle(){
      return this.modelValue || this.editMode
    },
    showAddButton(){
      return !this.modelValue && !this.readOnly && !this.editMode
    },
    showTextArea(){
      return this.editMode || (!this.editMode && this.modelValue)
    },
    showEditButton(){
      return !this.editMode && !this.readOnly && this.modelValue
    }
  },
  methods: {
    save(){
      this.ruleFormRef.validate(valid => {
        if(valid){
          this.$emit('collect:save-append', this.append.content)
          this.editMode = false
        }else {
          return false
        }
      })

    },
    close(){
      this.editMode = false;
      this.append.content = this.modelValue;
    }
  },
  watch: {
    modelValue(newVal){
      this.append.content = newVal;
    }

  }
}
</script>

<style scoped>

</style>
