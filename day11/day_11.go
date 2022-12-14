package day11

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

var monkeys [8]Monkey

type Monkey struct {
	items       []int
	opModeMulti bool
	opValue     int
	testVal     int
	trueDest    int
	falseDest   int
	inspections int
}

func Start() {
	Part1()
	Part2()
}

func Part2() {
	createMonkeys()
	commonDenomiator := 0
	for _, monkey := range monkeys {
		if commonDenomiator == 0 {
			commonDenomiator += monkey.testVal
		} else {
			commonDenomiator *= monkey.testVal
		}
	}
	reps := 10000

	for i := 0; i < reps; i++ {
		monkeyRound2(commonDenomiator)
	}

	var vals []int
	for i := 0; i < len(monkeys); i++ {
		val := monkeys[i].inspections
		vals = append(vals, val)
	}

	sort.Ints(vals)
	sort.Slice(vals, func(i, j int) bool {
		return vals[i] > vals[j]
	})
	sum := vals[0] * vals[1]
	fmt.Printf("D11P2: most active monkey interacted %d times after %d rounds\n", sum, reps)
}

func Part1() {
	createMonkeys()
	reps := 20

	for i := 0; i < reps; i++ {
		monkeyRound()
	}

	var vals []int
	for i := 0; i < len(monkeys); i++ {
		val := monkeys[i].inspections
		vals = append(vals, val)
	}
	sort.Ints(vals)
	sort.Slice(vals, func(i, j int) bool {
		return vals[i] > vals[j]
	})
	sum := vals[0] * vals[1]
	fmt.Printf("D11P1: most active monkey interacted %d times\n", sum)
}

func createMonkeys() {

	file, _ := os.Open("day11/input.txt")
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)
	monkeyIndex := 0

	for fileScanner.Scan() {
		line := fileScanner.Text()
		if line == "" {

		} else if strings.HasPrefix(line, "Monkey") {
			// first line
			monkeyIndex, _ = strconv.Atoi(strings.TrimSuffix(strings.Split(line, " ")[1], ":"))
			monkeys[monkeyIndex] = Monkey{}
		} else if strings.HasPrefix(line, "  Starting items:") {
			values := strings.Split(strings.Split(line, ":")[1], ", ")
			for _, value := range values {
				val, _ := strconv.Atoi(strings.TrimSpace(value))
				monkeys[monkeyIndex].items = append(monkeys[monkeyIndex].items, val)

			}
		} else if strings.HasPrefix(line, "  Operation: new = old") {
			cutLine := strings.TrimPrefix(line, "  Operation: new = old ")
			ops := strings.Split(cutLine, " ")
			if ops[0] == "*" {
				monkeys[monkeyIndex].opModeMulti = true
			} else {
				monkeys[monkeyIndex].opModeMulti = false
			}

			if ops[1] == "old" {
				monkeys[monkeyIndex].opValue = 0
			} else {
				monkeys[monkeyIndex].opValue, _ = strconv.Atoi(ops[1])
			}
		} else if strings.HasPrefix(line, "  Test: divisible by ") {
			value, _ := strconv.Atoi(strings.TrimPrefix(line, "  Test: divisible by "))
			monkeys[monkeyIndex].testVal = value
		} else if strings.HasPrefix(line, "    If true: throw to monkey ") {
			value, _ := strconv.Atoi(strings.TrimPrefix(line, "    If true: throw to monkey "))
			monkeys[monkeyIndex].trueDest = value
		} else if strings.HasPrefix(line, "    If false: throw to monkey ") {
			value, _ := strconv.Atoi(strings.TrimPrefix(line, "    If false: throw to monkey "))
			monkeys[monkeyIndex].falseDest = value
		}
	}
}

func monkeyRound() {

	for index, _ := range monkeys {
		for _, item := range monkeys[index].items {
			monkeys[index].inspections++
			if monkeys[index].opModeMulti {
				if monkeys[index].opValue == 0 {
					item *= item
				} else {
					item *= monkeys[index].opValue
				}
			} else {
				if monkeys[index].opValue == 0 {
					item += item
				} else {
					item += monkeys[index].opValue
				}
			}

			item = item / 3

			if item%monkeys[index].testVal == 0 {
				monkeys[monkeys[index].trueDest].items = append(monkeys[monkeys[index].trueDest].items, item)
			} else {
				monkeys[monkeys[index].falseDest].items = append(monkeys[monkeys[index].falseDest].items, item)
			}

		}
		monkeys[index].items = nil
	}

}

func monkeyRound2(commonDenomiator int) {

	for index, _ := range monkeys {
		for _, item := range monkeys[index].items {
			monkeys[index].inspections++

			if monkeys[index].opModeMulti {
				if monkeys[index].opValue == 0 {
					item *= item
				} else {
					item *= monkeys[index].opValue
				}
			} else {
				if monkeys[index].opValue == 0 {
					item += item
				} else {
					item += monkeys[index].opValue
				}
			}
			item = item % commonDenomiator
			//item = item / 3

			if item%monkeys[index].testVal == 0 {
				//item = item / monkeys[index].testVal
				monkeys[monkeys[index].trueDest].items = append(monkeys[monkeys[index].trueDest].items, item)
			} else {
				monkeys[monkeys[index].falseDest].items = append(monkeys[monkeys[index].falseDest].items, item)
			}

		}
		monkeys[index].items = nil
	}

}
