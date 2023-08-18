import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import setting from "@element-plus/icons-vue/dist/es/setting.mjs";
import more from "@element-plus/icons-vue/dist/es/more.mjs";
import uploadFilled from "@element-plus/icons-vue/dist/es/upload-filled.mjs";
import deleteIC from "@element-plus/icons-vue/dist/es/delete.mjs";
import plus from "@element-plus/icons-vue/dist/es/plus.mjs";
import zoomIn from "@element-plus/icons-vue/dist/es/zoom-in.mjs";
import search from "@element-plus/icons-vue/dist/es/search.mjs";
import router from "@/router";
import check from "@element-plus/icons-vue/dist/es/check.mjs";
import refreshLeft from "@element-plus/icons-vue/dist/es/refresh-left.mjs";
import star from "@element-plus/icons-vue/dist/es/star.mjs";
import close from "@element-plus/icons-vue/dist/es/close.mjs";
import edit from "@element-plus/icons-vue/dist/es/edit.mjs";
import charLineSquare from "@element-plus/icons-vue/dist/es/chat-line-square.mjs";
import promotion from "@element-plus/icons-vue/dist/es/promotion.mjs";
import download from "@element-plus/icons-vue/dist/es/download.mjs";
import starFilled from "@element-plus/icons-vue/dist/es/star-filled.mjs";
import arrowLeftBold from "@element-plus/icons-vue/dist/es/arrow-left-bold.mjs";
const app =  createApp(App)

app.use(router)
app.component('ic-setting', setting)
app.component('ic-more', more)
app.component('ic-upload', uploadFilled)
app.component('ic-delete', deleteIC)
app.component('ic-plus', plus)
app.component('ic-zoom-in', zoomIn)
app.component('ic-search', search)
app.component('ic-check', check)
app.component('ic-refresh', refreshLeft)
app.component('ic-star', star)
app.component('ic-close', close)
app.component('ic-edit', edit)
app.component('ic-comment', charLineSquare)
app.component('ic-promotion', promotion)
app.component('ic-download', download)
app.component('ic-star-filled', starFilled)
app.component('ic-back', arrowLeftBold)
app.use(ElementPlus)
app.mount('#app')


