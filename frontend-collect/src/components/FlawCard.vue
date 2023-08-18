<template>
  <el-card shadow="hover">
    <template #header>
      <slot name="header"></slot>
    </template>
    <div>
      <el-form-item
          label="缺陷描述: "
          :prop="`flaws.${idx}.description`"
          :rules="[{
          required: !displayMode,
          message: '请输入缺陷描述',
          trigger: 'blur'
      }]">
        <el-input type="textarea"  v-model="description" :autosize="{ minRows: 4, maxRows: 8 }" :readonly="displayMode"></el-input>
      </el-form-item>
      <el-form-item
          label="复现步骤: "
          v-if="!fold"
          :prop="`flaws.${idx}.stepDes`"
          :rules="[
        {
          required: !displayMode,
          message: '请输入缺陷复现步骤',
          trigger: 'blur'
        }
      ]"
      >
        <el-input type="textarea"  v-model="stepDes" :autosize="{ minRows: 4, maxRows: 8 }" :readonly="displayMode"></el-input>
      </el-form-item>
      <el-form-item
          label="设备信息: "
          v-if="!fold"
          :prop="`flaws.${idx}.deviceInfo`"
          :rules="[
        {
          required: !displayMode,
          message: '请输入缺陷复现步骤',
          trigger: 'blur'
        }
      ]"
      >
        <el-input type="textarea"  v-model="deviceInfo" :autosize="{ minRows: 4, maxRows: 8 }" :readonly="displayMode"></el-input>
      </el-form-item>
      <el-form-item
          label="缺陷截图: "
          v-if="!fold"
          :prop="`flaws.${idx}.pictureURLs`"
          :rules="[
        {
          required: true ,
          trigger: 'blur',
          validator: validateImages
        }
      ]"
      >
        <image-upload-component v-if="!displayMode" v-model="pictureURLs"></image-upload-component>
      </el-form-item>
      <el-row v-if="displayMode && !fold">
        <el-image
            class="flaw-img"
            v-for="(url, idx) in pictureURLs"
            :key="idx"
            :preview-src-list="[url]"
            style="width: 100px; height: 100px"
            :src="url"
            fit="cover"
        >
        </el-image>

      </el-row>
    </div>
  </el-card>
</template>

<script>
import ImageUploadComponent from "@/fileComponents/ImageUploadComponent";
export default {
  name: "FlawCard",
  components: {ImageUploadComponent},
  setup(){
    const validateImages = (rule, value, callback, source) => {

      console.log(value)
      console.log(source)
      if ( !value || value.length === 0){
        callback(new Error('请上传图片'))
      }else {
        callback()
      }
    }
    return { validateImages }
  },
  props: {
    modelValue: {
    },
    displayMode: {
      default: false,
      type: Boolean
    },
    fold: {
      default: false,
      type: Boolean
    },
    idx: {
      default: 0,
      type: Number
    }
  },
  emits: [ 'update:modelValue' ],
  data: () => {
    return {
        id: 0,
        reportId:0,
        pictureURLs: [],
        description: 'description',
        stepDes: 'step description',
        deviceInfo: 'device information'
    }
  },
  watch: {
    pictureURLs(){
      this.dataUpdate();
    },
    description(){
      this.dataUpdate();
    },
    stepDes(){
      this.dataUpdate();
    },
    deviceInfo(){
      this.dataUpdate();
    },
    modelValue(){
      this.loadFromProp()
    }
  },
  methods: {
    dataUpdate(){
      console.log('update:modelValue')
      this.$emit('update:modelValue', {
        id: this.id,
        reportId: this.reportId,
        pictureURLs: this.pictureURLs,
        description: this.description,
        stepDes: this.stepDes,
        deviceInfo: this.deviceInfo
      })
    },
    loadFromProp(){
      this.id = this.modelValue.id;
      this.reportId = this.modelValue.reportId;
      this.pictureURLs = this.modelValue.pictureURLs;
      this.description = this.modelValue.description;
      this.stepDes = this.modelValue.stepDes;
      this.deviceInfo = this.modelValue.deviceInfo;
    }
  },
  mounted() {
    this.loadFromProp()
  }

}
</script>

<style scoped>
.flaw-img{
  margin: 3px;
  border-radius: 3px;
}
</style>
