<template>
    <div class="nav-bar-item">
      <slot></slot>
    </div>
</template>

<script>
  export default {
    name: "NavBarItem"
  }
</script>

<style scoped>
  .nav-bar-item{
    flex: 1; /*均等分布*/
    text-align: center; /*居中*/
    height: 49px;
    font-size: 14px;
    margin: 15px;
    padding-top: 1px;
  }




</style>