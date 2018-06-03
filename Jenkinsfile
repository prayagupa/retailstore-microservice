node {
   def mvnHome
   stage('Unit and component tests') {
      git url: 'https://github.com/prayagupd/eccount-rest.git', branch: 'REST-API-load-balancing'
      mvnHome = tool 'MAVEN3'
      sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean install"
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.war'
   }
}
