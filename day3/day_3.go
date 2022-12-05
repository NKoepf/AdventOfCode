package day3

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
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

	readFile, err := os.Open("day3/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	var prioSum int = 0

	for fileScanner.Scan() {
		var line string = fileScanner.Text()

		lineLength := len(line)
		var part1 string = line[0 : lineLength/2]
		var part2 string = line[lineLength/2 : lineLength]

		charsOfPart1 := []rune(part1)
		charsOfPart2 := []rune(part2)

		var match rune

	search:
		for _, char1 := range charsOfPart1 {
			for _, char2 := range charsOfPart2 {
				if char2 == char1 {
					match = char2
					break search
				}
			}
		}

		prioSum += calcPrioOfRune(match)

	}
	fmt.Printf("Sum of all prios: %d", prioSum)
}

func Part2() {

	readFile, err := os.Open("day3/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	var prioSum int = 0

	var elve1, elve2, elve3 string
	var charsOfElve1, charsOfElve2, charsOfElve3 []rune

	var index int = 0
	var match rune

	for fileScanner.Scan() {

		if index == 0 {
			elve1 = fileScanner.Text()
			charsOfElve1 = []rune(elve1)
			index++
		} else if index == 1 {
			elve2 = fileScanner.Text()
			charsOfElve2 = []rune(elve2)
			index++
		} else {
			elve3 = fileScanner.Text()
			charsOfElve3 = []rune(elve3)

		search:
			for _, char1 := range charsOfElve1 {
				for _, char2 := range charsOfElve2 {
					for _, char3 := range charsOfElve3 {
						if char2 == char1 && char2 == char3 {
							match = char2
							break search
						}
					}
				}
			}
			index = 0
			prioSum += calcPrioOfRune(match)
		}
	}
	fmt.Printf("Sum of all group prios: %d", prioSum)

}

func calcPrioOfRune(char rune) int {
	/*
		Ranges for runes:
		a-z = 97-122
		A-Z = 65-90
	*/

	if char >= 97 && char <= 122 {
		return int(char - 96)

	} else if char >= 65 && char <= 90 {
		return int(char - 64 + 26)
	} else {
		return 0
	}

}
