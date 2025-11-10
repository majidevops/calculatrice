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

        stage('Tests unitaires') {
            steps {
                sh './gradlew test'
                junit 'build/test-results/test/**/*.xml'
            }
        }

        stage('Couverture du code') {
            steps {
                sh './gradlew jacocoTestReport'
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

        stage('Analyse statique') {
            steps {
                sh "./gradlew checkstyleMain"
                publishHTML(target: [
                    reportDir: 'build/reports/checkstyle/',
                    reportFiles: 'main.html',
                    reportName: 'Checkstyle Report'
                ])
            }
        }
    }
    post {
always {
mail to: 'majidlearning7@gmail.com',
subject: "Notification de l'état de compilation est terminée: ${currentBuild.fullDisplayName}",
body: " Votre build est accompli, Veuilez vérifier avec patience: ${env.BUILD_URL}"
}
}
}