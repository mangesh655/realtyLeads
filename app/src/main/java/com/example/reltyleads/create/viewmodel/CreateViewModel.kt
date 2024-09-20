package com.example.reltyleads.create.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reltyleads.common.BaseViewModel
import com.example.reltyleads.create.model.FormField
import com.example.reltyleads.create.model.InputType
import com.example.reltyleads.create.model.TextInputType
import com.example.reltyleads.domain.CreateUseCase
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createUseCase: CreateUseCase
) : BaseViewModel() {

    private var projects = emptyList<ProjectDb>()
    private var towers = emptyList<TowerDb>()
    private var units = emptyList<UnitDb>()
    private var floors = emptyList<String>()

    private val _unitsDetailsForm = MutableStateFlow<List<FormField>>(emptyList())
    val unitsDetailsForm: StateFlow<List<FormField>> = _unitsDetailsForm

    private val _applicantForm = MutableStateFlow<List<FormField>>(emptyList())
    val applicantForm: StateFlow<List<FormField>> = _applicantForm

    private var selectedProject: ProjectDb? = null
    private var selectedTower: TowerDb? = null
    private var selectedFloor: String = "1"
    private var selectedUnit: UnitDb? = null
    private var fullName: String = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchProjects()
        }
    }

    private suspend fun fetchProjects() {
        projects = createUseCase.fetchProjects()
        selectedProject = projects.firstOrNull()
        onProjectChanged()
    }

    private suspend fun fetchTowers() {
        towers = createUseCase.fetchTowers(selectedProject?.id ?: "")
        onTowerChanged(towers.first())
    }

    private suspend fun fetchUnits() {
        units = createUseCase.fetchUnits(selectedTower?.id ?: "")
        onUnitsChanged(units.first())
    }

    private fun onUnitsChanged(selectedUnit: UnitDb?) {
        this.selectedUnit =  selectedUnit
        getUnitDetailsFields()
    }

    private fun getUnitDetailsFields() {
        val unitDetails = mutableListOf(
            FormField(
                placeholder = "Project name",
                type = InputType.Dropdown(projects.map { Pair(it.id, it.name) }),
                text = "",
                isVisible = true,
                singleLine = true,
            ), FormField(
                placeholder = "Tower name",
                type = InputType.Dropdown(towers.map { Pair(it.id, it.name) }),
                text = "",
                isVisible = true,
                singleLine = true,
            ), FormField(
                placeholder = "Floor number",
                type = InputType.Dropdown(floors.map { Pair(it, it) }),
                text = "",
                isVisible = true,
                singleLine = true,
            ), FormField(
                placeholder = "Unit number",
                type = InputType.Dropdown(units.map { Pair(it.id, it.number) }),
                text = "",
                isVisible = true,
                singleLine = true,
            ), FormField(
                placeholder = "Unit size",
                type = InputType.Text(TextInputType.NUMBER),
                keyboardType = KeyboardType.Number,
                isEnabled = false,
                text = (selectedUnit?.size ?: "").toString(),
                isVisible = true,
                singleLine = true,
                trailingIcon = {
                    Text(
                        text = "sqft",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            ), FormField(
                placeholder = "Expected Registration Date",
                type = InputType.Date("dd MMM, YYYY"),
                text = "",
                isVisible = true,
                singleLine = true,
                isEnabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Date Set Icon",
                        tint = Color.Gray
                    )
                }
            ), FormField(
                placeholder = "Agreement Value",
                type = InputType.Text(TextInputType.NUMBER),
                keyboardType = KeyboardType.Number,
                text = "",
                isVisible = true,
                singleLine = true,
            )
        )
        _unitsDetailsForm.value = unitDetails
    }

    fun onFieldValueChanged(fieldValue: Any, field: FormField, text: (String) -> Unit) {
        when (field.placeholder) {
            "Project name" -> {
                selectedProject =
                    projects.first { it.id == (fieldValue as Pair<*, *>).first.toString() }
                onProjectChanged()
                text((fieldValue as Pair<*, *>).second.toString())
            }

            "Tower name" -> {
                selectedTower =
                    towers.first { it.id == (fieldValue as Pair<*, *>).first.toString() }
                onTowerChanged(selectedTower!!)
                text((fieldValue as Pair<*, *>).second.toString())
            }

            "Unit name" -> {
                selectedUnit =
                    units.first { it.id == (fieldValue as Pair<*, *>).first.toString() }
                onUnitsChanged(selectedUnit!!)
                text((fieldValue as Pair<*, *>).second.toString())
            }

            "Full name" -> {
                Log.e("Mangesh", "onFieldValueChanged: $fieldValue", )
                fullName = fieldValue.toString()
                text(fullName)
            }
        }
    }

    private fun onProjectChanged() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchTowers()
        }
    }

    private fun onTowerChanged(selectedTower: TowerDb) {
        this.selectedTower = selectedTower
        floors =
            (1..selectedTower.floors).map { it.toString() }.toList()
        selectedFloor = floors.first()
        viewModelScope.launch(Dispatchers.IO) {
            fetchUnits()
        }
    }

}