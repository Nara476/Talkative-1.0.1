package com.example.talkative.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.talkative.R

@Composable
fun sansButton(
    color: Color = Color(0xFF1490CF),
    textcolor: Color = Color.White,
    modifier: Modifier = Modifier,
    text:String,
    icon:Boolean=true,
    onClick: () -> Unit) {
    // Create Class Button
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = modifier
            .padding(10.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
//        Text(text = text,
//            modifier = Modifier.padding(end = 10.dp),
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.Bold,
//            color = textcolor)
        Icon(
            modifier = Modifier
                .size(25.dp),
            painter = painterResource(R.drawable.send)
            , contentDescription = "send")
    }
}