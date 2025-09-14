package com.mahnoosh.detail.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mahnoosh.detail.presentation.DetailScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

@Serializable
data class DetailRoute(val uiModel: DetailUiModel)

@Serializable
data class DetailUiModel(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val tvSeries: List<String>,
)

object CustomNavType {
    val detailUiModelNavType = object : NavType<DetailUiModel>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): DetailUiModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): DetailUiModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: DetailUiModel): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: DetailUiModel) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}

fun NavGraphBuilder.detailScreen() {
    composable<DetailRoute>(
        typeMap = mapOf(
            typeOf<DetailUiModel>() to CustomNavType.detailUiModelNavType,
        )
    ) { backStackEntry ->
        val detailRoute = backStackEntry.toRoute<DetailRoute>()
        DetailScreen(uiModel = detailRoute.uiModel)
    }
}

fun NavController.navigateToDetails(uiModel: DetailUiModel) =
    navigate(route = DetailRoute(uiModel = uiModel))