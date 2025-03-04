import { defineStore } from 'pinia';

export const useRouterStore = defineStore('router', {
  state: () => ({
    fromRoute: null, // 存储来源路由
  }),
  actions: {
    setFromRoute(route) {
      this.fromRoute = route;
    },
  },
});