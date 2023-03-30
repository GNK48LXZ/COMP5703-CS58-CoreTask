package com.example.myapplication

import android.os.Build
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.*

import androidx.compose.ui.text.input.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostTaskPage() {
    /*
    * pageState:int represents which page should be shown
    * 1: SimplyDescribeTask
    * 2: WhenDone
    * 3: SelectDate
    * 4: WhereDone
    * 5: DescribeTask
    * 6: Select Address
    * 7：Suggest Budget
    *
    * */
    val pageState = remember {
        mutableStateOf(1)
    }
    if (pageState.value == 1) {
        SimplyDescribeTask(pageState)
    } else if (pageState.value == 2) {
        WhenDone(pageState)
    } else if(pageState.value == 3){
        SelectDate(pageState)
    } else if (pageState.value == 4) {
        WhereDone(pageState)
    } else if (pageState.value == 5) {
        DescribeTask(pageState)
    } else if (pageState.value == 6) {
        SelectAddress(pageState)
    } else if (pageState.value == 7) {
        SuggestBudget(pageState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplyDescribeTask(pageState: MutableState<Int>) {
    MaterialTheme(colorScheme = LightColorScheme) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable {/* */ }
                    .padding(horizontal = 16.dp),
                tint = Color(0xff333333)
            )

            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Describe what you need done in a few word.",
                lineHeight = 40.sp,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
            var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue("eg. Move my stuff", TextRange(0, 18)))
            }
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(70.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                FilledTonalButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { pageState.value = 2 }
                ) {
                    Text("Continue", fontSize = 20.sp)
                }
            }

        }
    }
}

@Composable
fun WhenDone(pageState: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 1 }
                .padding(horizontal = 16.dp),
            tint = Color(0xff333333)
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "When do you need this done?",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Radio button group
        val radioOptions = listOf("On date", "Before date", "I am flexible")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }

        //continue button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { pageState.value = 3 }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDate(pageState: MutableState<Int>) {
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date {},
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 2 }
                .padding(horizontal = 16.dp),
            tint = Color(0xff333333)
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Select Date",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0,0)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            enabled = false,
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { calendarState.show() },
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { pageState.value = 4 }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
    //Spacer(modifier = Modifier.height(70.dp))


}

@Composable
fun WhereDone(pageState: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 3 }
                .padding(horizontal = 16.dp),
            tint = Color(0xff333333)
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Where do you need this done?",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Prefer",
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Radio button group
        val radioOptions = listOf("In Person", "Online")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }

        //continue button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { pageState.value = 5 }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescribeTask(pageState: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 4 }
                .padding(horizontal = 16.dp),
            tint = Color(0xff333333)
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Title1
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Summarize the key detail",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        //Title2
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Help Taskers understand what needs doing",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        ElevatedButton(
            onClick = { /* Do something! */ },
            modifier = Modifier
                .padding(16.dp)
                .height(110.dp)
                .width(160.dp)
        ) {
            androidx.compose.material.Icon(
                Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(50.dp)
            )
            //Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        }


        //continue button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { pageState.value = 6 }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAddress(pageState: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 5 }
                .padding(horizontal = 16.dp),
            tint = Color(0xff333333)
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Select Address",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("Ultimo NSW 2023 Australia", TextRange(0, 50)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { pageState.value = 7 }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
    //Spacer(modifier = Modifier.height(70.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestBudget(pageState: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 6 }
                .padding(horizontal = 16.dp),
            tint = Color(0xff333333)
        )

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Estimate First, You Will Finlize this Later",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 35.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 18)))
        }
        TextField(

            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Enter the Price in AU$") },
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        val radioOptions = listOf("One-time payment", "Pay by the day","Pay by the hour")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }

}


