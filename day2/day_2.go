package day2

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
	readFile, err := os.Open("day2/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	totalScore := 0

	for fileScanner.Scan() {
		line := fileScanner.Text()
		parts := strings.Split(line, " ")

		totalScore += getScore(parts[0], parts[1])
	}

	fmt.Printf("D2P1: total score of rock, paper, scissors: %d\n", totalScore)

}

func Part2() {
	readFile, err := os.Open("day2/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	totalScore := 0

	for fileScanner.Scan() {
		line := fileScanner.Text()
		parts := strings.Split(line, " ")

		totalScore += getScoreForOutcome(parts[0], parts[1])
	}

	fmt.Printf("D2P2: total score of rock, paper, scissors: %d\n", totalScore)
}

/*
	A X = ROCK 1
	B Y = PAPER 2
	C Z = SCISSOR 3
*/

func getScore(opponents string, yours string) int {

	if opponents == "A" {
		if yours == "X" {
			// rock vs rock
			return 1 + 3
		} else if yours == "Y" {
			// rock vs paper
			return 2 + 6
		} else {
			// rock vs scissor
			return 3 + 0
		}
	} else if opponents == "B" {
		if yours == "X" {
			// paper vs rock
			return 1 + 0
		} else if yours == "Y" {
			// paper vs paper
			return 2 + 3
		} else {
			// paper vs scissor
			return 3 + 6
		}
	} else {
		if yours == "X" {
			// scissor vs rock
			return 1 + 6
		} else if yours == "Y" {
			// scissor vs paper
			return 2 + 0
		} else {
			// scissor vs scissor
			return 3 + 3
		}

	}

}

func getScoreForOutcome(opponent string, outcome string) int {
	if outcome == "X" {
		if opponent == "A" {
			// Lose vs rock
			return 3 + 0
		} else if opponent == "B" {
			// Lose vs paper
			return 1 + 0
		} else {
			// Lose vs scissors
			return 2 + 0
		}
	} else if outcome == "Y" {
		if opponent == "A" {
			// Draw vs rock
			return 1 + 3
		} else if opponent == "B" {
			// Draw vs paper
			return 2 + 3
		} else {
			// Draw vs scissor
			return 3 + 3
		}
	} else {
		if opponent == "A" {
			// Win vs rock
			return 2 + 6
		} else if opponent == "B" {
			// Win vs paper
			return 3 + 6
		} else {
			// Win vs scissors
			return 1 + 6
		}
	}

}
