 pipeline {
    agent any 

    stages {
        stage('Build Application') {
            steps {
                checkout([$class: 'SubversionSCM', 
                            additionalCredentials: [], 
                            excludedCommitMessages: '', 
                            excludedRegions: '', 
                            excludedRevprop: '', 
                            excludedUsers: '', 
                            filterChangelog: false, 
                            ignoreDirPropChanges: false, 
                            includedRegions: '', 
                            canUseUpdate: true,
                            locations: [[credentialsId: 'ef4c9d1a-232e-4e7d-8a68-eadf1f756355', 
                                            depthOption: 'infinity',
                                            ignoreExternalsOption: true, 
                                            local: 'master', 
                                            remote: "http://subversion.westcoast.co.uk/svn/jbatools/trunk/OPG"]], 
                            workspaceUpdater: [$class: 'hudson.scm.subversion.UpdateWithCleanUpdater']])               
                sh 'mvn -B -DskipTests -Dresume=false release:prepare release:perform -Darguments="-Dmaven.javadoc.skip=true" -f ./master/pom.xml clean package'
            }
        }
        stage('Build Application Image') {
            steps {
                echo 'Starting to build docker image'
                script {
                    def dockerfile = 'Dockerfile'
                    def buildid = '1.0'
                    def imagename = 'dockerwestcoast/pocimage'
                    def customImage = docker.build("${imagename}:${buildid}")
                    customImage.push('latest')       
                }
                echo 'Built Container'               
            }
        }
        stage('Update Test Environment') {
            steps {
                echo 'Restarting target environment with new container'
                            
                script {
                    def targetMachine = 'bnwci01'
                    powershell '$password = "Westcoast6033"; $username = "gordon.marsh@westcoast.co.uk"; $password = ConvertTo-SecureString -String $password -AsPlainText -Force; $credential = New-Object System.Management.Automation.PSCredential -argumentlist $username, $password;  invoke-command -computer $targetMachine -filepath "updatedockercontainer.ps1" -Credential $credential'
                 
                }
                echo 'Target environment container updated'
            }
        }
        stage('Ranorex Smoke Testing') {
            steps {
                echo 'Starting Ranorex application testing'
                script {
                    try{
                        bat ("Westcoast_Automation.exe /rc:createOPGOrder")
                        echo "Success - Prepare report"
                        echo "buildnumber is ${BUILD_NUMBER}"
                        step([$class: 'JUnitResultArchiver', allowEmptyResults: true, keepLongStdio: true, testResults: 'Reports\\myApp_Report_${BUILD_NUMBER}.rxlog.junit.xml'])            
                        archiveArtifacts 'Reports\\myApp_Report_${BUILD_NUMBER}.rxzlog'            
                   }
                    catch (Exception err)
                    {
                        echo "Execution Error: ${err}"            
                        archiveArtifacts 'Reports\\myApp_Report_${BUILD_NUMBER}.*'
                        mail to: 'gordon.marsh@westcoast.co.uk', subject: "Failed Pipeline: ${currentBuild.fullDisplayName}", body: "Test Failed ${env.BUILD_URL}"
                        currentBuild.result = 'FAILURE'
                    }
                }
                echo 'Completed Ranorex application testing'
            }
        }
        stage('BDD Application Testing') {
            steps {
                echo 'Starting BDD testing'
                echo 'Completed BDD testing'  
            }            
        }
    }
}
