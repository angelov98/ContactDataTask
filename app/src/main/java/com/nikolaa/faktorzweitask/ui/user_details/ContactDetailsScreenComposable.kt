package com.nikolaa.faktorzweitask.ui.user_details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.nikolaa.faktorzweitask.R
import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.util.ShimmerEffectUserDetailsComposable
import com.nikolaa.faktorzweitask.view_model.UsersViewModel

@Composable
fun ContactDetailsScreenComposable(
    navController: NavController,
    userId: Int,
    viewModel: UsersViewModel = hiltViewModel()
) {

    LaunchedEffect(userId) {
        viewModel.fetchUserById(userId)
    }

    val user by viewModel.userDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Contact details",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

            }
            if (!isLoading && user != null) {
                item {
                    ImageAndNameComposable(user)
                }
                item {
                    ContactInfoComposable(user)
                }
            } else {
                item {
                    ShimmerEffectUserDetailsComposable(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
                item {
                    ShimmerEffectUserDetailsComposable(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactInfoComposable(userModel: UserModel?) {
    val context = LocalContext.current
    val textColor = MaterialTheme.colorScheme.onBackground
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
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
                    text = userModel?.email ?: stringResource(R.string.email_unknown),
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone_number),
                    contentDescription = "Email Icon",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = userModel?.phone ?: stringResource(R.string.phone_number_unknown),
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Email Icon",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = listOfNotNull(
                        userModel?.address?.street,
                        userModel?.address?.suite,
                        userModel?.address?.city,
                        userModel?.address?.zipcode
                    ).joinToString(", "),
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_company_work),
                    contentDescription = "Email Icon",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = listOfNotNull(
                        userModel?.company?.name,
                        userModel?.company?.catchPhrase
                    ).joinToString("\n"),
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                modifier = Modifier
                    .clickable {
                        val rawWebsite = userModel?.website
                        val websiteUrl = rawWebsite?.let {
                            if (!it.startsWith("http")) "http://$it" else it
                        }
                        websiteUrl?.let {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    }
                    .padding(4.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_website),
                    contentDescription = "Email Icon",
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = userModel?.website ?: "",
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
fun ImageAndNameComposable(userModel: UserModel?) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground
    val borderColor = MaterialTheme.colorScheme.onSecondary
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userModel?.name ?: stringResource(R.string.name_unknown),
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = userModel?.username ?: stringResource(R.string.username_unknown),
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .shadow(elevation = 4.dp, shape = CircleShape)
                    .border(width = 2.dp, color = borderColor, shape = CircleShape)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = stringResource(R.string.image_loader_url, userModel?.id ?: 0),
                    contentDescription = "Flag",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(100.dp)
                        )
                    }
                )
            }
        }
    }
}


