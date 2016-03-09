import fung.xiangh.lightrep.tools.HashTools
import spock.lang.Specification

class HashToolsSpec extends Specification {

    def "Single element has depth 1"() {
        expect:
        1 == HashTools.getOptimalDepth(Collections.singleton(val))

        where:
        val << ["a", "test", 1, 0, new Object()]
    }

    def "Replication increase depth"() {
        expect:
        depth == HashTools.getOptimalDepth(val)

        where:
        depth | val
        2     | [1, 1]
        3     | [2, 2, 2]
        4     | ["abc", "abc", "abc", "abc"]
        2     | ["FB", "Ea"] // might become invalid if String hashcode change
        3     | [1, 2, 2, 1, 2, 4, 3, 13234]
    }

    def "Relevant bits of various elements"() {
        expect:
        res == HashTools.getRelevantBits(val)

        where:
        res | val
        1   | [1]
        1   | [1, 2]
        1   | [2, 3]
        2   | [2, 4]
        3   | [1, 2, 3]
        31  | [1, 2, 4, 8, 16]
        3   | [2, 3, 4, 5]
    }

    def "Bit count of various elements"() {
        expect:
        count == HashTools.countRelevantBits(val)

        where:
        count | val
        1     | [234323]
        1     | [1, 2]
        1     | [1, 3]
        1     | [1, 4]
        1     | [2234, 4343]
        2     | [1, 2, 3]
        3     | [1, 2, 4]
        3     | [1, 2, 3, 4]
    }

    def "reducers of various int"() {
        expect:
        res == HashTools.getReducerString(val, "var")

        where:
        val | res
        1     | "((var)&1)"
        2     | "((var>>>1)&1)"
        3     | "((var)&3)"
        6     | "((var>>>1)&3)"
        10    | "((var>>>1)&1)|((var>>>3)&1)"
    }
}
