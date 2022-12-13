package day9

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
		RunBoth()
	}
}

var visitedSet map[Position]void
var headPos = Position{xPos: 0, yPos: 0}
var tailPos = Position{xPos: 0, yPos: 0}

type Position struct {
	xPos int
	yPos int
}
type void struct{}

func RunBoth() {
	Part1()
	Part2()

}

func Part1() {

	visitedSet = make(map[Position]void)

	file, err := os.Open("day9/input.txt")
	if err != nil {
		return
	}
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)

	for fileScanner.Scan() {
		moveByLine(fileScanner.Text())
	}
	fmt.Printf("D9P1: There were %d distinct fields visited by the tail\n", len(visitedSet))
}

func Part2() {
	visitedSet = make(map[Position]void)

	var rope [10]Position
	for i, _ := range rope {
		rope[i] = Position{0, 0}
	}

	file, err := os.Open("day9/input.txt")
	if err != nil {
		return
	}
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)

	for fileScanner.Scan() {
		var splits = strings.Split(fileScanner.Text(), " ")
		var direction = splits[0]
		var val, _ = strconv.Atoi(splits[1])

		for i := 0; i < val; i++ {

			switch direction {
			case "R":
				rope[0].xPos = rope[0].xPos + 1
			case "L":
				rope[0].xPos = rope[0].xPos - 1
			case "U":
				rope[0].yPos = rope[0].yPos - 1
			case "D":
				rope[0].yPos = rope[0].yPos + 1
			}

			for i := 1; i < len(rope); i++ {
				rope[i] = drag(rope[i-1], rope[i])
			}

			_, ok := visitedSet[rope[9]]
			if !ok {
				visitedSet[rope[9]] = void{}
			}

		}
	}
	fmt.Printf("D9P2: There were %d distinct fields visited by the long tail\n", len(visitedSet))

}

func moveByLine(line string) {
	var splits = strings.Split(line, " ")
	var direction = splits[0]
	var val, _ = strconv.Atoi(splits[1])

	for i := 0; i < val; i++ {

		switch direction {
		case "R":
			headPos.xPos = headPos.xPos + 1
		case "L":
			headPos.xPos = headPos.xPos - 1
		case "U":
			headPos.yPos = headPos.yPos - 1
		case "D":
			headPos.yPos = headPos.yPos + 1
		}

		tailPos = drag(headPos, tailPos)
		_, ok := visitedSet[tailPos]
		if !ok {
			visitedSet[tailPos] = void{}
		}
	}

}

func moveLongRopeByLine(line string, rope [10]Position) {

}

func drag(headPos, tailPos Position) Position {

	xdiff := diff(tailPos.xPos, headPos.xPos)
	ydiff := diff(tailPos.yPos, headPos.yPos)

	if xdiff > 1 || ydiff > 1 {
		// tails gotta move
		if xdiff == 0 {
			dir := 0
			if tailPos.yPos-headPos.yPos > 0 {
				dir = -1
			} else {
				dir = 1
			}
			tailPos.yPos = tailPos.yPos + dir

		} else if ydiff == 0 {
			dir := 0
			if headPos.xPos-tailPos.xPos > 0 {
				dir = 1
			} else {
				dir = -1
			}
			tailPos.xPos = tailPos.xPos + dir

		} else {
			// diagonal movement
			xdir := 0
			if headPos.xPos-tailPos.xPos > 0 {
				xdir = 1
			} else {
				xdir = -1
			}
			ydir := 0
			if tailPos.yPos-headPos.yPos > 0 {
				ydir = -1
			} else {
				ydir = 1
			}
			tailPos.xPos = tailPos.xPos + xdir
			tailPos.yPos = tailPos.yPos + ydir
		}
	}
	return tailPos
}

func diff(a, b int) int {
	if a < b {
		return b - a
	}
	return a - b
}
