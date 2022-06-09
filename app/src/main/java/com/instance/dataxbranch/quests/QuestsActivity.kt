package com.instance.dataxbranch.quests


/*
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class QuestsActivity : AppCompatActivity() {
    private val viewModel: QuestsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataOrException = viewModel.data
            QuestsActivity(dataOrException)
        }
    }

    @Composable
    fun QuestsActivity(dataOrException: DataOrException<List<Quest>, Exception>) {
        val quests = dataOrException.data
        quests?.let {
            LazyColumn {
                items(
                    items = quests
                ) { quest ->
                    QuestCard(quest = quest)
                }
            }
        }

        val e = dataOrException.e
        e?.let {
            Text(
                text = e.message!!,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressBar(
                isDisplayed = viewModel.loading.value
            )
        }
    }
}*/