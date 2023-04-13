package com.example.myapplication

import android.app.ActivityManager.TaskDescription
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate

@Preview
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostTaskPage() {
    /*
    * pageState:int represents which page should be shown
    * 1: SimplyDescribeTask
    * 2: SelectDate
    * 3: WhenDone
    * 4: DescribeTask
    * 5: Select Address
    * 6: Job require
    * 7: Suggest Budget
    * 8: TaskDetail
    * */
    val pageState = remember { mutableStateOf(1) }
    val taskTopic = remember { mutableStateOf("") }
    val taskDescription = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val money = remember { mutableStateOf("") }
    val require = remember { mutableStateOf("") }
    val startTime = remember { mutableStateOf("") }
    val endTime = remember { mutableStateOf("") }

    if (pageState.value == 1) {
        SimplyDescribeTask(pageState,taskTopic)
    } else if(pageState.value == 2){
        SelectRepeatDate(pageState,date,startTime,endTime)
    } else if (pageState.value == 3) {
        DescribeTask(pageState,taskDescription)
    } else if (pageState.value == 4) {
        SelectAddress(pageState,address)
    } else if (pageState.value == 5) {
        JobRequires(pageState,require)
    } else if (pageState.value == 6) {
        SuggestBudget(pageState,money)
    } else if (pageState.value == 7) {
        TaskDetail(pageState,taskTopic,date,taskDescription,address,require,money,startTime,endTime)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplyDescribeTask(pageState: MutableState<Int>, taskTopic:MutableState<String>) {
    MaterialTheme(colorScheme = LightColorScheme) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(){
                androidx.compose.material.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Icon",
                    modifier = Modifier
                        .clickable {/* */ }
                        .padding(horizontal = 16.dp)
                        .size(30.dp),
                    tint = Color(0xff333333)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Start with a title",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
            }
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
                mutableStateOf(TextFieldValue(taskTopic.value, TextRange(0,0)))
            }
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
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
                    colors = ButtonDefaults.buttonColors(buttonColor),
                    onClick = {
                        pageState.value = 2
                        taskTopic.value = text.text
                    }
                ) {
                    Text("Continue", fontSize = 20.sp)
                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRepeatDate(
    pageState: MutableState<Int>,
    startDate:MutableState<String>,
    startTime:MutableState<String>,
    endTime:MutableState<String>
) {
    val startCalendarState = rememberSheetState()
    val startClockState = rememberSheetState()
    val endClockState = rememberSheetState()

    CalendarDialog(
        state = startCalendarState,
        selection = CalendarSelection.Date {
            startDate.value = it.toString()
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )
    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes{
            hours, minutes ->  startTime.value = "$hours:$minutes"
        }
    )
    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes{
                hours, minutes ->  endTime.value = "$hours:$minutes"
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(){
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 1 }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Choose a time",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Tell the job seeker your preferred time",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .clickable { startCalendarState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Date:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = startDate.value,
                    fontSize = 20.sp,
                    color = buttonColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .clickable { startClockState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Start Time:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = startTime.value,
                    fontSize = 20.sp,
                    color = buttonColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .clickable { endClockState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "End Time:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = endTime.value,
                    fontSize = 20.sp,
                    color = buttonColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

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
                colors = ButtonDefaults.buttonColors(buttonColor),
                onClick = { pageState.value = 3 }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
}
/*
@Composable
fun WhenDone(pageState: MutableState<Int>,option:MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(){
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 1 }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Decide on When",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

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
        val radioOptions = listOf("On date", "Before date", "Repeated tasks at regular date")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
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
                        onClick = null,
                        colors = RadioButtonDefaults.colors(buttonColor)
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
                onClick = {
                    if(selectedOption!=radioOptions[2]){
                        pageState.value = 3
                        option.value = "one day"
                    }
                    else{
                        pageState.value = 9
                        option.value = "repeat day"
                    }
                          },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }

    }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescribeTask(
    pageState: MutableState<Int>,
    taskDescription:MutableState<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(background)
    ) {
        //TopBar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Row(){
                androidx.compose.material.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Icon",
                    modifier = Modifier
                        .clickable {
                            pageState.value = 2
                        }
                        .padding(horizontal = 16.dp)
                        .size(30.dp),
                    tint = Color(0xff333333)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Task Description",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Summarize the detail of the task",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 40.sp,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            //Title2
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Help the tasker understand what you need to do",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 40.sp,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue(taskDescription.value))
            }
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "You can upload photos(Optional)",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 40.sp,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            ElevatedButton(
                onClick = { /* Do something! */ },
                modifier = Modifier
                    .padding(16.dp)
                    .height(110.dp)
                    .width(160.dp),
                colors = ButtonDefaults.buttonColors(textFieldColor)
            ) {
                androidx.compose.material.Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(50.dp)
                )
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
                    onClick = {
                        pageState.value = 4
                        taskDescription.value = text.text
                    },
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text("Continue", fontSize = 20.sp)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAddress(pageState: MutableState<Int>,address:MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(){
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 3 }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Work address",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Where does the job seeker work",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(address.value, TextRange(0,0)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
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
                onClick = {
                    pageState.value = 5
                    address.value = text.text
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobRequires(pageState: MutableState<Int>,requires:MutableState<String>) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(){
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 4 }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Work Requires",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Please indicate the certificates that the job seeker must have",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(requires.value, TextRange(0,0)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
        )
        Text(
            text = "You may want to say:",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 16.dp)
            .clickable {
                clipboardManager.setText(
                    AnnotatedString(
                        text = "Certificate II in Cleaning Operations"
                    )
                )
            },
            colors = CardDefaults.cardColors(textFieldColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Certificate II in Cleaning Operations",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                AnnotatedString(
                    text = "Certificate II in Cleaning Operations",
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 16.dp)
            .clickable {
                clipboardManager.setText(
                    AnnotatedString(
                        text = "Occupational Health and Safety Certification"
                    )
                )
            },
            colors = CardDefaults.cardColors(textFieldColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Occupational Health and Safety Certification",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 16.dp)
            .clickable {
                clipboardManager.setText(
                    AnnotatedString(
                        text = "Carpet Cleaning Technician Certification"
                    )
                )
            },
            colors = CardDefaults.cardColors(textFieldColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Carpet Cleaning Technician Certification",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

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
                onClick = {
                    pageState.value = 6
                    requires.value = text.text
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestBudget(pageState: MutableState<Int>,money:MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(){
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 5 }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Suggest Budget",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Please tell the job seeker your budget",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 40.sp,
            fontSize = 35.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(money.value, TextRange(0, 18)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Enter the Price in AU$") },
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
        )

        Spacer(modifier = Modifier.height(20.dp))

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
                onClick = {
                    pageState.value = 7
                    money.value = text.text
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetail(
    pageState:MutableState<Int>,
    taskTopic:MutableState<String>,
    date:MutableState<String>,
    taskDescription:MutableState<String>,
    address:MutableState<String>,
    require:MutableState<String>,
    money:MutableState<String>,
    startTime:MutableState<String>,
    endTime:MutableState<String>
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            Row(){
                androidx.compose.material.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Icon",
                    modifier = Modifier
                        .clickable { pageState.value = 6 }
                        .padding(horizontal = 16.dp)
                        .size(30.dp),
                    tint = Color(0xff333333)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Task Confirmation",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = taskTopic.value,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 40.sp,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            //Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(70.dp)
                    .fillMaxWidth()
            ){
                Icon(
                    Icons.Outlined.Person,
                    modifier = Modifier.size(70.dp),
                    contentDescription = "User",
                )
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "POSTED BY",fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(text = "Jessica L",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.5.dp,
                color = textFieldColor,
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier
                .padding(horizontal = 30.dp)
                .height(100.dp)
                .fillMaxWidth()
            ){
                Column() {
                    Spacer(modifier = Modifier.height(15.dp))
                    Icon(
                        Icons.Outlined.LocationOn,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Address",
                    )
                }
                Spacer(modifier = Modifier.width(28.dp))
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "LOCATION",fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(//text = "Darlington NSW 2008 Australia",
                        text = address.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 25.dp),
                thickness = 1.5.dp,
                color = textFieldColor,
            )

            Row(modifier = Modifier
                .padding(horizontal = 30.dp)
                .height(90.dp)
                .fillMaxWidth()
            ){
                Column() {
                    Spacer(modifier = Modifier.height(15.dp))
                    Icon(
                        Icons.Outlined.DateRange,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Date",
                    )
                }
                Spacer(modifier = Modifier.width(28.dp))
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "TO BE DONE ON",fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(//text = "Monday April 10",
                        text = date.value+" "+startTime.value+" - "+endTime.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("TASK PRICE",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = money.value + "$",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(21.dp),
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(buttonColor)
                    ) {
                        Text("Change", fontSize = 20.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Task Detail",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = taskDescription.value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Certificate the job seeker need",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = require.value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {/*To do something!*/},
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Post this Task!", fontSize = 20.sp)
            }
        }
    }
}


