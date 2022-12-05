package main

import (
	"bufio"
	"fmt"
	"github.com/badgerodon/collections"
	"os"
	"regexp"
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
	readFile, err := os.Open("day5/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	var part1 bool = true
	var grid [8][9]int
	var stacks [9]collections.Stack[string]

	var lineIndex int = 0

	for fileScanner.Scan() {
		line := fileScanner.Text()
		if line == "" {
			part1 = false

			for i := 0; i < len(grid[0]); i++ {
				stacks[i] = collections.NewStack[string]()
				for j := len(grid) - 1; j >= 0; j-- {
					var char = rune(grid[j][i])
					if char != 0 {
						stacks[i].Push(string(char))

					}
				}
			}
			continue
		}

		if part1 && lineIndex < 8 {
			runes := []rune(line)
			for j := 0; j < 9; j++ {
				var target int = 1 + (j * 4)
				if target < len(runes) {
					if int(line[target]) != 32 {
						grid[lineIndex][j] = int(line[target])
					}
				}
			}
			fmt.Println()
			lineIndex++
		} else if !part1 {
			regexp := regexp.MustCompile("[^0-9 ]")
			line := regexp.ReplaceAllString(line, "")

			split := strings.Split(line, " ")
			var move, _ = strconv.Atoi(split[1])
			var from, _ = strconv.Atoi(split[3])
			var to, _ = strconv.Atoi(split[5])

			for i := 0; i < move; i++ {
				value, _ := stacks[from-1].Pop()
				stacks[to-1].Push(value)

			}

		}
	}

	for i := 0; i < len(stacks); i++ {
		value, _ := stacks[i].Pop()
		fmt.Print(value)
	}
}

func Part2() {
	readFile, err := os.Open("day5/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	var part1 bool = true
	var grid [8][9]int
	var stacks [9]collections.Stack[string]

	var lineIndex int = 0

	for fileScanner.Scan() {
		line := fileScanner.Text()
		if line == "" {
			part1 = false

			for i := 0; i < len(grid[0]); i++ {
				stacks[i] = collections.NewStack[string]()
				for j := len(grid) - 1; j >= 0; j-- {
					var char = rune(grid[j][i])
					if char != 0 {
						stacks[i].Push(string(char))

					}
				}
			}
			continue
		}

		if part1 && lineIndex < 8 {
			runes := []rune(line)
			for j := 0; j < 9; j++ {
				var target int = 1 + (j * 4)
				if target < len(runes) {
					if int(line[target]) != 32 {
						grid[lineIndex][j] = int(line[target])
					}
				}
			}
			fmt.Println()
			lineIndex++
		} else if !part1 {
			regexp := regexp.MustCompile("[^0-9 ]")
			line := regexp.ReplaceAllString(line, "")

			split := strings.Split(line, " ")
			var move, _ = strconv.Atoi(split[1])
			var from, _ = strconv.Atoi(split[3])
			var to, _ = strconv.Atoi(split[5])

			var tmp collections.Stack[string] = collections.NewStack[string]()

			for i := 0; i < move; i++ {
				value, _ := stacks[from-1].Pop()
				tmp.Push(value)
			}

			var size = tmp.Size()
			for i := 0; i < size; i++ {
				value, _ := tmp.Pop()
				stacks[to-1].Push(value)
			}

		}
	}

	for i := 0; i < len(stacks); i++ {
		value, _ := stacks[i].Pop()
		fmt.Print(value)
	}
}
