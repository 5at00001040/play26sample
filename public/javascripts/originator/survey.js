
if (document.getElementById('survey-list') != null) {
    var surveyList = new Vue({
      el: '#survey-list',
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
            this.getSurveyList()
        }
      }
    });
}


if (document.getElementById('question-list') != null) {
    var questionList = new Vue({
      el: '#question-list',
      data: {
        questionList: [],
        question: "",
        choice1: "",
        choice2: "",
        choice3: "",
        choice4: "",
        choice5: ""
      },
      mounted: function () {
        this.getQuestionList()
      },
      methods: {
        getQuestionList: function () {
            var sid = document.getElementById("surveyId").textContent
            axios.get("/api/question/sid/" + sid)
            .then(response => {this.questionList = response.data.res})
        },
        postQuestion: function () {
            var sid = Number(document.getElementById("surveyId").textContent)
            var param = {req: {
            surveyId: sid,
            questionType: "sa",
            question: this.question,
            choice1: this.choice1,
            choice2: this.choice2,
            choice3: this.choice3,
            choice4: this.choice4,
            choice5: this.choice5
            }}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/question", param)
            this.getQuestionList()
        }
      }
    });
}
