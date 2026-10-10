package dz.couture.book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    var tab by remember { mutableIntStateOf(0) }
                    val titles = listOf("Clients", "Commandes", "Mesures")
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Column(Modifier.padding(16.dp)) {
                            Text("CoutureDZ — carnet du tailleur (offline)", style = MaterialTheme.typography.titleMedium)
                            TabRow(selectedTabIndex = tab) {
                                titles.forEachIndexed { i, t -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) }) }
                            }
                            when (tab) {
                                0 -> TabInfo("Clients: nom, telephone, notes. Stockes en Room, 100% hors-ligne.")
                                1 -> TabInfo("Commandes: tissu, modele, deadline, prix et acompte en DZD. Reste = prix - acompte.")
                                else -> TabInfo("Mesures: poitrine, taille, hanches, epaule, manche, longueur (cm).")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabInfo(s: String) { Text(s, Modifier.padding(top = 16.dp)) }
