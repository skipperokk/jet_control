<template>
  <div class="radioBlock" :style="'position: absolute; left: calc(' + airport.x + '% - 100px); top: calc(' + airport.y + '% - 100px)'">
    <div class="radioPoint" @click="clickCallback(airport)"></div>
    <div class="title">
      {{ airport.name }} <span v-for="(row, ind) in airport.boards"
                          style="color: #fff;"
                          :title="row"
                          :key="ind">&#9992;</span>
    </div>
  </div>
</template>
<script>
export default {
  props:{
    airport: null,
    clickCallback: null
  }
}
</script>
<style>
.radioBlock .radioPoint{
  width: 200px;
  height: 200px;
  background: transparent url("assets/radio.png") no-repeat center center/cover;
}

.radioBlock .title{
  background: #232323;
  color: chartreuse;
  padding: 5px 5px;
  text-align: center;
  font-size: 18px;
}
</style>
