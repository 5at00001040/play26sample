var saList = new Vue({
  el: '#sa-app',
  data: {
    question: "質問",
    choice1: "選択肢1",
    choice2: "選択肢2",
    choice3: "選択肢3",
    choice4: "選択肢4",
    choice5: "選択肢5",
    picked: "",
    res: [],
    interval: null,
  },
  mounted: function () {
    this.loadData()
  },
  methods: {
    loadData: function () {
        axios.get("http://localhost:9000/sa")
        .then(response => {this.res = response.data.res})
    },
    addData: function () {
        var param = {req: {question: this.question, choice1: this.choice1, choice2: this.choice2, choice3: this.choice3, choice4: this.choice4, choice5: this.choice5}}
        axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
        axios.post("http://localhost:9000/sa", param)
        this.loadData()
    },
    answer: function (qid, radioName) {
        var elements = document.getElementsByName(radioName)
        for (var checked = "", i=elements.length; i--;) {
            if ( elements[i].checked ) {
                var checked = elements[i].value
                break
            }
        }

        console.log(qid + ":" + checked)
        var param = {req: {questionId: qid, choice: Number(checked)}}
        axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
        axios.post("http://localhost:9000/sa/answer", param)

        this.loadData()
    },
    resetData: function () {
        axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
        axios.post("http://localhost:9000/reset")
        this.loadData()
    }
  },
  created: function () {
    this.loadData()
    this.interval = setInterval(function () {
        this.loadData()
    }.bind(this), 10000)
  },
  beforeDestroy: function(){
    clearInterval(this.interval);
  }.bind(this)
});
