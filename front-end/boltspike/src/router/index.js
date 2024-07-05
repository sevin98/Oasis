import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import BoardView from '@/views/BoardView.vue'
import UserView from '@/views/UserView.vue'
import BoardList from '@/components/board/BoardList.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'
import BoardCreate from '@/components/board/BoardCreate.vue'
import BoardUpdate from '@/components/board/BoardUpdate.vue'
import UserSignUp from '@/components/user/UserSignUp.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import UserDetail from '@/components/user/UserDetail.vue'
import UserUpdate from '@/components/user/UserUpdate.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView,
      children: [
        {
          path:'',
          name:'boardList',
          component: BoardList
        },
        {
          path:':id',
          name:'boardDetail',
          component: BoardDetail
        },
        {
          path:'create',
          name:'boardCreate',
          component: BoardCreate
        },
        {
          path:':id/update',
          name:'boardUpdate',
          component: BoardUpdate
        },
      ]
    },
    {
      path: '/user',
      name: 'user',
      component: UserView,
      children: [
        {
          path: 'signup',
          name: 'userSignup',
          component: UserSignUp
        },
        {
          path: 'login',
          name: 'userLogin',
          component: UserLogin
        },
        {
          path: ':id',
          name: 'userDetail',
          component: UserDetail
        },
        {
          path: ':id/update',
          name: 'userUpdate',
          component: UserUpdate
        },
      ]
    },    
  ]
})

export default router
