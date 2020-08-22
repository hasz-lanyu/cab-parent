<template>
  <div>
    <nav-bar-content></nav-bar-content>
    <hots-view></hots-view>
    <cate-bar :cname="[{path:'/need',title:'需求支持'},
    {path:'/skill',title:'技术支持'},{path:'/play',title:'主题中心'},
    {path:'/prize',title:'活动中心'}]"></cate-bar>

  </div>
  
</template>

<script>
  import NavBarContent from '../NavBarContent/NavBarContent';
  import HotsView from '../hots/HotsView'
  import CateBar from '../../components/common/catebar/CateBar'

  export default {
    name: "index",
    components:{
      NavBarContent,
      HotsView,
      CateBar,
    }

  }
</script>


<style scoped>

</style>