## 環境構築
1. XAMPPコントロールパネルのアプリケーションを開く。
2. ApacheとMySQLを起動する。（各「Start」ボタンを押下）
3. MySQLの「Admin」ボタンを押下し、PHPMySQLAdminの管理画面を開く。
4. データベースを新規作成作成する。→ データベース名 : **trainingdb**
   <br/><font color="Red">※ 文字コード：「utf8mb4_general_ci」を選択すること</font>
5. TrainingList\src\main\resourcesフォルダにあるapplication.propertiesファイルのusername、passwordを設定する

~~~
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/trainingdb
spring.datasource.username=  ←ココ
spring.datasource.password=  ←ココ
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.sql.init.mode=always
~~~
***
**ブラウザで「 http://localhost:8080/training/list 」を開く。**