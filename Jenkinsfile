pipeline {
    agent any
    
    environment {
        // Définit JAVA_HOME directement
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build et Tests') {
            steps {
                sh '''
                    echo "Java version:"
                    java -version
                    echo "Gradle version:"
                    ./gradlew --version
                    ./gradlew clean build
                '''
            }
            post {
                always {
                    junit 'build/test-results/test/**/*.xml'
                }
            }
        }
        
        stage('Rapports Couverture') {
            steps {
                sh './gradlew jacocoTestReport'
            }
        }
    }
    
    post {
        always {
            // Rapport JaCoCo
            jacoco(
                execPattern: 'build/jacoco/test.exec',
                classPattern: 'build/classes/java/main',
                sourcePattern: 'src/main/java'
            )
            
            // Rapport HTML détaillé
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: 'Rapport JaCoCo Détaillé'
            ])
        }
    }
}