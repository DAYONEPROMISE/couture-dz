package dz.couture.book

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import dz.couture.book.util.Dzd

private data class DemoOrder(val fabric: String, val model: String, val price: Long, val deposit: Long)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { CoutureDzApp() }
    }
}

@Composable
fun CoutureDzApp() {
    val ctx = LocalContext.current
    val dark = isSystemInDarkTheme()
    val scheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> if (dark) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        dark -> darkColorScheme()
        else -> lightColorScheme()
    }
    MaterialTheme(colorScheme = scheme) {
        var dest by remember { mutableIntStateOf(0) }
        var query by remember { mutableStateOf("") }
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val wide = maxWidth >= 840.dp
            if (wide) {
                Row(Modifier.fillMaxSize()) {
                    NavRail(dest, onPick = { dest = it })
                    AppContent(dest, query, onQuery = { query = it }, wide = true, modifier = Modifier.weight(1f))
                }
            } else {
                Scaffold(
                    topBar = { DzTopBar() },
                    bottomBar = { DzNavBar(dest, onPick = { dest = it }) }
                ) { pad ->
                    AppContent(dest, query, onQuery = { query = it }, wide = false, modifier = Modifier.padding(pad).consumeWindowInsets(pad))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DzTopBar() {
    val scroll = TopAppBarDefaults.pinnedScrollBehavior()
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        scrollBehavior = scroll,
        modifier = Modifier.nestedScroll(scroll.nestedScrollConnection)
    )
}

@Composable
private fun DzNavBar(dest: Int, onPick: (Int) -> Unit) {
    NavigationBar {
        NavItems.forEachIndexed { i, n ->
            NavigationBarItem(
                selected = dest == i,
                onClick = { onPick(i) },
                icon = { Icon(n.icon, contentDescription = null) },
                label = { Text(n.labelRes.let { stringResource(it) }) }
            )
        }
    }
}

@Composable
private fun NavRail(dest: Int, onPick: (Int) -> Unit) {
    NavigationRail {
        NavItems.forEachIndexed { i, n ->
            NavigationRailItem(
                selected = dest == i,
                onClick = { onPick(i) },
                icon = { Icon(n.icon, contentDescription = null) },
                label = { Text(stringResource(n.labelRes)) }
            )
        }
    }
}

private data class Nav(val labelRes: Int, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val NavItems = listOf(
    Nav(R.string.nav_clients, Icons.Filled.People),
    Nav(R.string.nav_orders, Icons.Filled.ReceiptLong),
    Nav(R.string.nav_measures, Icons.Filled.Straighten),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(dest: Int, query: String, onQuery: (String) -> Unit, wide: Boolean, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        if (!wide) Spacer(Modifier.height(8.dp))
        else Spacer(Modifier.height(16.dp))
        SearchBarDefaults.InputField(
            query = query,
            onQueryChange = onQuery,
            onSearch = {},
            expanded = false,
            onExpandedChange = {},
            placeholder = { Text(stringResource(R.string.search_hint)) },
            modifier = Modifier.fillMaxWidth().widthIn(max = 640.dp)
        )
        Spacer(Modifier.height(12.dp))
        when (dest) {
            0 -> SectionTitle(R.string.nav_clients, R.string.clients_sub)
            1 -> OrdersPane(wide)
            else -> SectionTitle(R.string.nav_measures, R.string.measures_sub)
        }
    }
}

@Composable
private fun SectionTitle(titleRes: Int, subRes: Int) {
    Text(stringResource(titleRes), style = MaterialTheme.typography.headlineSmall, modifier = Modifier.semantics { heading() })
    Spacer(Modifier.height(4.dp))
    Text(stringResource(subRes), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(Modifier.height(12.dp))
    Text(stringResource(R.string.offline_note), style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun OrdersPane(wide: Boolean) {
    val demo = remember {
        listOf(
            DemoOrder("Satin", "Karakou", 45000, 15000),
            DemoOrder("Velours", "Caftan", 60000, 20000),
            DemoOrder("Dentelle", "Robe", 32000, 10000),
        )
    }
    SectionTitle(R.string.nav_orders, R.string.orders_sub)
    if (wide) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            demo.forEach { OrderCard(it, Modifier.weight(1f)) }
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(demo) { OrderCard(it, Modifier.fillMaxWidth()) }
        }
    }
}

@Composable
private fun OrderCard(o: DemoOrder, modifier: Modifier = Modifier) {
    val due = Dzd.balance(o.price, o.deposit)
    Card(modifier, elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("${o.model} · ${o.fabric}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.semantics { heading() })
            Text(
                stringResource(R.string.order_amounts, Dzd.format(o.price), Dzd.format(o.deposit), Dzd.format(due)),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
