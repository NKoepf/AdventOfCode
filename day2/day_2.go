package main

import (
	"fmt"
	"os"
	"strconv"
)

func main() {

	part, _ := strconv.Atoi(os.Args[1])

	if part == 1 {
		Part1()
	} else if part == 2 {
		day2Part2()
	} else {
		fmt.Println("Wrong or missing input")
	}

}

func Part1() {

}

func day2Part2() {

}
