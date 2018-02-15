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
@CucumberOptions(tags=['~@ignore'])
class KarateRunnerSpec extends Specification {

    private static final KARATE_REPORT_PATH = 'build/reports/karate'
    private static final KARATE_XML_PATH = 'build/reports/karate/xml'
    private static final KARATE_THREADS = 1

    void "Run Karate Scenarios"() {
        setup: "Export current port number"
            System.setProperty('karate.server.port', serverPort.toString())

        when: "Running Karate"
            KarateStats stats = CucumberRunner.parallel(getClass(), KARATE_THREADS, KARATE_XML_PATH)

        then: "No errors are expected"
            stats.failCount == 0

        cleanup: "Generate report"
            List<String> jsonPaths = []
            new File(KARATE_XML_PATH).eachFileMatch(FileType.FILES, ~/.+\.json/) { File file ->
                jsonPaths << file.absolutePath
            }

            Configuration config = new Configuration(new File(KARATE_REPORT_PATH), "Karate Tests")
            ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config)
            reportBuilder.generateReports()
    }
}
