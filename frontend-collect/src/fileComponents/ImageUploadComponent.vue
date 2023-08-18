<template>
  <el-row>
    <div class="image-square-container" @mousemove="show = true" @mouseleave="show = false" v-for="file in fileList" :key="file.id">
      <div class="overlay-file-state image-square" v-if="file.loadType === 2" >
        <span @click="removeFile(file.id)" type="text">
            <el-icon style="background-color: mediumseagreen; margin: 5px; border-radius: 10px; padding: 2px"><ic-check/></el-icon>
        </span>
      </div>
      <div class="overlay image-square" >
        <span @click="removeFile(file.id)" type="text">
            <el-icon><ic-delete style="z-index: 3"/></el-icon>
        </span>
      </div>
      <el-progress
          v-if="file.loadType && file.loadType > 0 && file.loadType < 2"
          :width="100"
          class="overlay-process image-square"
          type="circle"
          :percentage="file.percent">
      </el-progress>
      <img class="image-square" :src="file.url ? file.url : file.preloadUrl" alt=""/>
    </div>
    <div class="add-btn image-square">
      <el-button type="text" :id="componentId">
        <el-icon size="30px" color="darkgrey">
          <ic-plus></ic-plus>
        </el-icon>
      </el-button>
    </div>
  </el-row>
</template>
<script>
import plupload from "plupload";
import {getSignature} from "@/api/FileApi";
import {getRandomName} from "@/utils/util";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
export default {
  name: "ImageUploadComponent",
  props: {
    modelValue: {
      default: []
    },
  },
  watch:{
    modelValue(newVal){
      let oldVal = this.fileList.filter(file => {
        //if(!file.url)allUploaded = false;
        return file.url;
      }).map(file => {
        return file.url;
      })

      let newValSet = new Set(newVal)
      let oldValSet = new Set(oldVal)

      //console.log(this.fileList)
      let newFileList = this.fileList.filter(file => {//删除需要删除的图片
        let url = file.url
        return !(url && oldValSet.has(url) && !newValSet.has(url))
      })
      newFileList.push(
          ...newVal.filter(url => {//添加需要添加的图片
            return !oldValSet.has(url)
          }).map(url => {
            return {
              id: url,
              url: url,
              loadType: 2
            }
          }))

      this.fileList = newFileList;
      //console.log(this.fileList)
    }
  },
  data: () => {
    return {
      show: false,
      fileList: [],
      signatureExpire: 0,
      multipart_params: {},
      host: '',
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
      filters: {
        mime_types: [
          //文件格式
          {
            title: "movie files",
            extensions: "jpeg,png,jpg" //文件格式
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
      let objarr = files.map((val) => {
        let obj = {};
        obj.id = val.id;
        obj.name = val.name;
        obj.type = val.type;
        obj.size = parseInt((val.origSize / 1024 / 1024) * 100) / 100;
        obj.percent = 0;
        obj.loadType = 0;
        obj.preloadUrl = window.URL.createObjectURL(val.getNative());
        return obj;
      });
      this.fileList.push(...objarr);
      this.set_upload_param(uploader, '', false)
    },
    //上传之前回调
    BeforeUpload(uploader, file){
      const randName = getRandomName(file.name);
      this.fileList = this.fileList.filter( (val) => {
        if (val.id === file.id) {
          val.loadType = 1;
          console.log(`before load: ${val.name}`)
          val.name = randName
        }
        return val;
      });
      this.set_upload_param(uploader, randName, true)
    },
    //上传进度回调
    UploadProgress(uploader, file) {
      console.log(`loading: ${file.name} ${file.percent}`)
      this.fileList = this.fileList.map((val) => {
        if (val.id === file.id) {
          val.percent = file.percent;
        }
        return val;
      });
    },
    // 上传成功回调
    FileUploaded(uploader, file, responseObject) {
      this.fileList = this.fileList.map((val) => {
        if (val.id === file.id) {
          setTimeout(() => {
            val.loadType = 2;
          }, 500)
          console.log(`complete: ${val.name} ${val.loadType}`)
          val.url = `${this.host}/${this.multipart_params.dir}${this.multipart_params.dir.endsWith('/') ? '' : '/'}${val.name}`
        }
        return val;
      });
      console.log(responseObject.response)
/*      //let allUploaded = true;
      let urls = this.fileList.filter(file => {
        //if(!file.url)allUploaded = false;
        return file.url;
      }).map(file => {
        return file.url;
      })
      console.log(JSON.stringify(urls))
      this.$emit('update:modelValue', urls)*/
      this.sendUpdateEvent();
    },
    //取消上传回调
    removeFile(id) {
      //console.log(id);
      this.uploader.removeFile(id);
      this.fileList = this.fileList.filter((val) => {
        return val.id !== id;
      });
      this.sendUpdateEvent();
    },
    sendUpdateEvent(){
      let urls = this.fileList.filter(file => {
        //if(!file.url)allUploaded = false;
        return file.url;
      }).map(file => {
        return file.url;
      })
      //console.log('update:modelValue')
      this.$emit('update:modelValue', urls)
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

.image-square {
  height: 100px;
  width: 100px;
  border: 1px solid lightgrey;
  margin: 3px;
  border-radius: 5px;
}

.overlay {
  position: absolute;
  height: 100px;
  width: 100px;

  color: rgba(0, 0, 0, 0);
  z-index: 3;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.overlay-file-state {
  position: absolute;
  height: 100px;
  width: 100px;
  z-index: 2;
  cursor: pointer;
  color: white;
}

.overlay-process {
  position: absolute;
  background-color: rgba(255, 255, 255, 0.8);
  height: 100px;
  width: 100px;
  z-index: 2;
  cursor: pointer;
}

.overlay:hover {
  background-color: rgba(0, 0, 0, 0.3);
  color: white;
}

.image-square-container {
  display: flex;
  flex-direction: row;
}

.add-btn {
  height: 100px;
  width: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
