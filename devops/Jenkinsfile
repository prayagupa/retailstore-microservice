node {
   def mvnHome
   def applicationVersion="1.0.${BUILD_NUMBER}"
   def applicationName="eccount-rest"
   def containerTag="$applicationName:$applicationVersion"

   //export as env or setup in jenkins
   def devContainerRegistry="${env.DEV_CONTAINER_REGISTRY}"
   def remoteContainerRegistryTag="$devContainerRegistry/$containerTag"

   stage('Preparation') {
      git url: 'https://github.com/prayagupd/eccount-rest.git', branch: 'j8'

      // ** NOTE:
      // ** This 'MAVEN3' Maven tool must be configured
      // ** in the global configuration.
      mvnHome = tool 'MAVEN3'
   }
   stage('Build app artifacts') {
      withEnv(["MVN_HOME=$mvnHome"]) {
            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
      }
   }
   stage('artifacts info') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archiveArtifacts 'eccount-rest-server/target/*.jar'
   }

   stage('build container artifact') {
     println(remoteContainerRegistryTag)
     sh "docker build -t $containerTag ."
     sh "docker tag $containerTag $remoteContainerRegistryTag"
     sh "docker push $remoteContainerRegistryTag"
   }

   stage('deploy container'){
     def remoteContainerRegistryTag1="$devContainerRegistry\\/$containerTag"
     sh 'kubectl apply -f devops/restserver-k8-service.yaml --namespace dev'
     sh "sed -i 's/.*REMOTE_CONTAINER_REGISTRY_TAG.*/        - image: '${remoteContainerRegistryTag1}'/' devops/restserver-k8-deployment.yaml"
     sh "kubectl apply -f devops/restserver-k8-deployment.yaml --namespace dev"
     //sh "git checkout ."
   }
}
