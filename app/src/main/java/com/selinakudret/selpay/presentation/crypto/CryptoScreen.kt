package com.selinakudret.selpay.presentation.crypto

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.ui.theme.*

data class CoinData(
    val symbol: String,
    val name: String,
    val price: Float,
    val change: Float,
    val holding: Float,
    val color: Color,
    val sparkline: List<Float>
)

@Composable
fun CryptoScreen(navController: NavController) {
    val currency by LocalCurrency.current

    val coins = remember {
        listOf(
            CoinData("BTC", "Bitcoin", 67342f, 2.4f, 0.0241f, BitcoinOrange,
                listOf(60f, 65f, 62f, 68f, 67f, 70f, 67f, 72f, 75f, 73f)),
            CoinData("ETH", "Ethereum", 3245f, -1.2f, 0.842f, EthereumBlue,
                listOf(50f, 45f, 48f, 42f, 38f, 40f, 35f, 38f, 32f, 30f)),
            CoinData("BNB", "BNB", 612f, 4.8f, 2.5f, BinanceYellow,
                listOf(30f, 35f, 38f, 42f, 45f, 50f, 48f, 52f, 55f, 60f)),
            CoinData("USDT", "Tether", 1f, 0.01f, 1250f, TetherGreen,
                listOf(50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f)),
            CoinData("SOL", "Solana", 168.50f, 6.2f, 5.8f, AccentPurple,
                listOf(20f, 25f, 30f, 35f, 40f, 45f, 50f, 55f, 60f, 68f)),
            CoinData("XRP", "Ripple", 0.62f, -2.1f, 850f, AccentBlue,
                listOf(40f, 42f, 38f, 35f, 33f, 30f, 28f, 32f, 30f, 28f))
        )
    }

    val totalValue = 8420f

    val infinite = rememberInfiniteTransition(label = "live")
    val livePulse by infinite.animateFloat(
        initialValue = 0.7f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "p"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        // Live ticker tape
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardDark)
                .padding(vertical = 8.dp)
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(coins.size) { i ->
                    TickerItem(coins[i], currency.code)
                }
                items(coins.size) { i ->
                    TickerItem(coins[i], currency.code)
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Crypto", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 26.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp).clip(CircleShape)
                                .background(GreenPositive.copy(alpha = livePulse))
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("Live • 6 markets", color = GreenPositive, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Box(
                    modifier = Modifier
                        .size(44.dp).clip(CircleShape)
                        .background(Brush.linearGradient(listOf(BitcoinOrange, AccentRose)))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) { Text("+", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black) }
            }

            Spacer(Modifier.height(24.dp))

            // Portfolio hero card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF1A1F35), Color(0xFF2D1B4E))))
                    .border(1.dp, AccentPurple.copy(alpha = 0.3f), RoundedCornerShape(28.dp))
                    .padding(22.dp)
            ) {
                Column {
                    Text("Portfolio Value", color = TextSecondary, fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${currency.code} ${"%,.2f".format(totalValue)}",
                        color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 34.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(GreenPositive.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text("▴ ${currency.code} 248.20 (3.0%)", color = GreenPositive, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(8.dp))
                        Text("24h", color = TextSecondary, fontSize = 11.sp)
                    }
                    Spacer(Modifier.height(20.dp))
                    PortfolioMiniChart()
                }
            }

            Spacer(Modifier.height(22.dp))

            // Connect Binance
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(BinanceYellow.copy(alpha = 0.2f), CardDark)
                        )
                    )
                    .border(1.dp, BinanceYellow.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .clickable { navController.navigate("binance_connect") }
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp).clip(RoundedCornerShape(12.dp))
                            .background(BinanceYellow),
                        contentAlignment = Alignment.Center
                    ) { Text("B", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 22.sp) }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Connect Binance", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Sync portfolio in real-time", color = TextSecondary, fontSize = 11.sp)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(BinanceYellow)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) { Text("Connect", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 11.sp) }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Heat map
            Text("Market Heat Map", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            HeatMap(coins)

            Spacer(Modifier.height(24.dp))

            // Top movers
            Text("Top Movers", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(coins.sortedByDescending { it.change.coerceAtLeast(0f) }.take(4).size) { i ->
                    val coin = coins.sortedByDescending { kotlin.math.abs(it.change) }[i]
                    TopMoverCard(coin, currency.code)
                }
            }

            Spacer(Modifier.height(24.dp))

            // My holdings
            Text("My Holdings", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))

            coins.forEachIndexed { i, coin ->
                HoldingRow(coin, currency.code)
                if (i < coins.size - 1) Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun TickerItem(coin: CoinData, currency: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(coin.symbol, color = coin.color, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Spacer(Modifier.width(4.dp))
        Text("$currency ${"%,.2f".format(coin.price)}", color = TextPrimary, fontSize = 11.sp)
        Spacer(Modifier.width(4.dp))
        Text(
            "${if (coin.change > 0) "▴" else "▾"} ${"%.1f".format(kotlin.math.abs(coin.change))}%",
            color = if (coin.change > 0) GreenPositive else AccentRose,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun PortfolioMiniChart() {
    val data = listOf(70f, 65f, 72f, 80f, 75f, 82f, 78f, 85f, 88f, 95f, 92f, 98f)

    Canvas(
        modifier = Modifier.fillMaxWidth().height(60.dp)
    ) {
        val w = size.width
        val h = size.height
        val maxV = data.max()
        val minV = data.min()
        val range = maxV - minV
        val stepX = w / (data.size - 1)

        val path = Path()
        val fillPath = Path()
        data.forEachIndexed { i, v ->
            val x = i * stepX
            val y = h - ((v - minV) / range) * h
            if (i == 0) {
                path.moveTo(x, y)
                fillPath.moveTo(x, h)
                fillPath.lineTo(x, y)
            } else {
                path.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }
        fillPath.lineTo(w, h)
        fillPath.close()

        drawPath(
            fillPath,
            brush = Brush.verticalGradient(listOf(GreenPositive.copy(alpha = 0.3f), Color.Transparent))
        )
        drawPath(
            path,
            brush = Brush.horizontalGradient(listOf(GreenPositive, AccentTeal)),
            style = Stroke(width = 3f)
        )
    }
}

@Composable
private fun HeatMap(coins: List<CoinData>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            HeatTile(coins[0], modifier = Modifier.weight(2f).height(100.dp))
            HeatTile(coins[1], modifier = Modifier.weight(1f).height(100.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            HeatTile(coins[2], modifier = Modifier.weight(1f).height(80.dp))
            HeatTile(coins[3], modifier = Modifier.weight(1f).height(80.dp))
            HeatTile(coins[4], modifier = Modifier.weight(1f).height(80.dp))
        }
    }
}

@Composable
private fun HeatTile(coin: CoinData, modifier: Modifier = Modifier) {
    val bg = when {
        coin.change > 3f -> GreenPositive.copy(alpha = 0.7f)
        coin.change > 0f -> GreenPositive.copy(alpha = 0.4f)
        coin.change > -2f -> AccentRose.copy(alpha = 0.4f)
        else -> AccentRose.copy(alpha = 0.7f)
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .padding(12.dp)
    ) {
        Column {
            Text(coin.symbol, color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                "${if (coin.change > 0) "+" else ""}${"%.2f".format(coin.change)}%",
                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun TopMoverCard(coin: CoinData, currency: String) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(1.dp, coin.color.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(32.dp).clip(CircleShape)
                    .background(coin.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(coin.symbol.first().toString(), color = coin.color, fontWeight = FontWeight.Black, fontSize = 14.sp)
            }
            Spacer(Modifier.height(10.dp))
            Text(coin.symbol, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text("$currency ${"%,.2f".format(coin.price)}", color = TextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(8.dp))

            // Mini sparkline
            Canvas(modifier = Modifier.fillMaxWidth().height(24.dp)) {
                val maxV = coin.sparkline.max()
                val minV = coin.sparkline.min()
                val range = (maxV - minV).coerceAtLeast(1f)
                val stepX = size.width / (coin.sparkline.size - 1)
                val path = Path()
                coin.sparkline.forEachIndexed { i, v ->
                    val x = i * stepX
                    val y = size.height - ((v - minV) / range) * size.height
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(
                    path,
                    color = if (coin.change > 0) GreenPositive else AccentRose,
                    style = Stroke(width = 2f)
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                "${if (coin.change > 0) "▴" else "▾"} ${"%.1f".format(kotlin.math.abs(coin.change))}%",
                color = if (coin.change > 0) GreenPositive else AccentRose,
                fontWeight = FontWeight.Black, fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun HoldingRow(coin: CoinData, currency: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .clickable { }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp).clip(CircleShape)
                    .background(coin.color),
                contentAlignment = Alignment.Center
            ) { Text(coin.symbol.first().toString(), color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp) }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(coin.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("${"%.4f".format(coin.holding)} ${coin.symbol}", color = TextSecondary, fontSize = 11.sp)
            }

            // Sparkline
            Canvas(modifier = Modifier.width(60.dp).height(28.dp)) {
                val maxV = coin.sparkline.max()
                val minV = coin.sparkline.min()
                val range = (maxV - minV).coerceAtLeast(1f)
                val stepX = size.width / (coin.sparkline.size - 1)
                val path = Path()
                coin.sparkline.forEachIndexed { i, v ->
                    val x = i * stepX
                    val y = size.height - ((v - minV) / range) * size.height
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(
                    path,
                    color = if (coin.change > 0) GreenPositive else AccentRose,
                    style = Stroke(width = 2f)
                )
            }

            Spacer(Modifier.width(12.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "$currency ${"%,.2f".format(coin.holding * coin.price)}",
                    color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 14.sp
                )
                Text(
                    "${if (coin.change > 0) "▴" else "▾"} ${"%.1f".format(kotlin.math.abs(coin.change))}%",
                    color = if (coin.change > 0) GreenPositive else AccentRose,
                    fontSize = 11.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}