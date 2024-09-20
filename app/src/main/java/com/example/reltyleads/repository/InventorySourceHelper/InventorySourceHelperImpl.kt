package com.example.reltyleads.repository.InventorySourceHelper

import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import com.example.reltyleads.repository.InventoryRepository
import javax.inject.Inject

class InventorySourceHelperImpl @Inject constructor(
    private val inventoryRepository: InventoryRepository
) : InventorySourceHelper {

    private fun createDummyInventory(): InventorySourceJsonModel {
        return InventorySourceJsonModel(
            listOf(
                Project(
                    "Platinum Emporius",
                    "Bengaluru",
                    listOf(
                        Tower(
                            "London",
                            20, listOf(
                                Unit("A1", 1250),
                                Unit("A2", 1250),
                                Unit("A3", 1250),
                                Unit("A4", 1250),
                                Unit("A5", 1250),
                                Unit("A6", 1250),
                                Unit("A7", 1250),
                                Unit("A8", 1250),
                                Unit("A9", 1250),
                                Unit("A10", 1250),
                                Unit("A11", 1250),
                                Unit("A12", 1250),
                                Unit("A13", 1250),
                                Unit("A14", 1250),
                                Unit("A15", 1250),
                                Unit("A16", 1250),
                                Unit("A17", 1250),
                                Unit("A18", 1250),
                                Unit("A19", 1250),
                                Unit("A20", 1250)
                            )
                        ),
                        Tower(
                            "Paris", 20, listOf(
                                Unit("C1", 870),
                                Unit("C2", 870),
                                Unit("C3", 870),
                                Unit("C4", 870),
                                Unit("C5", 870),
                                Unit("C6", 870),
                                Unit("C7", 870),
                                Unit("C8", 870),
                                Unit("C9", 870),
                                Unit("C10", 870),
                                Unit("C11", 870),
                                Unit("C12", 870),
                                Unit("C13", 870),
                                Unit("C14", 870),
                                Unit("C15", 870),
                                Unit("C16", 870),
                                Unit("C17", 870),
                                Unit("C18", 870),
                                Unit("C19", 870),
                                Unit("C20", 870)
                            )
                        ),
                        Tower(
                            "Tokyo", 20, listOf(
                                Unit("B1", 1400),
                                Unit("B2", 1400),
                                Unit("B3", 1400),
                                Unit("B4", 1400),
                                Unit("B5", 1400),
                                Unit("B6", 1400),
                                Unit("B7", 1400),
                                Unit("B8", 1400),
                                Unit("B9", 1400),
                                Unit("B10", 1400),
                                Unit("B11", 1400),
                                Unit("B12", 1400),
                                Unit("B13", 1400),
                                Unit("B14", 1400),
                                Unit("B15", 1400),
                                Unit("B16", 1400),
                                Unit("B17", 1400),
                                Unit("B18", 1400),
                                Unit("B19", 1400),
                                Unit("B20", 1400)
                            )
                        )
                    )
                ),
                Project(
                    "Raheja Embassy",
                    "Mumbai",
                    listOf(
                        Tower(
                            "Joy", 20, listOf(
                                Unit("D1", 1801),
                                Unit("D2", 1801),
                                Unit("D3", 1801),
                                Unit("D4", 1801),
                                Unit("D5", 1801),
                                Unit("D6", 1801),
                                Unit("D7", 1801),
                                Unit("D8", 1801),
                                Unit("D9", 1801),
                                Unit("D10", 1801),
                                Unit("D11", 1801),
                                Unit("D12", 1801),
                                Unit("D13", 1801),
                                Unit("D14", 1801),
                                Unit("D15", 1801),
                                Unit("D16", 1801),
                                Unit("D17", 1801),
                                Unit("D18", 1801),
                                Unit("D19", 1801),
                                Unit("D20", 1801)
                            )
                        ),
                        Tower(
                            "Serenity",
                            20,
                            listOf(
                                Unit("E1", 1254),
                                Unit("E2", 1254),
                                Unit("E3", 1254),
                                Unit("E4", 1254),
                                Unit("E5", 1254),
                                Unit("E6", 1254),
                                Unit("E7", 1254),
                                Unit("E8", 1254),
                                Unit("E9", 1254),
                                Unit("E10", 1254),
                                Unit("E11", 1254),
                                Unit("E12", 1254),
                                Unit("E13", 1254),
                                Unit("E14", 1254),
                                Unit("E15", 1254),
                                Unit("E16", 1254),
                                Unit("E17", 1254),
                                Unit("E18", 1254),
                                Unit("E19", 1254),
                                Unit("E20", 1254)
                            )
                        ),
                        Tower(
                            "Harmony", 20, listOf(
                                Unit("A1", 1700),
                                Unit("A2", 1700),
                                Unit("A3", 1700),
                                Unit("A4", 1700),
                                Unit("A5", 1700),
                                Unit("A6", 1700),
                                Unit("A7", 1700),
                                Unit("A8", 1700),
                                Unit("A9", 1700),
                                Unit("A10", 1700),
                                Unit("A11", 1700),
                                Unit("A12", 1700),
                                Unit("A13", 1700),
                                Unit("A14", 1700),
                                Unit("A15", 1700),
                                Unit("A16", 1700),
                                Unit("A17", 1700),
                                Unit("A18", 1700),
                                Unit("A19", 1700),
                                Unit("A20", 1700)
                            )
                        )
                    )
                ),
                Project(
                    "Embassy Tech",
                    "Pune",
                    listOf(
                        Tower(
                            "India",
                            20, listOf(
                                Unit("A1", 1700),
                                Unit("A2", 1700),
                                Unit("A3", 1700),
                                Unit("A4", 1700),
                                Unit("A5", 1700),
                                Unit("A6", 1700),
                                Unit("A7", 1700),
                                Unit("A8", 1700),
                                Unit("A9", 1700),
                                Unit("A10", 1700),
                                Unit("A11", 1700),
                                Unit("A12", 1700),
                                Unit("A13", 1700),
                                Unit("A14", 1700),
                                Unit("A15", 1700),
                                Unit("A16", 1700),
                                Unit("A17", 1700),
                                Unit("A18", 1700),
                                Unit("A19", 1700),
                                Unit("A20", 1700)
                            )
                        ),
                        Tower(
                            "China",
                            20, listOf(
                                Unit("F1", 1850),
                                Unit("F2", 1850),
                                Unit("F3", 1850),
                                Unit("F4", 1850),
                                Unit("F5", 1850),
                                Unit("F6", 1850),
                                Unit("F7", 1850),
                                Unit("F8", 1850),
                                Unit("F9", 1850),
                                Unit("F10", 1850),
                                Unit("F11", 1850),
                                Unit("F12", 1850),
                                Unit("F13", 1850),
                                Unit("F14", 1850),
                                Unit("F15", 1850),
                                Unit("F16", 1850),
                                Unit("F17", 1850),
                                Unit("F18", 1850),
                                Unit("F19", 1850),
                                Unit("F20", 1850)
                            )
                        ),
                        Tower(
                            "Australia",
                            20, listOf(
                                Unit("G1", 1900),
                                Unit("G2", 1900),
                                Unit("G3", 1900),
                                Unit("G4", 1900),
                                Unit("G5", 1900),
                                Unit("G6", 1900),
                                Unit("G7", 1900),
                                Unit("G8", 1900),
                                Unit("G9", 1900),
                                Unit("G10", 1900),
                                Unit("G11", 1900),
                                Unit("G12", 1900),
                                Unit("G13", 1900),
                                Unit("G14", 1900),
                                Unit("G15", 1900),
                                Unit("G16", 1900),
                                Unit("G17", 1900),
                                Unit("G18", 1900),
                                Unit("G19", 1900),
                                Unit("G20", 1900)
                            )
                        )
                    )
                ),
                Project(
                    "Prestige Niketan",
                    "Delhi",
                    listOf(
                        Tower(
                            "Everest",
                            20, listOf(
                                Unit("H1", 2000),
                                Unit("H2", 2000),
                                Unit("H3", 2000),
                                Unit("H4", 2000),
                                Unit("H5", 2000),
                                Unit("H6", 2000),
                                Unit("H7", 2000),
                                Unit("H8", 2000),
                                Unit("H9", 2000),
                                Unit("H10", 2000),
                                Unit("H11", 2000),
                                Unit("H12", 2000),
                                Unit("H13", 2000),
                                Unit("H14", 2000),
                                Unit("H15", 2000),
                                Unit("H16", 2000),
                                Unit("H17", 2000),
                                Unit("H18", 2000),
                                Unit("H19", 2000),
                                Unit("H20", 2000)
                            )
                        )
                    )
                )
            )
        )
    }

    override suspend fun createAndInsertDummyProjectsData() {

        resetInventory()

        createDummyInventory().projects.forEach { project ->
            val projectId = "PRJ_${System.currentTimeMillis()}"

            inventoryRepository.insertProject(
                ProjectDb(
                    projectId,
                    project.projectName,
                    "",
                    project.location
                )
            )

            project.towers.forEach { tower ->
                val towerId = "TWR_${System.currentTimeMillis()}"
                inventoryRepository.insertTower(
                    TowerDb(
                        towerId,
                        tower.towerName,
                        tower.floors,
                        projectId
                    )
                )

                tower.units.forEachIndexed { index, unit ->
                    val unitId = "UNIT_${System.currentTimeMillis()}"
                    inventoryRepository.insertUnit(
                        UnitDb(
                            unitId,
                            unit.number,
                            index + 1,
                            unit.size,
                            towerId
                        )
                    )
                }

            }
        }
    }

    private suspend fun resetInventory() {
        inventoryRepository.deleteAllProjects()
        inventoryRepository.deleteAllTowers()
        inventoryRepository.deleteAllUnits()
    }
}