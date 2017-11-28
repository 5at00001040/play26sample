


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

            var radioArray = Array.from(document.querySelectorAll('.a-radio'))
            var selectArray = radioArray.filter(e => e.checked).map(e => e.value)
            var answerArray = selectArray.map(e => {
                var a = e.split(":");
                var b = a[0].split("-");
                return { qid: Number(b[0]), qType: b[1], value: Number(a[1]) };
            })

            var paramArray = answerArray.map(e => {
                var p = null
                if (e.qType == "sa") {
                    p = {questionId: e.qid, questionType: e.qType, choice: e.value}
                } else if (e.qType == "eo") {
                    p = {questionId: e.qid, questionType: e.qType, choice: e.value}
                }
                return p
            })

            var param = {req: paramArray}
            axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
            axios.post("/api/answer", param)
            this.getQuestionList()
        },
      }
    });
}
