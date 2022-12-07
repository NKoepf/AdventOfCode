package day6

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
	findStart(4)
}

func Part2() {
	findStart(14)
}

func containsDuplicate(input []string) bool {
	for i := 0; i < len(input); i++ {
		for j := 0; j < len(input); j++ {
			if i == j {
				continue
			}
			if input[i] == input[j] {
				return true
			}

		}

	}
	return false
}

func findStart(sequenceLength int) {
	readFile, err := os.Open("day6/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanRunes)

	var index int = 0

	lastLetters := make([]string, sequenceLength)
	var arrayIndex int = 0

	for fileScanner.Scan() {
		if index > sequenceLength && !containsDuplicate(lastLetters) {
			break
		}
		letter := fileScanner.Text()
		lastLetters[arrayIndex] = letter

		if arrayIndex == sequenceLength-1 {
			arrayIndex = 0
		} else {
			arrayIndex++
		}
		index++
	}

	if sequenceLength == 4 {
		fmt.Print("D6P1: ")
	} else {
		fmt.Print("D6P2: ")
	}
	fmt.Printf("First letter to have %d individuals %d\n", sequenceLength, index)
}
