var saList = new Vue({
  el: '#survey-app',
  data: {
    surveyTitle: "",
    surveyList: []
  },
  mounted: function () {
    this.getSurveyList()
  },
  methods: {
    getSurveyList: function () {
        axios.get("/api/survey")
        .then(response => {this.surveyList = response.data.res})
    },
    postSurvey: function () {
        var param = {req: {title: this.surveyTitle}}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/survey", param)
    }
  }
});
