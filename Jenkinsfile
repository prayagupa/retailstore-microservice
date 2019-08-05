node {
   def mvnHome
   def applicationVersion="1.0.${BUILD_NUMBER}"
   def applicationName="eccount-rest"
   def containerTag="$applicationName:$applicationVersion"

   println(applicationVersion)

   stage('Preparation') {
      git url: 'https://github.com/prayagupd/eccount-rest.git', branch: 'j8'

      // ** NOTE:
      // ** This 'MAVEN3' Maven tool must be configured
      // ** in the global configuration.
      mvnHome = tool 'MAVEN3'
   }
   stage('Build app artifacts') {
      // Run the maven build
      withEnv(["MVN_HOME=$mvnHome"]) {
            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
      }
   }
   stage('artifacts info') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archiveArtifacts 'eccount-rest-server/target/*.jar'
   }

   stage('build container artifact') {
     println(containerTag)
     sh "docker build -t $containerTag ."
   }
}
