package org.unbrokendome.gradle.plugins.helm.command

import org.spekframework.spek2.style.specification.describe
import org.unbrokendome.gradle.plugins.helm.command.tasks.HelmAddRepository
import org.unbrokendome.gradle.plugins.helm.spek.*
import org.unbrokendome.gradle.plugins.helm.testutil.exec.singleInvocation
import org.unbrokendome.gradle.plugins.helm.testutil.execute


object HelmAddRepositoryTest : ExecutionResultAwareSpek({

    val project by setupGradleProject { applyPlugin<HelmCommandsPlugin>() }

    val execMock by gradleExecMock()

    val task by gradleTask<HelmAddRepository> {
        repositoryName.set("my-repo")
        url.set(project.uri("http://my-repo.example.com"))
    }


    withOptionsTesting(GlobalOptionsTests) {

        describe("executing a HelmAddRepository task") {

            it("should execute helm repo add") {

                task.execute()

                execMock.singleInvocation {
                    expectCommand("repo", "add")
                    expectArg("my-repo")
                    expectArg("http://my-repo.example.com")
                }
            }


            it("should use caFile property") {

                task.caFile.set(project.file("ca.pem"))

                task.execute()

                execMock.singleInvocation {
                    expectCommand("repo", "add")
                    expectOption("--ca-file", "${project.projectDir}/ca.pem")
                    expectArg("my-repo")
                    expectArg("http://my-repo.example.com")
                }
            }


            it("should use certificateFile and keyFile properties") {

                with(task) {
                    certificateFile.set(project.file("cert.pem").absolutePath)
                    keyFile.set(project.file("key.pem").absolutePath)
                }

                task.execute()

                execMock.singleInvocation {
                    expectCommand("repo", "add")
                    expectOption("--cert-file", "${project.projectDir}/cert.pem")
                    expectOption("--key-file", "${project.projectDir}/key.pem")
                    expectArg("my-repo")
                    expectArg("http://my-repo.example.com")
                }
            }


            it("should use username and password properties") {

                with(task) {
                    username.set("testUser42")
                    password.set("topsecret")
                }

                task.execute()

                execMock.singleInvocation {
                    expectCommand("repo", "add")
                    expectOption("--username", "testUser42")
                    expectOption("--password", "topsecret")
                    expectArg("my-repo")
                    expectArg("http://my-repo.example.com")
                }
            }


            it("should use failIfExists property") {

                task.failIfExists.set(true)

                task.execute()

                execMock.singleInvocation {
                    expectCommand("repo", "add")
                    expectFlag("--no-update")
                    expectArg("my-repo")
                    expectArg("http://my-repo.example.com")
                }
            }
        }
    }
})
