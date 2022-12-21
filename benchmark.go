package main

import (
	"AdventOfCode_2022/day1"
	"AdventOfCode_2022/day11"
	"AdventOfCode_2022/day12"
	"AdventOfCode_2022/day2"
	"AdventOfCode_2022/day3"
	"AdventOfCode_2022/day4"
	"AdventOfCode_2022/day5"
	"AdventOfCode_2022/day6"
	"AdventOfCode_2022/day7"
	"AdventOfCode_2022/day8"
	"AdventOfCode_2022/day9"
	"log"
	"time"
)

func main() {

	var reps int = 50

	times := make([]time.Duration, reps)

	for i := 0; i < reps; i++ {

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
		day6.Part1()
		day6.Part2()
		day7.Start()
		day8.RunBoth()
		day9.RunBoth()
		//day10.Start()
		day11.Start()
		day12.RunBoth()
		elapsed := time.Since(start)

		times[i] = elapsed
	}
	var sumOfTimes int64
	for _, val := range times {
		sumOfTimes += val.Milliseconds()
	}
	sumOfTimes = sumOfTimes / int64(reps)

	log.Printf("\n\n\nAverage time for all Days combined %d ms", sumOfTimes)
}
