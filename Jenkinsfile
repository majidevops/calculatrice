pipeline {
    agent any
    stages {
        
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