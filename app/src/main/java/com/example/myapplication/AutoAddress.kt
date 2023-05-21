package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApiRequest
import com.google.maps.model.GeocodingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun getAutoSuggestList(address: String): MutableList<String> {
    val context = GeoApiContext.Builder()
        .apiKey("AIzaSyDLcuX3F378KbA6HMhKEKvBjCn5ruJSWYQ")
        .build()

    val request = GeocodingApiRequest(context)
        .address(address)

    val results: Array<GeocodingResult> = request.await()

    return results.map { result ->
        result.formattedAddress
    }.toMutableList()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTest(address: MutableState<String>){
    Column(

    ) {

        var autoSuggestListState by remember { mutableStateOf(emptyList<String>()) }
        val addressTextState = remember { mutableStateOf(address.value) }

        LaunchedEffect(addressTextState.value) {
            if (addressTextState.value!="") {
                val autoSuggestList = withContext(Dispatchers.IO) {
                    getAutoSuggestList(addressTextState.value)
                }
                autoSuggestListState = autoSuggestList
            } else {
                autoSuggestListState = emptyList()
            }
        }

        androidx.compose.material3.TextField(
            value = addressTextState.value,
            onValueChange = { newValue ->
                addressTextState.value = newValue
                address.value = newValue
                /*if (newValue.isEmpty()) {
                    autoSuggestListState = emptyList()
                    //isDropdownExpanded = True
                } else {
                    autoSuggestListState = getAutoSuggestList(newValue)
                }*/
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
        )
        LazyColumn {
            items(autoSuggestListState.size) { index ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            addressTextState.value = ""
                            addressTextState.value = autoSuggestListState[index]
                            address.value = autoSuggestListState[index]
                        }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = autoSuggestListState[index],
                        //modifier = Modifier.clickable {
                        //    addressTextState.value = ""
                        //    addressTextState.value = autoSuggestListState[index]
                        //    address.value = autoSuggestListState[index]
                        //}
                    )
                    Spacer(modifier = Modifier.height(10.dp) )
                    Divider()
                }

            }
        }


    }
}
