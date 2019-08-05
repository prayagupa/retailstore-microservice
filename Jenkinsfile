node {
   def mvnHome
   stage('Unit and component tests') {
      git url: 'https://github.com/prayagupd/eccount-rest.git', branch: 'master'
      mvnHome = tool 'MAVEN3'
      sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'eccount-rest-server/target/*.jar'
   }
}
