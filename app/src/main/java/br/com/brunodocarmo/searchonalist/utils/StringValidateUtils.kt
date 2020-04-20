package br.com.brunodocarmo.searchonalist.utils

import kotlin.collections.HashMap
import kotlin.math.floor

object StringValidateUtils {
    fun isTypo(template: String, test: String): Boolean {
        val result = removePrefixSuffixCommon(template, test)

        return (result.first.length <= 1 && result.second.length <= 1)
    }

    fun isPartialPermutation(template: String, test: String): Boolean {
        if (template.length != test.length || template[0] != test[0]) {
            return false
        } else {
            val templateDict = HashMap<Char, Int>()
            val testDict = HashMap<Char, Int>()

//            Check if the words have the same letters
            for (pair in template.asSequence().zip(test.asSequence())) {
//            Pair (Template Letter, Test Letter)
                if (templateDict.containsKey(pair.first)) {
                    templateDict[pair.first] = templateDict[pair.first]!!.plus(1)
                } else {
                    templateDict[pair.first] = 1
                }

                if (testDict.containsKey(pair.first)) {
                    testDict[pair.first] = testDict[pair.first]!!.plus(1)
                } else {
                    testDict[pair.first] = 1
                }
            }

            if (templateDict != testDict) {
                return false
            }

            if (template.length >= 3) {
                var changePossibilty = floor((2/3 * template.length).toFloat()).toInt()

                for (pair in template.asSequence().zip(test.asSequence())) {
                    if (pair.first != pair.second) {
                        if (changePossibilty > 0) {
                            changePossibilty--
                        } else {
                            return false
                        }
                    }
                }
            }
            return true
        }
    }


    private fun removePrefixSuffixCommon(template: String, test: String): Pair<String, String> {
        var prefixLen = 0
        var suffixLen = 0

//        Calculate prefix of template and test
        for (pair in template.asSequence().zip(test.asSequence())) {
//            Pair (Template Letter, Test Letter)
            if (pair.first != pair.second) {
                break
            } else {
                prefixLen++
            }
        }

//        Remove prefix
        var newTemplate = template.substring(prefixLen, template.length)
        var newTest = test.substring(prefixLen, test.length)

//        Calculate suffix of template and test
        for (pair in newTemplate.reversed().asSequence().zip(newTest.reversed().asSequence())) {
//            Pair (Template Letter, Test Letter)
            if (pair.first != pair.second) {
                break
            } else {
                suffixLen++
            }
        }

//        Remove suffix
        newTemplate = newTemplate.substring(0, newTemplate.length - suffixLen)
        newTest = newTest.substring(0, newTest.length - suffixLen)

        return Pair(newTemplate, newTest)
    }


}
