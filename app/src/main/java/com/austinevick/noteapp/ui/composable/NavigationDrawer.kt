package com.austinevick.noteapp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.austinevick.noteapp.R
import com.austinevick.noteapp.navigation.Routes
import com.austinevick.noteapp.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navController: NavHostController,
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit
) {


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.padding(end = 50.dp),
                drawerContainerColor = Color.White
            ) {

                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, Green, CircleShape)
                    .size(120.dp)) {
                    Image(
                        painterResource(R.drawable.bookmark_border),
                        null, colorFilter = ColorFilter.tint(Green), modifier = Modifier
                            .size(50.dp)
                    )
                }
                Spacer(Modifier.height(25.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(painterResource(R.drawable.archive), null)
                    },
                    label = {
                        NavItemLabel("Archived Notes")
                    },
                    selected = false,
                    onClick = { navController.navigate(Routes.ArchivedScreen.route) }
                )

                NavigationDrawerItem(
                    icon = {
                        Icon(painterResource(R.drawable.lock), null)
                    },
                    label = {
                        NavItemLabel("Locked Notes")
                    },
                    selected = false,
                    onClick = { navController.navigate(Routes.PasscodeScreen.route) }
                )

            }
        }
    ) { drawerContent() }

}


@Composable
private fun NavItemLabel(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            fontWeight = FontWeight.W600
        )
        Icon(Icons.Default.KeyboardArrowRight, null)

    }
}