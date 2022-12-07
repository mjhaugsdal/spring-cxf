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
                  codescene analyzeBranchDiff: true, baseRevision: 'origin/master', credentialsId: '30be1bd7-a4ee-4713-ac00-8ac3530d8029', deltaAnalysisUrl: 'http://localhost:3003/projects/1/delta-analysis', failOnDecliningCodeHealth: false, failOnFailedGoal: false, originUrl: 'https://github.com/mjhaugsdal/spring-cxf.git', repository: 'spring-cxf'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}