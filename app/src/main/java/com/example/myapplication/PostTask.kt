@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.maps.android.compose.*
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.text.SimpleDateFormat
import java.util.*


var dateType = ""

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostTaskPage(navController: NavController) {
    val pageState = remember { mutableStateOf(1) }
    val taskTopic = remember { mutableStateOf("") }
    val taskDescription = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val money = remember { mutableStateOf("") }
    val require = remember { mutableStateOf("") }
    val startTime = remember { mutableStateOf("") }
    val endTime = remember { mutableStateOf("") }
    val repeat = remember { mutableStateOf("") }

    if (pageState.value == 1) {
        SimplyDescribeTask(navController, pageState, taskTopic)
    } else if (pageState.value == 8) {
        SelectTaskType(pageState)
    } else if (pageState.value == 2) {
        SelectRepeatDate(pageState, startDate, startTime, endTime)
    } else if (pageState.value == 9) {
        SpecificPeriod(pageState, startDate, endDate, startTime, endTime)
    } else if (pageState.value == 10) {
        RecurringTask(pageState, startDate, endDate, startTime, endTime, repeat)
    } else if (pageState.value == 3) {
        DescribeTask(pageState, taskDescription)
    } else if (pageState.value == 4) {
        SelectAddress(pageState, address)
    } else if (pageState.value == 5) {
        JobRequires(pageState, require)
    } else if (pageState.value == 6) {
        SuggestBudget(pageState, money)
    } else if (pageState.value == 7) {
        TaskDetail(
            pageState, taskTopic, startDate,
            taskDescription, address, require, money,
            endDate, startTime, endTime, repeat,
            navController
        )
    } else if (pageState.value == 11) {
        SubmitInf(navController)
    }
}

