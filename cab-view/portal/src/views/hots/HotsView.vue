<template>
  <div class="hots-view">

      <b-card
              title="Card Title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 15rem;"
              class="mb-2"
              img-height="90px">

      </b-card>


      <b-card
              title="Card Title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 15rem;"
              class="mb-2"
              img-height="90px">

      </b-card>


      <b-card
              title="Card Title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 15rem;"
              class="mb-2"
              img-height="90px">
      </b-card>

  </div>

</template>

<script>
  export default {
    name: "HotsView",
    props: {}
  }
</script>

<style scoped>
  .hots-view {

    display: flex;
    position: absolute;
    top: 100px;
    left: 0;
    right: 0;
    padding-top: 1px;

  }

  .mb-2 {
    flex: 1;
    padding-top: 1px;
    margin: 30px auto;
  }
  .hots-view-item img{

  }

</style>