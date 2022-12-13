package day10

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	Start()
}

func Start() {

	file, _ := os.Open("day10/input.txt")
	scanner := bufio.NewScanner(file)
	scanner.Split(bufio.ScanLines)

	cycle := 1
	registerVal := 1

	totalSum := 0
	sleepCycles := 0
	valToAdd := 0

	crtPos := 0

	for scanner.Scan() {
		line := scanner.Text()

		if sleepCycles > 0 {
			for i := sleepCycles; i > 0; i-- {
				if cycle == 20 || (cycle-20)%40 == 0 {
					val := registerVal * cycle
					totalSum = totalSum + val
				}

				if crtPos > 39 {
					crtPos = 0
					fmt.Print("\n")
				}

				if registerVal-1 <= crtPos && registerVal+1 >= crtPos {
					fmt.Print("#")
				} else {
					fmt.Print(".")
				}

				crtPos++
				cycle++

			}
			sleepCycles = 0
			registerVal += valToAdd
			valToAdd = 0

		}

		if strings.HasPrefix(line, "noop") {
			sleepCycles = 1
		} else {

			val, _ := strconv.Atoi(strings.Split(line, " ")[1])
			sleepCycles = 2
			valToAdd = val

		}
	}

	fmt.Printf("D10P1: Total sum of signal strengths %d\n", totalSum)
}
