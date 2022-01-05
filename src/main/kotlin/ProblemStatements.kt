fun main() {
    println("Permutation in a String:")
    println("${findPermutation("abc","oidbcaf")} expected: true")
    println("${findPermutation("dc","odicf")} expected: false")
    println("${findPermutation("bcdyabcdx","bcdxabcdy")} expected: true")
    println("${findPermutation("abc","aaacb")} expected: true")
    println("${findPermutation("abc","llacccb")} expected: false")
    println("${findPermutation("abc","llacccba")} expected: true")
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