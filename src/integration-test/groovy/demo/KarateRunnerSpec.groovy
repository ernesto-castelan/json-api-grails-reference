package demo

import com.intuit.karate.cucumber.CucumberRunner
import com.intuit.karate.cucumber.KarateStats
import cucumber.api.CucumberOptions
import grails.testing.mixin.integration.Integration
import groovy.io.FileType
import net.masterthought.cucumber.Configuration
import net.masterthought.cucumber.ReportBuilder
import spock.lang.Specification

@Integration
class KarateRunnerSpec extends Specification {

    private static final KARATE_REPORT_PATH = 'build/reports/karate'
    private static final KARATE_XML_PATH = 'build/reports/karate/xml'
    private static final KARATE_THREADS = 1

    def setup() {
        System.setProperty('karate.server.port', serverPort.toString())
    }

    def cleanupSpec() {
        List<String> jsonPaths = []
        new File(KARATE_XML_PATH).eachFileMatch(FileType.FILES, ~/.+\.json/) { File file ->
            jsonPaths << file.absolutePath
        }

        Configuration config = new Configuration(new File(KARATE_REPORT_PATH), "Karate Tests")
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config)
        reportBuilder.generateReports()
    }

    void "Run Karate scenarios with no data"() {
        when: "Running Karate"
            KarateStats stats = CucumberRunner.parallel(KarateNoDataTestSet.class, KARATE_THREADS, KARATE_XML_PATH)

        then: "No errors are expected"
            stats.failCount == 0
    }

    void "Run Karate scenarios with test data"() {
        setup: "Create test users"
            List<String> usernameList = ['alfa', 'bravo', 'charlie', 'delta', 'echo', 'foxtrot']
            User.withNewSession {
                usernameList.each { String username ->
                    new User(username:username, fullname:"$username zulu").save(flush:true)
                }
                assert User.count() == usernameList.size()
            }

        when: "Running Karate"
            KarateStats stats = CucumberRunner.parallel(KarateWithDataTestSet.class, KARATE_THREADS, KARATE_XML_PATH)

        then: "No errors are expected"
            stats.failCount == 0

        cleanup: "Remove test data"
            User.withNewSession { User.executeUpdate('delete from User') }
    }
}

@CucumberOptions(tags=['~@ignore', '@nodata'])
class KarateNoDataTestSet { }

@CucumberOptions(tags=['~@ignore','~@nodata'])
class KarateWithDataTestSet { }
