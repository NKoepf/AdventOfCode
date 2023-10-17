package day1

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
	readFile, err := os.Open("day1/day_1.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	elveIndex := 1
	caloriesPerElve := 0

	highScoreCals := 0
	indexOfHighscore := 1

	fmt.Print(elveIndex)

	for fileScanner.Scan() {
		line := fileScanner.Text()

		if line == "" {
			//fmt.Printf("Elve %d is done. Carried Calories is %d\n", elveIndex, caloriesPerElve)
			if caloriesPerElve > highScoreCals {
				highScoreCals = caloriesPerElve
				indexOfHighscore = elveIndex

			}
			caloriesPerElve = 0
			elveIndex++
			continue
		}

		value, err := strconv.Atoi(line)
		if err != nil {
			return
		}

		caloriesPerElve += value

	}

	fmt.Printf("D1P1: Highscore: %d from elve %d\n", highScoreCals, indexOfHighscore)
}

func Part2() {
	readFile, err := os.Open("day1/day_1.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	elveIndex := 1
	caloriesPerElve := 0

	topThree := [3]int{0, 0, 0}

	for fileScanner.Scan() {
		line := fileScanner.Text()

		if line == "" {
			//fmt.Printf("Elve %d is done. Carried Calories is %d\n", elveIndex, caloriesPerElve)

			topThree = replaceLowest(topThree, caloriesPerElve)
			//fmt.Printf("Current top three vals %v\n", topThree)

			caloriesPerElve = 0
			elveIndex++
			continue
		}

		value, err := strconv.Atoi(line)
		if err != nil {
			return
		}
		caloriesPerElve += value
	}

	sumOfTopThree := topThree[0] + topThree[1] + topThree[2]
	fmt.Printf("D1P2: Combine vaue of the top three %d\n", sumOfTopThree)

}

func replaceLowest(input [3]int, newVal int) [3]int {

	lowestVal := 9999999
	indexOfLowest := 0

	// get lowest value in trio
	for index, val := range input {
		if val < lowestVal {
			lowestVal = val
			indexOfLowest = index
		}
	}

	if newVal > lowestVal {
		input[indexOfLowest] = newVal
	}

	return input

}
