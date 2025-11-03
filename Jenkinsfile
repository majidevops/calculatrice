pipeline {
    agent any
    
    tools {
        jdk 'jdk21' // Doit correspondre au nom dans Jenkins
    }
    
    environment {
        // Variables d'environnement utiles
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh './gradlew clean compileJava'
            }
        }
        
        stage('Tests et Couverture') {
            steps {
                sh './gradlew test jacocoTestReport jacocoTestCoverageVerification'
            }
            post {
                always {
                    // Rapport JUnit pour les résultats des tests
                    junit 'build/test-results/test/**/*.xml'
                }
            }
        }
        
        stage('Rapports') {
            steps {
                // Ce stage s'assure que les rapports sont générés
                sh './gradlew jacocoTestReport'
                script {
                    // Vérifie que le rapport existe
                    if (!fileExists('build/reports/jacoco/test/html/index.html')) {
                        error "Le rapport JaCoCo n'a pas été généré"
                    }
                }
            }
        }
    }
    
    post {
        always {
            // 1. RAPPORT JACOCO INTÉGRÉ (Graphiques Jenkins)
            jacoco(
                execPattern: 'build/jacoco/test.exec',
                classPattern: 'build/classes/java/main',
                sourcePattern: 'src/main/java',
                inclusionPattern: '**/*.class',
                exclusionPattern: '**/test/**'
            )
            
            // 2. RAPPORT HTML DÉTAILLÉ (Navigation complète)
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: '📊 Rapport JaCoCo Détaillé',
                reportTitles: 'Couverture de Code Détail'
            ])
            
            // 3. RAPPORT XML (Pour intégrations futures)
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/jacoco/test',
                reportFiles: 'jacocoTestReport.xml',
                reportName: '📈 Rapport JaCoCo XML',
                reportTitles: 'Données Brutes JaCoCo'
            ])
            
            // 4. ARCHIVAGE pour téléchargement
            archiveArtifacts artifacts: 'build/reports/jacoco/test/html/**/*', fingerprint: true
            
            // Nettoyage des workspace si nécessaire
            cleanWs()
        }
        
        success {
            emailext (
                subject: "SUCCÈS Build: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                Le build ${env.BUILD_URL} a réussi !
                
                📊 Couverture de code générée
                🔗 Rapport détaillé: ${env.BUILD_URL}HTML_20Report_20/
                """,
                to: "votre-email@example.com"
            )
        }
        
        failure {
            emailext (
                subject: "ÉCHEC Build: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                Le build ${env.BUILD_URL} a échoué !
                
                Vérifiez les logs pour plus de détails.
                """,
                to: "votre-email@example.com"
            )
        }
    }
    
    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
}