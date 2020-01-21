package io.kommons.designpatterns.delegation

/**
 * Kotlin Class delegation을 활용
 */
class PrinterController(printer: Printer): Printer by printer