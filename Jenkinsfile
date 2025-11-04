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

        stage('Diagnostic') {
            steps {
                sh '''
                    echo "=== Fichiers jacoco trouvés ==="
                    find build -name "*.exec" -o -name "*.xml" | head -20
                    
                    echo "=== Répertoires rapports HTML ==="
                    ls -la build/reports/jacoco/test/html || echo "Aucun rapport HTML"
                '''
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
            // Plugin Jenkins JaCoCo → XML après JaCoCo + Gradle 8+
            jacoco(
                execPattern: '**/jacoco.exec',
                classPattern: 'build/classes/java/main',
                sourcePattern: 'src/main/java',
                exclusionPattern: ''
            )

            // Publication du rapport HTML
            publishHTML(target: [
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: 'Rapport JaCoCo HTML'
            ])
        }
    }
}
