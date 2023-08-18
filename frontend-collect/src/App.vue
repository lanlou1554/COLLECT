<template>
  <router-view></router-view>
</template>

<script>

export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
body {
  margin: 0;
}
.shadow-around{
  box-shadow: 5px 5px 15px lightgray;
}
.shadow-below{
  box-shadow: 0 5px 10px lightgray;
}
.adaptive-col-container {
  display: flex;
  flex-direction: row;
}



.col-container {
  display: flex;
  flex-direction: row;
  margin: 0;
}

.row-container {
  display: flex;
  flex-direction: column;
}
.f1{
  flex: 1;
}
.f2{
  flex: 2;
}
.f3{
  flex: 3;
}
.f4{
  flex: 4;
}
.f5{
  flex: 5;
}
.f6{
  flex: 6;
}
.f7{
  flex: 7;
}
.f8{
  flex: 8;
}
.f9{
  flex: 9;
}
.centertext{
  text-align: center;
}
.space-even{
  justify-content: space-evenly;
}
.al-it-center{
  align-items: center;
}
.card-l {
  border-radius: 30px;
  padding: 10px;
}
.card-m {
  border-radius: 15px;
  padding: 5px;
}
.card-s {
  border-radius: 8px;
  padding: 5px;
}
.card-xs {
  border-radius: 6px;
  padding: 3px;
}

.auto-nl{
  word-wrap: break-word;
}

.title-m{
  font-size: larger;
  font-weight: bold;
}


.inline-title{
  font-weight: bold;
}
</style>
