pipeline {
    agent any
        tools {
            // Use Java 8 for the build
            jdk 'Java8'
        }
    agent { label "maven" }

    stages {
        stage('Clone Source') {
            steps {
                checkout([$class: 'GitSCM',
                            branches: [[name: '*/dev']],
                            extensions: [
                              [$class: 'RelativeTargetDirectory', relativeTargetDir: 'diplo-cloud-notificacion-service']
                            ],
                            userRemoteConfigs: [[url: 'https://github.com/urielhdez/diplo-cloud-notificacion-service.git']]
                        ])
            }
        }
        stage("Build Service") {
            steps {
                dir('diplo-cloud-notificacion-service') {
                    sh 'mvn clean install -DskipTests=true'
                }
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
                step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
            }
        }

        stage("Docker Build") {
            steps {
              sh '''
                  oc start-build notificaciones --from-file=./Dockerfile
                  # oc start-build -F notificaciones --from-dir=./pom.xml
              '''
            }
        }
    }
}
