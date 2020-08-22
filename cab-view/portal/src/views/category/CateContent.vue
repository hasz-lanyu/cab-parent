<template>
  <div class="cate-content">
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  <div class="cate-item">aaaaaaaaaaaaaa</div>
  </div>
</template>

<script>
  export default {
    name: "CateContent",
    created(){
      console.log(`我被创建了啊`);
    }
  }
</script>

<style scoped>
.cate-content{
  display: flex;
  left: 0;
  right: 0;
  position: fixed;
  background: url("../../assets/img/cate-hotel.png");
  text-align: center;
  top: 131px;

}
  .cate-item{
    flex: 1;
  }
.cate-item img{
    width: 91%;
}
</style>