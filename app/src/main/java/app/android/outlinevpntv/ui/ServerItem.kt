package app.android.outlinevpntv.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.android.outlinevpntv.viewmodel.ServerItemViewModel
import app.android.outlinevpntv.R

import coil.compose.AsyncImage

@Composable
fun ServerItem(
    serverName: String,
    serverHost: String,
    onForwardIconClick: () -> Unit,
) {
    val viewModel: ServerItemViewModel = viewModel(factory = ServerItemViewModel.Factory)

    val serverIconState by viewModel.serverIconState.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(serverHost) { viewModel.serverHost(serverHost) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFFEEEEEE))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(true),
                onClick = onForwardIconClick
            )
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            if (serverIconState != null) {
                AsyncImage(
                    model = serverIconState,
                    contentDescription = "Server icon",
                    modifier = Modifier.size(36.dp).clip(CircleShape),
                    contentScale = FixedScale(3f),
                    placeholder = painterResource(id = R.drawable.flag),
                    error = painterResource(id = R.drawable.flag)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.flag),
                    contentDescription = "Server icon placeholder",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = serverName.ifEmpty { context.getString(R.string.default_server_name) },
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = serverHost.ifEmpty { context.getString(R.string.default_host_name) },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { onForwardIconClick() }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun ServerItemPreview() {
    ServerItem(
        serverName = "Server",
        serverHost = "0.0.0.0",
        onForwardIconClick = {}
    )
}
