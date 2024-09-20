package com.example.reltyleads.repository.InventorySourceHelper

data class InventorySourceJsonModel(
    var projects: List<Project>
)

data class Project(
    var projectName: String,
    var location: String,
    var towers: List<Tower>
)

data class Tower(
    var towerName: String,
    var floors: Int,
    var units: List<Unit>
)

data class Unit(
    var number: String,
    var size: Int
)