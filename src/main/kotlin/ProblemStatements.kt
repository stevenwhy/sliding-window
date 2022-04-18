fun main() {
    println("Permutation in a String:")
    println("${findPermutation("abc","oidbcaf")} expected: true")
    println("${findPermutation("dc","odicf")} expected: false")
    println("${findPermutation("bcdyabcdx","bcdxabcdy")} expected: true")
    println("${findPermutation("abc","aaacb")} expected: true")
    println("${findPermutation("abc","llacccb")} expected: false")
    println("${findPermutation("abc","llacccba")} expected: true")

    println("${findCountOfAnagrams("pq","ppqp")} expected: [1, 2]")
    println("${findCountOfAnagrams("abc","abbcabc")} expected: [2, 3, 4]")
}

/*
    (hard) Given a string and a pattern, find all anagrams of the pattern in the given string.

    Here we can first set up a freq map of the pattern string.
    Then setup up a sliding window. As we increment windowEnd, we check if letter is in freq Map > 0 times
        If it is, we decrement the map and a counter of letters in the patter.
            If counter = 0, we found an anagram and can do result++
        If letter is not in freq map or is = 0, need to increment windowStart and update freq map if it's in there
 */
fun findCountOfAnagrams(pattern: String, str: String): MutableList<Int> {
    val result = mutableListOf<Int>()
    var patternCount = pattern.length
    var freqMap = pattern.groupingBy { it }.eachCount().toMutableMap()
    val goalMap = freqMap
    var windowStart = 0
    var windowEnd = 0
    while(windowStart < str.length && !freqMap.containsKey(str[windowStart])) {
        windowStart++
        windowEnd++
    }

    while(windowEnd < str.length) {
        val endChar = str[windowEnd]
        if(!freqMap.containsKey(endChar)) {
            // set window to next letter
            windowStart = windowEnd + 1
            freqMap = goalMap // reset the freqMap
        } else if (freqMap.getOrDefault(endChar,0) == 0) {
            // we have seen letter enough times
            // increment windowStart
            while(freqMap.getOrDefault(endChar,0) == 0) {
                val startChar = str[windowStart]
                if(freqMap.containsKey(startChar)) {
                    freqMap[startChar] = freqMap.getOrDefault(startChar,0) + 1
                    patternCount++
                }
                windowStart++
            }
        }
        if(freqMap.getOrDefault(endChar,0) > 0) {
            // it is in freqMap
            freqMap[endChar] = freqMap.getOrDefault(endChar,0) - 1
            patternCount--
            if(patternCount == 0) result.add(windowStart)
        }
        windowEnd++
    }
    return result
}

/**
 * Given a string and a pattern, find out if the string contains any permutation of the pattern.
 */
fun findPermutation(pattern: String, str: String): Boolean {
    // We can store the pattern in a map
    // so we cna O(1) check if the next letter belongs in our pattern
    // We are done once our map has each char exactly once
    // reset the map when finding a letter that is not in the patter
    var result = 0
    var windowStart = 0
    val goalMap: HashMap<Char,Int> = HashMap()
    val frequencyMap: HashMap<Char,Int> = HashMap()
    for(char in pattern) {
        frequencyMap[char] = 0
        goalMap[char] = goalMap.getOrDefault(char,0) + 1
    }

    for(windowEnd in str.indices) {
        val rightLetter = str[windowEnd]
        if(result == pattern.length) break
        if(!frequencyMap.containsKey(rightLetter)) {
            result = 0
            for(key in frequencyMap.keys) frequencyMap[key] = 0
        } else if(frequencyMap.containsKey(rightLetter) && frequencyMap[rightLetter]!! < goalMap[rightLetter]!!) {
            // is in pattern but haven't seen yet all of them
            frequencyMap[rightLetter] = frequencyMap.getOrDefault(rightLetter,0) + 1
            result++
        } else {
            // is in pattern but we have seen all of them already
            for(key in frequencyMap.keys) frequencyMap[key] = 0
            frequencyMap[rightLetter] = frequencyMap.getOrDefault(rightLetter,0) + 1
            windowStart = windowEnd
            result = 1
        }
    }

    return result >= pattern.length
}