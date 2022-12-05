package day4

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {

	part, _ := strconv.Atoi(os.Args[1])

	if part == 1 {
		Part1()
	} else if part == 2 {
		Part2()
	} else {
		fmt.Println("Wrong or missing input")
	}
}

func Part1() {
	readFile, err := os.Open("day4/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	var sectorIds [4]int
	var numberOfFullyContained int = 0

	for fileScanner.Scan() {

		sectorIds = getSectorIds(fileScanner.Text())
		if isFullyContained(sectorIds[0], sectorIds[1], sectorIds[2], sectorIds[3]) {
			numberOfFullyContained++
		}
	}

	fmt.Printf("There are %d sectors which are fully covered by the other elve", numberOfFullyContained)
}

func Part2() {
	readFile, err := os.Open("day4/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	var sectorIds [4]int
	var numberOfPartiallyContained int = 0

	for fileScanner.Scan() {

		sectorIds = getSectorIds(fileScanner.Text())
		if isPartiallyContained(sectorIds[0], sectorIds[1], sectorIds[2], sectorIds[3]) {
			numberOfPartiallyContained++
		}
	}

	fmt.Printf("There are %d sectors which are partialy covered by the other elve", numberOfPartiallyContained)

}

func getSectorIds(pairLine string) [4]int {
	var sectorIds [4]int

	elves := strings.Split(pairLine, ",")
	sectorsElve1 := strings.Split(elves[0], "-")
	sectorsElve2 := strings.Split(elves[1], "-")

	sectorIds[0], _ = strconv.Atoi(sectorsElve1[0])
	sectorIds[1], _ = strconv.Atoi(sectorsElve1[1])
	sectorIds[2], _ = strconv.Atoi(sectorsElve2[0])
	sectorIds[3], _ = strconv.Atoi(sectorsElve2[1])

	return sectorIds
}

func isFullyContained(low1 int, high1 int, low2 int, high2 int) bool {

	// first one contained by second
	if low1 >= low2 && high1 <= high2 {
		return true
		// second one fully contained by first
	} else if low1 <= low2 && high1 >= high2 {
		return true
	} else {
		return false
	}

}

// 2-6,4-8
func isPartiallyContained(low1 int, high1 int, low2 int, high2 int) bool {

	// first one overlaps with second
	if low1 <= low2 && high1 >= low2 {
		return true
		// second one overlaps with first
	} else if low2 <= low1 && high2 >= low1 {
		return true
	} else {
		return false
	}

}
