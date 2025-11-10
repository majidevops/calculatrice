pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build + Tests') {
            steps {
                sh '''
                    echo "Java version:"
                    java -version
                    ./gradlew clean test
                '''
            }
            post {
                always {
                    junit 'build/test-results/test/**/*.xml'
                }
            }
        }

        stage('Génération JaCoCo') {
            steps {
                sh './gradlew jacocoTestReport'
            }
        }

        stage("Analyse statique du code") {
            steps {
                sh "./gradlew checkstyleMain"
                publishHTML(target: [
                    reportDir: 'build/reports/checkstyle/',
                    reportFiles: 'main.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }
    }

    post {
        always {
            jacoco(
                execPattern: '**/jacoco.exec',
                classPattern: 'build/classes/java/main',
                sourcePattern: 'src/main/java',
                exclusionPattern: ''
            )

            publishHTML(target: [
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: 'Rapport JaCoCo HTML'
            ])
        }
    }
}