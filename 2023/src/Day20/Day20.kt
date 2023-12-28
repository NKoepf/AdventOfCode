package Day20

import java.io.File
import java.util.*

fun main() {
    val file = File("2023/src/Day20/input.txt")
    part1And2(file)
}

private fun part1And2(file: File) {
    val modulesMap: MutableMap<String, MachineModule> = mutableMapOf()
    val actionQueue: Queue<Signal> = LinkedList()

    file.forEachLine { line: String ->
        val parts = line.split("->")
        val type = if (parts[0].startsWith("%")) ModuleType.FLIP_FLOP
        else if (parts[0].startsWith("&")) ModuleType.CONJUNCTION
        else if (parts[0].startsWith("broadcaster")) ModuleType.BROADCAST
        else throw RuntimeException("Like wtf is habbening?")

        val module = MachineModule(
            type,
            parts[0].trim().substringAfter(type.type),
            parts[1].trim().split(", "),
            false,
        )
        modulesMap[module.name] = module
    }
    modulesMap.forEach { (key, value) ->
        if (value.type == ModuleType.CONJUNCTION) {
            modulesMap.values.forEach loop@{ module ->
                if (module.name == key) return@loop
                if (module.connectedModules.contains(key)) value.conjunctedModules.add(module.name to false)
            }
        }
    }

    var buttonPressed = 0
    var highPulses = 0
    var lowPulses = 0
    var foundRxSignal = false
    val neededConj: MutableMap<String, Int> =
        mutableMapOf("lk" to Int.MAX_VALUE, "zv" to Int.MAX_VALUE, "sp" to Int.MAX_VALUE, "xt" to Int.MAX_VALUE)

    do {
        // button pressed
        buttonPressed++
        lowPulses++
        modulesMap[""]!!.connectedModules.forEach {
            actionQueue.add(Signal("", it, false))
            lowPulses++
        }

        while (actionQueue.size > 0) {
            var step = actionQueue.poll()
            var mod = modulesMap[step.destination]
            if (mod != null) {
                if (mod.type == ModuleType.FLIP_FLOP) {
                    if (step.high == false) { //low signal received, on/off toggled
                        var high = !mod.on //was on -> sends low signal / was off -> sends high signal
                        mod.on = !mod.on
                        mod.connectedModules.forEach { m ->
                            actionQueue.add(Signal(mod.name, m, high))
                            if (high) highPulses++ else lowPulses++

                        }
                    }

                } else if (mod.type == ModuleType.CONJUNCTION) {
                    val originMod = mod.conjunctedModules.find { it.first == step.origin }
                    mod.conjunctedModules.remove(originMod)
                    mod.conjunctedModules.add((originMod!!.first to step.high))

                    val high = !mod.conjunctedModules.map { it.second }.all { it }
                    mod.connectedModules.forEach { m ->
                        actionQueue.add(Signal(mod.name, m, high))
                        if (high) highPulses++ else {

                            if (neededConj.keys.contains(m) && buttonPressed < neededConj[m]!!) {
                                neededConj[m] = buttonPressed
                                if (neededConj.values.all { it < Int.MAX_VALUE }) foundRxSignal = true
                            }
                            lowPulses++
                        }
                    }
                }
            }
        }
    } while (!foundRxSignal)

    Util.printPart1()
    println("done with $lowPulses lo and $highPulses high pulses. Multiplied to ${highPulses * lowPulses}")

    Util.printPart2()
    val neededButtonCycles: Long = neededConj.values.fold(1) { acc, i -> acc * i }
    println("There are $neededButtonCycles needed to emit a low signal for rx")
}

private data class Signal(
    val origin: String,
    val destination: String,
    val high: Boolean
)

data class MachineModule(
    val type: ModuleType,
    val name: String,
    val connectedModules: List<String>,
    var on: Boolean,
    var conjunctedModules: MutableList<Pair<String, Boolean>> = mutableListOf()
)

enum class ModuleType(val type: String) {
    FLIP_FLOP("%"),
    CONJUNCTION("&"),
    BROADCAST("broadcaster")
}