@Composable
fun progressBar(checked: Float, waiting: Float, text: String) {
    if (waiting != 0f) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
                .height(38.dp)
                .background(Color.LightGray, RoundedCornerShape(25.dp))
                .border(0.dp, Color.Transparent)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(checked)
                        .fillMaxHeight()
                        .background(Color.Blue, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Text(
                        text,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(waiting)
                        .height(38.dp),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
                .height(38.dp)
                .background(Color.LightGray, RoundedCornerShape(25.dp))
                .border(0.dp, Color.Transparent)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.Blue, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Text(
                        text,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplyDescribeTask(
    navController: NavController,
    pageState: MutableState<Int>,
    taskTopic: MutableState<String>
) {
    val openDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(38.dp)
            .background(Color.LightGray, RoundedCornerShape(25.dp))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp)
                    .clickable { pageState.value = 1 }
                    .background(Color.Gray, RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "My Posted Task",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp)
                    .clickable { pageState.value = 2 },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "My Assign Task",
                    fontSize = 12.sp,
                    color = androidx.compose.material.MaterialTheme.colors.onSurface
                )
            }
        }
    }

    MaterialTheme(colorScheme = LightColorScheme) {
        Dialog(openDialog)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                androidx.compose.material.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Icon",
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
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
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(2.dp))
            progressBar(checked = 1f, waiting = 5f, text = "1")
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                //text = "Describe what you need done in a few word.",
                text = "Task title",
                style = MaterialTheme.typography.headlineLarge
                //lineHeight = 40.sp,
                //fontSize = 40.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                //text = "Describe what you need done in a few word.",
                text = "In a few words, what do you need?",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue(taskTopic.value, TextRange(0, 0)))
            }
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    //.padding(horizontal = 16.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor),
                    placeholder = { Text("Cleaning my living room") }
                )
            }
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
                        if (text.text.length < 8) {
                            openDialog.value = true
                        } else {
                            openDialog.value = false
                            pageState.value = 8
                            taskTopic.value = text.text
                        }
                    }
                ) {
                    Text("Continue", fontSize = 20.sp)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTaskType(pageState: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
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
        Spacer(modifier = Modifier.height(20.dp))
        progressBar(checked = 2f, waiting = 4f, text = "2")
        Divider()
        Spacer(modifier = Modifier.height(40.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Task type",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Select your preferred task frequency",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    pageState.value = 2
                    dateType = "oneday"
                },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "A specific time",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    pageState.value = 9
                    dateType = "period"
                },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "A specific period",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    pageState.value = 10
                    dateType = "recurring"
                },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "A recurring task",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRepeatDate(
    pageState: MutableState<Int>,
    startDate: MutableState<String>,
    startTime: MutableState<String>,
    endTime: MutableState<String>
) {
    val startCalendarState = rememberSheetState()
    val startClockState = rememberSheetState()
    val endClockState = rememberSheetState()
    val openDialog = remember { mutableStateOf(false) }
    DateDialog(openDialog)
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
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            startTime.value = "$hours:$minutes"
        }
    )
    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            endTime.value = "$hours:$minutes"
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 8 }
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

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(2.dp))
        progressBar(checked = 2f, waiting = 4f, text = "2")
        Spacer(modifier = Modifier.height(40.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Date",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "When do you want to start?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable { startCalendarState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Date:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = startDate.value.ifEmpty { "05/08/2023" },
                    fontSize = 20.sp,
                    color = if (startDate.value.isNotEmpty()) buttonColor else Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable { startClockState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "From:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = startTime.value.ifEmpty { "11:00 AM" },
                    fontSize = 20.sp,
                    color = if (startTime.value.isNotEmpty()) buttonColor else Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable { endClockState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "To:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = endTime.value.ifEmpty { "01:30 PM" },
                    fontSize = 20.sp,
                    color = if (endTime.value.isNotEmpty()) buttonColor else Color.LightGray,
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
                onClick = {
                    if (startDate.value == "" || startTime.value == "" || endTime.value == "") {
                        openDialog.value = true
                    } else {
                        pageState.value = 3
                    }
                }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecificPeriod(
    pageState: MutableState<Int>,
    startDate: MutableState<String>,
    endDate: MutableState<String>,
    startTime: MutableState<String>,
    endTime: MutableState<String>
) {
    val startCalendarState = rememberSheetState()
    val endCalendarState = rememberSheetState()
    val startClockState = rememberSheetState()
    val endClockState = rememberSheetState()
    val openDialog = remember { mutableStateOf(false) }
    DateDialog(openDialog)
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
    CalendarDialog(
        state = endCalendarState,
        selection = CalendarSelection.Date {
            endDate.value = it.toString()
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )
    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            startTime.value = "$hours:$minutes"
        }
    )
    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            endTime.value = "$hours:$minutes"
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 8 }
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
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        progressBar(checked = 2f, waiting = 4f, text = "2")
        Spacer(modifier = Modifier.height(40.dp))
        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Date",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Select the start date and end date for your specific period task",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "From: ",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .clickable { startCalendarState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  startDate:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = startDate.value.ifEmpty { "05/08/2023" },
                        color = if (startDate.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.02f))
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(50.dp)
                    .clickable { startClockState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  startTime:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = startTime.value.ifEmpty { "11:00 AM" },
                        color = if (startTime.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "To: ",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .clickable { endCalendarState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  endDate:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = endDate.value.ifEmpty { "05/10/2023" },
                        color = if (endDate.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.02f))
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(50.dp)
                    .clickable { endClockState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  endTime:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = endTime.value.ifEmpty { "01:30 PM" },
                        color = if (endTime.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
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
                onClick = {
                    if (startDate.value == "" || endDate.value == "") {
                        openDialog.value = true
                    } else {
                        pageState.value = 3
                    }
                }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
}

enum class RepeatFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    CUSTOM
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurringTask(
    pageState: MutableState<Int>,
    startDate: MutableState<String>,
    endDate: MutableState<String>,
    startTime: MutableState<String>,
    endTime: MutableState<String>,
    repeat: MutableState<String>
) {
    val startCalendarState = rememberSheetState()
    val endCalendarState = rememberSheetState()
    val startClockState = rememberSheetState()
    val endClockState = rememberSheetState()
    val openDialog = remember { mutableStateOf(false) }
    DateDialog(openDialog)
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
    CalendarDialog(
        state = endCalendarState,
        selection = CalendarSelection.Date {
            endDate.value = it.toString()
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )
    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            startTime.value = "$hours:$minutes"
        }
    )
    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            endTime.value = "$hours:$minutes"
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable { pageState.value = 8 }
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
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        progressBar(checked = 2f, waiting = 4f, text = "2")
        Spacer(modifier = Modifier.height(40.dp))
        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Date",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Select the start date and end date for your specific period task",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "From: ",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .clickable { startCalendarState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  startDate:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = startDate.value.ifEmpty { "05/08/2023" },
                        color = if (startDate.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.02f))
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(50.dp)
                    .clickable { startClockState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  startTime:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = startTime.value.ifEmpty { "11:00 AM" },
                        color = if (startTime.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "To: ",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .clickable { endCalendarState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  endDate:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = endDate.value.ifEmpty { "05/10/2023" },
                        color = if (endDate.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.02f))
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(50.dp)
                    .clickable { endClockState.show() },
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Text(text = "  endTime:")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = endTime.value.ifEmpty { "01:30 PM" },
                        color = if (endTime.value.isNotEmpty()) buttonColor else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxSize()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Repeat: ",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        val radioOptions = listOf("Daily", "Weekly", "Monthly", "Yearly", "Custom")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        var customFrequencyText by remember { mutableStateOf("") }
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                repeat.value = selectedOption
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val radioColors = RadioButtonDefaults.colors(
                        selectedColor = buttonColor, // 设置选中状态的颜色为红色
                        unselectedColor = Color.Gray // 设置未选中状态的颜色为灰色
                    )
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            //repeat.value = selectedOption
                        },
                        colors = radioColors
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    if (text == "Custom" && selectedOption == "Custom") {
                        TextField(
                            value = customFrequencyText,
                            onValueChange = { customFrequencyText = it },
                            colors = TextFieldDefaults.textFieldColors(
                                if (customFrequencyText.isNotEmpty()) buttonColor else Color.LightGray,
                                containerColor = Color.Transparent
                            ),
                            placeholder = { Text("Input your own words") },
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
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
                onClick = {
                    if (startDate.value == "" || endDate.value == "") {
                        openDialog.value = true
                    } else {
                        pageState.value = 3
                    }
                }
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescribeTask(
    pageState: MutableState<Int>,
    taskDescription: MutableState<String>
) {
    val openDialog = remember { mutableStateOf(false) }
    Dialog(openDialog)
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
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
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
        Divider()
        Spacer(modifier = Modifier.height(2.dp))
        progressBar(checked = 3f, waiting = 3f, text = "3")
        Spacer(modifier = Modifier.height(40.dp))
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                //text = "Summarize the detail of the task",
                text = "Details",
                style = MaterialTheme.typography.headlineLarge,
                //lineHeight = 40.sp,
                //fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            //Title2
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Help the tasker understand what you need to do",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
                //lineHeight = 40.sp,
                //fontSize = 20.sp
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

            /*Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "You can upload photos to help job seekers understand your task(Optional)",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
                //lineHeight = 40.sp,
                //fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /* Do something! */ },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Upload Photos")
            }*/
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
                        if (text.text.length < 8) {
                            openDialog.value = true
                        } else {
                            pageState.value = 4
                            taskDescription.value = text.text
                        }
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
fun SelectAddress(pageState: MutableState<Int>, address: MutableState<String>) {
    val openDialog = remember { mutableStateOf(false) }
    Dialog(openDialog)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)

    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
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

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(2.dp))
        progressBar(checked = 4f, waiting = 2f, text = "4")
        Spacer(modifier = Modifier.height(10.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Location",
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Where do you need this done?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray

        )

        Spacer(modifier = Modifier.height(20.dp))
        MapTest(address)

        var text by remember {
            mutableStateOf(address.value)
        }
        /*var la = remember {
            mutableStateOf(-33.91409339)
        }
        var lo = remember {
            mutableStateOf(151.2058800)
        }

        var s by remember {
            mutableStateOf(false)
        }
        var autoSuggestListState by remember { mutableStateOf(emptyList<String>()) }
        val addressTextState = remember { mutableStateOf("") }
        if (!s) {
            var current = getCurrentLocation(la.value, lo.value)
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor),
                leadingIcon = {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Localized description",
                        modifier = Modifier.clickable {
                            address.value = current
                            text = current
                            s = true
                        }
                    )
                }
            )
        } else {
            var current = getCurrentLocation(la.value, lo.value)
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor),
                leadingIcon = {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Localized description",
                        modifier = Modifier.clickable {
                            address.value = current
                            text = current
                            s = false
                        }
                    )
                }
            )
        }
        MapContent(la, lo)*/

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
                    if (address.value.length < 8) {
                        openDialog.value = true
                    } else {
                        pageState.value = 5
                        //address.value = text
                    }
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }

}

@Composable
fun MapContent(la: MutableState<Double>, lo: MutableState<Double>) {
    val pos = LatLng(-33.91409339, 151.2058800)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 12f)
    }
    val mapState = rememberMarkerState(position = pos)

    Column(
        modifier = Modifier
            .height(400.dp)
            .padding(16.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = true)

        ) {
            Marker(
                state = mapState,
                draggable = true,
                onClick = {
                    la.value = it.position.latitude
                    lo.value = it.position.longitude
                    false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobRequires(pageState: MutableState<Int>, requires: MutableState<String>) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val openDialog = remember { mutableStateOf(false) }
    ClipDialog(openDialog)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
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

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(2.dp))
        progressBar(checked = 5f, waiting = 1f, text = "5")
        Spacer(modifier = Modifier.height(40.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Certificate",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 30.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Please indicate the certificates that the job seeker must have (Optional)",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(requires.value, TextRange(0, 0)))
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    clipboardManager.setText(
                        AnnotatedString(
                            text = "Certificate II in Cleaning Operations"
                        )
                    )
                    openDialog.value = true
                },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    clipboardManager.setText(
                        AnnotatedString(
                            text = "Occupational Health and Safety Certification"
                        )
                    )
                    openDialog.value = true
                },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    clipboardManager.setText(
                        AnnotatedString(
                            text = "Carpet Cleaning Technician Certification"
                        )
                    )
                    openDialog.value = true
                },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
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
fun SuggestBudget(pageState: MutableState<Int>, money: MutableState<String>) {
    val openDialog = remember { mutableStateOf(false) }
    BudgetDialog(openDialog)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
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

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(2.dp))
        progressBar(checked = 6f, waiting = 0f, text = "6")

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Hourly rate",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 35.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "You will pay based on the number of hours",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
            //lineHeight = 40.sp,
            //fontSize = 35.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(money.value, TextRange(0, 0)))
        }
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                //modifier = Modifier
                //.fillMaxWidth()
                //.padding(horizontal = 16.dp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor),
                placeholder = { Text("A$ 50") }
            )
            Column() {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "/hour",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 18.sp
                )
            }
        }
        /*TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Enter the Price in AU$") },
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor),
        )*/

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
                    if (text.text.length < 1) {
                        openDialog.value = true
                    } else {
                        pageState.value = 7
                        money.value = text.text
                    }
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }

}

