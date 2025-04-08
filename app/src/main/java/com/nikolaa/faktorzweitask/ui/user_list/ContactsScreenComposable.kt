package com.nikolaa.faktorzweitask.ui.user_list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nikolaa.faktorzweitask.R
import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.ui.theme.redColor
import com.nikolaa.faktorzweitask.ui.theme.whiteColor
import com.nikolaa.faktorzweitask.util.CustomSearchBar
import com.nikolaa.faktorzweitask.util.NavigationConstants.CONTACT_DETAILS_NAV_ROUTE
import com.nikolaa.faktorzweitask.util.ShimmerEffectUserListComposable
import com.nikolaa.faktorzweitask.view_model.UsersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreenComposable(
    navController: NavController,
    userViewModel: UsersViewModel = hiltViewModel()
) {

    //region variables
    val isRefreshing by userViewModel.isRefreshing.collectAsState()
    val deletedUserIds = remember { mutableStateListOf<Int>() }
    val userListRemovable = remember { mutableStateListOf<UserModel>() }
    val userList by userViewModel.userList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val isLoading = userList.isEmpty()

    val filteredUsers = userList.filter {
        it != null &&
                it.id !in deletedUserIds &&
                it.name?.contains(searchQuery, ignoreCase = true) == true
    }

    //endregion
    LaunchedEffect(Unit) {
        userViewModel.fetchAllUsers()
    }


    LaunchedEffect(filteredUsers) {
        userListRemovable.clear()
        filteredUsers.filterNotNull().let { userListRemovable.addAll(it) }
    }

    val backgroundColor = MaterialTheme.colorScheme.background
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            userViewModel.fetchAllUsers(true)
            userListRemovable.clear()
            deletedUserIds.clear()
            filteredUsers.filterNotNull().let { userListRemovable.addAll(it) }
        }
    ) {
        if (isLoading) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(backgroundColor),
            ) {
                item {
                    ShimmerEffectUserListComposable(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                    )
                }
                items(7) {
                    ShimmerEffectUserListComposable(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(135.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(backgroundColor),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    CustomSearchBar(
                        query = searchQuery,
                        onQueryChanged = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Find the contact",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "you are looking for!",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                }
                items(userListRemovable, key = { it.id ?: 0 }) {
                    SwipeToDeleteItem(
                        modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                        onClick = {
                            navController.navigate("${CONTACT_DETAILS_NAV_ROUTE}/${it.id}")
                        },
                        item = it,
                        onRemove = {

                            userListRemovable.remove(it)
                            it.id?.let { id ->
                                deletedUserIds.add(id)
                                userViewModel.deleteUser(id)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun SwipeToDeleteItem(
    item: UserModel,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground
    val borderColor = MaterialTheme.colorScheme.onSecondary

    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                coroutineScope.launch {
                    delay(200)
                    onRemove()
                }
                true
            } else {
                false
            }
        }
    )
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val deleteBackgroundColor = animateColorAsState(
                targetValue = when (swipeToDismissBoxState.currentValue) {
                    SwipeToDismissBoxValue.EndToStart -> redColor
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.Settled -> redColor
                },
                label = "Animate bg state"
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(deleteBackgroundColor.value),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    tint = whiteColor,
                    painter = painterResource(R.drawable.ic_trash),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 30.dp)
                )
            }
        },
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .shadow(elevation = 4.dp, shape = CircleShape)
                            .border(width = 2.dp, color = borderColor, shape = CircleShape)
                            .clip(CircleShape)
                            .background(backgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?img={${item.id}}",
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = item.name ?: "",
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "Email Icon",
                            tint = textColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = item.email ?: "",
                            color = textColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}



