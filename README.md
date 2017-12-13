Study for a Play Framework 2.6
====

## 目的
- 職場でアンケート実施してみたものの紙だと扱いづらいのでシステムしたい
- Playの2.6を触ってみて躓く事がないか調べる
- Vue.jsを使ってみる

## 覚書
### プロジェクト作成

### PlayとSBTの組み合わせ
Play 2.6.5はsbtの1.x系に対応できていないのでsbt 0.13を使う  
sbtのバージョンは以下のファイルで指定する  
project/build.properties  
と思ったら2.6.6からsbt 1.x系も使えるようになったようです  
https://blog.playframework.com/play-2-6-6-released/  

### Activator開発終了
activator開発終了になったのでsbtで起動する  
sbtのコンソールを立ち上げなくても以下のコマンドでポート指定できる  

```
sbt "run 9000"
```



### slickCodeGen関連

slick-codegen3.2.0からSourceCodeGenerator初期化のパラメーターが変わった  
slickDriverからprofileに変わっているので気をつける

```
SourceCodeGenerator.main(
      Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
    )
```

http://slick.lightbend.com/doc/3.2.0/code-generation.html  


slick-codegenとは直接関係ないがsbtではrunMainで実行するclassを指定できる

```
runMain infrastructures.SlickCodegen
```


### セキュリティ関連
CDN使う場合はapplication.confにcontentSecurityPolicyを設定する必要がある  
今回のように静的ページからPOSTする場合CSRFのトークンが使えないので、csrfのチェックを行わない条件にするヘッダーの指定をする  

```
play.filters.csrf.header.bypassHeaders
```

本番用として起動する場合は秘密鍵の設定を行う必要があるため、環境変数から読むようにする  
設定し忘れる可能性が高いので、changemeも変えておいたほうがよい  
AWSのパラメータストアを使うと便利  

または、後述のように起動時にパラメータとして渡す

```
play.crypto.secret="changeme"
play.crypto.secret=${?APPLICATION_SECRET}
```


### 起動/停止

```
sbt "start 9000"
# 開発時は便利だが本番ではEvolutionsは使用しないほうがいいかもしれない
sbt "start 9000 -DapplyEvolutions.default=true -Dplay.evolutions.db.default.autoApplyDowns=true -Dplay.http.secret.key=かえる"

kill $(cat target/universal/stage/RUNNING_PID)
```

### その他

EC2のt2.microだどCPUの性能的にコンパイルに時間がかかったり失敗(?)するので、実行可能Jarに固めてデプロイしたほうが良いかもしれない