data class Task(
    val taskTopic: String? = null,
    val date: String? = null,
    val taskDescription: String? = null,
    val address: String? = null,
    val require: String? = null,
    val money: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val status: String = "Open",
    val AssignID: String? = null,
    val UserID: String = user,
    val offerList: List<String>,
    val starRate: Double = 0.0,
    val classification: String = "Cleaning"
)

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetail(
    pageState: MutableState<Int>,
    taskTopic: MutableState<String>,
    startDate: MutableState<String>,
    taskDescription: MutableState<String>,
    address: MutableState<String>,
    require: MutableState<String>,
    money: MutableState<String>,
    endDate: MutableState<String>,
    startTime: MutableState<String>,
    endTime: MutableState<String>,
    repeat: MutableState<String>,
    navController: NavController
) {
    val storage = Firebase.storage
    var storageRef = storage.reference
    val avatarImagesRef = storageRef.child("avatar/"+user+".jpg")
    val avatar =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
        avatar.value = BitmapFactory.decodeByteArray(it,0,it.size)
    }.addOnFailureListener {
        // Handle any errors
    }

    val list = ArrayList<String>()
    var task = Task(
        taskTopic.value,
        startDate.value + "-" + endDate.value,
        taskDescription.value,
        address.value,
        require.value,
        money.value,
        startTime.value,
        endTime.value + " " + repeat,
        "Open",
        "",
        user,
        list
    )
    if (dateType == "oneday") {
        task = Task(
            taskTopic.value, startDate.value,
            taskDescription.value, address.value, require.value,
            money.value, startTime.value, endTime.value,
            "Open", "", user, list
        )
    }
    if (dateType == "period") {
        task = Task(
            taskTopic.value, startDate.value + " to " + endDate.value,
            taskDescription.value, address.value, require.value,
            money.value, startTime.value, endTime.value,
            "Open", "", user, list
        )
    }
    if (dateType == "recurring") {
        task = Task(
            taskTopic.value, startDate.value + " to " + endDate.value,
            taskDescription.value, address.value, require.value,
            money.value, startTime.value, endTime.value + ", " + repeat.value,
            "Open", "", user, list
        )
    }
    val openDialog3 = remember { mutableStateOf(false) }
    val flag2 = remember { mutableStateOf(false) }
    ConfirmationDialog(openDialog = openDialog3, flag2 = flag2)
    LaunchedEffect(flag2.value) {
        if(flag2.value){
            val db = Firebase.firestore
            db.collection("Task").document().set(task)
            //navController.popBackStack()
            pageState.value = 11
        }
        flag2.value = false
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
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
        Divider()
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
                text = "Please confirm your task details",
                color= buttonColor,
                style = MaterialTheme.typography.headlineSmall,
                //lineHeight = 40.sp,
                //fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
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
            ) {
                /*Icon(
                    Icons.Outlined.Person,
                    modifier = Modifier.size(70.dp),
                    contentDescription = "User",
                )*/
                avatar.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.width(25.dp))
                Column() {
                    //Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "POSTED BY", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))

                    var Username by remember { mutableStateOf("") }
                    LaunchedEffect("User") {
                        FireStore.collection("User")
                            .document(user)
                            .addSnapshotListener { snapshot, error ->
                                if (error != null) {
                                    //
                                } else {
                                    if (snapshot != null && snapshot.exists()) {
                                        Username = snapshot.getString("name") ?: ""
                                    }
                                }
                            }
                    }
                    Text(
                        text = Username,
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
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
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
                    Text(text = "LOCATION", fontSize = 13.sp)
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

            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .height(90.dp)
                    .fillMaxWidth()
            ) {
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
                    Text(text = "Date", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    if (dateType == "oneday") {
                        Text(//text = "Monday April 10",
                            text = startDate.value + " " + startTime.value + "-" + endTime.value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    if (dateType == "period") {
                        Text(//text = "Monday April 10",
                            text = startDate.value + " to " + endDate.value + " " + startTime.value + "-" + endTime.value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    if (dateType == "recurring") {
                        Text(//text = "Monday April 10",
                            text = startDate.value + " to " + endDate.value + " " + startTime.value + "-" + endTime.value + ", " + repeat.value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
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
                    Text(
                        "TASK BUDGET",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "A$ " + money.value +"/hour",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Task Detail",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = taskDescription.value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 25.dp),
                thickness = 1.5.dp,
                color = textFieldColor,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Certificate the job seeker need",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = require.value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    openDialog3.value = true
                    //val sdf = SimpleDateFormat("dd-MM-yyyy-hh:mm:ss")
                    //val currentDate = sdf.format(Date())
                    //val db = Firebase.firestore
                    //db.collection("Task").document().set(task)
                    //navController.popBackStack()
                    //pageState.value = 11
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Confirm and Create this task", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun ProgressIndicator(currentPage: Int, totalPages: Int) {
    LinearProgressIndicator(
        progress = currentPage.toFloat() / totalPages.toFloat(),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    )
}


