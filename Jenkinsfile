pipeline {
    agent any 
    environment {
    	APP_NAME = "provisioning"
        REGISTRY="asia.gcr.io"
        REGISTRY_URL="https://${REGISTRY}"
        REGISTRY_AUTH_KEY='gcr:testunited-sandbox-gcr-push'
        GIT_SSH_KEY='chamith_github'
        DOCKER_IMAGE_LOCAL="testunited/examples-learnright-${APP_NAME}"
        DOCKER_IMAGE_NS="chamith-testunited"
        KUBE_NS="learnright-int"
    }
    
    stages {
   		stage('Define') {
   			steps {
   				script {
		        	version = sh(returnStdout: true, script: "gradle -q bootJarVersion").trim()
		        	BUILD_TAG = "${version}-${BUILD_NUMBER}"
        			TESTUNITED_SESSION_NAME = "${env.JOB_BASE_NAME}-ci-build-${BUILD_TAG}"
        			DOCKER_IMAGE_REMOTE="${REGISTRY}/${DOCKER_IMAGE_NS}/${DOCKER_IMAGE_LOCAL}:${BUILD_TAG}"
		        }
   			}
   		}
   		
        stage('Build') { 
            steps {
                sh "gradle build -x test"
            }
        }
        
        stage('Dev Test') { 
            steps {
                sh "gradle test -Dtestunited.testsession.name=${TESTUNITED_SESSION_NAME}"
            }
        }
        
        stage('Package') { 
            steps{
                sh "gradle jar docker"
            }
        }
        
        stage('Tag') {
            steps{
                sh "git tag ${BUILD_TAG}"

                sshagent(["${GIT_SSH_KEY}"]) {
                    sh "git push origin HEAD:master ${BUILD_TAG}"
                }
                
                sh "docker tag ${DOCKER_IMAGE_LOCAL} ${DOCKER_IMAGE_REMOTE}"
            }
        }
        
        stage('Publish') { 
            steps {
                sh "gradle publish publishStubsPublicationToMavenLocal"
                script{            
                    docker.withRegistry("${REGISTRY_URL}", "${REGISTRY_AUTH_KEY}") {
                        sh "docker push ${DOCKER_IMAGE_REMOTE}"
                    }
                }
            }
        }
        
        stage('Deploy') { 
            steps {
                sh "kubectl set image deployment/${APP_NAME} ${APP_NAME}=${DOCKER_IMAGE_REMOTE} -n ${KUBE_NS}"
            }
        }
        
        stage('API Test') { 
 
            steps {
                script {
                    hook = registerWebhook()
                    
                    sh "cat src/main/kube/job-api-test.yml | sed 's^BUILD_NUMBER^${BUILD_NUMBER}^' | sed 's^JENKINS_CALLBACK^${hook.getURL()}^' | sed 's^TESTUNITED_SESSION_NAME^${TESTUNITED_SESSION_NAME}^' | sed 's^NAMESPACE^${KUBE_NS}^' | kubectl create -f -"
                    echo "Waiting for POST to ${hook.getURL()}"

                    data = waitForWebhook hook
                    echo "Webhook called with data: ${data}"

                    def testunited_session_result_url = "${env.TESTUNITED_SERVICE_URL}/testsessions/${data}/result"
                    def response = sh(script: "curl -s ${testunited_session_result_url}", returnStdout: true)
                    
                    if(response=="false"){
                        currentBuild.result = 'FAILED'
                        testunited_failures_url =  "${env.TESTUNITED_SERVICE_URL}/testsessions/${data}/testruns?result=failed"
                        failures = sh(script: "curl -s ${testunited_failures_url}", returnStdout: true)
                        echo "\n####### FAILURES #######\n${failures}"
                    }
                }
            }
        }
    }
}