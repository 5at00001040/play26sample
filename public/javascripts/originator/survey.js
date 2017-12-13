
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
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.get("/api/survey")
            .then(response => {this.surveyList = response.data.res})
        },
        postSurvey: function () {
            var param = {req: {title: this.surveyTitle}}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/survey", param)
            .then(response => {
            location.href = '/originator/question/' + response.data.res.id
            })
        },
        deleteSurvey: function (id) {
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.delete("/api/survey/" + id)
            .then(response => {this.surveyList = response.data.res})
        }
      }
    });
}


if (document.getElementById('question-list') != null) {
    var questionList = new Vue({
      el: '#question-list',
      data: {
        surveyTitle: "",
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
        this.getSurveyTitle()
        this.getQuestionList()
      },
      methods: {
        getSurveyTitle: function () {
            var sid = document.getElementById("surveyId").textContent
            axios.get("/api/survey/" + sid)
            .then(response => {this.surveyTitle = response.data.res.title})
        },
        getQuestionList: function () {
            var sid = document.getElementById("surveyId").textContent
            axios.get("/api/question/sid/" + sid)
            .then(response => {this.questionList = response.data.res})
        },
        postSaQuestion: function () {
            var sid = Number(document.getElementById("surveyId").textContent)

            var oc = [this.saChoice1, this.saChoice2, this.saChoice3, this.saChoice4, this.saChoice5]
            var ec = oc.filter(e => e != "")
            var cc = ec.length
            var pc = ec.concat(["", "", "", "", ""]);

            var param = {req: {
            surveyId: sid,
            questionType: "sa",
            question: this.saQuestion,
            questionCount: cc,
            choice1: pc[0],
            choice2: pc[1],
            choice3: pc[2],
            choice4: pc[3],
            choice5: pc[4]
            }}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/question", param)
            .then(response => this.getQuestionList())
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
            .then(response => this.getQuestionList())
        },
        deleteQuestion: function (qtype, qid) {
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.delete("/api/question/" + qtype + "/" + qid)
            .then(response => this.getQuestionList())
        }
      }
    });
}

if (document.getElementById('survey-result') != null) {
    var surveyList = new Vue({
      el: '#survey-result',
      data: {
        surveyTitle: "",
        surveyResult: []
      },
      mounted: function () {
        this.getSurveyTitle()
        this.getSurveyResult()
      },
      methods: {
        getSurveyTitle: function () {
            var sid = document.getElementById("surveyId").textContent
            axios.get("/api/survey/" + sid)
            .then(response => {this.surveyTitle = response.data.res.title})
        },
        getSurveyResult: function () {
            var sid = document.getElementById("surveyId").textContent
            axios.get("/api/survey-result/" + sid)
            .then(response => {this.surveyResult = response.data.res})
        }
      }
    });
}
