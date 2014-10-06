grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolution = {
    inherits( "global" ) {
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
    }
    dependencies {
    }
    plugins {
        runtime(':asynchronous-mail:1.1') {
            excludes 'hibernate'
        }
        compile ":fields:1.4", {
            excludes 'spock'
        }
        runtime ":hibernate:3.6.10.14"
        compile ":mail:1.0.7", {
            excludes 'spring-test'
        }
        build(":tomcat:7.0.52.1", ":release:2.0.3", ":rest-client-builder:1.0.2") {
            export = false
        }
    }
}