


if (document.getElementById('question-list') != null) {
    var questionList = new Vue({
      el: '#question-list',
      data: {
        questionList: []
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
        postAnswer: function () {
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
      }
    });
}
