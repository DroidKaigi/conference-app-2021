package io.github.droidkaigi.feeder.staff

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.fakeStaffs

@Composable
fun StaffItem(staff: Staff, onClickItem: (Staff) -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .semantics { testTag = "StaffItem" }
            .clickable {
                onClickItem(staff)
            }
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { }
            .padding(start = 8.dp, end = 8.dp),
    ) {
        val (image, name) = createRefs()

        Surface(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(parent.start, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .size(56.dp),
            shape = CircleShape
        ) {
            NetworkImage(
                url = staff.image,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                contentDescription = "Staff Icon"
            )
        }
        Text(
            modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end, 16.dp)
                end.linkTo(parent.end, 16.dp)
                top.linkTo(image.top)
                bottom.linkTo(parent.bottom, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = staff.name,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStaffItem() {
    ConferenceAppFeederTheme {
        val staff = fakeStaffs().first()
        CompositionLocalProvider(staffViewModelProviderValue(fakeStaffViewModel())) {
            StaffItem(staff = staff) {
            }
        }
    }
}
