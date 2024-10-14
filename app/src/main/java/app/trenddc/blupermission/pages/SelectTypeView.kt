package app.trenddc.blupermission.pages

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.trenddc.blupermission.R
import app.trenddc.blupermission.base.NavigationConstants

@Composable
fun SelectTypeView(paddingValues: PaddingValues, navHostController: NavHostController) {
    var selectType by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            painter = painterResource(R.drawable.ksla),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Ksla"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "من فضلك قم بالاختيار",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    selectType = NavigationConstants.STUDENT_PAGE
                },
                modifier = Modifier.border(
                    width = 4.dp,
                    color = if (selectType == NavigationConstants.STUDENT_PAGE)
                        colorResource(R.color.purple_200) else Color.DarkGray,
                    shape = RoundedCornerShape(20.dp)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (selectType == NavigationConstants.STUDENT_PAGE)
                        colorResource(R.color.purple_200) else Color.DarkGray
                )

            ) {
                textWihIcon(name = "طالب", selectType == NavigationConstants.STUDENT_PAGE)
            }


            Button(
                onClick = {
                    selectType = NavigationConstants.TEACHER_PAGE
                },
                modifier = Modifier.border(
                    width = 4.dp,
                    color = if (selectType == NavigationConstants.TEACHER_PAGE)
                        colorResource(R.color.purple_200) else Color.DarkGray,
                    shape = RoundedCornerShape(20.dp)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor =
                    if (selectType == NavigationConstants.TEACHER_PAGE)
                        colorResource(R.color.purple_200) else Color.DarkGray
                )

            ) {
                textWihIcon(name = "دكتور", selectType == NavigationConstants.TEACHER_PAGE)
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        if (selectType.isNotEmpty())
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        navHostController.navigate(selectType)
                    }, colors = ButtonColors(
                        contentColor = colorResource(R.color.purple_200),
                        containerColor = colorResource(R.color.purple_200),
                        disabledContentColor = colorResource(R.color.purple_200),
                        disabledContainerColor = colorResource(R.color.purple_200)
                    )
                ) {
                    Text("تاكيد", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(start = 60.dp, end = 60.dp))
                }
            }
    }
}

@Composable
fun textWihIcon(name: String, checked: Boolean) {
    Box(
        modifier = Modifier.width(60.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = if (checked)
                colorResource(R.color.purple_200) else Color.DarkGray
        )
    }
}