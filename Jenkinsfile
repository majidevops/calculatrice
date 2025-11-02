pipeline {
    agent any
    stages {
        stage("Checkout") {
            steps {
                git url: "https://github.com/majidevops/calculatrice.git", branch: "main"
            }
        }
        stage("Compilation") {
            steps {
                sh "./gradlew compileJava"
                
            }
            
        }
        stage("test unitaire") {
      steps {
               sh "./gradlew test"
         }
}
    }
                
}