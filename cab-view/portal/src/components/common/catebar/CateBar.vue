<template>
  <div class="cate-bar">
    <div :class="activeClass(i)" v-for="(item,i) in cname" class="cate-bar-item" :key="i"
         @click="currEvent(i,item.path)">{{item
      .title}}
      </div><p></p>
    <router-view></router-view>
  </div>

</template>

<script>
  import CateContent from '../../../views/category/CateContent'
  export default {
    name: "CateBar",
    props:{
      cname:{
        type:Array,
        default(){
          return []
        }
      }
    },
    data(){
      return{
        current:-1,
        show:false
      }
    },
    methods:{
      currEvent(i,path){
        this.current=i;

        this.$router.push(path);
        console.log(path);
      },
      activeClass(i){
        return {active:this.current===i}
      }
    },
    components:{
      CateContent
    }
  }
</script>

<style scoped>
  .cate-bar{
    display: flex;
    left: 0;
    right: 0;
    position: fixed;


    text-align: center;
    top: 75px;
  }

  .cate-bar-item{
    flex:1;
    font-size: 15px;
    color: #cdcdcd;
  }

  .active{
    color: #ffffff;
    font-style: oblique;
    font-size: 20px;
    font-weight: bolder;
  }
</style>