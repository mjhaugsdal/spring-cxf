pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('codescene') {
            steps {
                  codescene analyzeBranchDiff: true, baseRevision: 'origin/master', credentialsId: '7e18ef4f-ef75-4a38-8d42-f57e721da225', deltaAnalysisUrl: 'http://localhost:3003/projects/1/delta-analysis', failOnDecliningCodeHealth: false, failOnFailedGoal: false, originUrl: 'https://github.com/mjhaugsdal/spring-cxf.git', repository: 'spring-cxf'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}