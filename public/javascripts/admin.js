var saList = new Vue({
  el: '#admin-app',
  data: {},
  methods: {
    resetData: function () {
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/admin/api/reset")
    }
  }
});
