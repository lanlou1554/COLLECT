<template>
  <div>
    <el-row class="rol-container file-container" :id="componentId">
      <el-col style=" color: darkgrey;">
          <el-icon :size="100" style="margin-left: 85px"><ic-upload/></el-icon>
          <div style="text-align: center" class="text-break">{{ file.name? file.name : msg }}</div>
      </el-col>
    </el-row>
    <el-progress
        style="margin: 0 10px"
        :stroke-width="20"
        v-if="file.loadType"
        :percentage="file.percent">
      <template #default>
        <el-col style="display: flex; width: 24px">
          <el-button type="danger" circle size="small" @click="removeFile()">
            <el-icon><ic-delete></ic-delete></el-icon>
          </el-button>
        </el-col>
      </template>
    </el-progress>




  </div>

</template>

<script>
import plupload from 'plupload'
import {getSignature} from "@/api/FileApi";
import {getRandomName} from "@/utils/util";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
export default {
  name: "FileUpLoadComponent",
  props: {
    modelValue: {
    },
    msg:{}
  },
  data: () => {
    return {
      show: false,
      file: {},
      signatureExpire: 0,
      multipart_params: {},
      host: ''
    }
  },
  setup(){
    const componentId = `${Math.floor(Math.random() * 10000000) }${new Date().getDate()}`
    const fileOptions = {
      runtimes : 'html5,flash,silverlight,html4',
      browse_button: componentId,
      drop_element: componentId,
      url : 'http://oss.aliyuncs.com',
      flash_swf_url: "script/Moxie.swf",
      silverlight_xap_url: "script/Moxie.xap",
      multi_selection: false,
      filters: {
        mime_types: [
          //文件格式
          {
            title: "movie files",
            extensions: "zip,rar,doc,md,txt,png,jpeg,ppt,pptx,docx,pdf" //文件格式
          }
        ],
        max_file_size: "16mb", //最大上传的文件
        prevent_duplicates: true //不允许选取重复文件
      }
    }
    return {
      fileOptions, componentId
    }
  },
  mounted() {
    //实例化一个plupload上传对象
    this.uploader.init();
    //绑定进队列
    this.uploader.bind("FilesAdded", this.FilesAdded);
    //绑定进度
    this.uploader.bind("UploadProgress", this.UploadProgress);
    //上传之前
    this.uploader.bind("BeforeUpload", this.BeforeUpload);
    //上传成功监听
    this.uploader.bind("FileUploaded", this.FileUploaded);
  },
  computed: {
    //实例化一个plupload上传对象
    uploader() {
      return new plupload.Uploader(this.fileOptions);
    }
  },
  methods: {
    //绑定进队列
    FilesAdded(uploader, files) {
      console.log('add file')
      let obj = {};
      let val =files[0]
      obj.id = val.id;
      obj.name = val.name;
      obj.type = val.type;
      obj.size = parseInt((val.origSize / 1024 / 1024) * 100) / 100;
      obj.percentage = 0;
      obj.loadType = 0;
      obj.preloadUrl = window.URL.createObjectURL(val.getNative());
      if(this.file.id){uploader.removeFile(this.file.id);}
      this.file = obj
      this.set_upload_param(uploader, '', false)

    },
    //上传之前回调
    BeforeUpload(uploader, file){
      const randName = getRandomName(file.name);
      if(this.file.id === file.id){
        this.file.name = randName
        console.log(`before load: ${this.file.name}`)
        this.file.loadType = 1;
        this.set_upload_param(uploader, randName, true)
      }
    },
    //上传进度回调
    UploadProgress(uploader, file) {
      console.log(`loading: ${file.name} ${file.percent}`)
      this.file.percent = file.percent;

    },
    // 上传成功回调
    FileUploaded(uploader, file, responseObject) {
      if (this.file.id === file.id) {
        this.file.loadType = 2;
        console.log(`complete: ${this.file.name} ${this.file.loadType}`)
        this.file.url = `${this.host}/${this.multipart_params.dir}${this.multipart_params.dir.endsWith('/') ? '' : '/'}${this.file.name}`
        console.log(responseObject.response)
        console.log(this.file.url)
        this.$emit('update:modelValue', this.file.url)
      }

    },
    //取消上传回调
    removeFile() {
      console.log('remove');
      this.uploader.removeFile(this.file.id);
      this.file = {}
      this.$emit('update:modelValue', null)
    },

    set_upload_param(up, filename, ret)
    {
      if (ret === false)
      {
        let now = new Date().getDate() / 1000;
        if(this.signatureExpire < now + 3){
          getSignature().then((res) => {
            if(res.code === SUCCESS){
              return res.data;
            }else {
              this.$emit(SHOW_ALERT, res)
            }
          }).then(res => {
            this.multipart_params = {
              key : res.dir,
              policy: res.policy,
              OSSAccessKeyId: res.accessKeyId,
              success_action_status : '200', //让服务端返回200,不然，默认会返回204
              callback : res.callback,
              signature: res.signature,
              dir: res.dir
            };
            this.signatureExpire = res.expire;
            this.host = res.host;
            this.startLoading(up, filename)
          })
        }else{
          this.startLoading(up, filename)
        }
      }else{
        this.startLoading(up, filename)
      }
    },
    startLoading(up, filename){
      let dir = this.multipart_params.dir;
      this.multipart_params.key = dir + (dir.endsWith('/') ? '' : '/') + filename;
      up.setOption({
        url: this.host,
        multipart_params: this.multipart_params
      });
      up.start();
    }

  }
}
</script>

<style scoped>
.file-container{
  height: 150px;
  width: 270px;
  border-radius: 10px;
  border: 1px dashed darkgrey;
  display: flex;
  justify-content: center;
  margin: 5px;
}
.overlay {
  position: absolute;
  height: 150px;
  width: 270px;
  top: 0;
  left: 0;
  border-radius: 10px;
  z-index: 3;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}
.overlay:hover {
  background-color: rgba(0, 0, 0, 0.3);
  color: white;
}
</style>
