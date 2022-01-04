import kotlin.math.max
import kotlin.math.min

fun main() {
    println("Welcome to sliding window")

    println("Max SubArray of Size K:")
    println("${max_sub_array_of_size_k(3, intArrayOf(2,1,5,1,3,2))} expected: 9")
    println("${max_sub_array_of_size_k(2, intArrayOf(2,3,4,1,5))} expected: 7")


    println("Smalled SubArray whose Sum is greater than S:")
    println("${smallest_subarray_with_given_sum(7, intArrayOf(2,1,5,2,3,2))} expected: 2")
    println("${smallest_subarray_with_given_sum(7, intArrayOf(2,1,5,2,8))} expected: 1")
    println("${smallest_subarray_with_given_sum(8, intArrayOf(3,4,1,1,6))} expected: 3")


    println("Longest Substring with K Distinct Characters:")
    println("${longestSubstringWithKDistinct(2, "araaci")} expected: 4")
    println("${longestSubstringWithKDistinct(1, "araaci")} expected: 2")
    println("${longestSubstringWithKDistinct(3, "cbbebi")} expected: 5")

    println("Length of the longest substring which has no repeating characters:")
    println("${nonRepeatingSubstring("aabccbb")} expected: 3")
    println("${nonRepeatingSubstring("abbbb")} expected: 2")
    println("${nonRepeatingSubstring("abccde")} expected: 3")
}

/**
 * Given an array of positive numbers and a positive number ‘k’,
 *   find the maximum sum of any contiguous subarray of size ‘k’.
 */
fun max_sub_array_of_size_k(k: Int, array: IntArray): Int {
    // Plan is to maintain a window of size k
    // to check the next subarray we add the next value in the array
    //   and subtract the value which is getting pushed out
    var windowStart = 0
    var windowEnd = 0
    var result = 0
    var sum = 0
    while(windowEnd <= array.size-1) {
        sum += array[windowEnd]
        if(windowEnd >= k-1) {
            // we have our window of size k
            // need to start subtracting values out
            result = max(result,sum)
            sum -= array[windowStart]
            windowStart++
        }
        windowEnd++
    }
    return result
}
/**
 * Given an array of positive numbers and a positive number ‘S’,
 *   find the length of the smallest contiguous subarray whose sum is greater than or equal to ‘S’.
 * Return 0, if no such subarray exists.
 */
fun smallest_subarray_with_given_sum(sum: Int, array: IntArray): Int {
    // We want to start adding number until our windowSum >= sum
    // then we can compare result to windowSum and save the smallest number
    // then we can try to make the window smaller by subtracting array[windowStart]
    var windowStart = 0
    var result = Int.MAX_VALUE
    var windowSum = 0
    for(windowEnd in 0 until array.size) {
        windowSum += array[windowEnd]

        while(windowSum >= sum) {
            if(windowEnd == windowStart) return 1
            result = min(result,windowEnd-windowStart + 1)
            windowSum -= array[windowStart]
            windowStart++
        }
    }
    if(result == Int.MAX_VALUE) return 0
    return result
}

/**
 * Given a string, find the length of the longest substring in it with no more than K distinct characters.
 */
fun longestSubstringWithKDistinct(k: Int, str: String): Int {
    // We will have a moving window going through the string
    // At every step we can check if we can add the next char at str[windowEnd]
    //    This check will be done by checking if the letter can be added to fixed length k hash map
    //    if the hash map has space add it and increment the window
    //    if the letter already exists in the hash map we can increment the count
    // Else, we need to start removing letter from the windowStart.
    //    remove a letter by decrementing count at HashMap[str[windowStart]]. If becomes 0, remove the key
    //       and incrementing windowStart
    //       continue while HashMap has no space
    val hashMap: HashMap<Char,Int> = HashMap()
    var result = 0
    var windowStart = 0
    for(windowEnd in str.indices) {
        hashMap[str[windowEnd]] = hashMap.getOrDefault(str[windowEnd],0) + 1

        while(hashMap.size > k) {
            // we reached our limit
            hashMap[str[windowStart]] = hashMap.getOrDefault(str[windowStart],1) - 1
            if(hashMap[str[windowStart]] == 0) hashMap.remove(str[windowStart])
            windowStart++
        }
        result = max(result,windowEnd-windowStart+1)
    }
    return result
}


/**
 * Given a string, find the length of the longest substring which has no repeating characters.
 */
fun nonRepeatingSubstring(str: String): Int {
    // We will use a sliding window which will try to add the next letter as long
    //    as it is not already present in a hashMap.
    //    If it already in the hashMap, we need to start deleting from windowStart
    //       until the letter can be added

    var windowStart = 0
    var result = 0
    val hashMap: HashMap<Char,Int> = HashMap()
    for(windowEnd in str.indices) {
        val rightChar = str[windowEnd]
        while(hashMap.containsKey(rightChar)) {
            val leftChar = str[windowStart]
            hashMap.remove(leftChar)
            windowStart++
        }
        hashMap[rightChar] = 1
        result = max(result,windowEnd-windowStart+1)
    }
    return result
}