var saList = new Vue({
  el: '#sa-app',
  data: {
    question: "質問を入力してください",
    choice1: "選択肢を入力してください",
    choice2: "選択肢を入力してください",
    choice3: "選択肢を入力してください",
    choice4: "選択肢を入力してください",
    choice5: "選択肢を入力してください",
    questionIdList: [],
    selectQuestionId: 0,
    questionData: {},
    answerData: {}
  },
  mounted: function () {
    this.pullQuestionIdList()
  },
  methods: {
    pullQuestionIdList: function () {
      axios.get("/sa")
      .then(response => {this.questionIdList = response.data.res})
    },
    pullQuestion: function () {
      axios.get("/sa/" + this.selectQuestionId)
      .then(response => {this.questionData = response.data.res[0]})
    },
    pullAnswer: function () {
      axios.get("/sa/answer/" + this.selectQuestionId)
      .then(response => {this.answerData = response.data})
    },
    changeQuestion: function () {
        this.pullQuestionIdList()
        this.pullQuestion()
        this.pullAnswer()
    },
    pushQuestion: function () {
        var param = {req: {question: this.question, choice1: this.choice1, choice2: this.choice2, choice3: this.choice3, choice4: this.choice4, choice5: this.choice5}}
        axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
        axios.post("/sa", param)
        this.pullQuestionIdList()
    },
    pushAnswer: function (qid, radioName) {
        var elements = document.getElementsByName(radioName)
        for (var checked = "", i=elements.length; i--;) {
            if ( elements[i].checked ) {
                var checked = elements[i].value
                break
            }
        }
        var param = {req: {questionId: qid, choice: Number(checked)}}
        axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
        axios.post("/sa/answer", param)
        this.pullAnswer()
    },
    resetData: function () {
        axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
        axios.post("/reset")
        this.questionIdList = []
        this.selectQuestionId = 0
        this.questionData = {},
        this.answerData = {}
    }
  }
});
