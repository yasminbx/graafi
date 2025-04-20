package com.example.graafi
// HeartRateViewModel.kt
class HeartRateViewModel : ViewModel() {
    private val _heartRates = mutableStateListOf<Int>()
    val heartRates: List<Int> = _heartRates

    init {

        repeat(20) {
            _heartRates.add((60..120).random())
        }
    }

    fun addHeartRate(value: Int) {
        _heartRates.add(value)
    }
}

@Composable
fun MainScreen(navController: NavController, viewModel: HeartRateViewModel) {
    val heartRates = viewModel.heartRates

    Scaffold(
        topBar = { TopAppBar(title = { Text("Heart Rate Measurements") }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            itemsIndexed(heartRates) { index, rate ->
                Text(
                    text = "Measurement $index: $rate BPM",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("graph") }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun GraphScreen(viewModel: HeartRateViewModel) {
    val heartRates = viewModel.heartRates

    Scaffold(
        topBar = { TopAppBar(title = { Text("Heart Rate Graph") }) }
    ) { padding ->
        AndroidView(
            factory = { context ->
                val chart = LineChart(context)
                val entries = heartRates.mapIndexed { index, rate ->
                    Entry(index.toFloat(), rate.toFloat())
                }
                val dataSet = LineDataSet(entries, "Heart Rate (BPM)").apply {
                    color = Color.BLUE
                    valueTextColor = Color.BLACK
                    circleRadius = 4f
                }
                chart.data = LineData(dataSet)
                chart.description.text = "Measurement Index"
                chart.invalidate()
                chart
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: HeartRateViewModel = viewModel()

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(navController, viewModel)
                }
                composable("graph") {
                    GraphScreen(viewModel)
                }
            }
        }
    }
}

