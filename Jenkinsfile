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
                sh 'mvn -B -f ./master/pom.xml clean install -P test package'
            }
        }
        stage('Build & Publish Docker Image') {
            steps {
                echo 'Starting to build docker image'
                script {
                    def dockerfile = 'Dockerfile'
                    def buildid = '1.0'
                    def imagename = 'dockerwestcoast/pocimage'
                    def customImage = docker.build("${imagename}:${buildid}") 
                    echo 'Docker image built'
                    echo 'Starting to publish new Docker image'            
                    customImage.push('latest')
                    customImage.push("${buildid}") 
                }
                echo 'Docker image published'               
            }
        }
        stage('Update Test Environment') {
            steps {
                echo 'Restarting target environment with new container'
                            
                script {
                    //powershell '$targetMachine = "bnwci01"; $password = "Westcoast952"; $username = "automation.test1"; $password = ConvertTo-SecureString -String $password -AsPlainText -Force; $credential = New-Object System.Management.Automation.PSCredential -argumentlist $username, $password;  invoke-command -computer $targetMachine -filepath ".\\InstallFiles\\updatedockercontainer.ps1" -Credential $credential -AsJob'
                    //powershell '$targetMachine = "bnwci01"; $password = "Westcoast6034"; $username = "gordon.marsh@westcoast.co.uk"; $password = ConvertTo-SecureString -String $password -AsPlainText -Force; $credential = New-Object System.Management.Automation.PSCredential -argumentlist $username, $password;  invoke-command -computer $targetMachine -filepath ".\\InstallFiles\\updatedockercontainer.ps1" -AsJob'
                    //powershell 'New-Item -Path "C:/" -Name "testfile1.txt" -ItemType "file" -Value "This is a text string." '
                    //powershell 'invoke-command -computer "bnwci01" -filepath ".\\InstallFiles\\updatedockercontainer.ps1" -AsJob'
                    powershell 'Invoke-Command -ComputerName "bnwci01" -ScriptBlock { docker pull dockerwestcoast/pocimage }'
                    powershell 'Invoke-Command -ComputerName "bnwci01" -ScriptBlock { docker stop opg1 }'
                    powershell 'Invoke-Command -ComputerName "bnwci01" -ScriptBlock { docker rm opg1 }'
                    powershell 'Invoke-Command -ComputerName "bnwci01" -ScriptBlock { docker run -p 8080:8080 --name opg1 -d dockerwestcoast/pocimage }'                    
                }
                echo 'Target environment container updated'
            }
        }
        stage('Ranorex Smoke Testing') {
            steps {
                echo 'Starting Ranorex application testing'
                script {
                    try{
                        bat (".\\Ranorex\\AQA_CD_Test.exe /rc:SimpleRun /reportfile:Ranorex\\Reports\\Report.rxlog")
                        echo "Success - Prepare report"
                        echo "buildnumber is ${BUILD_NUMBER}"
                        //step([$class: 'JUnitResultArchiver', allowEmptyResults: true, keepLongStdio: true, testResults: 'Reports\\AQA_CD_Test_${BUILD_NUMBER}.rxlog.junit.xml'])            
                        //archiveArtifacts 'Reports\\AQA_CD_Test_${BUILD_NUMBER}.rxzlog'            
                   }
                    catch (Exception err)
                    {
                        echo "Execution Error: ${err}"            
                        //archiveArtifacts 'Reports\\AQA_CD_Test_${BUILD_NUMBER}.*'
                        //mail to: 'gordon.marsh@westcoast.co.uk', subject: "Failed Pipeline: ${currentBuild.fullDisplayName}", body: "Test Failed ${env.BUILD_URL}"
                        currentBuild.result = 'FAILURE'
                    }
                }
                echo 'Completed Ranorex application testing'
            }
        }
        stage('BDD Application Testing') {
            steps {
                echo 'Starting BDD testing'
                sh 'mvn -B clean install'
                echo 'Completed BDD testing'  
            }            
        }
    }
}
