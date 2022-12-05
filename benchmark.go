package main

import (
	"AdventOfCode_2022/day1"
	"AdventOfCode_2022/day2"
	"AdventOfCode_2022/day3"
	"AdventOfCode_2022/day4"
	"AdventOfCode_2022/day5"
	"log"
	"time"
)

func main() {

	start := time.Now()

	day1.Part1()
	day1.Part2()
	day2.Part1()
	day2.Part2()
	day3.Part1()
	day3.Part2()
	day4.Part1()
	day4.Part2()
	day5.Part1()
	day5.Part2()

	elapsed := time.Since(start)
	log.Printf("All day took %s", elapsed)
}
