package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.flow

@Preview
@ExperimentalMaterial3Api
@Composable
fun FindFliter() {
    var selectedCategory by remember { mutableStateOf("All Task") }
    var selectedBillRange by remember { mutableStateOf("All Bill") }
    var selectedStatus by remember { mutableStateOf("All Status") }

    FilterDropdown(
        selectedCategory = selectedCategory,
        selectedBillRange = selectedBillRange,
        selectedStatus = selectedStatus,
        onCategorySelected = { category -> selectedCategory = category },
        onBillRangeSelected = { billRange -> selectedBillRange = billRange },
        onStatusSelected = { status -> selectedStatus = status }
    )

}

@Composable
fun FilterDropdown(
    selectedCategory: String,
    selectedBillRange: String,
    selectedStatus: String,
    onCategorySelected: (String) -> Unit,
    onBillRangeSelected: (String) -> Unit,
    onStatusSelected: (String) -> Unit
) {
    val categories = listOf("All Task", "Cleaning", "Removals", "Repairs", "Painting")
    val billRanges = listOf("All Bill", "0-50", "51-100", "101-200", "200~")
    val statuses = listOf("All Status", "Open", "Assigned", "Completed")

    val filteredItems = remember {
        mutableStateListOf<String>() // 存储过滤结果的列表
    }

    // 过滤逻辑
    LaunchedEffect(selectedCategory, selectedBillRange) {
        filteredItems.clear()
        if (selectedCategory == "All Task" && selectedBillRange == "All Bill") {
            // 如果选择的都是"All"，显示所有内容
            //filteredItems.addAll() // 这里的dummyData是你的数据源，需要替换成你的实际数据
        } else {
            // 进行过滤操作
            /*dummyData.forEach { item ->
                if ((selectedCategory == "All Task" || item.category == selectedCategory) &&
                    (selectedBillRange == "All Bill" || item.billRange == selectedBillRange)
                ) {
                    filteredItems.add(item)
                }
            }*/
        }
    }

    Row(Modifier.padding(16.dp)) {
        Column(Modifier.weight(1f)) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { category },
                        onClick = { onCategorySelected(category) })
                }
            }
        }
        Column(Modifier.weight(1f)) {
            DropdownMenu(
                expanded = false,
                onDismissRequest = { }
            ) {
                billRanges.forEach { billRange ->
                    DropdownMenuItem(
                        text = { billRange },
                        onClick = { onBillRangeSelected(billRange) })
                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            DropdownMenu(
                expanded = false,
                onDismissRequest = { }
            ) {
                statuses.forEach { status ->
                    DropdownMenu(
                        expanded = false,
                        onDismissRequest = { }
                    ) {
                        statuses.forEach { status ->
                            DropdownMenuItem(text = { status }, onClick = { onStatusSelected(status) })

                        }
                    }
                }
            }
        }
    }

    Box(Modifier.padding(horizontal = 16.dp)) {
        if (filteredItems.isNotEmpty()) {
            // 显示过滤结果
            LazyColumn {
                items(filteredItems) { item ->
                    // 显示每个过滤项的内容
                    Text(text = item) // 这里根据你的实际数据结构显示相应的内容
                }
            }
        } else {
            // 过滤结果为空的情况
            Text(
                text = "No items match the selected filters",
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}








