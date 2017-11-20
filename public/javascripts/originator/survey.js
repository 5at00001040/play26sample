
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
        saQuestion: "",
        saChoice1: "",
        saChoice2: "",
        saChoice3: "",
        saChoice4: "",
        saChoice5: "",
        eoQuestion: ""
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
        postSaQuestion: function () {
            var sid = Number(document.getElementById("surveyId").textContent)
            var param = {req: {
            surveyId: sid,
            questionType: "sa",
            question: this.saQuestion,
            choice1: this.saChoice1,
            choice2: this.saChoice2,
            choice3: this.saChoice3,
            choice4: this.saChoice4,
            choice5: this.saChoice5
            }}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/question", param)
            this.getQuestionList()
        },
        postEoQuestion: function () {
            var sid = Number(document.getElementById("surveyId").textContent)
            var param = {req: {
            surveyId: sid,
            questionType: "eo",
            question: this.eoQuestion
            }}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/question", param)
            this.getQuestionList()
        }
      }
    });
}
