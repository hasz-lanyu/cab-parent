import Vue from 'vue'
import VueRouter from 'vue-router'

const Cate = () => import('../views/category/CateContent');
const Paly = () => import('../views/category/PlayContent');
const Prize = () => import('../views/category/PrizeContent');
const Skill = () => import('../views/category/SkillContent');

Vue.use(VueRouter);

const routes = [
  {
    path: '/need',
    component: Cate
  }, {
    path: '/play',
    component: Paly
  },
  {
    path: '/prize',
    component: Prize
  },
  {
    path: '/skill',
    component: Skill
  }

]

const router = new VueRouter({
  routes,
  mode:'history'
})

export default router
