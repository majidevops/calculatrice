pipeline {
    agent any
    triggers {
pollSCM('* * * * *')
}
    stages {
        
        stage("Compilation") {
            steps {
                sh "./gradlew compileJava"
            }
        }
        
        stage("Tests unitaires") {
            steps {
                sh "./gradlew test"
            }
        }
        
        stage("Couverture de code") {
            steps {
                sh "./gradlew jacocoTestReport"
                publishHTML(target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: "Rapport JaCoCo"
                ])
                sh "./gradlew jacocoTestCoverageVerification"
            }
        }
        stage("Analyse statique du code") {
      steps {
           sh "./gradlew checkstyleMain"
           publishHTML (target: [
           reportDir: 'build/reports/checkstyle/',
           reportFiles: 'main.html',
           reportName: "Checkstyle Report"
])
           }
        }
    }
    post {
always {
mail to: 'majidlearning7@gmail.com',
subject: "Notification de l'état de compilation est terminée: ${currentBuild.fullDisplayName}",
body: " Votre build est accompli, Veuilez vérifier: ${env.BUILD_URL}"
}
}
}
