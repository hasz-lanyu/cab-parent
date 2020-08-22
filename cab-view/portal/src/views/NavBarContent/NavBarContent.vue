<template>
  <div id="nav-bar-content">
    <nav-bar>
      <nav-bar-item slot="left">
        <div class="left">CAB</div>
      </nav-bar-item>
      <nav-bar-item slot="center">
        <div class="center" >
        <input class="search" type="text">
          <b-button  variant="dark" size="sm"> <span class="btncla">C</span></b-button></div>
      </nav-bar-item>
      <nav-bar-item slot="right">
        <b-button>登录</b-button>
        <b-button>注册</b-button>
      </nav-bar-item>
    </nav-bar>
  </div>
</template>

<script>
  import NavBar from '../../components/common/navbar/NavBar'
  import NavBarItem from '../../components/common/navbar/NavBarItem'

  export default {
    name: "index",
    components: {
      NavBar,
      NavBarItem

    }
  }
</script>

<style scoped>
  .left {
    font-size: 50px;
    padding: 10px;
  }

  .search {
    -webkit-appearance: none;
    -moz-appearance: none;
    font-size: 1.4em;
    height: 1.9em;
    border-radius: 3px;
    outline: 0;
    box-sizing: border-box;
    text-align: center;
    color: #6a6f77;
    background: transparent;
    border: 1px solid #6a6f77;
    padding: 1%;
    text-decoration: none;
  }
  .btncla{
    font-size: 30px;
  }
  .center{
    padding: 7px;
  }




</style>