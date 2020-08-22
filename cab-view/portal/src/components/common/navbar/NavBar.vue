<template>
  <div class="nav-bar">
    <div class="left"><slot name="left"></slot></div>
    <div class="center"><slot name="center"></slot></div>
    <div class="right"><slot name="right"></slot></div>
  </div>
</template>

<script>
  import NavBarItem from './NavBarItem'
  export default {
    name: "NavBar",
    components:{
      NavBarItem
    }
  }
</script>

<style scoped>
.nav-bar{
  /*横向布局*/
  display: flex;
  position: absolute;
  left: 0;
  right: 0;

  box-shadow: 20px 20px 20px rgba(200, 200, 200, 0.7); /*阴影*/
}
  .left{
    width: 23%;
  }
  .right{
    width: 23%;
  }
 .center{
   flex: 1;
 }

</style>