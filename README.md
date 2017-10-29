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
sbtのコンソールを立ち上げなくても  
sbt "run 9000"  
でポート指定で起動できる

### slickCodeGen関連

slick-codegen3.2.0からSourceCodeGenerator初期化のパラメーターが変わった  

```
SourceCodeGenerator.main(
      Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
    )
```

http://slick.lightbend.com/doc/3.2.0/code-generation.html  

slickDriverからprofileに変わっているので気をつける


slick-codegenとは直接関係ないがsbtではrunMainで実行するclassを指定できる  
runMain infrastructures.SlickCodegen


### セキュリティ関連
CDN使う場合はapplication.confにcontentSecurityPolicyを設定する必要がある  
今回のように静的ページからPOSTする場合CSRFのトークンが使えないので、csrfのチェックを行わない条件にするヘッダーの指定をする  
play.filters.csrf.header.bypassHeaders

play.crypto.secret="適当な値に変える"

### 起動停止

sbt "start 9000 -DapplyEvolutions.default=true"

kill $(cat target/universal/stage/RUNNING_PID)

