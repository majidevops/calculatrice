pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {
        

        stage('Compilation') {
            steps {
                sh '''
                    echo "Java version:"
                    java -version
                    ./gradlew classes
                '''
            }
        }

        stage('Tests') {
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    junit 'build/test-results/test/**/*.xml'
                }
            }
        }

        stage('Analyse statique') {
            steps {
                sh "./gradlew checkstyleMain"
            }
        }
    }

    post {
        always {
            // Génération rapport JaCoCo
            sh './gradlew jacocoTestReport'
            
            // Publication rapport JaCoCo dans Jenkins
            jacoco(
                execPattern: '**/jacoco.exec',
                classPattern: 'build/classes/java/main',
                sourcePattern: 'src/main/java',
                exclusionPattern: ''
            )

            // Publication rapport HTML JaCoCo
            publishHTML(target: [
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: 'Rapport JaCoCo HTML'
            ])

            // Publication rapport Checkstyle
            publishHTML(target: [
                reportDir: 'build/reports/checkstyle/',
                reportFiles: 'main.html',
                reportName: 'Checkstyle Report'
            ])
        }
    }
}