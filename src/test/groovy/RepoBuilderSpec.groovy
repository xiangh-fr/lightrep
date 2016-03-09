import fung.xiangh.lightrep.RepoBuilder
import spock.lang.Specification

class RepoBuilderSpec extends Specification {

    def "Empty build"() {
        given:
        RepoBuilder<String, String> builder = new RepoBuilder<>();

        expect:
        null == builder.build().get(key)

        where:
        key << ["a", "key"]
    }

    def "Build with elements"() {
        given:
        RepoBuilder<String, String> builder = new RepoBuilder<>();

        when:
        tuple.each {
            builder.add(it.get(0), it.get(1));
        }
        then:
        "value" == builder.build().get("key")

        where:
        tuple << [[["key","value"]],[["a","b"],["key","value"], ["key2","value2"]]]
    }
}
